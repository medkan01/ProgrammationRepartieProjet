package client.controleur;

import java.rmi.RemoteException;
import java.util.UUID;

import commun.interfaceRMI.InterfaceAllumettes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AllumetteControleur {
	@FXML private Button btn_valider;
	@FXML private HBox pane;
	@FXML private Label lbl_tour;
	@FXML private Label lbl_scoreJ1;
	@FXML private Label lbl_scoreJ2;
	@FXML private Button btn_retour;
	
	private int nbAllChoisies = 0;
	private Node tabAllumetteRetirer[] = new Node [2];
	private InterfaceAllumettes interfaceAllumettes;
	private UUID idPartie;
	
	/**
	 * {@code Affiche les allumettes,
	 * le premier joueur à devoir jouer,
	 * lance le premier tour de jeu}
	 */
	public void initialisation(UUID uuid) {
		try {
			this.idPartie = uuid;
			
			afficheAllumettes(interfaceAllumettes.getPartieAllumettes(uuid).getNbAllumettes());
			
			affPremierJoueur(interfaceAllumettes.nomJoueurTour(uuid));
			
			tour();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * {@code Exécute un tour de jeu} 
	 */
	private void tour() {
		try {
			affTourJoueur(interfaceAllumettes.nomJoueurTour(this.idPartie));
			
			if (interfaceAllumettes.getPartieAllumettes(this.idPartie).getTour()%2 == 0) {
				new Thread(() -> {
					try {
						for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
							node.setDisable(true);
						}
						
						Thread.sleep(800);
						//Le serveur choisit un nombre d'allumettes
						try {
							nbAllChoisies = interfaceAllumettes.coupIA(this.idPartie);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
						
						//Choix des allumettes sur l'interface graphique
						tourIA(nbAllChoisies);
						
						//actualisation de l'affichage
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								valider();
							}
						});
						
						for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
							node.setDisable(false);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}).start();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void setInterfaceAllumettes(InterfaceAllumettes interfaceAllumettes) {
		this.interfaceAllumettes = interfaceAllumettes;
	}

	/**
	 * {@code Créer et affiche le nombre d'allumettes spécifié}
	 * @param nbAllumette Le nombre d'allumettes de la partie
	 */
	private void afficheAllumettes(int nbAllumette) {
		
		for (int i=1; i<=nbAllumette; i++) {
			String id = "allumette".concat(String.valueOf(i));
			
			Button button = new Button();
			button.setId(id);
			button.setOnAction(a -> {
				choixAllumette(a);
			});
			button.setMinWidth(5);
			button.setMaxWidth(5);
			button.setPrefWidth(5);

			button.setMinHeight(150);
			button.setMaxHeight(150);
			button.setPrefHeight(150);
			
			pane.getChildren().add(button);
		}
		
		btn_valider.setDisable(true);
	}
	
	
	/**
	 * {@code Affiche une alerte du premier jouer à devoir jouer}
	 * @param nomPJoueur Le nom du premier joueur
	 */
	private void affPremierJoueur(String nomPJoueur) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Premier joueur");
		alert.setContentText("Le premier joueur à jouer est : " + nomPJoueur);
		alert.showAndWait();
	}
	
	
	/**
	 * {@code Sélectionne les nbAlluChoisies premières allumettes disponibles}
	 * @param nbAlluChoisies Le nombre d'allumettes choisies
	 */
	private void tourIA(int nbAlluChoisies) {
		//Liste de toutes les allumettes pas encore selectionnées
		ObservableList<Node> alluVisibles = pane.getChildren().filtered(t->t.isVisible());
		
		//on sélectionne les "nbAllChoisies" premières allumettes
		for (int i=0; i<nbAlluChoisies; i++) {
			tabAllumetteRetirer[i] = alluVisibles.get(i);
		}
	}
	
	
	/**
	 * {@code Affiche le tableau des scores}
	 * @param tabScore Le tableau des scores
	 */
	private void affTabScore(int[] tabScore) {
		//On affiche le tableau des scores actualisé
		lbl_scoreJ1.setText(String.valueOf(tabScore[0]));
		lbl_scoreJ2.setText(String.valueOf(tabScore[1]));
	}

	
	/**
	 * {@code Affiche le nom du joueur qui doit jouer}
	 * @param nomJoueur Le nom du joueur
	 */
	private void affTourJoueur(String nomJoueur) {
		this.lbl_tour.setText(nomJoueur);
	}
	
	/**
	 * {@code Sélectionne ou désélectionne une allumette lorsque l'on clique dessus}
	 * @param event
	 */
	@FXML
	private void choixAllumette(ActionEvent event) {
		//On recupère la Node sur laquelle on a cliqué
		final Node source = (Node) event.getSource();
		
		//Si on a déjà modifié son style, donc != "", alors ça veut dire qu'on a déjà cliqué dessus
		if (source.getStyle() == "") {
			//Comme on ne peut choisir que 2 allumettes max, on ne sélectionne cette allumette que si nbAllChoisies != 2
			//Si on la choisie, on la rajoute dans un tableau, on modifie son style et on incrémente nbAllChoisies
			if (nbAllChoisies != 2) {
				nbAllChoisies += 1;
				if (tabAllumetteRetirer[0] == null)
					tabAllumetteRetirer[0] = source;
				else 
					tabAllumetteRetirer[1] = source;
				
				source.setStyle("-fx-background-color: red;");
			}
		}
		//Si on a déjà cliqué dessus, on la retire du tableau, on remet le style de base et on décrémente nbALLChoisies
		else {
			nbAllChoisies -= 1;
			if (tabAllumetteRetirer[0] != null) {
				if (tabAllumetteRetirer[0].equals(source))
					tabAllumetteRetirer[0] = null;
				else 
					tabAllumetteRetirer[1] = null;
			}
			else 
				tabAllumetteRetirer[1] = null;
			
			source.setStyle("");
		}
		
		//Si aucune allumette n'est sélectionnée, alors le bouton Valider n'est pas actif
		if ( (tabAllumetteRetirer[0] != null) || (tabAllumetteRetirer[1] != null) )
			btn_valider.setDisable(false);
		else
			btn_valider.setDisable(true);
	}
	
	/**
	 * {@code affiche une alerte d'aide}
	 */
	@FXML
	private void aide() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Aide");
		alert.setContentText("Le jeu des allumettes ! \n    Un tas d'allumettes est disposé sur la table, il y en a un nombre impair."
				+ "\n    Chaque joueur prend, tour à tour, 1 ou 2 allumettes dans le tas (vous pouvez essayez de tricher ça ne fonctionnera pas !"
				+ " La partie se termine quand il n'y a plus d'allumettes sur la table. Le gagant est celui qui aura un nombre impair d'allumettes à la fin !"
				+ "\n    Le nombre d'allumettes de départ est aléatoire, tout comme le joueur qui commence. Que le meilleur gagne !"
				+ "\n\nComment jouer ? \n    Mais c'est très simple jammy ! Il suffit de cliquer sur une allumette présente sur la table pour la sélectionner !"
				+ "\n    Pour valider votre sélection il suffit de cliquer sur le bouton \"Valider\". ");
		
		alert.showAndWait();
	}
	
	/**
	 * {@code Appelée lors du clic sur le bouton "btn_valider",
	 * actualise les données serveur en fonction du nombre d'allumettes choisies,
	 * actualise le tableau des scores,
	 * retire les allumettes sélectionnées de l'affichage,
	 * relance un tour de jeu si la partie n'est pas finie}
	 */
	@FXML
	private void valider() {
		
		try {
			//actualisation des données sur le serveur
			interfaceAllumettes.action(this.idPartie, nbAllChoisies);
			
			//Actualisation des scores
			affTabScore(interfaceAllumettes.getPartieAllumettes(this.idPartie).getTabScore());
			
			//On retire les allumettes sélectionnées de l'affichage
			for (int i=0; i<tabAllumetteRetirer.length; i++) {
				if (tabAllumetteRetirer[i] != null) {
					tabAllumetteRetirer[i].setVisible(false);
					tabAllumetteRetirer[i] = null;
				}
			}
			
			if (interfaceAllumettes.getPartieAllumettes(this.idPartie).getNbAllumettes() == 0) {
				finPartie(interfaceAllumettes.nomGagnant(this.idPartie), interfaceAllumettes.scoreGagnant(this.idPartie));
			}
			else {
				//On réinitialise les variables nécessaires à un tour de jeu
				nbAllChoisies = 0;
				
				tour();
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * {@code Ferme la fenêtre de jeu}
	 */
	@FXML
	public void retour() {
		// get a handle to the stage
	    Stage stage = (Stage) btn_retour.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
	
	/**
	 * {@code Affiche une alerte de fin de partie avec le gagnant et son score. Ferme ensuite la fenêtre}
	 * @param nomGagnant Le nom du joueur gagnant
	 * @param scoreGagnant Le score du joueur gagnant
	 */
	private void finPartie(String nomGagnant, int scoreGagnant) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fin de partie");
		alert.setContentText("Fin de la partie ! Le gagnant est " + nomGagnant + " avec un score de " + scoreGagnant );
		
		alert.showAndWait();
		retour();
	}
}

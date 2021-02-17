package controleur;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import modele.implementation.AllumettesImpl;

public class JeuAllumetteControleur implements Initializable {
	@FXML private Button btn_valider;
	
	@FXML private HBox pane;
	@FXML private Label lbl_tour;
	@FXML private Label lbl_scoreJ1;
	@FXML private Label lbl_scoreJ2;
	
	@FXML private Button btn_retour;
	
	@FXML private Button allumette1;
	@FXML private Button allumette2;
	@FXML private Button allumette3;
	@FXML private Button allumette4;
	@FXML private Button allumette5;
	@FXML private Button allumette6;
	@FXML private Button allumette7;
	@FXML private Button allumette8;
	@FXML private Button allumette9;
	@FXML private Button allumette10;
	@FXML private Button allumette11;
	@FXML private Button allumette12;
	@FXML private Button allumette13;
	@FXML private Button allumette14;
	@FXML private Button allumette15;
	@FXML private Button allumette16;
	@FXML private Button allumette17;
	@FXML private Button allumette18;
	@FXML private Button allumette19;
	@FXML private Button allumette20;
	@FXML private Button allumette21;
	
	
	private int nbAllChoisies = 0;
	private Node tabAllumetteRetirer[] = new Node [2];
	private AllumettesImpl allumettesImpl;
	
	private int pJoueur;
	private int nbAllumettes;
	private int[] tabScore = {0,0};
	private int tour;
	
	@Override
	//Fonction appellée lors d'un appel de la page
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			allumettesImpl = new AllumettesImpl();
		}
		catch (RemoteException re) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur d'implémentation");
			alert.setContentText(re.getMessage());
			
			alert.showAndWait();
		}
		
		//Choix du nombre d'allumettes et du premier joueur
		nbAllumettes = allumettesImpl.initialise();
		pJoueur = allumettesImpl.premierCoup();
		tour = pJoueur;
		
		//Récupération de toutes les allumettes du panel
		ObservableList<Node> listeNode = pane.getChildren();
		/*
		 * Comme 21 allumettes sont affichées de base, on en "efface" 21-nbAllumettes
		 * Pour garder une symétrie, on rend invisibles la première et la dernière, on les retire de la liste et on recommence
		 */
		for (int i=1; i<=(21-nbAllumettes)/2; i++) {
			listeNode.get(0).setVisible(false);
			listeNode.get(listeNode.size()-1).setVisible(false);
			listeNode.remove(0);
			listeNode.remove(listeNode.size()-1);
		}
		
		btn_valider.setDisable(true);
		
		if (tour == 0) {
			new Thread(() -> {
				try {
					for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
						node.setDisable(true);
					}
					
					Thread.sleep(1200);
					tourIA();
					
					for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
						node.setDisable(false);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
			
		affTourJoueur();
	}
	
	private void tourIA() {
		Random random = new Random();
		
		//Liste de toutes les allumettes pas encore selectionnées
		ObservableList<Node> alluVisibles = pane.getChildren().filtered(t->t.isVisible());
		
		//Nombre d'allumettes maximum que l'ordinateur pourra choisir 
		int max = Math.min(2, alluVisibles.size());
		
		nbAllChoisies = random.nextInt(max)+1;
		
		//on sélectionne les "nbAllChoisies" premières allumettes
		for (int i=0; i<=nbAllChoisies-1; i++) {
			tabAllumetteRetirer[i] = alluVisibles.get(i);
		}
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				valider();
			}
		});
		
	}
	
	//Fonction qui affiche le joueur qui doit jouer
	private void affTourJoueur() {
		String str = allumettesImpl.choixCoup(tour) == 0 ? "Ordinateur" : "Vous";
		this.lbl_tour.setText(str);
	}
	
	@FXML
	//Fonction appellée lorsqu'un clic de souris est dététcté sur une allumette
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
	
	@FXML
	//Fonction appelée lorsque du clic sur le bouton d'aide
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
	
	//Fonction appelée lors du clic sur le bouton btn_valider, on rend invisibles les allumettes choisies par le joueur,
	// on actualise le tableau de scores et on remet les variables nécessaires à un tour de jeu à leur valeur initiale
	public void valider() {
		for (int i=0; i<tabAllumetteRetirer.length; i++) {
			if (tabAllumetteRetirer[i] != null) {
				nbAllumettes -= 1;
				tabAllumetteRetirer[i].setVisible(false);
				tabAllumetteRetirer[i] = null;
			}
		}
		
		//On affiche le tableau des scores actualisé
		tabScore = allumettesImpl.compteAllumette(tabScore, nbAllChoisies, tour);
		lbl_scoreJ1.setText(String.valueOf(tabScore[0]));
		lbl_scoreJ2.setText(String.valueOf(tabScore[1]));
		
		//On réinitialise les variables nécessaires à un tour de jeu
		nbAllChoisies = 0;
		tour += 1;
		
		if (nbAllumettes == 0)
			finPartie();
		else {
			affTourJoueur();
			btn_valider.setDisable(true);
			
			if (tour%2 == 0) {
				/*
				 * Lorsque c'est le tour de l'ordinateur, on créer un nouveau thread pour pouvoir laisser
				 * le temps au joueur de voir ce que l'ordinateur à joué.
				 * Pendant ce temps les allumettes ne sont pas actives pour que le joueur ne puisse pas les sélectionner.
				 */				
				new Thread(() -> {
					try {
						for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
							node.setDisable(true);
						}
						
						Thread.sleep(1200);
						tourIA();
						
						for (Node node : pane.getChildren().filtered(t->t.isVisible())) {
							node.setDisable(false);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}).start();
			}
		}
	}

	//Lorsque la partie est terminée, on affiche le gagant avec son score
	private void finPartie() {
		btn_valider.setDisable(true);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fin de partie");
		alert.setContentText("Fin de la partie ! Le gagnant est" + 
				(tabScore[0]%2 == 0 ? " vous ":" l'ordinateur ") + "avec un score de " + tabScore[tabScore[0]%2 == 0 ? 1:0] );
		
		alert.showAndWait();
		retour();
	}
	
	@FXML
	private void retour() {
		System.exit(0);
	}
}

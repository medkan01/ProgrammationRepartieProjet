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
	//Fonction appell�e lors d'un appel de la page
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			allumettesImpl = new AllumettesImpl();
		}
		catch (RemoteException re) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur d'impl�mentation");
			alert.setContentText(re.getMessage());
			
			alert.showAndWait();
		}
		
		//Choix du nombre d'allumettes et du premier joueur
		nbAllumettes = allumettesImpl.initialise();
		pJoueur = allumettesImpl.premierCoup();
		tour = pJoueur;
		
		//R�cup�ration de toutes les allumettes du panel
		ObservableList<Node> listeNode = pane.getChildren();
		/*
		 * Comme 21 allumettes sont affich�es de base, on en "efface" 21-nbAllumettes
		 * Pour garder une sym�trie, on rend invisibles la premi�re et la derni�re, on les retire de la liste et on recommence
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
		
		//Liste de toutes les allumettes pas encore selectionn�es
		ObservableList<Node> alluVisibles = pane.getChildren().filtered(t->t.isVisible());
		
		//Nombre d'allumettes maximum que l'ordinateur pourra choisir 
		int max = Math.min(2, alluVisibles.size());
		
		nbAllChoisies = random.nextInt(max)+1;
		
		//on s�lectionne les "nbAllChoisies" premi�res allumettes
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
	//Fonction appell�e lorsqu'un clic de souris est d�t�tct� sur une allumette
	private void choixAllumette(ActionEvent event) {
		//On recup�re la Node sur laquelle on a cliqu�
		final Node source = (Node) event.getSource();
		
		//Si on a d�j� modifi� son style, donc != "", alors �a veut dire qu'on a d�j� cliqu� dessus
		if (source.getStyle() == "") {
			//Comme on ne peut choisir que 2 allumettes max, on ne s�lectionne cette allumette que si nbAllChoisies != 2
			//Si on la choisie, on la rajoute dans un tableau, on modifie son style et on incr�mente nbAllChoisies
			if (nbAllChoisies != 2) {
				nbAllChoisies += 1;
				if (tabAllumetteRetirer[0] == null)
					tabAllumetteRetirer[0] = source;
				else 
					tabAllumetteRetirer[1] = source;
				
				source.setStyle("-fx-background-color: red;");
			}
		}
		//Si on a d�j� cliqu� dessus, on la retire du tableau, on remet le style de base et on d�cr�mente nbALLChoisies
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
		
		//Si aucune allumette n'est s�lectionn�e, alors le bouton Valider n'est pas actif
		if ( (tabAllumetteRetirer[0] != null) || (tabAllumetteRetirer[1] != null) )
			btn_valider.setDisable(false);
		else
			btn_valider.setDisable(true);
	}
	
	@FXML
	//Fonction appel�e lorsque du clic sur le bouton d'aide
	private void aide() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Aide");
		alert.setContentText("Le jeu des allumettes ! \n    Un tas d'allumettes est dispos� sur la table, il y en a un nombre impair."
				+ "\n    Chaque joueur prend, tour � tour, 1 ou 2 allumettes dans le tas (vous pouvez essayez de tricher �a ne fonctionnera pas !"
				+ " La partie se termine quand il n'y a plus d'allumettes sur la table. Le gagant est celui qui aura un nombre impair d'allumettes � la fin !"
				+ "\n    Le nombre d'allumettes de d�part est al�atoire, tout comme le joueur qui commence. Que le meilleur gagne !"
				+ "\n\nComment jouer ? \n    Mais c'est tr�s simple jammy ! Il suffit de cliquer sur une allumette pr�sente sur la table pour la s�lectionner !"
				+ "\n    Pour valider votre s�lection il suffit de cliquer sur le bouton \"Valider\". ");
		
		alert.showAndWait();
	}
	
	//Fonction appel�e lors du clic sur le bouton btn_valider, on rend invisibles les allumettes choisies par le joueur,
	// on actualise le tableau de scores et on remet les variables n�cessaires � un tour de jeu � leur valeur initiale
	public void valider() {
		for (int i=0; i<tabAllumetteRetirer.length; i++) {
			if (tabAllumetteRetirer[i] != null) {
				nbAllumettes -= 1;
				tabAllumetteRetirer[i].setVisible(false);
				tabAllumetteRetirer[i] = null;
			}
		}
		
		//On affiche le tableau des scores actualis�
		tabScore = allumettesImpl.compteAllumette(tabScore, nbAllChoisies, tour);
		lbl_scoreJ1.setText(String.valueOf(tabScore[0]));
		lbl_scoreJ2.setText(String.valueOf(tabScore[1]));
		
		//On r�initialise les variables n�cessaires � un tour de jeu
		nbAllChoisies = 0;
		tour += 1;
		
		if (nbAllumettes == 0)
			finPartie();
		else {
			affTourJoueur();
			btn_valider.setDisable(true);
			
			if (tour%2 == 0) {
				/*
				 * Lorsque c'est le tour de l'ordinateur, on cr�er un nouveau thread pour pouvoir laisser
				 * le temps au joueur de voir ce que l'ordinateur � jou�.
				 * Pendant ce temps les allumettes ne sont pas actives pour que le joueur ne puisse pas les s�lectionner.
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

	//Lorsque la partie est termin�e, on affiche le gagant avec son score
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

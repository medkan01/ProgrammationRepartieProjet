package controleur;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.ResourceBundle;

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
		 * Pour garder une symétrie, on "efface" la première et la dernière, on les retire de la liste et on recommence
		 */
		for (int i=1; i<=(21-nbAllumettes)/2; i++) {
			listeNode.get(0).setVisible(false);
			listeNode.get(listeNode.size()-1).setVisible(false);
			listeNode.remove(0);
			listeNode.remove(listeNode.size()-1);
		}
		
		btn_valider.setDisable(true);
		affTourJoueur();
	}
	
	
	//Fonction qui affiche le joueur qui doit jouer
	private void affTourJoueur() {
		this.lbl_tour.setText(
				String.valueOf(allumettesImpl.choixCoup(tour)+1));
	}
	
	
	@FXML
	//Fonction appellée lorsqu'un clic de souris est dététcté sur une allumette
	private void choixAllumette(ActionEvent event) {
		//On recupère la Node sur laquelle on a cliqué
		final Node source = (Node) event.getSource();
		
		//Si on a déjà modifié son style, donc != "", alors ça veut dire qu'on a déjà cliqué dessus
		if (source.getStyle() == "") {
			//Comme on ne peut choisir que 2 allumettes max, on sélectionne cette allumette que si nbAllChoisies != 2
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
			if (tabAllumetteRetirer[0] != null)
				if (tabAllumetteRetirer[0].equals(source))
					tabAllumetteRetirer[0] = null;
			else 
				tabAllumetteRetirer[1] = null;
			
			source.setStyle("");
		}
		
		if ( (tabAllumetteRetirer[0] != null) || (tabAllumetteRetirer[1] != null) )
			btn_valider.setDisable(false);
		else
			btn_valider.setDisable(true);
	}
	
	
	//Fonction appelée lors du clic sur le bouton btn_valider, on "efface" les allumettes choisies par le joueur
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
		
		//On réinitialise les variables nécessaires au tour
		nbAllChoisies = 0;
		tour += 1;
		
		if (nbAllumettes == 0)
			finPartie();
		else {
			affTourJoueur();
			btn_valider.setDisable(true);
		}
			
	}

	//Lorsque la partie est terminée, on affiche le gagant avec son score
	private void finPartie() {
		btn_valider.setDisable(true);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fin de partie");
		alert.setContentText("Fin de la partie ! Le gagnant est le joueur " + 
				(tabScore[0] > tabScore[1] ? 1:2) + " avec un score de " + Math.max(tabScore[0], tabScore[1]));
		
		alert.showAndWait();
		retour();
	}
	
	@FXML
	private void retour() {
		System.exit(0);
	}
}

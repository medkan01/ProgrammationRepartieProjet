package modele;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import interfaceRMI.InterfacePendu;

public class PenduImpl extends UnicastRemoteObject implements InterfacePendu{
    
    private static final long serialVersionUID = 1L;

    public PenduImpl() throws RemoteException {
        super();
    }

    /*
    * Fonction qui génère un mot aléatoirement parmis ceux disponible dans le dictionnaire.
    */
    public String motAleatoire() throws RemoteException {
        //On récupère toutes les valeurs du dictionnaire.
        Dictionnaire[] dictionnaire = Dictionnaire.values();
        //On créer un entier aléatoire de la taille du dictionnaire.
        int entierAleatoire = randInt(dictionnaire.length);
        //On génère le mot aléatoirement et on le retourne.
        String mot = dictionnaire[entierAleatoire].toString();
        return mot;
    }

    /*
    * Fonction qui renvoie un entier aléatoire entre 0 et {int max}.
    */
    public int randInt(int max) {
        Random rand = new Random();
        int n = rand.nextInt(max);
        return n;
    }

    /*
    * Fonction qui renvoie une chaine en fonction des lettres correctes ou non.
    * En paramètre, on rentre une liste avec les lettre qui sont données par le joueur.
    */
    public String changeMot(String mot, char[] lettres) throws RemoteException {
        //On initialise les variables de travail.
        String motAffichage = "";
        String lettre = "";
        //On décompose le mot en liste de caractère pour faciliter les comparaisons de caractère.
        char[] motDecompose = mot.toCharArray();
        /*
        * On rentre dans la boucle qui va permettre de vérifier, pour chaque lettre du mot cherché, si les
        * lettres données par le joueur et une lettre du mot correspondent.
        */
        for(int i = 0; i < motDecompose.length; i++){
            lettre = "_ "; //Initialisation de la lettre par défaut pour le cas où la lettre ne correspond pas.
            for(int j = 0; j < lettres.length; j++){
                if(motDecompose[i] == lettres[j]) {
                    lettre = lettres[j] + " "; //La lettre correspond donc on lui attribue la bonne valeur.
                }
            }
            motAffichage += lettre; //On concatène le lettre au mot qui va s'afficher.
        }
        return motAffichage; //On retourne le mot qui va s'afficher sur l'écran du joueur.
    }

    public boolean contientLettre(char[] lettres, char lettre){
        for(int i = 0; i < lettres.length; i++){
            if(lettres[i] == lettre){
                return true;
            }
        }
        return false;
    }

    public char[] ajouterLettre(char[] lettres, char lettre){
        char[] test = new char[1];
        for(int i = 0; i < lettres.length; i++){
            if(lettres[i] == test[0]){
                if(!contientLettre(lettres, lettre)){
                    lettres[i] = lettre;
                }
            }
        }
        return lettres;
    }
}

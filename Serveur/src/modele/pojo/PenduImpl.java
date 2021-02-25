package modele.pojo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import modele.interfaceRMI.InterfacePendu;

public class PenduImpl extends UnicastRemoteObject implements InterfacePendu{
    
    private static final long serialVersionUID = 1L;

    public PenduImpl() throws RemoteException {
        super();
    }

    public String motAleatoire() throws RemoteException {
        //On récupère toutes les valeurs du dictionnaire.
        Dictionnaire[] dictionnaire = Dictionnaire.values();
        //On créer un entier aléatoire de la taille du dictionnaire.
        int entierAleatoire = randInt(dictionnaire.length);
        //On génère le mot aléatoirement et on le retourne.
        String mot = dictionnaire[entierAleatoire].toString();
        return mot;
    }

    public int randInt(int max) throws RemoteException {
        Random rand = new Random();
        int n = rand.nextInt(max);
        return n;
    }

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

    public boolean contientChar(char[] tab, char c) throws RemoteException {
        for(int i = 0; i < tab.length; i++){
            if(tab[i] == c){
                return true;
            }
        }
        return false;
    }

    public char[] ajouterChar(char[] tab, char c) throws RemoteException {
        char[] testNull = new char[1];
        for(int i = 0; i < tab.length; i++){
            if(tab[i] == testNull[0]){
                if(!contientChar(tab, c)){
                    tab[i] = c;
                }
            }
        }
        return tab;
    }
}

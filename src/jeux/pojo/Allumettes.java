package jeux.pojo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import jeux.interf.InterfaceAllumettes;

public class Allumettes extends UnicastRemoteObject implements InterfaceAllumettes {

	protected Allumettes() throws RemoteException {
		super();
	}
	
	@Override
	// Fonction renvoyant le nombre d'allumettes de départ. Limite d'allumettes fixée à 21. 
	public int initialise() {
		Random rand = new Random();
        int n = rand.nextInt(21)+2;
        
        return (n % 2)==0 ? n+1 : n;
	}

	@Override
	//Fonction renvoyant le numéro du joueur qui doit jouer en premier.
	public int premierCoup() {
		Random rand = new Random();
        
        return rand.nextInt(2);
	}

	@Override
	//Fonction renvoyant le numéro du joueur qui doit jouer en fonction du tour.
	public int choixCoup(int tour) {
		return (tour % 2)==0 ? 0 : 1;
	}

	@Override
	public int[] compteAllumette(int[] tabScore, int allumettes, int tour) {
		tabScore[choixCoup(tour)] += allumettes;
		
		return tabScore ;
	}
}

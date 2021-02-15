package jeux.interf;

public interface InterfaceAllumettes {
	public int initialise();
	public int premierCoup();
	public int choixCoup(int tour);
	public int[] compteAllumette(int[] tabScore, int allumettes, int tour);
}

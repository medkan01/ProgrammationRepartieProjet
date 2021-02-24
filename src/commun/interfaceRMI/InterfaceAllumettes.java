package commun.interfaceRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

import commun.PartieAllumettes;

public interface InterfaceAllumettes extends Remote {
	
	/**
	 * @return L'id de la partie cr��e
	 * @throws RemoteException
	 */
	public UUID creerPartie() throws RemoteException;
	
	/**
	 * @throws RemoteException
	 */
	public void initialise(UUID uuid) throws RemoteException;
	
	
	/**
	 * @param nbAllChoisies
	 * @throws RemoteException
	 */
	public void action(UUID uuid, int nbAllChoisies) throws RemoteException;
	
	
	/**
	 * @return Le maximum d'allumettes s�lectionnables
	 * @throws RemoteException
	 */
	public int maxAllumettes(UUID uuid) throws RemoteException;
	
	
	/**
	 * @return Une cha�ne du nom du joueur gagnant
	 * @throws RemoteException
	 */
	public String nomGagnant(UUID uuid) throws RemoteException;
	
	
	/**
	 * @return Le score du joueur gagnant
	 * @throws RemoteException
	 */
	public int scoreGagnant(UUID uuid) throws RemoteException;
	
	
	/**
	 * @return Une cha�ne du nom du joueur qui doit jouer pour ce tour
	 * @throws RemoteException
	 */
	public String nomJoueurTour(UUID uuid) throws RemoteException;
	
	
	/**
	 * @return L'objet partie de jeu instanci�
	 * @throws RemoteException
	 */
	public PartieAllumettes getPartieAllumettes(UUID uuid) throws RemoteException;
	
	
	/**
	 * @return Le nombre d'allumettes s�lectionn�es par le serveur
	 * @throws RemoteException
	 */
	public int coupIA(UUID uuid) throws RemoteException;
}
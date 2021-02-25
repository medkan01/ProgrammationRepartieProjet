package modele.pojo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Random;
import modele.interfaceRMI.*;
import java.util.UUID;

public class AllumettesImpl extends UnicastRemoteObject implements InterfaceAllumettes {
	
	private static final long serialVersionUID = 1L;

	// private PartieAllumettes partieAllumettes = new PartieAllumettes();
	private Hashtable<UUID, PartieAllumettes> listeParties = new Hashtable<UUID, PartieAllumettes>();

	public AllumettesImpl() throws RemoteException {
		super();
	}
	
	@Override
	public UUID creerPartie() {
		UUID uuid = UUID.randomUUID();
		
		listeParties.put(uuid, new PartieAllumettes());
		
		return uuid;
	}
	
	@Override
	// Fonction renvoyant le nombre d'allumettes de d�part. Limite d'allumettes fix�e � 21. 
	public void initialise(UUID uuid) {
		Random rand = new Random();
		int n = 0;
        while ( n<3 || (n%2==0) ) {
        	n = rand.nextInt(22);
        }
        
        listeParties.get(uuid).setNbAllumettes(n);
        listeParties.get(uuid).setTabScore(new int[] {0,0});
        listeParties.get(uuid).setTour(rand.nextInt(2));
	}
	
	@Override
	public void action(UUID uuid, int nbAllChoisies) {
		listeParties.get(uuid).setNbAllumettes(listeParties.get(uuid).getNbAllumettes()-nbAllChoisies);
		listeParties.get(uuid).getTabScore()[listeParties.get(uuid).getTour()%2] += nbAllChoisies;
		listeParties.get(uuid).setTour(listeParties.get(uuid).getTour()+1);
	}
	
	@Override
	public int maxAllumettes(UUID uuid) {
		return Math.min(2, listeParties.get(uuid).getNbAllumettes());
	}
	
	@Override
	public String nomGagnant(UUID uuid) {
		int[] tabScore = listeParties.get(uuid).getTabScore();
		
		return (tabScore[0]%2 == 0 ? "vous":"l'ordinateur");
	}
	
	@Override
	public int scoreGagnant(UUID uuid) {
		int[] tabScore = listeParties.get(uuid).getTabScore();
		
		return (tabScore[0]%2 == 0 ? tabScore[1]:tabScore[0]);
	}
	
	@Override
	public String nomJoueurTour(UUID uuid) {
		return (listeParties.get(uuid).getTour()%2 == 0 ? "l'ordinateur":"vous");
	}
	
	@Override
	public PartieAllumettes getPartieAllumettes(UUID uuid) {
		return this.listeParties.get(uuid);
	}
	
	@Override
	public int coupIA(UUID uuid) {
		Random random = new Random();
		
		return random.nextInt(maxAllumettes(uuid))+1;
	}
}

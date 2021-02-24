package serveur;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import serveur.implementation.*;

public class ServeurAllumettes {
	public static void lancementServeur() {
		int port = 3000;
		
		try {
			LocateRegistry.createRegistry(port);
			String url = "rmi://localhost:" + port + "/allumettes";
			Naming.rebind(url, new AllumettesImpl());
			System.out.println ("Serveur des allumettes prêt !");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
	}
}

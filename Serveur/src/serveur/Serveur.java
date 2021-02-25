package serveur;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import modele.pojo.PenduImpl;

public class Serveur {

    public static void main(String[] args) {
        try {
            // Initialisation de l'adresse et du port pour le serveur.
            System.out.println("Démarrage du serveur..");
            String hote = args[0];
            int port = Integer.parseInt(args[1]);
            LocateRegistry.createRegistry(port);
            // Implémentation des différentes classes que le client va pouvoir utiliser.
            PenduImpl pendu = new PenduImpl();

            // Création des routes pour que le client puisse utiliser les méthodes des classes ci-dessus.
            Naming.rebind("rmi://" + hote + ":" + port + "/Pendu", pendu);
            // Le serveur est prêt à être utilisé.
            System.out.println("Serveur prêt à être utilisé.");
        } catch(Exception e) {
            System.out.println("Une erreur est survenue:\n" + e);
        }
    }
}

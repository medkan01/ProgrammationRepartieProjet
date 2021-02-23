package interfaceRMI;

import java.rmi.RemoteException;

public interface InterfacePendu {
    public String motAleatoire() throws RemoteException;
    public int randInt(int max);
    public String changeMot(String mot, char[] lettres) throws RemoteException;
}

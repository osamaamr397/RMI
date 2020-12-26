import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIRegistry {
    public static void main(String args[]) throws InterruptedException, RemoteException {
        LocateRegistry.createRegistry(1900);
        Thread.sleep(10000000);
    }
}
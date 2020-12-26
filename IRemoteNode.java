import java.rmi.*; 

public interface IRemoteNode extends Remote {
    
	public String Search(String k,int ra)throws RemoteException;
	public String Store(String k , String v ,int ra)throws RemoteException;
	public void AddNode(String node)throws RemoteException,NotBoundException;
	public void RemoveNode(String node)throws RemoteException,NotBoundException;
}

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Node extends UnicastRemoteObject implements IRemoteNode  {
    ArrayList<Integer> RandomNONode=new ArrayList<Integer>();
    ArrayList<Integer> SECRandomNONode=new ArrayList<Integer>();

	String NodeName;
	boolean visited;
	int NodeNumber;
    Map<String, String> hm = new HashMap<String, String>(); 
    ArrayList<IRemoteNode> NodeFriends = new ArrayList<IRemoteNode>();
    private Registry r;
    boolean visited2;
	
	Node() throws RemoteException 
    { 
        super(); 
    	this.r=LocateRegistry.getRegistry("localhost",1900);

    } 
	public ArrayList<IRemoteNode> getFriends() {
		return NodeFriends;
	}

	@Override
	public String Search(String k,int ra) throws RemoteException {
		//System.out.println("key "+ k+" "+ra);
		//System.out.println(this.hm.get(k));
	//	System.out.flush();

		if(RandomNONode.contains(ra)) {
			System.out.println("already done");
			System.out.flush();

			return null;
		}
		RandomNONode.add(ra);
		boolean flag=false;
		if(this.hm.containsKey(k)) {
		/**/	System.out.println(this.NodeName);
			return this.hm.get(k);

		}else {
			ArrayList<IRemoteNode> NodeFriends=this.getFriends();
			this.visited=true;
			for(int i=0;i<NodeFriends.size();i++) {
				IRemoteNode n=NodeFriends.get(i);
				if(n!=null) {
				String s=	n.Search(k,ra);
				if(s!=null) {
					return s;
					
				}
					
				}
			}
		}
		
		return null;
		
	}

	@Override
	public String Store(String k, String v,int ra) throws RemoteException {
		if(SECRandomNONode.contains(ra)) {
			System.out.println("in store ");
			return null;
			
		}
		SECRandomNONode.add(ra);
		if(this.hm.size()<3) {
			this.hm.put(k, v);
			System.out.println("key and value is added  ");
		}else {
			ArrayList<IRemoteNode> NodeFriends=this.getFriends();
			this.visited2=true;
			for(int i=0;i<NodeFriends.size();i++) {
				IRemoteNode n=NodeFriends.get(i);
				if(n!=null) {
					String ss= n.Store(k,v,ra);
					if(ss!=null) {
						return ss;
						
					}
				}
				
			}
		}
		return null;

	}

	@Override
	public void AddNode(String node) throws RemoteException, NotBoundException {
   
        
   	// Registry registry = LocateRegistry.getRegistry("localhost",1900);
   	 IRemoteNode access = (IRemoteNode) this.r.lookup(node);
   	 NodeFriends.add(access);
	}
	public void addpeer(String node) throws RemoteException, NotBoundException {
		System.out.println("is added ");
		 //Registry registry = LocateRegistry.getRegistry("localhost",1900);
		
	   	 IRemoteNode access = (IRemoteNode) this.r.lookup(this.NodeName);
		access.AddNode(node);
		this.AddNode(node);
	}

	@Override
	public void RemoveNode(String node) throws RemoteException, NotBoundException {
		System.out.println("is deleted");

		//Registry registry = LocateRegistry.getRegistry("localhost",1900);
	   	 IRemoteNode access = (IRemoteNode) this.r.lookup(node);
	   	 NodeFriends.remove(access);		
	}
	
	public void Removepeer(String node) throws AccessException, RemoteException, NotBoundException {
		//Registry registry = LocateRegistry.getRegistry("localhost",1900);
	   	 IRemoteNode access = (IRemoteNode) this.r.lookup(node);
		access.RemoveNode(this.NodeName);
		this.RemoveNode(node);
		
	}
	public void  setNodeName(String name) {
		this.NodeName=name;
	}
	public void  setNodeNumber(int number) {
		this.NodeNumber=number;
	}

	public String  getNodeName() {
		return this.NodeName;
	}
	public int  getNodeNumber() {
		return this.NodeNumber;
	}
	


}

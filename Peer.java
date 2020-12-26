import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Peer {

	public static void main(String args[]) 
    { 

    	
        try
        { 
        	int counter=0;
        	int counter2=0;
        	int fail=0;
            Registry registry = LocateRegistry.getRegistry("localhost",1900);

            // Create an object of the interface 
            // implementation class 
            Node node = new Node();
  
            // rmiregistry within the server JVM with 
            // port number 1900 
  
            // Binds the remote object by the name 
            
            Scanner sc= new Scanner(System.in); 
            System.out.println("Enter the node name");
            String NODENAME=sc.nextLine();
            node.setNodeName(NODENAME);
            System.out.println("Enter the uniqe number for the node");
            int NODENUMBER=sc.nextInt();
            node.setNodeNumber(NODENUMBER);            
            registry.bind(node.getNodeName(),node); //server name of peer it should change to 
            //i will will delete server and i will replace it with input from user
            //i will make while loop to read command

            
            System.out.println("if you want to add node 1");
            System.out.println("if you want to search about key 2");
            System.out.println("if you want to delete node 3");
            System.out.println("if you want to store data 4");
            System.out.println("if you want to stop 0");
            
            while(true) {
            	int flag=sc.nextInt();
            	switch(flag) {
            	case 1:{
            		System.out.println("enter name of node you want to add");
                    Scanner input= new Scanner(System.in); 

            		String name=input.nextLine();
            		node.addpeer(name);
            		break;
            	}
            	case 2:{
            		counter2++;
            		
                    Scanner scc= new Scanner(System.in); 
                    System.out.println("enter name of key you want to search about");

            		String search=scc.nextLine();
                    int ra = ThreadLocalRandom.current().nextInt(); 
                    
                    long lStartTime = System.nanoTime();
                    
                    String result=node.Search(search,ra);
            		System.out.println(  node.Search(search,ra));
            		
            		long lEndTime = System.nanoTime();
            	   
            		long output = lEndTime - lStartTime;
            		
            		System.out.println("Elapsed time in milliseconds: " + output / 1000000);
            		
            		 counter+=output;
             		System.out.println("Avg time in milliseconds: " + counter/counter2);

             		
             		if(result==null) {
             			fail++;
             		}
         			System.out.println("failure rate" + fail/counter2);


            	   break;
            	}
            	
                   case 3:{
               		System.out.println("enter name of node you want to delete");

               		Scanner inputtt= new Scanner(System.in); 
            		String deleting=inputtt.nextLine();
            	   node.Removepeer(deleting);
            	   break;
            	}

            	case 4:{
            		System.out.println("enter name of key and value you want to add");
            		Scanner inputt= new Scanner(System.in); 
            		String key=inputt.nextLine();
            		String value=inputt.nextLine();
                    int ra2 = ThreadLocalRandom.current().nextInt(); 

            		node.Store(key, value, ra2);
            		break;
            	}


            	
            }
            }
        } 
        catch(Exception ae) 
        { 
            System.out.println(ae); 
        } 
    } 
}

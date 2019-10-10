
/* A Java program for a Server */
import java.net.*; 
import java.io.*; 
import java.util.*;
//import client.java;
  


public class chat_server
{ 
/* initialize socket and input stream */
private Socket socket = null; 
private ServerSocket server = null;
private DataInputStream in = null;
private DataOutputStream out = null;
public static ArrayList<Client> clientList = new ArrayList<Client>();

/* constructor with port */
public chat_server(int port) 
{ 
	/* starts server and waits for a connection */
	try { 
		server = new ServerSocket(port); 
	} catch(Exception i) {
		System.out.println("Error in port");
		System.exit(0);
	}
	System.out.println("Server started"); 
	int clientCount = 0;
	while (true) {
		System.out.println("Waiting for a client ..."); 
		try {
			socket = server.accept(); 
			System.out.println("Client accepted"); //Connected 
			// sends output
			out = new DataOutputStream(socket.getOutputStream());

			/* takes input from the client socket */
			in = new DataInputStream( 
		    	new BufferedInputStream(socket.getInputStream())); 

			out.writeUTF("Enter client name:");
			String name = in.readUTF();
			out.writeUTF("Welcome to the 416 chat server");
			Client newClient = new Client(name, socket, in, out);

			Thread t = new Thread(newClient);

			clientList.add(newClient);
			t.start();
			
		
			
		} catch(EOFException i) { 
	    	System.out.println(i + "Server's sockettttt"); 
		} catch(Exception i) { 
	    	System.out.println(i + "Server's sockettttt"); 
		}
	}
	/* close connection 
			input.close();
			socket.close(); 
			in.close(); 
			out.close();
			*/
}

public static void main(String args[]) 
{ 
	if (args.length < 1) {
		System.out.println("Server usage: java Server #port_number");
	}
	else {
		try {
			chat_server server = new chat_server(Integer.parseInt(args[0])); 
		} catch(Exception i) {
			System.out.println("Error in port");	
		}
	}
} 

} 

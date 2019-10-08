
/* A Java program for a Client */
import java.net.*; 
import java.io.*; 
  
public class Client 
{ 
/* initialize socket and input output streams */
private Socket socket = null; 
private BufferedReader input = null; 
private DataOutputStream out = null;
private DataInputStream in = null;

/* constructor to put ip address and port */
public Client(String address, int port) 
{ 
	/* establish a connection */
	try {
		socket = new Socket(address, port); 
	} catch(Exception i) {
		System.out.println("Error in IP or port");
		System.exit(0);
    	}
	System.out.println("Connected"); // Connected
	try { 
		/* takes input from terminal */
		input = new BufferedReader(new InputStreamReader(System.in)); 

		/* sends output to the socket */
		out = new DataOutputStream(socket.getOutputStream()); 
		
		// Data from Server
		in = new DataInputStream(
			new BufferedInputStream(socket.getInputStream()));
		
	
	} catch(IOException i) { 
		System.out.println(i); 
	} 
	
	/* string to read message from input */
	String o_line = ""; 
	String i_line = "";

	/* keep reading until "Over" is input */
	while (!o_line.equals("Over")) { 
		try { 
			// Data to send to server
			o_line = input.readLine(); 
			out.writeUTF(o_line);
			//Data from server
			i_line = in.readUTF();
			System.out.println("Got input from Server ...");
			System.out.println("Printing input: "+ i_line);

		} catch(Exception i) { 
			System.out.println(i); 
		} 
	} 
	
	/* close the connection */
	try { 
		input.close(); 
		out.close(); 
		socket.close();
	       	in.close();	
	} catch(Exception i) {
		System.out.println(i);  
	} 
}

public static void main(String args[]) 
{ 
	if (args.length < 2) {
		System.out.println("Client usage: java Client #IP_address #port_number");
	}
	else {
		Client client = new Client(args[0], Integer.parseInt(args[1])); 
	}
} 

}

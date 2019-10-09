/* A Java program for a Client */
/* Client Must:


*/
import java.net.*; 
import java.io.*; 
  
public class chat_client 
{ 
/* Declare socket and input output streams */
private Socket socket = null; 
private BufferedReader input = null; 
private DataOutputStream out = null;
private DataInputStream in = null;
private String o_line = "";
private String i_line = "";
// Recieving Thread
public class ReadingThread extends Thread {
		// Data from Server
	ReadingThread() {
		try {		
			in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			//	String i_line = "";	
		} catch(IOException i) {
			System.out.println(i);
			System.exit(0); 
		}
	}	
	public void run() {
		while (!o_line.equals("Over") || !i_line.equals("Over")) { 
			// Data from server
			
			try {
				Thread.sleep(4000);
				i_line = in.readUTF();
				System.out.println("Got input from Server ...");
				System.out.println("Printing input: "+ i_line);
			} catch(Exception i) {
				System.out.println(i);  
			}// catch (IOException i) {
			//	System.out.println(i);  
			//}
		}
	}
}

// Sending Thread
public class SendingThread extends Thread {
		// Data from Server
	SendingThread() {
		// Initialize buffer readers and input and output streams
		try { 
			
			/* takes user input from terminal */
			input = new BufferedReader(new InputStreamReader(System.in)); 
			/* sends output to the socket */
			out = new DataOutputStream(socket.getOutputStream()); 
		} catch(IOException i) { 
			System.out.println(i);
 			System.exit(0); 
		} 
	}
	public void run() {
		/* keep reading until "Over" is input */
		while (!o_line.equals("Over") || !i_line.equals("Over")) { 
			try { 
				Thread.sleep(4000);
				// Data to send to server
				o_line = input.readLine(); 
				out.writeUTF(o_line);
			} catch(Exception i) { 
				System.out.println(i); 
			} 
		} 
	}
}
/* constructor to put ip address and port */
public chat_client(String address, int port) 
{ 
	/* Establish a connection */
	try {
		socket = new Socket(address, port); 
	} catch(Exception i) {
		System.out.println("Error in IP or port");
		System.exit(0);
  }
	System.out.println("Connected"); // Connected
	/* string to read message from input */
	//String o_line = ""; 




// ------------------- Receive Data ----------------------
// ------------------- Send Data -------------------------

	ReadingThread r = new ReadingThread();
	SendingThread s = new SendingThread();
	new Thread(r).start();
	new Thread(s).start();

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
		chat_client client = new chat_client(args[0], Integer.parseInt(args[1])); 
	}
} 
} // End class chat_client

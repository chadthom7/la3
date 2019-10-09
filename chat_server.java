
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
private BufferedReader input = null;
private DataInputStream in = null;
private DataOutputStream out = null; 
private ArrayList<Client> clientList = new ArrayList<Client>();
private String o_line = "";
private String i_line = "";

public void newConnection() {
	
}

public void runningConnection() {
	
}

public class WaitingForConnectionThread extends Thread {
	WaitingForConnectionThread() {
		ReadingThread r = new ReadingThread();
		ForwardingThread f = new ForwardingThread();
		new Thread(r).start();
		new Thread(f).start();
	}

	public void run() {
		
	}
}

public class ReadingThread extends Thread {
	ReadingThread() {
		try {
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch(IOException i) {
			System.out.println(i);
			System.exit(0);
		}
	}

	public void run() {
		while (!socket.isClosed()) {
			try{
				i_line = in.readUTF();
				System.out.println(i_line);
			} catch(Exception i) {
				System.out.println(i +"in Reading Thread (Just means" + 
							" connection Closed)");
				System.exit(0);
			}
		}
		try {  
			if(!socket.isClosed())
				socket.close();
	    in.close();	
		} catch(Exception i) {
			System.out.println(i);  
		} 
	}
}

public class ForwardingThread extends Thread {
	ForwardingThread() {
		try {
			input = new BufferedReader(new InputStreamReader(System.in));
			out = new DataOutputStream(socket.getOutputStream());
		} catch(IOException i) {
			System.out.println(i + " in Sending Thread");
			System.exit(0);
		}
	}

	public void run() {
		while (!socket.isClosed()) {
			try {
				o_line = input.readLine();
				out.writeUTF(o_line);
			} catch(Exception i) {
				System.out.println(i);
				System.exit(0);
			}
		}

		try {
			if(!socket.isClosed())
				socket.close();
			input.close();
			out.close();
		} catch(Exception i) {
			System.out.println(i);
		}
	}
}


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

	System.out.println("Waiting for a client ..."); 
	try {
		socket = server.accept(); 
		System.out.println("Client accepted"); //Connected 
		
		// terminal input
		input = new BufferedReader(new InputStreamReader(System.in));
		
		// sends output
		out = new DataOutputStream(socket.getOutputStream());

		/* takes input from the client socket */
		in = new DataInputStream( 
		    new BufferedInputStream(socket.getInputStream())); 
		
		String i_line = ""; 
		String o_line = "";	
		/* reads message from client until "Over" is sent */
		while (!i_line.equals("Over") && !o_line.equals("Over")) 
		{ 
		        i_line = in.readUTF(); 
		        System.out.println(i_line); 
			//Data to Client
			o_line = input.readLine();
			out.writeUTF(o_line);

		} 
		System.out.println("Closing connection"); 
		
		/* close connection */
		input.close();
		socket.close(); 
		in.close(); 
		out.close();
	} catch(EOFException i) { 
	    System.out.println(i); 
	} 
	catch(Exception i) { 
	    System.out.println(i); 
	}

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
		WaitingForConnectionThread c = new WaitingForConnectionThread();
		new Thread(c).start();
	}
} 

} 

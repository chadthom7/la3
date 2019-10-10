/* A Java program that represents a client object */
import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {
	String name = "";
	boolean isBusy;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	String partner_name = "";
	int partner = -1;

	//Constructor
	public Client(String name, Socket socket, DataInputStream in, DataOutputStream out) {
		this.name = name;
		this.isBusy = false;
		this.socket = socket;
		this.in = in;
		this.out = out;
	}

	public String busy() {
		if (isBusy) {
			return "busy";
		}
		else {
			return "free";
		}
	}

	public void run() {
		System.out.println("Thread is running");	
		while (true) {
			try {
				out.writeUTF("List of clients and states");
			
				//System.out.println("past .write");	
				String list = "";
				
				for(Client c : chat_server.clientList)
					list += c.name + " " + c.busy() + "\n";
				for(Client c : chat_server.clientList) {
					if (!c.isBusy)
						c.out.writeUTF(list);
				}
				
				/*
				for(int i = 0; i < chat_server.clientList.size(); i++) {
					list += chat_server.clientList.get(i).name + " " + 
						chat_server.clientList.get(i).isBusy() + "\n"; 
					//this.partner = i;
					//this.partner_name = partner_name; 
					//this.out = clientList.get(i).dos;
				
				}
				for(int i = 0; i < chat_server.clientList.size(); i++) {
					if(!chat_server.clientList.get(i).busy()) {
							chat_server.clientList.get(i).busy() 
				*/

				
				System.out.println("past print list");	
				String yn = "";
				// PROMPT connect to which client
				out.writeUTF("Connect to which client?");
				// Wait for response
				try {
					this.partner_name = in.readUTF();
				} catch(IOException i) {
					this.in =  new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
				}
				 
				// ^ Need to figure out how to skip this if someone types your name
				if(this.partner_name != "") {
					// Set partner from list	
					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).name.equals(partner_name)) {
							this.partner = i;
						//this.partner_name = partner_name; 
						//this.out = clientList.get(i).dos;
						}
					}
					// Close and reopen partners input buffer so stop waiting for name
					chat_server.clientList.get(this.partner).in.close();
					//chat_server.clientList.get(this.partner).in = new DataInputStream(new BufferedInputStream(chat_server.clientList.get(this.partner).socket.getInputStream()));				
				}
				// You typed a partner name
				if(this.partner_name != "") {
					yn = "y";
					chat_server.clientList.get(this.partner).out.writeUTF("Recieved request from " + this.name + "\nConnect?");
					chat_server.clientList.get(this.partner).partner_name = this.name;
				}	else { // Someone typed your name
					Thread.sleep(50);	
					yn = this.in.readUTF();
					//if (yn == "y")
						
				}
				if (yn != "y") {
					break;
				}
				
				if(this.partner == -1) {
 					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).name.equals(this.partner_name)) {
							this.partner = i;
						}
					}
				}

				
				// Send Confirmation message	
				System.out.println(this.name + " chose " + this.partner_name);
				
			
				// Confirmation Received or given

						
			} catch(IOException i) {
				System.out.println(i);
			} catch(InterruptedException i) {
				System.out.println(i);
			}
			/*
			// If someone Request you
			for(Client c : chat_server.clientList) {
					if (this.name == c.partner_name) {
							
					}
			*/

/*
			try {	
				chat_server.clientList.get(this.partner).out.writeUTF(this.partner_name);
			} catch (IOException i) {
						
			}
*/

			// CONVERSATION:	
			while(true) {
				
				try {
					String message = this.in.readUTF();
					chat_server.clientList.get(this.partner).out.writeUTF(this.name + ": " + message); 
				} catch (IOException i) {
					try {
						this.out.writeUTF(chat_server.clientList.get(this.partner).name + " disconnected");
					} catch(IOException e) {
						System.out.println(e);
					}
					this.partner = -1;
					this.partner_name = "";
					this.isBusy = false;
					break;
				}
				/*
				try {
									} catch (IOException i) {
						this.out.writeUTF(chat_server.clientList.get(this.partner).name + " disconnected");

				}			
				*/


//chat_server.clientList.get(i).out.writeUTF("");
				// this.dis writeUTF();
			}
			/*	
			try {
				this.in.close();
				this.out.close();
			} catch(IOException i) {
				System.out.println(i);
			}
			*/
	}
}
/*
	public String getClientName() {
		return this.name;
	}

	public boolean getClientState() {
		return this.isBusy;
	}

	public int getClientPortNum() {
		return this.portNum;
	}

	public String getClientIP() {
		return this.ip;
	}

	public void setClientState(boolean isBusy) {
		this.isBusy = isBusy;
	}
	*/
}

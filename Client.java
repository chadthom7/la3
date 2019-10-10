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
	int partner;

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
			
				System.out.println("past .write");	
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
			
				out.writeUTF("Connect to which client?");
				this.partner_name = in.readUTF();
			} catch(IOException i) {
				System.out.println(i);
			}
			// Send Confirmation message
			System.out.println("You chose " + this.partner_name);
			
	
			
			for(int i = 0; i < chat_server.clientList.size(); i++) {
				if(chat_server.clientList.get(i).name.equals(partner_name)) {
					this.partner = i;
					//this.partner_name = partner_name; 
					//this.out = clientList.get(i).dos;
				}
			}
			try {	
				chat_server.clientList.get(this.partner).out.writeUTF(this.partner_name);
			} catch (IOException i) {
						
			}

		
			while(true) {
				
				try {
					String message = this.in.readUTF();
					chat_server.clientList.get(this.partner).out.writeUTF(this.name + ": " + message); 
				} catch (IOException i) {
						
				}
				try {
					this.out.writeUTF(chat_server.clientList.get(this.partner).name + " disconnected");
				} catch (IOException i) {
						
				}			

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

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

				//System.out.println("past print list");	
				String yn = "";
				// PROMPT connect to which client
				out.writeUTF("Connect to which client?");
				// Wait for response
				//try {
				String input ="";
				input  = this.in.readUTF();
				System.out.println(this.name + "--------- " + input);
				//this.partner_name = in.readUTF();
				if (input.equals("") || input.equals("y") || input.equals("n")){
					yn = input;
				} else {
					this.partner_name = input;
					// Set partner from list	
					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).name.equals(partner_name)) {
							this.partner = i;
						//this.partner_name = partner_name; 
						//this.out = clientList.get(i).dos;
						}
					}
					// You typed a partner name
					yn = "y";
					// Send Request
					chat_server.clientList.get(this.partner).out.writeUTF("Recieved request from " + this.name + "\nConnect?");
					// Have potential partner save your name
					chat_server.clientList.get(this.partner).partner_name = this.name;
					/*
					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).partner_name.equals(this.name)) {
						chat_server.clientList.get(this.partner).partner = i;
						//this.partner_name = partner_name; 
						//this.out = clientList.get(i).dos;
						}
					}	
					*/
					//else { // Someone typed your name
					//Thread.sleep(50);	
					// yn = this.in.readUTF();
					//if (yn == "y")
				}
				//if(this.partner == -1)

				if(this.partner == -1) {
 					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).name.equals(this.partner_name)) {
							this.partner = i;
						}
					}
				}




				System.out.println(this.partner + "--------- " + this.partner_name);	
				/*}
				if (yn != "y" && input!= "y") {
				 System.out.println("--------- ?????????????");
					//break;
				}*/
				System.out.println(this.partner + "--------- " + this.partner_name);
				/*
				if(this.partner == -1) {
 					for(int i = 0; i < chat_server.clientList.size(); i++) {
						if(chat_server.clientList.get(i).name.equals(this.partner_name)) {
							this.partner = i;
						}
					}
				}
				*/
				
				// Send Confirmation message	
				this.out.writeUTF("You are connected to " + this.partner_name);
				this.isBusy = true;	
			
				// Confirmation Received or given

						
			} catch(IOException i) {
				System.out.println(i);
			}/* catch(InterruptedException i) {
				System.out.println(i);
			}*/


			// CONVERSATION:	
			while(true)	{
			  System.out.println(this.partner + "--------- " + this.partner_name);
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

			}

	}
}

}

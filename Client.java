/* A Java program that represents a client object */

public class Client implements Runnable{
	String name = "";
	boolean isBusy;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	//Constructor
	public Client(String name, Socket socket, DataInputStream in, DataOutputStream out) {
		this.name = name;
		this.isBusy = true;
		this.Socket = socket;
		this.DataInputStream = in;
		this.DataOutputStream = out;
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
		while (true) {
			try {

			out.writeUTF("List of clients and states");
			String list = "";
			for(Client c : clientList)
				list += c.name + " " + c.busy();
			for(Client c : clientList) {
				if (!c.isBusy)
					out.writeUTF(list);
			}




			} catch(IOException i) {
				System.out.println(i);
			}
		}
		try {
			this.in.close();
			this.out.close();
		} catch(IOException i) {
			System.out.println(i);
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
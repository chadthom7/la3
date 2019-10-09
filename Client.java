/* A Java program that represents a client object */

public class Client {
	private String name = "";
	private boolean isBusy;
	private int portNum = null;
	private String ip = "";

	//Constructor with name
	public Client(int portNum, String ip) {
		this.name = "";
		this.isBusy = false;
		this.portNum = portNum;
		this.ip = ip;
	}

	public Client(String name, int portNum, String ip) {
		this.name = name;
		this.portNum = portNum;
		this.ip = ip;
		this.isBusy = true;
	}

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
}
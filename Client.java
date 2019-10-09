/* A Java program that represents a client object */

public class Client {
	private String name = "";
	private boolean isBusy;

	//Constructor with name
	public Client(String aName) {
		this.name = aName;
		this.isBusy = false;
	}

	public String getClientName() {
		return this.name;
	}

	public boolean getClientState() {
		return this.isBusy;
	}

	public void setClientState(boolean isBusy) {
		this.isBusy = isBusy;
	}
}
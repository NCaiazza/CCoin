package CCoin;

public class User {
	private String name;
	private double ccoinAmount;
	
	public User(String name, int amount) {
		this.name = name;
		this.ccoinAmount = amount;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return ccoinAmount;
	}

	public void withdraw(double ccoinAmount) {
		this.ccoinAmount -= ccoinAmount;
	}
	
	public void deposit(double ccoinAmount) {
		this.ccoinAmount += ccoinAmount;
	}
}

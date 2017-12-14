package CCoin;

public class User {
	private String name;
	private double ccoinAmount;
	
	public User(String name, int amount) {
		this.name = name;
		this.ccoinAmount = amount;
	}

	/**
	 * This method returns the name of the user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the CCoin balance of the user.
	 */
	public double getBalance() {
		return ccoinAmount;
	}

	/**
	 * This method withdraws ccoinAmount from the user's balance.
	 */
	public void withdraw(double ccoinAmount) {
		this.ccoinAmount -= ccoinAmount;
	}
	
	/**
	 * This method deposits ccoinAmount into the user's balance.
	 */
	public void deposit(double ccoinAmount) {
		this.ccoinAmount += ccoinAmount;
	}
	
	@Override
	public String toString() {
		return "Name: " + this.name + ", Balance: " + this.ccoinAmount;
	}
}

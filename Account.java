
/**
 * The Account class holds information about and manipulates the account at the bank
 *
 * @author Kwinn Danforth
 * @version 1.0.01
 */



public class Account
{
    private final String TYPE;
    private double  balance;

    /**
     * Constructor for objects of class Account
     */
    public Account(String type, Double balance)
    {
        this.balance = balance;
        this.TYPE = type;
    }

    /**
     * This method returns the type of the account
     *
     * @return The type of the account
     */
    public String getTYPE() {
        return TYPE;
    }

    /**
     * This method returns the balance of the account
     *
     * @return The balance of the account
     */
    public double getBalance() {
        return balance;
    }

    /**
     * This method deposits money into the account. Adds the amount to the balance.
     *
     * @param  amount, the amount to add to the balance
     */
    public void deposit(double amount){
        balance += amount;
    }

    /**
     * This method withdraws money out of the account. Subtracts the amount from the balance.
     *
     * @param amount, the amount to add to subtract from the balance
     */
    public void withdraw(double amount){
        balance -= amount;
    }
}
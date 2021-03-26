import java.util.HashMap;

/**
 * This class will simulate the bank for out ATM program
 *
 * @author Kwinn Danforth
 * @version 1.0.01
 */
public class Bank
{
    private final HashMap<String, Double> CHECKING_BALANCES;
    private final HashMap<String, Double> SAVINGS_BALANCES;
    private final HashMap<String, String> PINS;
    /**
     * Constructor for objects of class Bank
     */
    public Bank()
    {
        CHECKING_BALANCES = new HashMap<>();
        CHECKING_BALANCES.put("1000500020006000", 3255.89);
        CHECKING_BALANCES.put("1000300080007000", 154.22);
        CHECKING_BALANCES.put("1000900030004000", 578.25);
        CHECKING_BALANCES.put("1000200060007000", 100121.69);

        SAVINGS_BALANCES = new HashMap<>();
        SAVINGS_BALANCES.put("1000500020006000", 120000.00);
        SAVINGS_BALANCES.put("1000300080007000", 1587.32);
        SAVINGS_BALANCES.put("1000900030004000", 25000.00);
        SAVINGS_BALANCES.put("1000200060007000", 1000000000.00);

        PINS = new HashMap<>();
        PINS.put("1000500020006000", "1234");
        PINS.put("1000300080007000", "1514");
        PINS.put("1000900030004000", "1819");
        PINS.put("1000200060007000", "1417");
    }

    /**
     * This method takes a card number and returns the checking balance to the caller
     *
     * @param  cardNumber, the card number to check the balance of
     * @return    the checking account balance associated with the card number
     */
    public double getCheckingBalance(String cardNumber)
    {
        return CHECKING_BALANCES.get(cardNumber);
    }

    /**
     * This method takes a card number and returns the savings balance to the caller
     *
     * @param  cardNumber, the card number to check the balance of
     * @return    the savings account balance associated with the card number
     */
    public double getSavingsBalance(String cardNumber)
    {
        return SAVINGS_BALANCES.get(cardNumber);
    }

    /**
     * This method takes a card number and a pin number and returns weather or not they match
     *
     * @param  cardNumber, the card number to check the balance of
     * @return  Boolean true if the pin matches to the card number
     */
    public boolean checkPin(String cardNumber, String pin)
    {
        if(pin.equals(PINS.get(cardNumber))){
            return true;
        }
        return false;
    }
}

import java.util.Scanner;

/**
 * The ATM class is the functionality for our ATM to work
 *
 * @author Kwinn Danforth
 * @version 1.0.01
 */
public class ATM
{
    private final Bank BANK;
    private final Account CHECKING;
    private final Account SAVINGS;
    private final Scanner KEYBOARD;

    private boolean cardTaken;
    private boolean cashDispensed;
    private boolean envelopeDropped;
    
    /**
     * Constructor for objects of class ATM
     */
    public ATM() {
        BANK = new Bank();
        KEYBOARD = new Scanner(System.in);
        printWelcomeScreen();
        final String CARD_NUMBER = "1000500020006000";
        System.out.println("Card Inserted...");

        cardTaken = false;
        signIn(CARD_NUMBER);

        CHECKING = new Account("checking", BANK.getCheckingBalance(CARD_NUMBER));
        SAVINGS = new Account("savings", BANK.getSavingsBalance(CARD_NUMBER));

        switch(choose()){
            case 1:
                withdraw(whatAccount("withdraw from"), howMuch(true));
                cashDispensed = true;
                exit();
                break;
            case 2:
                deposit(whatAccount("deposit to"), howMuch(false));
                exit();
                break;
            case 3:
                transfer(whatAccount("transfer from"), howMuch(false));
                exit();
                break;
            case 4:
                checkBalance(whatAccount("check the balance of"));
                exit();
                break;
            case 5:
                exit();
                break;
            default:
                System.out.println("You must enter a valid menu option...");
                menu();
                break;
        }

    }

    private void printWelcomeScreen() {
        System.out.println("Welcome");
        System.out.println("Please insert your card");
    }


    /**
     * This method simulates a sign in by the user by checking their entered pin against the one in the bank database.
     *
     * @param cardNumber, the number from the simulated magnetic strip on the card inserted in our ATM
     */
    public void signIn( String cardNumber)
    {
        final int MAX_ALLOWED_ATTEMPTS = 3;
        int attempts = 0;
        boolean signedIn = false;

        while(attempts < MAX_ALLOWED_ATTEMPTS && !signedIn) {
            System.out.println("Enter Pin (Simulation pin = 1234): ");
            String pin = KEYBOARD.nextLine();
            attempts++;
            if(BANK.checkPin(cardNumber, pin)){
                menu();
                signedIn = true;
            }else{
                System.out.println("After three failed pin entry attempts, your card will be confiscated and you will need to contact your bank! " + "Failed attempts: " + attempts);
                if(attempts >= MAX_ALLOWED_ATTEMPTS){
                    System.out.println("That's too many failed attempts! Contact your bank to retrieve your card..");
                    cardTaken = true;
                    exit();
                }
            }
        }

    }

    /**
     * This method displays a menu for the user to choose from.
     *
     */
    public void menu()
    {
      System.out.println("Automated Teller Machine");

      System.out.println("Choose 1 for Withdraw");
      System.out.println("Choose 2 for Deposit");
      System.out.println("Choose 3 for Transfer");
      System.out.println("Choose 4 for Check Balance");
      System.out.println("Choose 5 for EXIT");

      System.out.print("Enter the number from the list of options: ");
    }
    
    /**
     * This method lets the user make a choice from the menu
     *
     *@return the integer of the choice from the menu made by the user
     */
    public int choose()
    {
        int choice = 0;
        while(choice < 1 || choice > 6){
            choice = Integer.parseInt(KEYBOARD.nextLine());
        }

        return choice;
    }
    
    /**
     * This method lets the user choose what account to work with
     *
     *@return the account chosen by the user
     */
    public String whatAccount(String fromOrTo)
    {
        String choice = "notMade";

        while(! choice.toLowerCase().equals("checking")  && ! choice.toLowerCase().equals("savings")){
            System.out.println("Enter the account you would like to " + fromOrTo + " (checking or savings, E to exit): ");
            choice = KEYBOARD.nextLine();
            if(choice.toLowerCase().equals("e")){
                exit();
            }
        }
        return choice;
    }
    
    /**
     * This method lets the user choose how much money to work with
     *
     *@return the amount of money chosen by the user
     */
    public double howMuch(boolean mustBeTwenties)
    {
        String input;
        double choice = 0;
        if(!mustBeTwenties) {
            System.out.println("Enter the amount (enter E to exit): ");
            input = KEYBOARD.nextLine();
            if(input.toLowerCase().equals("e")){
                exit();
            }
            choice = Double.parseDouble(input);
        }else{
            while(!isMultiple20(choice)) {
                System.out.println("Enter amount to withdraw (must be in increments of $20, enter E to exit): ");
                input = KEYBOARD.nextLine();
                if(input.toLowerCase().equals("e")){
                    exit();
                }
                choice = Double.parseDouble(input);
            }
        }

        return choice;
    }

    public boolean isMultiple20(double n)
    {
        while ( n > 0 ) {
            n = n - 20;
            if (n == 0) {
                return true;
            }
        }
            return false;
    }
    
    /**
     * This method withdraws money from the account.
     *
     * @param account, the type of account and the amount to take out of the account
     */
    public void withdraw(String account, double amount)
    {
        if(account.toLowerCase().equals(CHECKING.getTYPE())){
            if(amount <= CHECKING.getBalance() && amount > 0){
                CHECKING.withdraw(amount);
            }else{
                System.out.println("You have insufficient funds!");
            }
        }else {
            if(amount <= SAVINGS.getBalance() && amount > 0){
                SAVINGS.withdraw(amount);
            }else{
                System.out.println("You have insufficient funds!");
            }
        }
    }
    
    /**
     * This method deposits money into the account.
     *
     * @param amount, the amount to add to add to the account and the type of account
     */
    public void deposit(String account, double amount)
    {
        System.out.println("Please place the envelope containing the proper value of either cash or check(s) in the drop box below...");
        envelopeDropped = true;
        if(account.toLowerCase().equals(CHECKING.getTYPE()) && envelopeDropped){
            CHECKING.deposit(amount);
        }else if(envelopeDropped) {
            SAVINGS.deposit(amount);
        }else{
            System.out.println("You must drop the envelope before funds will be deposited. You will be redirected to the main menu. Please try again...");
            menu();
        }
    }
    
    /**
     * This method transfers money from on account into the other account.
     *
     * @param amount, the amount to add to add to the account and the type of account to transfer from
     */
    public void transfer(String account, double amount)
    {
        if(account.toLowerCase().equals(CHECKING.getTYPE())){
            withdraw(account, amount);
            deposit(SAVINGS.getTYPE(), amount);
        }else {
            withdraw(account, amount);
            deposit(CHECKING.getTYPE(), amount);
        }
    }
    
    /**
     * This method checks the balance on the account and prints it to the screen.
     *
     * @param account, the account for which to retrieve the balance
     */
    public void checkBalance(String account)
    {
        if(account.toLowerCase().equals(CHECKING.getTYPE())){
            System.out.println("Your checking account balance is " + "$" + CHECKING.getBalance());
        }else{
            System.out.println("Your savings account balance is " + "$" + SAVINGS.getBalance());
        }
    }
    
    /**
     * This method exits the ATM and returns card.
     *
     */
    public void exit() {
        if (cashDispensed) {
                System.out.println("Your cash will be dispensed below...");
        }
        System.out.println("Thank you for using our ATM.");
        if (!anotherTransaction()) {
            if (!cardTaken) {
                System.out.println("You may now remove you card.");
            }
            System.out.println("Have A great day!");
            System.exit(1);
        }
    }

    private Boolean anotherTransaction() {
        String choice = "notMade";
        System.out.println("Would you like to do another transaction? (enter yes or no)");
        while(!choice.toLowerCase().equals("yes") && !choice.toLowerCase().equals("no")){
            choice = KEYBOARD.nextLine();
            if(choice.toLowerCase().equals("yes")){
                menu();
                return true;
            }
        }
        return false;
    }
}
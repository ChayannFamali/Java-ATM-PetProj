import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("Bank V");
        User aUser = theBank.addUser("Dmitrii", "Ves", "1234");
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);
        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(theBank, sc);
            ATM.printUserMenu(curUser, sc);
        }
    }
    /**
     * Print the ATM's login menu.
     * @param theBank
     * @param sc
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination. " +
                        "Please try again");
            }
        } while(authUser == null);
        return authUser;
    }
    /**
     * Print the ATM's menu for user actions.
     * @param theUser
     * @param sc
     */
    public static void printUserMenu(User theUser, Scanner sc) {
        theUser.printAccountsSummary();
        int choice;
        do {
            System.out.println("What would you like to do?");
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5.");
            }
        } while (choice < 1 || choice > 5);
        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than balance " +
                        "of $.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        theUser.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format(
                "Transfer from account %s", theUser.getAcctUUID(fromAcct)));
    }
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void withdrawFunds(User theUser, Scanner sc) {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "withdraw from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the amount to withdraw (max $%.02f): $",
                    acctBal);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            } else if (amount > acctBal) {
                System.out.printf("Amount must not be greater than balance " +
                        "of $%.02f.\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);
        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }
    /**
     * Process a fund deposit to an account.
     * @param theUser	the logged-in User object
     * @param sc		the Scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner sc) {
        int toAcct;
        double amount;
        String memo;
        do {
            System.out.printf("Enter the number (1-%d) of the account to " +
                    "deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        do {
            System.out.printf("Enter the amount to deposit: $");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero.");
            }
        } while (amount < 0);
        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        theUser.addAcctTransaction(toAcct, amount, memo);
    }
    /**
     *
     * @param theUser
     * @param sc
     */
    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;
        do {
            System.out.printf("Enter the number (1-%d) of the account\nwhose " +
                    "transactions you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);
    }
}
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {
    /**
     * The name of the bank.
     */
    private String name;
    /**
     * The account holders of the bank.
     */
    private ArrayList<User> users;
    /**
     * The accounts of the bank.
     */
    private ArrayList<Account> accounts;
    /**
     * Create a new Bank object with empty lists of users and accounts.
     */
    public Bank(String name) {
        this.name = name;
        users = new ArrayList<User>();
        accounts = new ArrayList<Account>();
    }
    /**
     * @return the uuid
     */
    public String getNewUserUUID() {
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;
    }

    /**
     * @return the uuid
     */
    public String getNewAccountUUID() {
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique = false;
        do {
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer) rng.nextInt(10)).toString();
            }
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }

    /**
     * Create a new user of the bank.
     *
     * @param firstName
     * @param lastName
     * @param pin
     * @return
     */
    public User addUser(String firstName, String lastName, String pin) {
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);
        return newUser;
    }

    /**
     * @param newAccount the account
     */
    public void addAccount(Account newAccount) {
        this.accounts.add(newAccount);
    }

    /**
     * @param userID
     * @param pin
     * @return
     */
    public User userLogin(String userID, String pin) {
        for (User u : this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;

    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }
}
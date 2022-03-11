import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;
    /**
     * Create new Account instance
     * @param name		the name of the account
     * @param holder	the User object that holds this account
     * @param theBank	the bank that issues the account
     */
    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();

    }
    /**
     *
     * @return	the uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     *
     * @param amount
     */
    public void addTransaction(double amount) {
        Transaction newTrans = new Transaction(amount, this);
        this.transactions.add(newTrans);

    }
    /**
     *
     * @param amount
     * @param memo
     */
    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);

    }
    /**
     *
     *
     * @return	the balance value
     */
    public double getBalance() {

        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }
    /**
     *
     * @return
     */
    public String getSummaryLine() {
        double balance = this.getBalance();
        if (balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance,
                    this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance,
                    this.name);
        }

    }
    /**
     * Print transaction history for account
     */
    public void printTransHistory() {

        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
}
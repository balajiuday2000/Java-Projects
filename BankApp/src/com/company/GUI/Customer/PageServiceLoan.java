package com.company.GUI.Customer;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Account.LoanAccount;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.AccountNotExistException;
import com.company.Factories.AccountFactory;
import com.company.GUI.PageManager;
import com.company.GUI.MyPage;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.LoanTxn;
import com.company.Transactions.Transaction;
import com.company.Utils.Writer;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static com.company.Account.AccountType.*;
import static com.company.Account.AccountType.LOAN;

/**
 * Class of page for doing loan services
 */
public class PageServiceLoan implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbService;
    private JComboBox cbService;
    private JComboBox cbCurrency1;
    private JLabel lbAmount1;
    private JTextField tfAmount1;
    private JPanel panelTake;
    private JPanel panelPay;
    private JLabel lbAmount2;
    private JTextField tfAmount2;
    private JLabel lbAccount2;
    private JComboBox cbAccount2;
    private JButton btConfirm;
    private JButton btCancel;
    private JLabel lbDate2;
    private JTextField tfDate2;
    private JLabel lbType2;
    private JComboBox cbType2;

    private String accountTypeChosen;
    private String accountIDChosen;

    private Map<LocalDate, Double> amountsDue = new HashMap<>();

    /**
     * Constructor
     * @param customer
     * @param currency
     * @param stockMarket
     */
    public PageServiceLoan(Customer customer, Currency currency, StockMarket stockMarket) {
        panelTake.setVisible(false);
        panelPay.setVisible(false);

        Writer writer = new Writer();
        try {
            List<Account> allLoanAccounts = customer.getAccountsByType(AccountType.LOAN);
        } catch (AccountNotExistException e2) {
            String accountNo = getRandomNumberString();
            String[] args = {accountNo, customer.getName(), customer.getPwd(), "LOAN", " ", " "};
            AccountFactory accountFactory = new AccountFactory();
            Account newAccount = accountFactory.produceAccount(args);
            customer.addAccount(newAccount);
            try {
                writer.grantNewAccount(customer, newAccount, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        List<Account> allLoanAccounts = customer.getAccountsByType(AccountType.LOAN);
        List<Account> allCheckingsAccounts = new LinkedList<>();
        List<Account> allSavingsAccounts = new LinkedList<>();
        try {
            allCheckingsAccounts = customer.getAccountsByType(AccountType.CHECKINGS);
        } catch (AccountNotExistException e2) {
        }
        try {
            allSavingsAccounts = customer.getAccountsByType(AccountType.SAVINGS);
        } catch (AccountNotExistException e2) {
        }
        for (Account loanAccount : allLoanAccounts) {
            amountsDue = ((LoanAccount) loanAccount).getAmountsDue();
        }

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageManager.backToOldPage();
            }
        });
        cbService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbService.getSelectedItem().equals("take loan")) {
                    panelTake.setVisible(true);
                } else {
                    panelPay.setVisible(true);
                }
            }
        });
        cbType2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountTypeChosen = (String) cbType2.getSelectedItem();
                if (accountTypeChosen.equals("Savings Account")) {
                    ArrayList<String> accountOptions = new ArrayList<>();
                    List<Account> AllSavingsAccounts = customer.getAccountsByType(SAVINGS);
                    for (Account account : AllSavingsAccounts) {
                        String option = account.getAccountId();
                        option += ", USD=" + account.getBalance().get(CurrencyType.USD);
                        option += ", EUR=" + account.getBalance().get(CurrencyType.EUR);
                        option += ", CAD=" + account.getBalance().get(CurrencyType.CAD);
                        option += ", JPY=" + account.getBalance().get(CurrencyType.JPY);
                        accountOptions.add(option);
                    }
                    String[] cbOptions = new String[accountOptions.size()];
                    for (int i = 0; i < accountOptions.size(); i++) {
                        cbOptions[i] = accountOptions.get(i);
                    }
                    DefaultComboBoxModel cbModel = new DefaultComboBoxModel(cbOptions);
                    cbAccount2.setModel(cbModel);
                } else if (accountTypeChosen.equals("Checking Account")) {
                    ArrayList<String> accountOptions = new ArrayList<>();
                    List<Account> AllCheckingsAccounts = customer.getAccountsByType(CHECKINGS);
                    for (Account account : AllCheckingsAccounts) {
                        String option = account.getAccountId();
                        option += ", USD=" + account.getBalance().get(CurrencyType.USD);
                        option += ", EUR=" + account.getBalance().get(CurrencyType.EUR);
                        option += ", CAD=" + account.getBalance().get(CurrencyType.CAD);
                        option += ", JPY=" + account.getBalance().get(CurrencyType.JPY);
                        accountOptions.add(option);
                    }
                    String[] cbOptions = new String[accountOptions.size()];
                    for (int i = 0; i < accountOptions.size(); i++) {
                        cbOptions[i] = accountOptions.get(i);
                    }
                    DefaultComboBoxModel cbModel = new DefaultComboBoxModel(cbOptions);
                    cbAccount2.setModel(cbModel);
                }
            }
        });
        cbAccount2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbAccount2.getSelectedItem();
                accountIDChosen = selectedItem.split(",")[0];
            }
        });
        btConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbService.getSelectedItem().equals("take loan")) {
                    double value = Double.parseDouble(tfAmount1.getText());
                    for (Account loanAccount : allLoanAccounts) {
                        ((LoanAccount) loanAccount).getLoan(LocalDate.now(), value);
                        try {
                            writer.updateAccountToDisk(loanAccount);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        try {
                            writer.writeTxn(recordTransaction(value, customer.getId()));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    displayConfirmForTake();
                }
                else {
                    double value = Double.parseDouble(tfAmount2.getText());
                    String date1 = tfDate2.getText();
                    LocalDate date = LocalDate.parse(date1);
                    if (accountTypeChosen.equals("Savings Account")) {
                        List<Account> allSavingsAccounts = customer.getAccountsByType(SAVINGS);
                        for (Account account : allSavingsAccounts) {
                            if (account.getAccountId().equals(accountIDChosen)) {
                                account.addToBalance(CurrencyType.USD, -value);
                                try {
                                    writer.updateAccountToDisk(account);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                        for (Account loanAccount : allLoanAccounts) {
                            ((LoanAccount) loanAccount).payLoan(date, value);
                            try {
                                writer.updateAccountToDisk(loanAccount);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            try {
                                writer.writeTxn(recordTransaction(-value, customer.getId()));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } else {
                        List<Account> allCheckingsAccounts = customer.getAccountsByType(CHECKINGS);
                        for (Account account : allCheckingsAccounts) {
                            if (account.getAccountId().equals(accountIDChosen)) {
                                account.addToBalance(CurrencyType.USD, -value);
                                try {
                                    writer.updateAccountToDisk(account);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                        for (Account loanAccount : allLoanAccounts) {
                            ((LoanAccount) loanAccount).payLoan(date, value);
                            try {
                                writer.updateAccountToDisk(loanAccount);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            try {
                                writer.writeTxn(recordTransaction(-value, customer.getId()));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        Account loanAccount = allLoanAccounts.get(0);
                        if (((LoanAccount) loanAccount).getAmountsDue().isEmpty()) {
                            customer.deleteAccount(LOAN, loanAccount.getAccountId());
                            try {
                                writer.deleteAccount(LOAN, loanAccount.getAccountId(), customer);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        displayConfirmForPay();
                    }
                }
            }
        });
    }

    /**
     * Method for logging transaction records
     * @param amount
     * @param cusID
     * @return
     */
    public static Transaction recordTransaction(Double amount, String cusID) {

        String ID = getRandomNumberString();
        String customerID = cusID;
        Double value = amount;
        LocalDate date = LocalDate.now();
        LoanTxn transaction = new LoanTxn(ID, date, amount, customerID);
        return transaction;
    }

    /**
     * Method for generating random number string
     * @return
     */
    public static String getRandomNumberString() {

        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        lbTitle = new JLabel();
        lbTitle.setText("Loan Service");
        rootPanel.add(lbTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbService = new JLabel();
        lbService.setText("Choose service:");
        rootPanel.add(lbService, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbService = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("take loan");
        defaultComboBoxModel1.addElement("pay loan");
        cbService.setModel(defaultComboBoxModel1);
        rootPanel.add(cbService, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelTake = new JPanel();
        panelTake.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelTake, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbAmount1 = new JLabel();
        lbAmount1.setText("Enter loan amount:");
        panelTake.add(lbAmount1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfAmount1 = new JTextField();
        panelTake.add(tfAmount1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panelPay = new JPanel();
        panelPay.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelPay, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbAmount2 = new JLabel();
        lbAmount2.setText("Enter pay back amount:");
        panelPay.add(lbAmount2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfAmount2 = new JTextField();
        panelPay.add(tfAmount2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbAccount2 = new JLabel();
        lbAccount2.setText("Choose paying account:");
        panelPay.add(lbAccount2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAccount2 = new JComboBox();
        panelPay.add(cbAccount2, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbDate2 = new JLabel();
        lbDate2.setText("Enter the date you want to pay back your loan (YYYY-MM-DD): ");
        panelPay.add(lbDate2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfDate2 = new JTextField();
        panelPay.add(tfDate2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbType2 = new JLabel();
        lbType2.setText("Choose type of paying account:");
        panelPay.add(lbType2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbType2 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Checking Account");
        defaultComboBoxModel2.addElement("Savings Account");
        cbType2.setModel(defaultComboBoxModel2);
        panelPay.add(cbType2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btConfirm = new JButton();
        btConfirm.setText("Confirm");
        rootPanel.add(btConfirm, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCancel = new JButton();
        btCancel.setText("Cancel");
        rootPanel.add(btCancel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    /**
     * Method for getting root panel
     * @return
     */
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Method for displaying confirmation info for taking loan
     */
    public void displayConfirmForTake() {
        String mesg = "Take Loan Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Method for displaying confirmation info for paying loan
     */
    public void displayConfirmForPay() {
        String mesg = "Pay Loan Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }
}

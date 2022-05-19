package com.company.GUI.Customer;

import com.company.Account.Account;
import com.company.Bank.CustomerAllOptions.CustomerBalance;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.GUI.PageManager;
import com.company.GUI.MyPage;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.DepositOrWithdrawTxn;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.company.Account.AccountType.CHECKINGS;
import static com.company.Account.AccountType.SAVINGS;

/**
 * Class of page for depositing money into accounts
 */
public class PageAccountDeposit implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbCurrency;
    private JComboBox cbCurrency;
    private JLabel lbAmount;
    private JTextField tfAmount;
    private JLabel lbAccount;
    private JComboBox cbAccount;
    private JButton btConfirm;
    private JButton btCancel;
    private JLabel lbType;
    private JComboBox cbType;

    private String accountTypeChosen;
    private String currencyChosen;
    private String accountIDChosen;

    /**
     * Constructor
     * @param customer
     * @param currency Holds Forex info for the day
     * @param stockMarket Holds stock info for the day
     */
    public PageAccountDeposit(Customer customer, Currency currency, StockMarket stockMarket) {
        Writer writer = new Writer();
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageManager.backToOldPage();
            }
        });
        cbCurrency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currencyChosen = (String) cbCurrency.getSelectedItem();
            }
        });
        cbType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountTypeChosen = (String) cbType.getSelectedItem();
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
                    cbAccount.setModel(cbModel);
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
                    cbAccount.setModel(cbModel);
                }
            }
        });
        cbAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbAccount.getSelectedItem();
                accountIDChosen = selectedItem.split(",")[0];
            }
        });
        btConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(tfAmount.getText());
                if (accountTypeChosen.equals("Savings Account")) {
                    List<Account> AllSavingsAccounts = customer.getAccountsByType(SAVINGS);
                    for (Account account : AllSavingsAccounts) {
                        if (account.getAccountId().equals(accountIDChosen)) {
                            if (currencyChosen.equals("USD")) {
                                account.addToBalance(CurrencyType.USD, value);
                            } else if (currencyChosen.equals("EUR")) {
                                account.addToBalance(CurrencyType.EUR, value);
                            } else if (currencyChosen.equals("CAD")) {
                                account.addToBalance(CurrencyType.CAD, value);
                            } else if (currencyChosen.equals("JPY")) {
                                account.addToBalance(CurrencyType.JPY, value);
                            }
                            try {
                                writer.updateAccountToDisk(account);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    try {
                        writer.writeTxn(recordTransaction(value, customer.getId()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                else if (accountTypeChosen.equals("Checking Account")) {
                    List<Account> AllCheckingsAccounts = customer.getAccountsByType(CHECKINGS);
                    for (Account account : AllCheckingsAccounts) {
                        if (account.getAccountId().equals(accountIDChosen)) {
                            if (currencyChosen.equals("USD")) {
                                account.addToBalance(CurrencyType.USD, value);
                            } else if (currencyChosen.equals("EUR")) {
                                account.addToBalance(CurrencyType.EUR, value);
                            } else if (currencyChosen.equals("CAD")) {
                                account.addToBalance(CurrencyType.CAD, value);
                            } else if (currencyChosen.equals("JPY")) {
                                account.addToBalance(CurrencyType.JPY, value);
                            }
                            try {
                                writer.updateAccountToDisk(account);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    try {
                        writer.writeTxn(recordTransaction(value, customer.getId()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                displayConfirm();
            }
        });
    }

    /**
     * Method responsible for recording transactions
     * @param amount Amount transacted
     * @param cusID Customer involved in transaction
     * @return Transaction info
     */
    public static Transaction recordTransaction(Double amount, String cusID) {

        String ID = getRandomNumberString();
        String customerID = cusID;
        Double value = amount;
        LocalDate date = LocalDate.now();
        DepositOrWithdrawTxn transaction = new DepositOrWithdrawTxn(ID, date, value, customerID);

        return transaction;
    }

    /**
     * Method responsible for generating random six-digit ID
     * @return Six digit ID
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
        rootPanel.setLayout(new GridLayoutManager(11, 1, new Insets(0, 0, 0, 0), -1, -1));
        lbTitle = new JLabel();
        lbTitle.setText("Deposit");
        rootPanel.add(lbTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbAmount = new JLabel();
        lbAmount.setText("Enter deposit amount:");
        rootPanel.add(lbAmount, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfAmount = new JTextField();
        rootPanel.add(tfAmount, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbAccount = new JLabel();
        lbAccount.setText("Choose account ID:");
        rootPanel.add(lbAccount, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAccount = new JComboBox();
        rootPanel.add(cbAccount, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btConfirm = new JButton();
        btConfirm.setText("Confirm");
        rootPanel.add(btConfirm, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCancel = new JButton();
        btCancel.setText("Cancel");
        rootPanel.add(btCancel, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbType = new JLabel();
        lbType.setText("Choose account type:");
        rootPanel.add(lbType, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Savings Account");
        defaultComboBoxModel1.addElement("Checking Account");
        cbType.setModel(defaultComboBoxModel1);
        rootPanel.add(cbType, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCurrency = new JLabel();
        lbCurrency.setText("Choose deposit currency:");
        rootPanel.add(lbCurrency, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbCurrency = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("USD");
        defaultComboBoxModel2.addElement("EUR");
        defaultComboBoxModel2.addElement("CAD");
        defaultComboBoxModel2.addElement("JPY");
        cbCurrency.setModel(defaultComboBoxModel2);
        rootPanel.add(cbCurrency, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    /**
     * Method for getting root panel
     * @return root panel
     */
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    /**
     * Method for displaying confirmation info
     */
    public void displayConfirm() {
        String mesg = "Deposit Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }
}

package com.company.GUI.Customer;

import com.company.Account.Account;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Factories.AccountFactory;
import com.company.GUI.PageManager;
import com.company.GUI.MyPage;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.Transaction;
import com.company.Transactions.TransferTxn;
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

import static com.company.Account.AccountType.*;

/**
 * Class of page for transfering money between accounts
 */
public class PageAccountTransfer implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbTypeFrom;
    private JComboBox cbTypeFrom;
    private JLabel lbTypeTo;
    private JComboBox cbTypeTo;
    private JLabel lbCurrency;
    private JComboBox cbCurrency;
    private JLabel lbAmount;
    private JTextField tfAmount;
    private JButton btConfirm;
    private JButton btCancel;
    private JLabel lbAccountFrom;
    private JComboBox cbAccountFrom;
    private JLabel lbAccountTo;
    private JComboBox cbAccountTo;

    private String currencyChosen;
    private String accountFromTypeChosen;
    private String accountFromIDChosen;
    private String accountToTypeChosen;
    private String accountToIDChosen;

    /**
     * Constructor
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket  Holds Stock info for the day
     */
    public PageAccountTransfer(Customer customer, Currency currency, StockMarket stockMarket) {
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
        cbTypeFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountFromTypeChosen = (String) cbTypeFrom.getSelectedItem();
                if (accountFromTypeChosen.equals("Savings Account")) {
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
                    cbAccountFrom.setModel(cbModel);
                } else if (accountFromTypeChosen.equals("Checking Account")) {
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
                    cbAccountFrom.setModel(cbModel);
                } else if (accountFromTypeChosen.equals("Stock Account")) {
                    ArrayList<String> accountOptions = new ArrayList<>();
                    List<Account> AllStockAccounts = customer.getAccountsByType(STOCK);
                    for (Account account : AllStockAccounts) {
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
                    cbAccountFrom.setModel(cbModel);
                }
            }
        });
        cbTypeTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountToTypeChosen = (String) cbTypeTo.getSelectedItem();
                if (accountToTypeChosen.equals("Savings Account")) {
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
                    cbAccountTo.setModel(cbModel);
                } else if (accountToTypeChosen.equals("Checking Account")) {
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
                    cbAccountTo.setModel(cbModel);
                } else if (accountToTypeChosen.equals("Stock Account")) {
                    ArrayList<String> accountOptions = new ArrayList<>();
                    List<Account> AllStockAccounts = customer.getAccountsByType(STOCK);
                    for (Account account : AllStockAccounts) {
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
                    cbAccountTo.setModel(cbModel);
                }
            }
        });
        cbAccountFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbAccountFrom.getSelectedItem();
                accountFromIDChosen = selectedItem.split(",")[0];
            }
        });
        cbAccountTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbAccountTo.getSelectedItem();
                accountToIDChosen = selectedItem.split(",")[0];
            }
        });
        btConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(tfAmount.getText());
                if (accountFromTypeChosen.equals("Savings Account")) {
                    List<Account> AllSavingsAccounts = customer.getAccountsByType(SAVINGS);
                    for (Account account : AllSavingsAccounts) {
                        if (account.getAccountId().equals(accountFromIDChosen)) {
                            if (currencyChosen.equals("USD")) {
                                account.addToBalance(CurrencyType.USD, -value);
                                try {
                                    feeToBank(CurrencyType.USD);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            } else if (currencyChosen.equals("EUR")) {
                                account.addToBalance(CurrencyType.EUR, -value);
                                try {
                                    feeToBank(CurrencyType.EUR);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            } else if (currencyChosen.equals("CAD")) {
                                account.addToBalance(CurrencyType.CAD, -value);
                                try {
                                    feeToBank(CurrencyType.CAD);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            } else if (currencyChosen.equals("JPY")) {
                                account.addToBalance(CurrencyType.JPY, -value);
                                try {
                                    feeToBank(CurrencyType.JPY);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                            try {
                                writer.updateAccountToDisk(account);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    try {
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountFromTypeChosen.equals("Checking Account")) {
                    List<Account> AllCheckingsAccounts = customer.getAccountsByType(CHECKINGS);
                    for (Account account : AllCheckingsAccounts) {
                        if (account.getAccountId().equals(accountFromIDChosen)) {
                            if (currencyChosen.equals("USD")) {
                                account.addToBalance(CurrencyType.USD, -value);
                            } else if (currencyChosen.equals("EUR")) {
                                account.addToBalance(CurrencyType.EUR, -value);
                            } else if (currencyChosen.equals("CAD")) {
                                account.addToBalance(CurrencyType.CAD, -value);
                            } else if (currencyChosen.equals("JPY")) {
                                account.addToBalance(CurrencyType.JPY, -value);
                            }
                            try {
                                writer.updateAccountToDisk(account);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    try {
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountFromTypeChosen.equals("Stock Account")) {
                    List<Account> AllStockAccounts = customer.getAccountsByType(STOCK);
                    for (Account account : AllStockAccounts) {
                        if (account.getAccountId().equals(accountFromIDChosen)) {
                            if (currencyChosen.equals("USD")) {
                                account.addToBalance(CurrencyType.USD, -value);
                            } else if (currencyChosen.equals("EUR")) {
                                account.addToBalance(CurrencyType.EUR, -value);
                            } else if (currencyChosen.equals("CAD")) {
                                account.addToBalance(CurrencyType.CAD, -value);
                            } else if (currencyChosen.equals("JPY")) {
                                account.addToBalance(CurrencyType.JPY, -value);
                            }
                            try {
                                writer.updateAccountToDisk(account);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                    try {
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                if (accountToTypeChosen.equals("Savings Account")) {
                    List<Account> AllSavingsAccounts = customer.getAccountsByType(SAVINGS);
                    for (Account account : AllSavingsAccounts) {
                        if (account.getAccountId().equals(accountToIDChosen)) {
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
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountToTypeChosen.equals("Checking Account")) {
                    List<Account> AllCheckingAccounts = customer.getAccountsByType(CHECKINGS);
                    for (Account account : AllCheckingAccounts) {
                        if (account.getAccountId().equals(accountToIDChosen)) {
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
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountToTypeChosen.equals("Stock Account")) {
                    List<Account> AllStockAccounts = customer.getAccountsByType(STOCK);
                    for (Account account : AllStockAccounts) {
                        if (account.getAccountId().equals(accountToIDChosen)) {
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
                        writer.writeTxn(recordTransaction(value, customer.getId(), accountFromIDChosen, accountToIDChosen));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                displayConfirm();
            }
        });
    }

    /**
     * Method for handling service fee
     * @param currencyType
     * @throws IOException to ensure proper file parsing
     */
    public static void feeToBank(CurrencyType currencyType) throws IOException {

        AccountFactory accountFactory = new AccountFactory();
        List<Account> allAdminAccounts = accountFactory.produceAccountsByType(ADMIN);
        Account adminAccount = allAdminAccounts.get(0);
        if (currencyType.equals(CurrencyType.USD)) {
            adminAccount.addToBalance(CurrencyType.USD, 5.00);
        } else if (currencyType.equals(CurrencyType.EUR)) {
            adminAccount.addToBalance(CurrencyType.EUR, 5.00);
        } else if (currencyType.equals(CurrencyType.CAD)) {
            adminAccount.addToBalance(CurrencyType.CAD, 5.00);
        } else if (currencyType.equals(CurrencyType.JPY)) {
            adminAccount.addToBalance(CurrencyType.JPY, 5.00);
        }

        Writer writer = new Writer();
        writer.updateAccountToDisk(adminAccount);
    }

    /**
     * Method responsible for recording transactions
     * @param amount Amount transacted
     * @param cusID Customer involved in transaction
     * @return Transaction info
     */
    public static Transaction recordTransaction(Double amount, String cusID, String from, String to) {

        String ID = getRandomNumberString();
        String customerID = cusID;
        Double value = -amount;
        LocalDate date = LocalDate.now();
        String fromID = from;
        String toID = to;
        TransferTxn transaction = new TransferTxn(ID, date, value, cusID, fromID, cusID, toID);

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
        rootPanel.setLayout(new GridLayoutManager(15, 1, new Insets(0, 0, 0, 0), -1, -1));
        lbTitle = new JLabel();
        lbTitle.setText("Account Transfer");
        rootPanel.add(lbTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbTypeFrom = new JLabel();
        lbTypeFrom.setText("Choose account type (transfer from):");
        rootPanel.add(lbTypeFrom, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbTypeFrom = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Savings Account");
        defaultComboBoxModel1.addElement("Checking Account");
        defaultComboBoxModel1.addElement("Stock Account");
        cbTypeFrom.setModel(defaultComboBoxModel1);
        rootPanel.add(cbTypeFrom, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbTypeTo = new JLabel();
        lbTypeTo.setText("Choose account type (transfer to):");
        rootPanel.add(lbTypeTo, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbTypeTo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Savings Account");
        defaultComboBoxModel2.addElement("Checking Account");
        defaultComboBoxModel2.addElement("Stock Account");
        cbTypeTo.setModel(defaultComboBoxModel2);
        rootPanel.add(cbTypeTo, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbAmount = new JLabel();
        lbAmount.setText("Enter transfer amount:");
        rootPanel.add(lbAmount, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfAmount = new JTextField();
        rootPanel.add(tfAmount, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btConfirm = new JButton();
        btConfirm.setText("Confirm");
        rootPanel.add(btConfirm, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCancel = new JButton();
        btCancel.setText("Cancel");
        rootPanel.add(btCancel, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCurrency = new JLabel();
        lbCurrency.setText("Choose transfer currency:");
        rootPanel.add(lbCurrency, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbCurrency = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("USD");
        defaultComboBoxModel3.addElement("EUR");
        defaultComboBoxModel3.addElement("CAD");
        defaultComboBoxModel3.addElement("JPY");
        cbCurrency.setModel(defaultComboBoxModel3);
        rootPanel.add(cbCurrency, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbAccountFrom = new JLabel();
        lbAccountFrom.setText("Choose account ID (transfer from):");
        rootPanel.add(lbAccountFrom, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAccountFrom = new JComboBox();
        rootPanel.add(cbAccountFrom, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbAccountTo = new JLabel();
        lbAccountTo.setText("Choose account ID (transfer to):");
        rootPanel.add(lbAccountTo, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAccountTo = new JComboBox();
        rootPanel.add(cbAccountTo, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        String mesg = "Transfer Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }
}

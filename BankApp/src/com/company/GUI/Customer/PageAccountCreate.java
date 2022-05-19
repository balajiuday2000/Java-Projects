package com.company.GUI.Customer;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.AccountNotExistException;
import com.company.Factories.AccountFactory;
import com.company.GUI.PageManager;
import com.company.GUI.MyPage;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Utils.Writer;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static com.company.Account.AccountType.ADMIN;

/**
 * Class of page for creating new customer accounts
 */
public class PageAccountCreate implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbType;
    private JComboBox cbType;
    private JLabel lbCurrency;
    private JComboBox cbCurrency;
    private JLabel lbAmount;
    private JTextField tfAmount;
    private JButton btConfirm;
    private JButton btCancel;

    private String typeChosen;
    private String currencyChosen;

    /**
     * Constructor
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket  Holds Stock info for the day
     */
    public PageAccountCreate(Customer customer, Currency currency, StockMarket stockMarket) {
        AccountFactory accountFactory = new AccountFactory();
        Writer writer = new Writer();

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageManager.backToOldPage();
            }
        });
        cbType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeChosen = (String) cbType.getSelectedItem();
            }
        });
        cbCurrency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currencyChosen = (String) cbCurrency.getSelectedItem();
            }
        });
        btConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeChosen.equals("Stock Account")) {
                    try {
                        List<Account> allStockAccounts = customer.getAccountsByType(AccountType.STOCK);
                    } catch (AccountNotExistException e2) {
                        String accountNo = getRandomNumberString();
                        String[] args = {accountNo, customer.getName(), customer.getPwd(), "STOCK", " ", " "};
                        Account newAccount = accountFactory.produceAccount(args);
                        customer.addAccount(newAccount);
                        try {
                            writer.grantNewAccount(customer, newAccount, true);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                } else if (typeChosen.equals("Savings Account")) {
                    double value = Double.parseDouble(tfAmount.getText());
                    value -= 5.0;
                    String accountNo = getRandomNumberString();
                    String values = null;
                    if (currencyChosen.equals("USD")) {
                        values = "USD" + " " + value;
                        try {
                            feeToBank(CurrencyType.USD);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("EUR")) {
                        values = "EUR" + " " + value;
                        try {
                            feeToBank(CurrencyType.EUR);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("CAD")) {
                        values = "CAD" + " " + value;
                        try {
                            feeToBank(CurrencyType.CAD);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("JPY")) {
                        values = "JPY" + " " + value;
                        try {
                            feeToBank(CurrencyType.JPY);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    String[] args = {accountNo, customer.getName(), customer.getPwd(), "SAVINGS", values};
                    Account newAccount = accountFactory.produceAccount(args);
                    customer.addAccount(newAccount);
                    try {
                        writer.grantNewAccount(customer, newAccount, true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (typeChosen.equals("Checking Account")) {
                    double value = Double.parseDouble(tfAmount.getText());
                    value -= 5.0;
                    String accountNo = getRandomNumberString();
                    String values = null;
                    if (currencyChosen.equals("USD")) {
                        values = "USD" + " " + value;
                        try {
                            feeToBank(CurrencyType.USD);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("EUR")) {
                        values = "EUR" + " " + value;
                        try {
                            feeToBank(CurrencyType.EUR);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("CAD")) {
                        values = "CAD" + " " + value;
                        try {
                            feeToBank(CurrencyType.CAD);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (currencyChosen.equals("JPY")) {
                        values = "JPY" + " " + value;
                        try {
                            feeToBank(CurrencyType.JPY);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    String[] args = {accountNo, customer.getName(), customer.getPwd(), "CHECKINGS", values};
                    Account newAccount = accountFactory.produceAccount(args);
                    customer.addAccount(newAccount);
                    try {
                        writer.grantNewAccount(customer, newAccount, true);
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
     * @param currencyType Holds Currency Type
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
     * Method for generating random number strings
     * @return a string of random number
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
        rootPanel.setLayout(new GridLayoutManager(9, 1, new Insets(0, 0, 0, 0), -1, -1));
        lbTitle = new JLabel();
        lbTitle.setText("Create New Account");
        rootPanel.add(lbTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbType = new JLabel();
        lbType.setText("Choose type of account:");
        rootPanel.add(lbType, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Savings Account");
        defaultComboBoxModel1.addElement("Checking Account");
        defaultComboBoxModel1.addElement("Stock Account");
        cbType.setModel(defaultComboBoxModel1);
        rootPanel.add(cbType, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCurrency = new JLabel();
        lbCurrency.setText("Choose deposit currency:");
        rootPanel.add(lbCurrency, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbCurrency = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("USD");
        defaultComboBoxModel2.addElement("EUR");
        defaultComboBoxModel2.addElement("CAD");
        defaultComboBoxModel2.addElement("JPY");
        cbCurrency.setModel(defaultComboBoxModel2);
        rootPanel.add(cbCurrency, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbAmount = new JLabel();
        lbAmount.setText("Enter deposit amount:");
        rootPanel.add(lbAmount, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfAmount = new JTextField();
        rootPanel.add(tfAmount, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btConfirm = new JButton();
        btConfirm.setText("Confirm");
        rootPanel.add(btConfirm, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btCancel = new JButton();
        btCancel.setText("Cancel");
        rootPanel.add(btCancel, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        String mesg = "Creation Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }
}

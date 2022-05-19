package com.company.GUI.Customer;

import com.company.Account.Account;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.company.Account.AccountType.*;
import static com.company.Currency.CurrencyType.USD;

/**
 * Class of page for closing customer accounts
 */
public class PageAccountClose implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbType;
    private JComboBox cbType;
    private JLabel lbAccount;
    private JComboBox cbAccount;
    private JButton btConfirm;
    private JButton btCancel;

    private String accountIDChosen;
    private String accountTypeChosen;

    /**
     * Constructor
     * @param customer Holds info about current customer
     * @param currency Holds Forex info for the day
     * @param stockMarket  Holds Stock info for the day
     */
    public PageAccountClose(Customer customer, Currency currency, StockMarket stockMarket) {
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
                } else if (accountTypeChosen.equals("Stock Account")) {
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
                if (accountTypeChosen.equals("Savings Account")) {
                    customer.deleteAccount(SAVINGS, accountIDChosen);
                    try {
                        writer.deleteAccount(SAVINGS, accountIDChosen, customer);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountTypeChosen.equals("Checking Account")) {
                    customer.deleteAccount(CHECKINGS, accountIDChosen);
                    try {
                        writer.deleteAccount(CHECKINGS, accountIDChosen, customer);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (accountTypeChosen.equals("Stock Account")) {
                    customer.deleteAccount(STOCK, accountIDChosen);
                    try {
                        writer.deleteAccount(STOCK, accountIDChosen, customer);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                try {
                    feeToBank(customer);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                displayConfirm();
            }
        });
    }

    /**
     * Method for handling service fee
     * @param customer Holds info about current customer
     * @throws IOException to ensure proper file parsing
     */
    public static void feeToBank(Customer customer) throws IOException {

        Writer writer = new Writer();

        AccountFactory accountFactory = new AccountFactory();
        List<Account> allAdminAccounts = accountFactory.produceAccountsByType(ADMIN);
        Account adminAccount = allAdminAccounts.get(0);
        adminAccount.addToBalance(USD, 5.00);

        List<Account> allAccounts;

        try {
            allAccounts = customer.getAccountsByType(CHECKINGS);
        } catch (AccountNotExistException e) {
            allAccounts = customer.getAccountsByType(SAVINGS);
        }

        for (Account account : allAccounts) {
            Map<CurrencyType, Double> map = account.getBalance();
            double value = map.get(USD);
            if (value > 5.0) {
                map.put(USD, value - 5.0);
                account.setAllBalance(map);
                writer.updateAccountToDisk(account);
                break;
            }
        }
        writer.updateAccountToDisk(adminAccount);
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
        lbTitle.setText("Close Account");
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
        lbAccount = new JLabel();
        lbAccount.setText("Choose account id:");
        rootPanel.add(lbAccount, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbAccount = new JComboBox();
        rootPanel.add(cbAccount, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        String mesg = "Close Account Success.";
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }
}

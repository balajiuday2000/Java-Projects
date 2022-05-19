package com.company.GUI.Customer;

import com.company.Account.Account;
import com.company.Account.AccountType;
import com.company.Account.StockAccount;
import com.company.Currency.Currency;
import com.company.Currency.CurrencyType;
import com.company.Exceptions.AccountNotExistException;
import com.company.Factories.AccountFactory;
import com.company.GUI.MyPage;
import com.company.GUI.PageManager;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Transactions.StockTxn;
import com.company.Transactions.Transaction;
import com.company.Utils.Parser;
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

/**
 * Class of page for stock market
 */
public class PageServiceStockMarket implements MyPage {
    private JPanel rootPanel;
    private JLabel lbTitle;
    private JLabel lbService;
    private JComboBox cbService;
    private JPanel p1;
    private JLabel lbStock;
    private JComboBox cbStock;
    private JLabel lbCurrency;
    private JComboBox cbCurrency;
    private JLabel lbShares;
    private JTextField tfShares;
    private JPanel p2;
    private JLabel lbStock2;
    private JComboBox cbStock2;
    private JLabel lbShares2;
    private JTextField tfShares2;
    private JButton btConfirm;
    private JButton btCancel;

    private Map<String, Double> allStocks;
    private List<Account> stockAccounts;
    private Map<String, Integer> sharesHolding;
    private double price;
    private Map<String, Integer> purchaseInfo = new HashMap<>();


    /**
     * Constructor
     * @param customer
     * @param currency
     * @param stockMarket
     */
    public PageServiceStockMarket(Customer customer, Currency currency, StockMarket stockMarket) {
        p1.setVisible(false);
        p2.setVisible(false);

        // init all the data
        Writer writer = new Writer();
        Parser parser = new Parser();
        allStocks = parser.parseAllStockInfo();
        stockAccounts = new LinkedList<>();
        try {
            stockAccounts = customer.getAccountsByType(AccountType.STOCK);
        } catch (AccountNotExistException e) {
            AccountFactory accountFactory = new AccountFactory();
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
        sharesHolding = new HashMap<>();
        for (Account stockAccount : stockAccounts) {
            sharesHolding = ((StockAccount) stockAccount).getSharesHolding();
        }
        String[] cbOptions = new String[allStocks.size()];
        int counter = 0;
        for (Map.Entry<String, Double> entry : allStocks.entrySet()) {
            String option = entry.getKey() + ", " + "price=" + entry.getValue();
            cbOptions[counter] = option;
            DefaultComboBoxModel cbModel = new DefaultComboBoxModel(cbOptions);
            cbStock.setModel(cbModel);
            cbStock2.setModel(cbModel);
            counter++;
        }

        // set up all listeners
        cbService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbService.getSelectedItem().equals("Buy Stocks")) {
                    p1.setVisible(true);
                    p2.setVisible(false);
                } else {
                    p1.setVisible(false);
                    p2.setVisible(true);
                }
            }
        });
        btConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbService.getSelectedItem().equals("Buy Stocks")) {
                    for (Account stockAccount : stockAccounts) {
                        String currencyChosen = (String) cbCurrency.getSelectedItem();
                        String stockChosen = (String) cbStock.getSelectedItem();
                        stockChosen = stockChosen.split(",")[0];
                        int noOfShares = Integer.valueOf(tfShares.getText());
                        if (currencyChosen.equals("USD")) {
                            try {
                                ((StockAccount) stockAccount).buyShare(stockChosen, noOfShares, CurrencyType.USD);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else if (currencyChosen.equals("EUR")) {
                            try {
                                ((StockAccount) stockAccount).buyShare(stockChosen, noOfShares, CurrencyType.EUR);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else if (currencyChosen.equals("CAD")) {
                            try {
                                ((StockAccount) stockAccount).buyShare(stockChosen, noOfShares, CurrencyType.CAD);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        } else if (currencyChosen.equals("JPY")) {
                            try {
                                ((StockAccount) stockAccount).buyShare(stockChosen, noOfShares, CurrencyType.JPY);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                        price = allStocks.get(stockChosen);
                        purchaseInfo.put(stockChosen, noOfShares);
                        try {
                            writer.updateAccountToDisk(stockAccount);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    displayConfirm(1);
                }
                else {
                    String stockChosen = (String) cbStock2.getSelectedItem();
                    stockChosen = stockChosen.split(",")[0];
                    int noOfShares = Integer.valueOf(tfShares2.getText());
                    for (Account stockAccount : stockAccounts) {
                        ((StockAccount) stockAccount).sellShare(stockChosen, noOfShares);
                        price = allStocks.get(stockChosen);
                        purchaseInfo.put(stockChosen, -noOfShares);
                        try {
                            writer.updateAccountToDisk(stockAccount);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    displayConfirm(2);
                }

                try {
                    writer.writeTxn(recordTransaction(price, customer.getId(), purchaseInfo));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PageManager.backToOldPage();
            }
        });
    }

    /**
     * Method for logging transaction records
     * @param amount
     * @param cusID
     * @param purchaseInfo
     * @return
     */
    public static Transaction recordTransaction(Double amount, String cusID, Map<String, Integer> purchaseInfo) {

        String ID = getRandomNumberString();
        String customerID = cusID;
        Double value = amount;
        LocalDate date = LocalDate.now();
        StockTxn transaction = new StockTxn(ID, date, amount, customerID, purchaseInfo);
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
        lbTitle.setText("Stock Market");
        rootPanel.add(lbTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbService = new JLabel();
        lbService.setText("Choose service:");
        rootPanel.add(lbService, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbService = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Buy Stocks");
        defaultComboBoxModel1.addElement("Sell Stocks");
        cbService.setModel(defaultComboBoxModel1);
        rootPanel.add(cbService, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p1 = new JPanel();
        p1.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(p1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbStock = new JLabel();
        lbStock.setText("Choose stock to buy:");
        p1.add(lbStock, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbStock = new JComboBox();
        p1.add(cbStock, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCurrency = new JLabel();
        lbCurrency.setText("Choose currency:");
        p1.add(lbCurrency, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbCurrency = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("USD");
        defaultComboBoxModel2.addElement("EUR");
        defaultComboBoxModel2.addElement("CAD");
        defaultComboBoxModel2.addElement("JPY");
        cbCurrency.setModel(defaultComboBoxModel2);
        p1.add(cbCurrency, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbShares = new JLabel();
        lbShares.setText("Enter number of shares:");
        p1.add(lbShares, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfShares = new JTextField();
        p1.add(tfShares, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        p2 = new JPanel();
        p2.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(p2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbStock2 = new JLabel();
        lbStock2.setText("Choose stock to sell:");
        p2.add(lbStock2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbStock2 = new JComboBox();
        p2.add(cbStock2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbShares2 = new JLabel();
        lbShares2.setText("Enter number of shares:");
        p2.add(lbShares2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfShares2 = new JTextField();
        p2.add(tfShares2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
     * Method for displaying confirmation info
     * @param i
     */
    public void displayConfirm(int i) {
        String mesg = null;
        if (i == 1) {
            mesg = "Buy Stock Success";
        }
        else if (i == 2) {
            mesg = "Sell Stock Success";
        }
        JOptionPane.showMessageDialog(
                rootPanel,
                mesg,
                "Message",
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Method for getting root panel
     * @return
     */
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

}

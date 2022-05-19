package com.company.GUI;

import com.company.Account.LoanAccount;
import com.company.Bank.BankATM;
import com.company.Bank.CustomerAllOptions.CreateCustomer;
import com.company.Bank.CustomerAllOptions.CustomerOptions;
import com.company.Bank.ManagerAllOptions.ManagerOptions;
import com.company.Currency.Currency;
import com.company.Factories.AccountFactory;
import com.company.Factories.PersonFactory;
import com.company.Persons.Customer;
import com.company.Stock.StockMarket;
import com.company.Utils.Printer;
import com.company.Utils.Writer;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Class of GUI main frame
 * contains layered panel for controlling page flow
 */
public class MyFrame {
    private static JFrame frame;
    private static JLayeredPane layeredPanels;
    private static ArrayList<MyPage> pages;

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 600;

    /**
     * Constructor
     */
    public MyFrame() throws IOException{
        // set up data
        com.company.Currency.Currency currency = new Currency();
        StockMarket stockMarket = StockMarket.getInstance();
        Writer writer = new Writer();

        AccountFactory accountFactory = new AccountFactory();
        LocalDate localDate = LocalDate.now();
        List<LoanAccount> overDueAccounts = accountFactory.produceOverdueAccounts(localDate);
        for(LoanAccount account : overDueAccounts){
            Map<LocalDate, Double> map = account.getAmountsDue();
            Set<LocalDate> dates = map.keySet();
            for(LocalDate date : dates){
                if (date.isBefore(localDate.minusMonths(1))){
                    Double value = map.get(date);
                    value += (value * (0.15));
                    map.remove(date);
                    map.put(localDate, value);
                }
            }
            account.setAmountsDue(map);
            writer.updateAccountToDisk(account);
        }

        // init frame
        frame = new JFrame();
        frame.setTitle("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(600,600);

        // init layered pane
        layeredPanels = new JLayeredPane();
        layeredPanels.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // init entry page
        PageAppEntry entryPage = new PageAppEntry(currency, stockMarket);
        entryPage.getRootPanel().setBounds(0,0,FRAME_WIDTH, FRAME_HEIGHT);
        pages = new ArrayList<>();
        pages.add(entryPage);
        layeredPanels.add(entryPage.getRootPanel(), Integer.valueOf(0));

        // set up frame
        frame.add(layeredPanels);
        frame.setVisible(true);
    }


    // getters
    public static JFrame getFrame() {
        return frame;
    }

    public static JLayeredPane getLayeredPanels() {
        return layeredPanels;
    }

    public static ArrayList<MyPage> getPages() {
        return pages;
    }

    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }
}

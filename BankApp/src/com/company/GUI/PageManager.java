package com.company.GUI;

import com.company.Persons.Customer;

import java.io.IOException;

/**
 * Class responsible for managing page jumping
 */
public class PageManager extends MyFrame{

    /**
     * Constructor
     * @throws IOException
     */
    public PageManager() throws IOException {
    }

    /**
     * Method responsible for jumping to a newly created page
     * @param newPage
     */
    public static void jumpToNewPage(MyPage newPage){
        getLayeredPanels().getComponent(getPages().size()-1).setEnabled(false);
        newPage.getRootPanel().setBounds(0,0,getFrameWidth(),getFrameHeight());
        getPages().add(newPage);
        int layers = getLayeredPanels().getComponentCount();
        getLayeredPanels().add(newPage.getRootPanel(), Integer.valueOf(layers));
        getLayeredPanels().repaint();
        getLayeredPanels().revalidate();
    }

    /**
     * Method responsible for jumping back to the old page
     */
    public static void backToOldPage(){
        getPages().remove(getPages().size()-1);
        getLayeredPanels().remove(0);
        getLayeredPanels().getComponent(getPages().size()-1).setEnabled(true);
        getLayeredPanels().repaint();
        getLayeredPanels().revalidate();
    }

}

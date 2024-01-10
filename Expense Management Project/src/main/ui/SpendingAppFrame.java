package ui;

import javax.swing.*;
import java.awt.event.WindowListener;


//Code influenced by example provided at edx phase 3
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application

//represent a spending management application window
public class SpendingAppFrame extends JFrame {

    private SpendingAppPanel menuPanel;
    private ImageIcon imageIcon;
    private LogPrinter logPrinter;



    //EFFECTS: initialize the basic setting of the spending management application window
    public SpendingAppFrame() {
        super("Spending Management Helper");
        imageIcon = new ImageIcon("data/projectIcon.png");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        logPrinter = new LogPrinter();
        this.addWindowListener(logPrinter);
        menuPanel = new SpendingAppPanel();
        add(menuPanel);
        this.setIconImage(imageIcon.getImage());
        pack();
        setVisible(true);
    }


}

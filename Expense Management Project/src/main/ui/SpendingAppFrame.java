package ui;

import javax.swing.*;
import java.awt.*;


//Code influenced by example provided at edx phase 3
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application

//represent a spending management application window
public class SpendingAppFrame extends JFrame {

    private SpendingAppPanel menuPanel;
    private ImageIcon imageIcon;
    private LogPrinter logPrinter;
    private static final int HEIGHT = 500;
    private static final int WIDTH = 450;



    //EFFECTS: initialize the basic setting of the spending management application window
    public SpendingAppFrame() {
        super("Spending Management Helper");
        imageIcon = new ImageIcon("./Expense Management Project/data/projectIcon.png");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        logPrinter = new LogPrinter();
        this.addWindowListener(logPrinter);
        menuPanel = new SpendingAppPanel();
        add(menuPanel);
        this.setIconImage(imageIcon.getImage());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setVisible(true);
        setResizable(false);
    }


}

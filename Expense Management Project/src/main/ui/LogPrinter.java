package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


//represent a printer that print logs into console
public class LogPrinter implements WindowListener {

    //EFFECTS: do nothing when window open
    @Override
    public void windowOpened(WindowEvent e) {

    }

    //EFFECTS: print out all events that have been logged to the console when user quit the application
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event event :  EventLog.getInstance()) {
            System.out.println(event.getDescription() + " Action performed at: " + event.getDate());

        }

    }

    //EFFECTS: do nothing when window open
    @Override
    public void windowClosed(WindowEvent e) {


    }

    //EFFECTS: do nothing
    @Override
    public void windowIconified(WindowEvent e) {

    }

    //EFFECTS: do nothing
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    //EFFECTS: do nothing
    @Override
    public void windowActivated(WindowEvent e) {

    }

    //EFFECTS: do nothing
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

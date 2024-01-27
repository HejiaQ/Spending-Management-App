package ui;

import model.Expense;
import model.ListOfExpense;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Code related to JOptionPane in this class is influenced by Alarm system
//https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

//Code related to JButton is influenced by traffic light
//https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter/blob/main/src/main/gui/TrafficLightGUI.java

//Code related to JScrollPane, JList, and JTable is influenced by documentation in Oracle
//https://docs.oracle.com/javase/tutorial/uiswing/components/index.html

//represent the panel that have menu and other information for the user interaction
public class SpendingAppPanel extends JPanel {
    private static final String JSON_SAVED = "Expense Management Project/data/listOfExpenses.json";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 450;
    private static final int bu_x = 80;
    private static final int bu_w = 300;
    private static final int bu_h = 30;

    private JButton viewAndManage;
    private JButton addNew;
    private JButton load;
    private JButton save;
    private JLabel welcome;
    private JLabel notification;
    private JScrollPane scrollList;
    private JList<Object> listToDisplay;
    JTextField nameInput = new JTextField();
    JTextField dateInput = new JTextField();
    JTextField amountInput = new JTextField();
    JTextField catInput = new JTextField();


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    private ImageIcon imageMenu;
    private JLabel imageLabel;

    private ListOfExpense listOfExpense;

    //EFFECTS: initialize the panel and components on the panel
    public SpendingAppPanel() {
        setLayout(null);
        viewAndManage = new JButton("View and manage expenses");
        addNew = new JButton("Add new expenses");
        load = new JButton("Load expenses saved last time");
        save = new JButton("Save");
        welcome = new JLabel("Welcome to expenses management application!");
        notification = new JLabel();
        jsonWriter = new JsonWriter(JSON_SAVED);
        jsonReader = new JsonReader(JSON_SAVED);
        listOfExpense = new ListOfExpense();
        scrollList = new JScrollPane();

        //code influenced by traffic light
        //https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter/blob/main/src/main/gui/TrafficLightGUI.java

        imageMenu = new ImageIcon("Expense Management Project/data/imageMenu.jpg");
        imageLabel = new JLabel(imageMenu);


        addToPane();
        setBoundary();
        viewButton();
        saveButton();
        loadButton();
        addButton();
        setBackground(new Color(255, 255, 255));

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }



    //MODIFIES: this
    //EFFECTS: add component to the panel
    private void addToPane() {
        add(welcome);
        add(viewAndManage);
        add(addNew);
        add(load);
        add(save);
        add(notification);
        add(scrollList);
        add(imageLabel);
        scrollList.setVisible(false);
    }

    //code inspired by traffic light GUI
    //https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter/blob/main/src/main/gui/IntersectionGUI.java

    //EFFECTS: add functionality to button to allow user view list of spending
    private void viewButton() {
        viewAndManage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hideMenu();
                displayTablePageButtons();
                displayTable();
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: display the list of expenses on a Jlist
    private void displayTable() {

        ArrayList<String> expenseNames = new ArrayList<>();
        for (Expense expense : listOfExpense.getListOfExpense()) {
            expenseNames.add(expense.getName());
        }
        Object[] expenses = expenseNames.toArray();
        listToDisplay = new JList<>(expenses);

        scrollList.setVisible(true);
        scrollList.setViewportView(listToDisplay);
        repaint();

    }

    //MODIFIES: this
    //EFFECTS: add buttons and set up their functionality to the page where the list of expenses are displayed
    private void displayTablePageButtons() {
        JButton backButton = new JButton("Back");
        JButton removeButton = new JButton("Remove");
        JButton viewDetailButton = new JButton("View Detail");
        JButton calculateButton = new JButton("Calculate Monthly Total");
        setBoundsDisplayPage(backButton, removeButton, calculateButton, viewDetailButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appearMenu();
                hideButtons(backButton);
                hideButtons(removeButton);
                hideButtons(calculateButton);
                hideButtons(viewDetailButton);
                scrollList.setVisible(false);
            }
        });
        removeButton(removeButton);
        calculateButton(calculateButton);
        viewDetail(viewDetailButton);

        this.add(removeButton);
        this.add(calculateButton);
        this.add(backButton);
        this.add(viewDetailButton);
        repaint();
    }

    //EFFECTS: set the buttons position
    private static void setBoundsDisplayPage(JButton backButton, JButton removeButton, JButton calculateButton,
                                             JButton viewButton) {
        backButton.setBounds(110, 400, 100, bu_h);
        removeButton.setBounds(10, 400, 100, bu_h);
        calculateButton.setBounds(210, 400, 200, bu_h);
        viewButton.setBounds(10, 430, 100, bu_h);
    }

    //EFFECTS: add functionality to button to allow displaying detail of the selected item in the list of expenses
    private void viewDetail(JButton viewDetailButton) {
        viewDetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listToDisplay.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select an expense to view.");
                } else {
                    int index = listToDisplay.getSelectedIndex();
                    Expense expense = listOfExpense.getListOfExpense().get(index);
                    JPanel singleExpense = new JPanel();
                    String[] columns = {"Name", "Amount", "Date", "Category", "Rating", "Dollar to Rate Ratio"};
                    String[][] values = {{expense.getName(), String.valueOf(expense.getAmount()),
                            expense.getMonthYear(), expense.getCategory(), String.valueOf(expense.getScore()),
                            String.valueOf(expense.getDollarScoreRatio())}};
                    JTable table = new JTable(values, columns);
                    //Code influenced by the following post (regarding change width of JTable columns)
                    //https://stackoverflow.com/questions/953972/java-jtable-setting-column-width
                    table.getColumnModel().getColumn(5).setMinWidth(150);
                    singleExpense.add(table.getTableHeader());
                    singleExpense.add(table);
                    singleExpense.setLayout(new BoxLayout(singleExpense, BoxLayout.Y_AXIS));

                    JOptionPane.showMessageDialog(null, singleExpense);


                }

            }
        });


    }

    //EFFECTS: add functionality to calculate button to allow user calculate amount of money spent in specified month
    //and year.
    private void calculateButton(JButton calculateButton) {
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userIn = JOptionPane.showInputDialog(null,
                        "please enter year and month that you want to calculate the total amount for.");
                if(userIn == null) {
                    return;
                }
                if (checkValidTime(userIn)) {
                    JOptionPane.showMessageDialog(null, "Invalid input, please try again.");
                } else {
                    double result = listOfExpense.calculateAmountTotal(userIn);
                    JOptionPane.showMessageDialog(null, "Your spending in " + userIn
                            + " is " + result);
                }
            }
        });

    }


    //MODIFIES: this
    //EFFECTS: add functionality to remove button to allow user remove the selected expense
    private void removeButton(JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listToDisplay.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select an expense to remove.");
                } else {
                    int yesOrNo = JOptionPane.showConfirmDialog(null,
                            "Do you want to remove this Expense?");
                    if (yesOrNo == 0) {
                        int index = listToDisplay.getSelectedIndex();
                        listOfExpense.removeExpense(index);
                        displayTable();
                        JOptionPane.showMessageDialog(null, "Successfully removed");
                    }
                }

            }
        });

    }


    //MODIFIES: this
    //EFFECTS: add functionality to add button to allow user adding new expense
    private void addButton() {
        addNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel addPanel = makeAddPanel();
                JTextArea text = new JTextArea();
                text.setText("Please enter information. Category must be one of: Study, Food, Entertainment, Furniture,"
                        + " Luxury, Clothes, Investment, Other Necessity, Other.");
                addPanel.add(text);
                addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
                int yesOrNo;
                yesOrNo = JOptionPane.showConfirmDialog(null, addPanel);
                if (yesOrNo == 0) {
                    addUserInputExpense();

                }

            }
        });

    }

    //MODIFIES: this
    //EFFECTS: check the validity of user input and add the expense if valid; give warning if not valid.
    private void addUserInputExpense() {
        String name = nameInput.getText();
        String amount = amountInput.getText();
        String category = catInput.getText();
        String date = dateInput.getText();
        if (checkAmount(amount)) {
            JOptionPane.showMessageDialog(null, "Cannot added: invalid amount.");
        } else if (checkValidTime(date)) {
            JOptionPane.showMessageDialog(null, "Cannot added: invalid date.");
        } else if (checkValidCategory(category)) {
            JOptionPane.showMessageDialog(null, "Cannot added: invalid category.");
        } else {
            Expense expense = new Expense(name, date, Double.parseDouble(amount), category);
            listOfExpense.addExpenses(expense);
        }

    }


    //EFFECTS: create a panel to display a text field and instruction to allow user enter information about their
    // expenses
    private JPanel makeAddPanel() {
        JPanel addPanel = new JPanel();
        JLabel name = new JLabel();
        JLabel date = new JLabel();
        JLabel amount = new JLabel();
        JLabel category = new JLabel();

        name.setText("Name of the expense");
        date.setText("Date(MM/YYYY)");
        amount.setText("Amount spent");

        category.setText("Category");

        addPanel.add(name);
        addPanel.add(nameInput);
        addPanel.add(date);
        addPanel.add(dateInput);
        addPanel.add(amount);
        addPanel.add(amountInput);
        addPanel.add(category);
        addPanel.add(catInput);


        return addPanel;

    }




    //EFFECTS: add functionality to save button to allow user click the button to save the current list they have
    private void saveButton() {
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

    }

    //EFFECTS: add functionality to load button to allow user click the button to load the previously saved list of
    // expenses
    private void loadButton() {
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

    }




    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //MODIFIES: this
    //EFFECTS: load the list of expenses from file
    private void loadFile() {
        try {
            listOfExpense = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded successfully from " + JSON_SAVED);


        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "ERROR: cannot loaded from " + JSON_SAVED);
        }

    }

    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECTS: save the list of expenses to file
    private void saveFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfExpense);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved successfully to " + JSON_SAVED);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Cannot save to non-existing file: " + JSON_SAVED);

        }
    }

    //EFFECTS: set the position of buttons, images, and instructions at the menu page
    private void setBoundary() {
        welcome.setBounds(bu_x, 40, bu_w, bu_h);
        viewAndManage.setBounds(bu_x, 75, bu_w, bu_h);
        addNew.setBounds(bu_x, 110, bu_w, bu_h);
        save.setBounds(bu_x, 145, bu_w, bu_h);
        load.setBounds(bu_x, 180, bu_w, bu_h);
        notification.setBounds(bu_x, 250, 400, 30);
        scrollList.setBounds(0, 0, 420, 400);
        imageLabel.setBounds(100, 250, 250, 200);


    }


    //MODIFIES: this
    //EFFECTS: make everything in the menu invisible to users
    private void hideMenu() {
        welcome.setVisible(false);
        hideButtons(viewAndManage);
        hideButtons(addNew);
        hideButtons(save);
        hideButtons(load);
        imageLabel.setVisible(false);
        notification.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: make everything in the menu visible to users
    private void appearMenu() {
        welcome.setVisible(true);
        appearButtons(viewAndManage);
        appearButtons(addNew);
        appearButtons(save);
        appearButtons(load);
        notification.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: disable and hide a button
    private void hideButtons(JButton button) {
        button.disable();
        button.setVisible(false);
    }

    //MODIFIES: this
    //EFFECTS: enable a button and make it visible
    private void appearButtons(JButton button) {
        button.enable();
        button.setVisible(true);
        imageLabel.setVisible(true);
    }


    //EFFECT: check if the amount is valid of not; if amount cannot be converted into double return or is not greater
    // than 0, return true; otherwise, return false.
    private boolean checkAmount(String amount) {
        try {
            double amountDouble = Double.parseDouble(amount);
            return amountDouble <= 0;

        } catch (NumberFormatException numberFormatException) {
            return true;
        }

    }


    //EFFECTS: check the validity of the time entered; if the format is wrong or nothing is entered, return ture;
    // otherwise return false;
    private boolean checkValidTime(String time) {
        if (time == null) {
            return true;
        }
        if (time.matches("(0[1-9]|1[0-2])/[0-9]{4}")) {
            return false;
        }

        return true;
    }

    //EFFECTS: check if the category belongs to one of the category specified in user instructions;  if yes, return
    // false, otherwise return true and print out warning message
    private boolean checkValidCategory(String category) {
        boolean b1 = category.equals("Study");
        boolean b2 = category.equals("Food");
        boolean b3 = category.equals("Furniture");
        boolean b4 = category.equals("Luxury");
        boolean b5 = category.equals("Investment");
        boolean b6 = category.equals("Entertainment");
        boolean b7 = category.equals("Other Necessity");
        boolean b8 = category.equals("Other");
        boolean b9 = category.equals("Clothes");

        if (b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9) {
            return false;
        }
        System.out.println("Invalid Category. Please try again.");
        return true;
    }

}

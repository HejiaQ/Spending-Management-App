package ui;

import model.Expense;
import model.ListOfExpense;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//represent the spending manage application console ui
public class SpendingManageApp {
    //learned use of scanner from teller, provided in edx phase 1 instruction

    private static final String JSON_SAVED = "./data/listOfExpenses.json";
    private ListOfExpense listOfExpense;
    private Scanner userInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: create a spending management application with welcome message and run the application
    public SpendingManageApp() throws FileNotFoundException {
        printWelcome();
        handleUserInputs();
    }

    //part of the structure of the class (e.g. usage of handleUSerInputs and executeCmd) is learned from TellerApp and
    //FitLifeGymChain, with many changes and edition


    //MODIFIES: this
    //EFFECTS: initialize the application and allowing user to type their command to use this application when
    //the application is still running; Stop allowing user input if the user end the app, and showing a message
    // to say application is closed
    private void handleUserInputs() {
        boolean isRunning = true;
        String cmd = null;
        userInput = new Scanner(System.in);
        listOfExpense = new ListOfExpense();
        jsonReader = new JsonReader(JSON_SAVED);
        jsonWriter = new JsonWriter(JSON_SAVED);

        while (isRunning) {
            homePageInstruction();
            cmd = userInput.nextLine();
            if (cmd.equals("e")) {
                System.out.println("Do you want to save your list of expenses? Enter y if yes, otherwise click any"
                        + "other letter or numbers to exit");
                cmd = userInput.nextLine();
                if (cmd.equals("y")) {
                    saveFile();
                }
                isRunning = false;
            } else {
                executeCmd(cmd);
            }
        }
        System.out.println("Application is closed.");
    }


    //MODIFIES: this
    //EFFECTS: execute different tasks of the application based on user's command, print out warning message if app
    //get invalid input.
    @SuppressWarnings("methodlength")
    private void executeCmd(String cmd) {
        switch (cmd) {
            case "1":
                viewExpenses();
                break;
            case "2":
                addToApp();
                break;
            case "3":
                removeFromApp();
                break;
            case "4":
                rate();
                break;
            case "5":
                totalAmountInTime();
                break;
            case "6":
                avgScore();
                break;
            case "l":
                loadFile();
                break;
            case "s":
                saveFile();
                break;
            default:
                System.out.println("Invalid input, please try again.");
                break;
        }
    }


    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //MODIFIES: this
    //EFFECTS: load the list of expenses from file
    private void loadFile() {
        try {
            listOfExpense = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_SAVED);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_SAVED);
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
            System.out.println("Saving your list of expenses  to " + JSON_SAVED);
            System.out.println("Successfully saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_SAVED);
        }
    }


    //MODIFIES: this
    //EFFECT: allow user to add a new expense to the list of expenses by entering the required information
    private void addToApp() {
        System.out.println("Please enter name of this expense: ");
        String name = userInput.nextLine();

        System.out.println("Please enter the amount of money of this expense: ");

        String amt = userInput.nextLine();
        while (!amountCheck(amt)) {
            System.out.println("Invalid input. Please try again.");
            amt = userInput.nextLine();
        }
        System.out.println("Please enter when this expense is made in form of MM/YYYY: ");
        String time = userInput.nextLine();
        while (!checkValidTime(time)) {
            time = userInput.nextLine();
        }
        System.out.println("Please enter category.");
        System.out.println("They must be one of: Study, Food, Entertainment, Furniture, "
                + "Luxury, Clothes, Investment, Other Necessity, Other.");
        String category = userInput.nextLine();
        while (!checkValidCategory(category)) {
            category = userInput.nextLine();
        }
        Expense expense = new Expense(name, time, Double.parseDouble(amt), category);
        listOfExpense.addExpenses(expense);
        System.out.println("successfully added!");

    }

    //EFFECTS: allow user to view the expenses they have recorded; if no record, print out a warning message
    private void viewExpenses() {
        List<Expense> loe = listOfExpense.getListOfExpense();
        if (loe.isEmpty()) {
            System.out.println("There is no expense recorded, please add expenses.");
        } else {
            printList();
            viewInDetail();
        }
    }


    //EFFECTS: allow user to view the selected expense in detail, showing warning message when input violates
    //the instruction. Also allow user to go back to view the list or go back to main menu.
    private void viewInDetail() {
        System.out.println("If you want to view any of the expense in details, please enter its index number.");
        System.out.println("If not, enter -2 to go back to main menu");
        int userIn = checkIntType();

        while (!(userIn >= 0 && userIn <= (listOfExpense.getListOfExpense().size() - 1)) && userIn != -2) {
            System.out.println("invalid input, please try again.");
            userIn = checkIntType();
        }
        if (userIn != -2) {
            System.out.println("Name |Amount |Time |Category  |Rate Score |Dollar-score Ratio  ");
            Expense expense = listOfExpense.getListOfExpense().get(userIn);
            printDetail(expense);
            System.out.println("Enter -2 to go back to main menu, and -1 to go to list of expenses");

            int newUserIn = checkIntType();
            while (newUserIn != -1 && newUserIn != -2) {
                System.out.println("Invalid input.Please try again.");
                newUserIn = checkIntType();
            }
            if (newUserIn == -1) {
                viewExpenses();
            }
        }
    }

    //EFFECTS: print out a single expense in details
    private void printDetail(Expense expense) {
        if (expense.calculateRatio()) {
            System.out.println(expense.getName() + "|" + expense.getAmount() + "|" + expense.getMonthYear()
                    + "|" + expense.getCategory() + "|"
                    + expense.getScore() + "|" + expense.getDollarScoreRatio());
        } else {
            System.out.println(expense.getName() + "|" + expense.getAmount() + "|" + expense.getMonthYear()
                    + "|" + expense.getCategory() + "|"
                    + "NA" + "|" + "NA");
        }
    }


    //EFFECTS: present the average score for given category, if all score in that category has never been set, print
    //a warning message
    private void avgScore() {
        System.out.println("please enter the category that you want to check average score for.");
        System.out.println("They must be one of: Study, Food, Entertainment, Furniture, "
                + "Luxury, Clothes, Investment, Other Necessity, Other.");
        String enteredCategory = userInput.nextLine();
        while (!checkValidCategory(enteredCategory)) {
            enteredCategory = userInput.nextLine();
        }
        double result = listOfExpense.calculateAvgScore(enteredCategory);
        if (result == 0) {
            System.out.println("There is no enough data to calculate.Please set score first.");
            System.out.println();
        } else {
            System.out.println("Average satisfaction score for " + enteredCategory + " is " + result);
        }
    }

    //MODIFIES: this
    //EFFECTS: allow users to enter their rating for selected expense
    private void rate() {
        List<Expense> loe = listOfExpense.getListOfExpense();
        if (loe.isEmpty()) {
            System.out.println("There is no expense recorded, please add expenses.");
        } else {
            System.out.println("Please enter the index number for the the expense you want to rate: ");
            printList();
            int userIn = checkIntType();
            while (userIn > (listOfExpense.getListOfExpense().size() - 1) || userIn < 0) {
                System.out.println("Invalid index, please try again.");
                userIn = checkIntType();
            }
            System.out.println("Please rate this item from 1 to 5. "
                    + "1 means very unsatisfied and 5 means very satisfied.");
            int rate = checkIntType();
            while (rate < 1 || rate > 5) {
                System.out.println("Invalid score, please rate again.");
                rate = checkIntType();
            }
            loe.get(userIn).setScore(rate);
            System.out.println("Your rating for " + loe.get(userIn).getName() + " is " + rate);

        }

    }


    //EFFECTS: if time format is valid, present the total amount spend for given time; print a warning message if time
    // entered is not valid
    private void totalAmountInTime() {
        System.out.println("Please enter for what month and year you would like to see the total spending.");
        System.out.println("It should be in the format of MM/YYYY");
        String userIn = userInput.nextLine();
        while (!checkValidTime(userIn)) {
            userIn = userInput.nextLine();
        }
        listOfExpense.calculateAmountTotal(userIn);
        System.out.println("Total spending during " + userIn + " is " + listOfExpense.calculateAmountTotal(userIn));
    }


    //MODIFIES: this
    //EFFECTS: show warning message if list is empty, otherwise remove an expense at given index from the application,
    //if successfully removed, print success message; if not successful, print out warning message and let them
    //try input something new
    private void removeFromApp() {
        List<Expense> loe = listOfExpense.getListOfExpense();
        if (loe.isEmpty()) {
            System.out.println("There is no expense recorded, please add expenses.");
        } else {
            System.out.println("Please enter the index number for the the expense you want to remove: ");
            printList();
            int userIn = checkIntType();
            while (!listOfExpense.removeExpense(userIn)) {
                System.out.println("Invalid index, please try again.");
                userIn = checkIntType();
            }
            System.out.println("Successfully removed.");
        }
    }

    //REQUIRES: list of expense should not be empty
    //EFFECTS: print out the list of expenses
    private void printList() {
        List<Expense> loe = listOfExpense.getListOfExpense();
        int index = 0;
        System.out.println("Index|Name of expense");
        for (Expense expense : loe) {
            System.out.println(index + "|" + expense.getName());
            index++;
        }
    }


    //EFFECTS: check if the time user write is in right format MM/YYYY; if yes, return true, otherwise return false and
    //print out warning message
    private boolean checkValidTime(String time) {
        if (time.matches("(0[1-9]|1[0-2])/[0-9]{4}")) {
            return true;
        }
        System.out.println("Invalid time format. Please try again.");
        return false;
    }

    //EFFECTS: check if the category belongs to one of the category specified in user instructions;  if yes, return
    // true, otherwise return false and print out warning message
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
            return true;
        }
        System.out.println("Invalid Category. Please try again.");
        return false;
    }

    //EFFECTS: check whether the input value can be transformed into double and is greater than 0, if yes, return true,
    //otherwise, return false.
    //Reference: try catch is learned from edx animal feeding example
    private boolean amountCheck(String amt) {
        try {
            double db = Double.parseDouble(amt);
            return db > 0;
        } catch (NumberFormatException numberFormatException) {
            return false;

        }
    }

    //EFFECTS: check if user input can be transformed into an integer; if yes, then return the transformed integer, if
    //not, then return -10
    private int checkIntType() {
        try {
            String userIn = userInput.nextLine();
            return Integer.parseInt(userIn);

        } catch (NumberFormatException numberFormatException) {
            return -10;
        }
    }

    //EFFECTS: print out welcome message when launching the application
    private void printWelcome() {
        System.out.println("Welcome to spending management app!");
        System.out.println("You will be able to record your spending and analyze them!");
        System.out.println("please choose what you want to do from the followings: ");
    }

    //EFFECTS: print out the home page instructions
    private void homePageInstruction() {
        System.out.println("To load previously saved expenses, please enter l");
        System.out.println("To save current recorded expenses, please enter s");
        System.out.println("To view all expenses in record, please enter 1");
        System.out.println("To add expense to the record, please enter 2");
        System.out.println("To remove expense to the record, please enter 3");
        System.out.println("To rate your satisfaction to an expense, please enter 4");
        System.out.println("To calculate total amount at a period of time, please enter 5");
        System.out.println("To calculate average satisfaction score, please enter 6");
        System.out.println("To exit the application, please enter e");
    }


}

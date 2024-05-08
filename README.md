# My Personal Project Proposal: **Spending Management Application**

## Introduction:

This application is designed to allow users to **record information regarding their 
expenses and be able to review it afterward** to have an 
understanding of their consumption habits. The application is also able to offer
**some numerical summaries to the expenses recorded** to help individual to analyze their spending patterns.
This application is mainly designed for **individuals** who are curious about their own consumption habits and may want 
to adjust their consumer behaviors. 

I find this project to be interesting because *consumerism* has become a heated topic
in modern society. Many people may wonder whether part of their consumption is necessary
or not. Some people want to make financial plans to help themselves retire early, or they
may want to plan their expenses to maximize utility. My application design can hopefully
become a handy tool for individuals who have such needs to achieve their goals. 



## User Stories:

- As a user, I want to add an expense (with necessary information like name of the 
expense, when spending is made, amount, and category of the expense) to my
list of expenses, and I want to be able to do this arbitrary times.
- As a user, I want to be able to view the list of expenses.
- As a user, I want to be able to rate my satisfaction about an expense.
- As a user, I want to be able to select an expense in the list and view it in details.
- As a user, I want to be able to remove an expense from the list of expenses.
- As a user, I want to be able to see the total amount of money spent for a specified month and year,
and the average value of my satisfaction score for each category of expenses.
- As a user, I want to have the option to save or not save my spending list to file when I quit the application. I also
want to be able to save it when I am working on it. 
- As a user, I want to be able to reload the spending list from file that I saved last time if I want to.

# Instructions

- You can generate the first required action related to adding expenses to a list of expenses by clicking the "Add new
expenses"
button at the home page (which is the page that initially shows up when you open the application). This will open
another window that allow you to enter information to add the new expense. Click yes after entering all information.
- You can generate the second required action related to expense and list of expense, which is removing the expenses, by
first clicking on the "View and manage expenses" button at the home page, and then after seeing the list of expenses, 
click on the expense in the list that you want to remove and then click on the "Remove" button.There will be a confirmation window
pops up, and you can click yes if you confirm you want to move that expense.
- You can locate my visual component by opening application. It is at the bottom of the home page, which is a image
with "Spending manage" on it put on the background. The icon of the application (at top left corner) is also changed from the default one.
- You can save the state of my application by clicking the "Save" button at home page.
- You can reload the state of my application by clicking the "Load expenses saved last time" button at home page.
- you can generate another action related to expenses and list of expenses, which is calculating monthly total,
by clicking "view and manage expenses" button at the home page and then click on "Calculate Monthly Total" button. It
will allow you to enter MM/YYYY format date, and it will give back the total expenses at the specified month.

update:
- You can rate the expense by clicking "View and manage expenses", select an expense, and then click "Rate Expense"
- You can calculate the average rating for a specific category of purchases by clicking "View and manage expenses" and then click "Average rate" which will ask you to enter the category you are interested in

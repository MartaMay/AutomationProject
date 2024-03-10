package AutomationProject4;

public class Instruction {
    /*
    Property Search, View, and Filtering in Airbnb

Preconditions:
The tester has a valid Airbnb account. The tester is logged out of Airbnb at the start.

Login:
Navigate to Airbnb's homepage. Click on the profile icon and 'Log in' link in the upper right corner (it's a link with a text 'Log in')
Enter valid credentials (email and password) and log in Validate that the user's profile button with initial appears at the top right.

Property Search:
Click on Add Guests to bring up the search bar Input a destination in the main search bar, e.g., "Ibiza, Spain" Set the date range for a week
in the future (e.g. November 6-12 )and specify the number of guests (e.g. 2 adults, 2 kids) Click the 'Search' button.

Filtering Results:
Click on Filters button In Price Range section set the min price to 100 and max price to 600 and apply the filter Extract all the prices from the
search results and verify that each of them is within the range Note: Some prices are discounted, some of them are not, so you need 2 separate
 locators to extract both and combine them with or (|) For example: //span[@class='_tyxjp1'] | //span[@class='_1y74zjx']

Detailed Property View:
Locate the first search result and store the price per night, total price and rating and click on the first search result Switch to the newly opened
window, obtain the same information (price per night, total price and rating) and verify it the information matches to previously stored information
 from the search results page Note: Get rid of any pop-up message, if there is any Close the second window (don't forget to switch back to original
 window)

Logout:
Click on the profile icon and click 'Log out' (it's a div with a text 'Log out') Validate logout using assertion to ensure the 'Log in' link/profile
 icon button is present.

Push the code to a new GitHub repo and share the link in a text file and submit.
     */
}

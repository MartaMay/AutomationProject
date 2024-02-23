package AutomationProject2;

public class Instruction {
    /*
    **Song Search on Spotify Web Player**

Preconditions:

The tester has a valid Spotify account. (Create a spotify account with a valid but not personal email)
The tester is logged out of the Spotify Web Player at the start.

1. Login:

   (You can't inspect the html in this app directly through the homepage by right click and inspect, so make sure the inspect/developer window is open before you navigate to the homepage)

    Navigate to Spotify's Web Player homepage. (https://open.spotify.com/)
    Click on the 'Log In' link.
    Enter valid credentials (email and password).
    Click the 'Log In' button.
    Validate that the profile icon is displayed at the top right, indicating a successful login. (it is a <figure> element)

2. Music Search:

    Click on Search link on the left bar and Use the search bar at the top to search for a specific artist and song, e.g., "Adele Hello".
    From the search results, play the relevant song (Hello by Adele) by clicking on the play icon Once song is playing, validate the play functionality by verifying the song name (Hello) and artist (Adele) in the now-playing section at the left bottom corner.
3. Logout:

    Click on the profile icon  at the top right to access the account dropdown.
    Click 'Log out'.
    Validate the user has been logged out by ensuring the 'Log In' button is present.

Suggestions:
- Maximize your window before test starts
- Use Thread.sleep() when needed, to synchronize your automation script with the web events.
- Alternatively, you can use implicit wait of 5 seconds for every locator which will wait up to 5 seconds if the element is not found right away.
  It should be set once before test starts
  Syntax:
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize(); // maximize
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // set implicit wait

- Try using xpath for all locators

Push your project to GitHub, and share the repo link in the given repo.txt file
     */
}

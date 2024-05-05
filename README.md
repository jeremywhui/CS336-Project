# CS336-Project
Mary Buist (mtb202)\
Jeremy Hui (jwh139)\
Damon Lin (dl1023)\
Edison Wang (ejw135)

## Project Setup

To test the project, roughly follow these instructions:
1. Unzip the ZIP file.
2. Run the SQL script. This can be done with `schema.sql`. `schema.sql` includes the tables as well as some tuples that contain dummy data. This is useful for testing and allows us to demo the functionality of our app.
3. Change the credentials to access the MySQL server in `src/main/java/com/cs336/pkg/ApplicationDB.java`. On line 38, the provided credentials are the username `root` and the password `password`. Change these to whatever username and password you use for your own MySQL server. This will ensure that the app is authorized to connect to your MySQL database and perform operations/queries as needed.
4. If needed, perform **Project > Clean...** to clean the project. This may be needed to recompile all the Java files and update the class files as needed.
5. Run the application on an Apache Tomcat 8.5 server. You should see the application in your browser on `http://localhost:8080/cs336Project`, which should redirect to `http://localhost:8080/cs336Project/login` because you are not logged in.

## Credentials

| Username | Password | Role                        |
| -------- | -------- | --------------------------- |
| test     | test     | End User                    |
| cr       | cr       | Customer Representative     |
| admin    | admin    | Admin                       |
| user1    | user1    | End User                    |
| user2    | user2    | End User                    |
| user3    | user3    | End User                    |

## App Structure

All URLs are prepended by `http://localhost:8080/cs336Project`

### `/login`

Handles logging into the app. Checks to see whether the inputted username and password exist in the database and are valid. If valid, the user is redirected to `/index` and can access the other functionalities of the app. Otherwise, they are restricted.

### `/register`

Handles registering a new account. A potential user can create a new account defined by a username and password. If the username already exists, they are restricted from creating an account. On success, the user is redirected to `/login` and can login with their new account

### `/index`

For a regular end user (a customer), the index page is the home page. They have the ability to view existing auctions they have created as well as a form to create their own auction. All fields are required except for secret minimum price. Each field is limited based on the type of field. For example, the deadline is restricted to being in the future. Numerical fields must be numbers and must be incremented/decremented by a certain step size. Based on the shoes type selected (sandals, sneakers, boots), the user is required to fill in the information about a specific field. On creation, a new auction is created and other users can begin participating in the auction.

For a customer representative, the index page displays all available auctions from any user. Note that this does not include auctions that have already been sold. Customer representatives have the ability to delete an auction, which will delete the auction and any reference to it.

For an admin, the index page just displays all available auctions from any user. Note that this does not include auctions that have already been sold.

### `/auctions`

This page allows for users to navigate to different auctions as well as search for auctions based on certain sorting and filtering conditions. For example, users can filter by seller usernames or buyer usernames, sort by shoe name in alphabetical order, only include shoes that have a deadline between a specific range, etc. Shoes are displayed as a table, with each shoe having a hyperlink to the specified shoes' page.

### `/auction?shoesId=#`

This page is specified by the `shoesId` in the URL. If the `shoesId` does not exist, it will not route to any specific shoe and an error will be displayed instead. The user can see all the details about the shoe, including the current highest bid, the minimum bid increment, and the minimum next bid a prospective bidder must make. The user can also see the bid history. The user can choose to make a manual bid, where they must make a bid that is at least the minimum next bid. A user also has the option to place an automatic bid with a bid increment and a maximum bid amount it should go up to.

For customer representatives, they also have the ability to delete bids.

### `/alerts`

Alerts are composed of two types of alerts: bid alerts and preference alerts. Bid alerts show up when you get outbid or when you win an auction. Preference alerts show up when a shoe you are looking for shows up as an auction. Bid alerts are automatically set up whenever you start bidding on an auction. Preferences can be set up by the user. The user can set optional fields for shoes they are looking for, such as from a specific brand or of a specific size.

### `/questions`

This is a basic forum. Users can ask questions. Customer representatives can answer questions. Both also have the option to search for specific questions based on keyword or the question ID.

### `/logout`

This is not really a page, but rather a hyperlink to logout by invalidating the session and returning to `/login`.

### `/admin`

The admin page is composed of two components. One is the registration of a new customer representative. Note that customer representatives are stored on the `End_User` table, so they cannot have the same username as any other username, regardless of role. Admins can also generate sales reports. These include total earnings reports, best selling items reports, earnings per item type, earnings per user, etc.

### `/customerrep`

Handles the customer representative's ability to modify a user's account. This includes changing a username, changing a password, or entirely deleting a user's account.

# CS336-Project
Mary Buist (mtb202)\
Jeremy Hui (jwh139)\
Damon Lin (dl1023)\
Edison Wang (ejw135)

## Part 3 Instructions (for Grader)

To test the project, roughly follow these instructions:
1. Unzip the ZIP file.
2. Run the SQL script. This can be done with `schema.sql` (a script we wrote). We need to create the table first to store the test user and test for authentication.
3. Change the credentials to access the MySQL server in `src\main\java\com\cs336\pkg\ApplicationDB.java`. On line 38, the provided credentials are the username `root` and the password `password`. Change these to whatever username and password you use for your own MySQL server.
4.  Run the application on an Apache Tomcat 8.5 server. You should see the application in your browser on `http://localhost:8080/cs336Project`, which should redirect to `http://localhost:8080/cs336Project/login.jsp`.
5. Login with the test user. The test user has username `test` and password `test`. If successful, you should be brought to `http://localhost:8080/cs336Project/index.jsp` and see a message welcoming the given username (test). You should no longer be able to access `http://localhost:8080/cs336Project/index.jsp` or `http://localhost:8080/cs336Project/register.jsp`
6. Click the logout button to logout and return back to `http://localhost:8080/cs336Project/login.jsp`. You should no longer be able to access `http://localhost:8080/cs336Project/index.jsp`.
7. If you want to, you can also try using registering new accounts to log into on `http://localhost:8080/cs336Project/register.jsp`

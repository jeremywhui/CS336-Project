# CS336-Project
Final project for CS336

## Issue with `loginpage` being in the URL instead of the root directory
If there is a problem in that the URL of your project still contains `"loginpage"`, you can fix this by going into `Servers/tomcat-server/server.xml` and finding where it says `"loginpage"`. You can replace it with whatever you want (I have it as `"cs336Project"` but it is your preference). 

Note that you may also need to make changes to the `.settings` to incorporate this change. You can either do this manually or could also just clean the build (`Project > Clean...`). This has not been tested yet.

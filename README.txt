NetflixProjectDesign.docx - Brief design of how this was implemented and how the System is supposed to work.
NetflixProjectPerfNumbers - See performance numbers for different number of movie requests when cached and not cached.

Pre-requsites:
  Jre 8 should be installed to run the Service
  Jdk 8 should be installed and Eclipse with maven plugin or STS to launch the source code in Eclipse
  Run movie_services.jar to make sure upstream services are up.


VishalNetflixInterview.rar
  a. Unrar the Vishal.rar file
  b. A VishalNetflixInterview folder should get created which should contain a UserMoviesService folder with source code and a jar file UserMoviesService-1.0.jar

Steps to run the application:

  VishalNetflixInterview folder contains the jar file UserMoviesService-1.0.jar which could be used to run the program.
     i.  Please open a command prompt and navigate to the folder ~/VishalNetflixInterview
     ii. Run the program using the command
                 java -jar UserMoviesService-1.0.jar

Steps to import Source code to Eclipse and run the application from Eclipse
 
  Open Eclipse
    i. Click on File > Import > Maven >  Existing Maven Projects
   ii. Select Root directory as VishalNetflixInterview\UserMoviesService folder created in above step
  iii. Click Finish
  iv. Run the File UserMoviesApplication.java

To view the code in Windows
  source code is located in the following folder
  ~/VishalNetflixInterview\UserMoviesApplication\src\main\java\com\vishal


Steps to Test the Service:

curl http://localhost:8080/users/901345879/movies
Returns a JSON data of Users Movies (Title, Art Image, Id).

Code flow
==========

Client starts a Web Service running the main class  UserMoviesApplication. 
REST call made by the client comes to UserMoviesController.getUserMovies
Calls UserMoviesService.userMovies with userid provided
UserMoviesService.userMovies first invokes MovieListService.userMovieIds. This Service will make an http request to http://localhost:7001 to get the Observable of list of movies
UserMoviesService.userMovies uses these ids and makes parallel requests to MovieArtService and MovieTitleService
MovieTitleService will first check the Cache and if not present makes an http request to http://localhost:7002 to get Observable movie title
MovieArtService will first check the Cache and if not present makes an http request to http://localhost:7003 to get Observable movie image urls
UserMoviesService.userMovies then returns an Observable with the list of Movie objects. 
UserMoviesController.getUserMovies converts this to List and returns the JSON to the client.

Links to download 
=================

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
https://eclipse.org/downloads/

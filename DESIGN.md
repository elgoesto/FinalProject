# Design Doc

## advanced sketches
<p>
  Here a clear overview of the app.
</p>
<img src=https://github.com/elgoesto/FinalProject/blob/master/doc/schets2.png width=700>

## diagrams of utility modules
<p>
  For this app a couple of classes are necessary.</br>
  First a class to handle the login, store the username and password. Make sure the user doesn't already exists. 
  Secondly a class to search for the different books. 
  Next, a class to get the precise data from the API.
  Lastly, a class to search your friends favorites.
</p>
  
<img src=https://github.com/elgoesto/FinalProject/blob/master/doc/classes.png width=700>

## APIs
The API used is google books, this makes it possible to search for a title. As shown in the image below, the API is accessible.
<p>
<img src=https://github.com/elgoesto/FinalProject/blob/master/doc/books_API.png width=700>
</p>

## Data conversion
<p>
  To store de data in a database, Firebase is used. For the information about the books the google books API is consulted. </br>
  No further data sources will be used.
</p>
<p>
  For the filtering of the data needed for this project, a class will be used to store only the important data from the API. 
</p>


## Database structure
In the database each user with password, email and favorites is stored. </br>
Inside favorites will be all the saved books. As shown in the image below
<p>
  <img src=https://github.com/elgoesto/FinalProject/blob/master/doc/datastructure.png width=700>
</p>

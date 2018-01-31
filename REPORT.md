# Report BookBase

## Introduction
For those who love to read, and have note all around the house and notes in youre phone. 
This is the solution. BookBase lets you search for books an then store them to a "To read/favorites" list.
If that is not enough, you can even see the favorites list of your friends.
Below a snap of the app. </br></br>
<img src="https://github.com/elgoesto/FinalProject/blob/master/doc/reportintro.png" width=250>

## Technical design
### Technical design: Overview
BookBase is simple to use. After the user logged in there are 4 pages. First the search page. As its name would suggest, it lets the user search for books. The books then shown in the list are clickable, when the user does click you get to the book detail page. Here you can see the title, cover, authors and description of the book. From here the user is able to add the book to his favorites list. Last, there is a page where you can search a friend and see his favorites list.
Below an overview of the pages. </br></br>
<img src="https://github.com/elgoesto/FinalProject/blob/master/doc/reportoverview.png" width=700>

### Technical design: Detail
There is a class to make an object of the books, this makes it possible to show the information of a volume, such as title and authors.
This class gets the data from the API. </br>
Furthermore there is a class to store the favorites of a user. This was necessary for firebase. This class stores and gets data from firebase. </br>
List of activities:
* LoginActivity.java --> Handles login and register of new user.
* BookOverViewActivity.java --> Seach for books and show them.
* BookDetailActivity.java --> Show the details of a book and add to favorites.
* UsersFavoritesBooksActivity.java --> Show users favorites and remove items.
* FriendsOverviewActivity.java --> search for friend and show their favorites.

## Progress
The first big difference with the DESIGN.md document is the structure of the database.
The first design caused big troubles when trying to search for a user bij name.
So now 2 tables are made, one for username with user ID as child. 
The second for user ID with child favorite books.

One of the activities in the DESIGN.md document is left out.
This was an activity with an overview of friends. 


## Decisions
The change of database structure was necessay to make a logical and fast search method. 
Leaving the overview of friends out and the function to add friends was due to a lack of time.
Now you can search on username only.
With more time that would defenitely be a function of the app.



# Report BookBase

## Introduction
For those who love to read and cannot keep track of what books they still need to read. Those who got notes all around the house and notes in their phone. 
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

* LoginActivity.java --> Handles login and register of new user.</br>
Here the default firebase login and register is used. When creating a new user, you need to give an email, a password and a username. This username is then stored in the firebase database together with the userID. This makes it possible to search other users by name.</br>

* BookOverViewActivity.java --> Seach for books and show them.</br>
After login you get to the bookOverViewActivity. This is an activity where you can search a book title or author. Then a scrollable list of hits is shown, with a maximum of 40 books. These books are clickable. When clicked you get to the bookDetailActivity.
</br>

* BookDetailActivity.java --> Show the details of a book and add to favorites.</br>
This page gives an overview of the book. With ofcourse the title and authors of the book, but also an image of the cover and a brief description. In the top right corner of this screen is the "Add to favorites" button. Once clicked this book is stored in the users favorites list, and the user also goes to the usersFavoritesBooksActivity. 
</br>

* UsersFavoritesBooksActivity.java --> Show users favorites and remove items.</br>
This is a list view of the favorite books of the user. When an item is clicked you go back to the book detail page again. When an item is long clicked the book is removed from the favorites list.
</br>

* FriendsOverviewActivity.java --> search for friend and show their favorites.</br>
In the bottom of the screen is the "friends" button. This brings the user to the friendsOverviewActivity. The user is able to search for other users by name. If the user/friend exists the favorites list of that user is shown. you can also add his books to your own favorites list.
</br>

## Progress
### Database
The first big difference with the DESIGN.md document is the structure of the database. So now 2 tables are made, one for username with user ID as child. The second for user ID with a child for favorite book titles and a child with its ID. So now you can search for a user, get his userID, then go to the users favorites. </br>
The book title and the bookID are stored in the same order. This makes it possible to delete books from this list and get to the detail page of the book, because the ID is used for this. Below is an image of the data base structure</br>
<img src="https://github.com/elgoesto/FinalProject/blob/master/doc/database_struct.png" width=600>
</br></br>

### Acivities
One of the activities in the DESIGN.md document is left out. This was an activity with an overview of friends. This was left out because the function to add friends to you friends list was not in the MVP. This function is not necessary for the functionallity of the app, so it is not that bad.</br></br>

### Layout
The intention was to create a simple/userfriendly app, With a logical and smooth interface. It turned out to be a logical interface but the buttons at the bottom of the screen to navigate trough the diffrent pages are awful. Due to streching of the images they look ugly. This was the result of using layout_weight, but they were functional so I spent my time on other parts of this project.


# Personal Challenges
The first big challenge I encountered was working with the Google Books API. This API returned alot of data in all kind of forms. For example the image of the cover. This caused some troubles because the image took too long to download and then the JSON request stopped and no image, or the image of the previous book, was shown. To solve this has been a real struggle and consumed allot of my time, But I solved this by using a separate class to just store the JSON objects in and from there use the data. Once I understood this JSON was great tool to work with.</br></br>

The second challenge had less to do with the app its self, but was with GitHub. In the previous course, App Studio, we were used to first create an app and then make a repository from there. But this time it was done the other way round. We started with a repository and afterwards we added in the app. To "solve" this I first made a new repository from the app and then copied my DESIGN.md to it. This was ofcourse not the right solution rather just a way to advoid the challenge. After some help of a couple of TA's I finally managed to add my app to the initial repository. During the process I accidentally pushed to the wrong repo wich was really annoying, but because of all this strugles GitHub is now second nature to me.</br></br>

An other challenge I came across was with FireBase. When creating a new user you want to make an empty list where you can store the favorites book, but FireBase won't accept empty lists (child). How I solved this was to always add a book when creating a new user. I called this "book": Favorite list. This is shown in the image below. So now FireBase accept it as a child and it would still work in the app, even if the favorite list of a user or friend is empty. After a lot of trial and error, I now feel confident using FireBase.</br></br>

 <img src="https://github.com/elgoesto/FinalProject/blob/master/doc/favoritebooktitle.png" width=400>
 
 ## Decisions
The app pretty much looked like the design, only the database structure changed and the abillity to add friends is left out. When there was more time it would have definitely been in the app. 



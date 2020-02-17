## CINEMA WORLD - Ticket booking App

Spring Rest Client app allows to:

+ List all the movies positions, provided by Cinema database

```javascript
GET /movies
------------------------------------------------------
Response:
[
   {
      "movieTitle" : "Lara",
      "id" : 1,
      "screeningStartTime" : "12:00",
      "projectionDate" : "2019-07-01",
   },
   ...
  {
      "movieTitle" : "Latte and the Magic Waterstone",
      "id" : 80,
      "screeningStartTime" : "21:00",
      "projectionDate" : "2019-12-11"
   }
]
```

**(Business scenario : p.1, p.2)**
+ List all the movies positions by giving date and time, sorted by title and time 
```javascript
GET /movies/selected
header: 'date: YYYY-MM-DD'
header: 'time: HH:MM'
----------------------------------------
Response:
[
   {
      "projectionDate" : "2020-02-21",
      "screeningStartTime" : "15:00",
      "id" : 74,
      "movieTitle" : "Mafia Chapter-1"
   },
   {
      "projectionDate" : "2020-02-21",
      "screeningStartTime" : "15:00",
      "movieTitle" : "Sekaiichi Hatsukoi: Propose-hen",
      "id" : 18
   },
   {
      "movieTitle" : "The Winds",
      "id" : 38,
      "screeningStartTime" : "15:00",
      "projectionDate" : "2020-02-21"
   }
]
```
**(Business scenario : p.3, p.4)**
+ Getting details about selected movie by Id: (name of room in Multiplex and free seats in room)

```javascript
GET /screeningdetails/{movieId}
path:movieId = long
-------------------------------------------------------
Response:
{
   "freeSeatsOfRoom" : "{1=[], 2=[], 3=[5, 6, 9, 10]}",
   "roomName" : "East Room"
}
```

**(Business scenario : p.5, p.6)**
+ Booking seat, based on movie id (given in path) and 5 infos (given in header)
 firstname, lastname, age, row, seats
```javascript
GET /ticketbooking/{movieId}
path:movieId = long
header: 'age: int'
        'firstname: String' 
        'lastname: String' 
        'row: int'
        'seat: int[]'
------------------------------------------------------------------------------------------
Response:
{
   "leftTime" : "Seat reservation expires 15 minutes before the screening begins. The remaining time to end of ticket reservation is: 4 hours 55 minutes",
   "totalAmount" : 36
}
```
RESPONSE TO GUIDELINES:


**Assumptions**
<br/>1. Multiplex has 4 rooms with random rows and random quantity of free seats.
<br/>4. Age is a determinant of ticket prices:
`    CHILD(1, 18),
    STUDENT(19, 25),
    ADULT(26,120);
`
  

**Business requirements**
<br/> 2. cannot be a single place left over in a row between two already reserved
   places - checked <br/>
   cannot be a single place in corner - added.

**Technical requirements**
<br/>1. Language: Java.
<br/>3. Include H2 Database.

**Demo**
1. In root of project: file run.sh (may start from terminal typing : `bash run.sh` ).
2. Init data comes from TMDB base. To fetch this data I've used simple client called Retrofit.
3. File bookingSystem.sh (In fact that custom Exceptions may occurs, I've recommended to launch in the terminal due to key information : `bash bookingSystem.sh`)
 
Extra info: 
- It's possible to simulate time (`variables.properties` in resources).
- It's assumed that the current date is always a screening date.
- If reccomended, I could add hibernate with batch insert (Spring jpa doesn't have such functionality).
- You could reserve a seats in the same room many times, the state of reserved places is updating every time.
- H2 console is available from "/console".
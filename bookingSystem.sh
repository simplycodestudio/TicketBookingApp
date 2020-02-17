#!/bin/bash

echo ' -------------------------*** CINEMA ***----------------------------- ' 
echo ' -------------------------*** WORLD  ***----------------------------- ' 
echo ' -------------------------------------------------------------------- ' 
read -p "To go to movie reviews, press any button..." -n1 -s
printf '\n\nCalling GET Method on: http://localhost:8080/movies\n'
sleep 2
curl -s http://localhost:8080/movies | json_pp 


echo ' -------------------------------------------------------------------- ' 
echo ' ----- NOW YOU COULD CHOOSE MOVIE BY DATE AND TIME OF SCREENING ----- ' 
echo ' -------------------------------------------------------------------- ' 
echo ' --- YOU SHOULD GIVE IN REQUEST HEADER DATE WHEN U WOULD LIKE TO ---- '
echo ' ----------------  GO TO THE CINEMA, EG. 2020-02-21  ---------------- ' 
echo ' -------------------------  AT 18:00  ------------------------------- '
echo ' -------------------------------------------------------------------- '
echo ' -----  I AM ASSUMING THAT THE RECEIVED MOVIES WILL BE SORTED BY ---- ' 
echo ' --------------------  NAME AND SCREENING TIME ---------------------- ' 
echo ' -------------------------------------------------------------------- ' 

read -p "Press any key to continue..." -n1 -s
clear
printf '\n\nCalling GET Method on : `http://localhost:8080/movies/selected` with `date:2020-02-21` and `time: 18:00` in header\n'
sleep 2
printf '\n'
printf '\n'

curl -s -X GET \
  http://localhost:8080/movies/selected \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: 9be7ab55-d662-4a9d-b2c7-263d7b34a64e,2066d398-b793-4adf-b342-0b5b97271110' \
  -H 'User-Agent: PostmanRuntime/7.19.0' \
  -H 'cache-control: no-cache' \
  -H 'date: 2020-02-21' \
  -H 'time: 18:00' | json_pp

printf '\n'
printf '\n'
echo ' -------------------------------------------------------------------- ' 
echo ' ---------- THEN YOU COULD CHOOSE INTERESTING MOVIE BY ID. ---------- '
echo ' ---------- IF ENDPOINT WORKS FINE, IT MAY BE `The Winds`, ---------- ' 
echo ' --------------- ,WHICH STARTS AT 18:00 (ID is 39) ------------------ ' 
echo ' -------------------------------------------------------------------- ' 
echo ' --------  I HAVE TO CHECK IF THERE ANY FREE SEATS AVAILABLE -------- ' 
echo ' -------------------------------------------------------------------- ' 
echo ' -------------------------------------------------------------------- ' 
read -p "Press any key to continue..." -n1 -s
printf '\n\nCalling GET Method on : `http://localhost:8080/screeningdetails/{movieId}` with `movieId = 39` in Path variable\n'
sleep 2
printf '\n'
printf '\n'

curl -s -X GET \
 http://localhost:8080/screeningdetails/39 | json_pp

printf '\n'
echo ' -------------------------------------------------------------------- ' 
echo ' -----------------------RESERVATION DETAILS-------------------------- ' 
echo ' -------------------------------------------------------------------- ' 


echo 'Please, give Your Name'
read name
echo $name please, give Your Surname
read surname
echo How old are You?
read age
echo which row do you prefer?
read row
printf 'which seats do You prefer? You can book more than one (just add `,` between numbers) and there cannot be a single place left over in a row between two already reserved
      places\n'
read seats

printf '\nCalling PUT Method on : `http://localhost:8080/ticketbooking/{movieId}` with `movieId = 39` in Path variable and 5 header variables (firstname, lastname, age, row, seats) given by user\n'
sleep 2
printf '\n'
printf '\n'
printf '\n'

curl -s -X PUT \
  http://localhost:8080/ticketbooking/39 \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 0' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: e2141873-7a7e-4105-b3db-ccca186c89d8,97819731-ea54-4113-9204-3302621a3dfe' \
  -H 'User-Agent: PostmanRuntime/7.19.0' \
  -H 'age: '$age \
  -H 'cache-control: no-cache' \
  -H 'firstname: '$name \
  -H 'lastname: '$surname \
  -H 'row: '$row \
  -H 'seat: '$seats | json_pp

printf '\n'
printf '\n'
sleep 1
printf '\n If reservation not passed, You could try again with correct Data'
printf '\n'
printf '\n In fact of many validation rules with different runtime exceptions, You could try again and check other scenario with fake user Data'
sleep 1

printf '\n'
printf '\n'
echo ' ---------------------THANK YOU FOR VISITING------------------------- '
echo ' -------------------------*** CINEMA ***----------------------------- '
echo ' -------------------------*** WORLD  ***----------------------------- '
echo ' -------------------------------------------------------------------- '
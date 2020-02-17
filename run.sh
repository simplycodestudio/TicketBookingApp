#!/bin/bash
mvn clean package
cd ./target
java -jar TicketBookingApp-0.0.1-SNAPSHOT.jar

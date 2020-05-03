#!/bin/sh

mvn clean package && docker build -t usermanagement .
docker cp C:/Users/harsha/Documents/mysql-connector-java-8.0.19.jar usermanagement:/mysql-connector-java-8.0.19.jar
docker rm -f usermanagement || true && docker run -p 9090:9090 -p 9443:9443 --name usermanagement usermanagement
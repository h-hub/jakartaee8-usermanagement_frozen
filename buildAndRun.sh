#!/bin/sh
mvn clean package && docker build -t com.harshajayamanna.usermanagement/usermanagement .
docker rm -f usermanagement || true && docker run -d -p 9080:9080 -p 9443:9443 --name usermanagement com.harshajayamanna.usermanagement/usermanagement
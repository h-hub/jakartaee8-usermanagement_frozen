@echo off
call mvn clean package
call docker build -t com.harshajayamanna.usermanagement/usermanagement .
call docker rm -f usermanagement
call docker run -d -p 9080:9080 -p 9443:9443 --name usermanagement com.harshajayamanna.usermanagement/usermanagement
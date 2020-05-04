# Project com.harshajayamanna.usermanagement

Steps to run this project:

1. Start your Docker daemon
2. Execute `./buildAndRun.sh` (Linux/MacOs) or `buildAndRun.bat` (Windows)
3. Wait until Open Liberty is up- and running (e.g. use `docker logs -f CONTAINER_ID`)
4. Visit http://localhost:9090

## Running with liberty dev

Since this project is using libery maven plugin, yu can run "mvn liberty:dev" to start the project in dev mode.


DB schema is included in the project repo.

Edit the MailingService to implement the email functionalities.
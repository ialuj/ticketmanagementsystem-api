# IT Support Management System API
Spring Boot API
Database: Oracle
Unit Tests with Junit Jupiter and Mock Tests with Mockito

How to setup everything?

# A. Setting up the database

1. Make sure you have docker and maven installed.
2. Clone this repository to your computer.
3. Access the src/main/resources folder of the project.
4. Run docker compose file using the following command: docker-compose-up -d. This will configure the database
5. Run "docker logs oracle-itms-hann-db -f" to track the state of the database configuration it might a long time.
6. If the container "oracle-itms-hann-db" is created successful, copy the generated password at "ORACLE PASSWORD FOR SYS, SYSTEM AND PDBADMIN: PASSWORD_TO_COPY" this is the second line
7. Access the database using DBeaver, SQL Developer or another Oracle Database Client of your preference, use SID - ITMSDB, USER - system, Password: PASSWORD_TO_COPY.
8. Copy and Run the content of the script at "scr/main/resources" folder located on file - schema.sql to create the tables and sequences
9. Copy and Run the content of the script at "scr/main/resources" folder located on file - data.sql to insert the IT Support User with username: john.doe and Password: admin that will allow you to add other Users.

# B. Running the API
1. Update the database password by copying it from section A. 5 - "ORACLE PASSWORD FOR SYS, SYSTEM AND PDBADMIN: PASSWORD_TO_COPY" and past into application-prod.properties which is located in src/main/resources
2. Go to the root folder of the project, access it from Terminal ou CMD and run "mvn clean install" or "mvn clean package"
3. Access the target folder from the terminal and run the .jar file, unfortunately still having some problems with the docker container, run using "java -jar filename.jar"
4. Now go to section C to run the Java Swing Application

# C. Setting up the Application

1. Clone the repository from url: https://github.com/ialuj/itms-app.git
2. Access the root folder of the project and run "mvn clean install" or "mvn clean package"
3. Access the target folder and open the Terminal or Command Line in that folder and run "java -jar itms-app-1.0-SNAPSHOT.jar"
4. You can see that a Login Form was opened, just input the credencials - username: john.doe, password: admin
5. Access this Google Drive URL https://drive.google.com/drive/folders/1bJvcgw0UhTohwYClDyvauAT8kmzmrGEA and download the Demo Video and watch

# Healthworker Agenda
It is a scheduling app designed for local system use, say at a hospital, which was used for the theme of this implimentation.

![icon](https://user-images.githubusercontent.com/91497673/229387112-3c84da95-738b-4ba3-9d8e-909c21944aaa.png)

## Setup and Requirements
**Java Install:[Java SE 17 from Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)**

Built using jdk17 from oracle, but presumably works on jdk19 aswell or install in terminal
```
you@yourpc: ~/$ sudo apt install openjdk-17-jdk openjdk-17-jre
```
**Python3 Install:[Python3](https://www.python.org/downloads/)**
```
you@yourpc: ~/$ sudo apt install python3 pip3
```
## Database:
### MySQL Server
Download [Docker](https://www.docker.com/products/docker-desktop/)

Open a terminal and start a MySQL server with the following command
```
you@yourpc: ~/$ docker run --name=sqlName -p "your port(usually 3306)":3306 -e MYSQL_ROOT_PASSWORD="yourpassword" -d mysql/mysql-server
```
### Python Server
Navigate to the database directory in a terminal
Pip install the requirements 
Then finally just run it
```
you@yourpc: ~/shiftmanager$ cd database
```
Build the .env
```
you@yourpc: ~/shiftmanager/database$ nano .env
```
And add the variables
* SQLPORT = mysql connection port
* SQLPASS = mysql connection password
* SECRETKEY = your own choice of encryption key

Then press Ctrl+X or Cmd+X then Y then enter to save the file
```
you@yourpc: ~/shiftmanager/database$ pip install requirements.txt
you@yourpc: ~/shiftmanager/database$ python3 server.py
```
## Application:
The project comes with a compiled .jar file, but if the user wants to *build it themselves* follow below
```
you@yourpc: ~/shiftmanager$ cd src
you@yourpc: ~/shiftmanager/src$ javac *.java
you@yourpc: ~/shiftmanager/src$ java exec.java
```
Or build to a jar aswell
```
you@yourpc: ~/shiftmanager/src$ jar -cvmf manifest.mf HealthAgenda.jar *.class
```

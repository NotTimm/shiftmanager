# Health Agenda
It is a scheduling app designed for local system use, say at a hospital, which was used for the theme of this implimentation. I ended up making nearly any changes in architecture, except for the backend. It was originally written in nodejs, but after a bluescreen, the file was corrupted and I just decided to change. I always had the vision of client-server, as I built such a thing in databases before. It came out quite impressive, and I enjoy the speed and security.

The client server means that while the frontends are inactive here, the resting python server will continue to stay updated with days as they come.

It was built in a linux terminal, but formatted for a Windows runtime, so the icon images may fail to load, as the extensions have a ../ call where a unix system requires ./ for the same folder, I would recommend any TA testing this run it on windows, but if not I will provide video

![icon](https://user-images.githubusercontent.com/91497673/229387112-3c84da95-738b-4ba3-9d8e-909c21944aaa.png)

## Setup and Requirements
**Java Install:[Java SE 17 from Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)**

Built using jdk17 from oracle, but presumably works on jdk19 aswell *optional:* install in terminal
```
you@yourpc:~/$ sudo apt install openjdk-17-jdk openjdk-17-jre
```
**Python3 Install:[Python3](https://www.python.org/downloads/)**
```
you@yourpc:~/$ sudo apt install python3 pip3
```
## Database:
### MySQL Server
Download & Start [Docker](https://www.docker.com/products/docker-desktop/)

Open a terminal and start a MySQL server with the following command
```
you@yourpc:~/$ docker run --name=sqlName -p "your port(usually 3306)":3306 -e MYSQL_ROOT_PASSWORD="yourpassword" -d mysql/mysql-server
```
Now bash into your server and build the required tables
```
you@yourpc:~/$ docker ps
you@yourpc:~/$ docker exec -it "your CONTAINER ID" bash
bash-4.4# mysql -uroot -p
Enter password: "enter your password"
mysql> 
```
Now Run all the commands in [dbsetup.sql](database/dbsetup.sql) one line at a time
### Python Server
Navigate to the database directory in a terminal
```
you@yourpc:~/shiftmanager$ cd database
```
Build the .env
```
you@yourpc:~/shiftmanager/database$ nano .env
```
And add the variables
* SQLPORT = mysql connection port
* SQLPASS = mysql connection password
* SECRETKEY = your own choice of encryption key

Confirm there is no whitespace between words and equals signs
Then press Ctrl+X or Cmd+X then Y then enter to save the file

Now pip install the required imports and run the server
```
you@yourpc:~/shiftmanager/database$ pip install -r requirements.txt
you@yourpc:~/shiftmanager/database$ python3 server.py
```
## Application:
The project comes with a compiled .jar file, but if the user wants to *build it themselves* follow below
```
you@yourpc:~/shiftmanager$ cd src
you@yourpc:~/shiftmanager/src$ javac *.java
you@yourpc:~/shiftmanager/src$ java exec.java
```
Or build to a jar aswell
```
you@yourpc:~/shiftmanager/src$ jar -cvmf manifest.mf HealthAgenda.jar *.class
```
### Then Just Run the app
![Screenshot 2023-04-02 200037](https://user-images.githubusercontent.com/91497673/229389711-04432e76-0995-44b1-82e8-a402dfd05f9a.png)

-# jigsaw-deployment
-Directions to setup a Jigsaw Docker container on a new server using the Image(Prefered)
-
-# Setup
-Follow install instructions here to install docker on the server.  This guide is specific to Amazon Web Services
-but will guide you through the correct steps required to get Docker up for any environment.
-http://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html#install_docker
-
-Run the following commands to setup docker and launch the Jigsaw App (assuming your using the Amazon standard Linux AMI):       
-
-# Docker Install
-
-Step 1. Launch new  server instance from an Amazon Linux AMI
-
-Step 2. Run the following commands:
-* sudo yum update -y
-* sudo yum install -y docker 
-* sudo service docker start
-* sudo usermod -a -G docker ec2-user
-* logout/log back in
-* docker info --> now should have docker permissions as ec2-user
-
# Jigsaw Install as Docker container

  Once you have a server configured with the Docker daemon, there are three steps:  
  
Step 1.  Pull Jigsaw image from DockerHub  
  * from the server with Docker installed, create a new folder called AFS-Jigsaw run: "mkdir AFS-Jigsaw"
  * change to that directory run: "cd AFS-Jigsaw"
  * pull the Jigsaw Docker image from DockerHub run: "docker pull joshbaker/jigsaw-prod:v3"  

Step 2.  Launch Docker container using Jigsaw image  
  * to start the image as a container, run: "docker run -i -t -p 8080:8080 joshbaker/jigsaw-prod:v3"

Step 3.  Browse to website 
  * http://(INSERT YOUR IP ADDRESS or URL HERE):8080/jigsaw/



# Additional details
This creates and starts a docker container and maps port 8080 of your host IP to port 8080 on your
running container. You can exit with ^c. This does not delete the container, only 
stops it. You can view all containers with "docker ps -a". You can delete all 
containers with "docker rm $(docker ps -a -q)"

NOTE: Running Tomcat from the Docker script will NOT be secured with SSL.  If you have an SSL certificate, you will need to configure Tomcat and the Docker container accordingly and then map the 443 port in your docker run command "docker run -i -t p 443:443 joshbaker/jigsaw-prod:v3"



![alt tag](https://github.com/AccentureFed/18FRFQ-Response/raw/master/process-documentation/agile-process-photos/response-images/proposal-header.png)

# Manual Linux Install Directions
# Setup
1. Download <a href="https://tomcat.apache.org/download-80.cgi" target="_blank">Apache Tomcat 8.0</a>
2. Extract the downloaded file to any directory. 
3. Copy <a href="https://s3.amazonaws.com/jigsaw-latest/jigsaw.war">jigsaw.war</a> to <Path-to-Tomcat>/webapps 
4. Ensure all scripts in <Path-to-Tomcat>/bin have executable permissions
5. Run <Path-to-Tomcat>/bin/startup.sh
6. Wait until Tomcat starts up (tail <Path-to-Tomcat>/logs/catalina.out) and look for `Cache Update Finished`
7. Open web browser, go to http://localhost:8080/jigsaw
8. Enjoy.
 




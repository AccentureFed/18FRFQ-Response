![alt tag](https://github.com/AccentureFed/18FRFQ-Response/raw/master/process-documentation/agile-process-photos/response-images/proposal-header.png)

# jigsaw-deployment
Directions to setup a Jigsaw Docker container on a new server

# Setup
Follow install instructions here to install docker on the server.  This guide is specific to Amazon Web Services
but will guide you through the correct steps required to get Docker up for any environment.
http://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html#install_docker

Run the following commands to setup docker and launch the Jigsaw App (assuming your using the Amazon standard Linux AMI):   

# Docker Install

Step 1. Launch new  server instance from an Amazon Linux AMI

Step 2. Run the following commands:
* sudo yum update -y
* sudo yum install -y docker 
* sudo service docker start
* sudo usermod -a -G docker ec2-user
* logout/log back in
* docker info --> now should have docker permissions as ec2-user

# Jigsaw Install as Docker container

  Once you have a server configured with the Docker daemon, there are two simple steps:  
    1.  Pull Jigsaw image from DockerHub  
  * from the server with Docker installed, create a new folder called AFS-Jigsaw run: "mkdir AFS-Jigsaw"
  * change to that directory run: "cd AFS-Jigsaw"
  * pull the Jigsaw Docker image from DockerHub run: "docker pull joshbaker/jigsaw-prod:latest"  
  
   2.  Launch Docker container using Jigsaw image  
  * to start the image as a container, run: "docker run -i -t -p 8080:8080 joshbaker/jigsaw-prod:latest"
    
# Additional details
This creates and starts a docker container and maps port 8080 of your host IP to port 8080 on your
running container. You can exit with ^c. This does not delete the container, only 
stops it. You can view all containers with "docker ps -a". You can delete all 
containers with "docker rm $(docker ps -a -q)"

NOTE: Running Tomcat from the Docker script will NOT be secured with SSL.  If you have an SSL certificate, you will need to configure Tomcat and the Docker container accordingly and then map the 443 port in your docker run command "docker run -i -t -p 443:443 joshbaker/jigsaw-prod:latest"

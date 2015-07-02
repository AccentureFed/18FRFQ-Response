# jigsaw-deployment
Docker file and directions to setup Jigsaw on a new server

# setup
Follow install instructions here to install docker on the server
http://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html#install_docker

Run the following commands to setup docker and start:
* sudo yum update -y
* sudo yum install -y docker 
* sudo service docker start
* sudo usermod -a -G docker ec2-user
* logout/log back in
* docker info --> now should have docker permissions as ec2-user

# usage
* copy Dockerfile and 'run.sh' to server into a directory
* In 'run.sh' set desired admin and admin-script password
* from that dir, run the command to build an 'image': docker build -t tomcat-server .
* to view the available images, run: docker images
* to start the image as a container, run: docker run -i -t -p 8080:8080 tomcat-server

# additional details
This creates and starts a docker container and maps port 8080 of your host IP to your
running container. You can exit with ^c. This does not delete the container, only 
stops it. You can view all containers with 'docker ps -a'. You can delete all 
containers with 'docker rm $(docker ps -a -q)'

NOTE: The running Tomcat from the Docker script will NOT be secured with SSL.  If you have an SSL certificate, you will need to configure Tomcat accordingly.
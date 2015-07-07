![alt tag](https://github.com/AccentureFed/18FRFQ-Response/raw/master/process-documentation/agile-process-photos/response-images/proposal-header.png)

# Manual Install Directions
# Setup
1. Download <a href="https://tomcat.apache.org/download-80.cgi" target="_blank">Apache Tomcat 8.0</a>
2. Extract the downloaded file to any directory. 
3. Copy <a href="https://s3.amazonaws.com/jigsaw-latest/jigsaw.war">jigsaw.war</a> to <Path-to-Tomcat>/webapps 
4. Ensure all scripts in <Path-to-Tomcat>/bin have executable permissions
5. Run <Path-to-Tomcat>/bin/startup.sh
6. Wait until Tomcat starts up (tail <Path-to-Tomcat>/logs/catalina.out) and look for `Cache Update Finished`
7. Open web browser, go to http://localhost:8080/jigsaw
8. Enjoy.
 




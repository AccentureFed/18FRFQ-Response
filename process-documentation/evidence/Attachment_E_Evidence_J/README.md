![alt tag](https://github.com/AccentureFed/process-documentation/raw/master/agile-process-photos/response-images/proposal-header.png)

[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)

|#|criteria|evidence|
|-------|---------------|------------------|
|j|deployed the prototype on an Infrastructure as a Service (IaaS) or Platform as a Service (PaaS) provider, and indicated which provider they used |The production server is an IaaS with the provider being Amazon Web Service with an EC2 instance within Amazons VPC. Jenkins was deploying to Tomcat on the EC2 instance. Each production deployment is a manual Jenkins Job build and deployment.  The team deployed at a minimum once a day after each new feature capability or bug fix was complete. Each team member had the credentials through Jenkins to push to production eliminating a single point of failure among the team.  The production server has an SSL certificate allowing open access. Our development environment initially was stood up as a standard IaaS with an EC2 instance with Tomcat installed.  Jenkins was continuously deploying on code commit.  The development environment did not have an SSL certificate, thus promoting an internal test environment deployed on each code commit. For traceability, the team modified the Grails build script allowing Jenkins to embed the job name, the build number, and the Git Commit hash into the war file.  This was done for both the deployment and production builds.  This enabled our testers to align defects to builds, and understand progression across the code.|


[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)


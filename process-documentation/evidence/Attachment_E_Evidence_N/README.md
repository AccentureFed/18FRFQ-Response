![alt tag](https://github.com/AccentureFed/process-documentation/raw/master/agile-process-photos/response-images/proposal-header.png)

[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)

|#|criteria|evidence|
|-------|---------------|------------------|
|n|set up or used continuous monitoring |We achieved continuous monitoring by utilizing the AWS Elastic Load Balancer Health Check to monitor the health of our application nodes within an auto-scaling group. Through this process if an EC2 node becomes unresponsive we can immediately create a new one, eliminating down time. Our site demonstrates authentication with a username and password to prevent configuration changes and enables the ability to display metrics.  We established the security parameters on our Virtual Private Cloud hosted within AWS.  Thus, removing any of our EC2 instances from public accessibility.  Our site is only available through our elastic load balancer. We will only allow public access to our site, via SSL, over port 443 through our elastic load balancer. Our site does not store or distribute any PII, or classified data.  To monitor system health, a authenticated admin page was developed for an FDA administrator. This page allows, the Admin to create and publish alerts shown on the main site banner and view system health metrics. |

[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)



NEED SCREEN SHOTS OF ADMIN PAGE WITH VISUALS ON HOW TO LOG INTO THE METRICS PAGE WITH THE USER NAME AND PASSWORD PROVIDED.


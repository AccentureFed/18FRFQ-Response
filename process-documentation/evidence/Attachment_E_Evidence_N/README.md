![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/agile-process-photos/response-images/proposal-header.png?raw=true)

[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)

|#|criteria|evidence|
|-------|---------------|------------------|
|n|set up or used continuous monitoring |We achieved continuous monitoring by utilizing the AWS Elastic Load Balancer Health Check to monitor the health of our application nodes within an auto-scaling group. Through this process if an EC2 node becomes unresponsive we can immediately create a new one, eliminating down time. Our site demonstrates authentication with a username and password to prevent configuration changes and enables the ability to display metrics.  We established the security parameters on our Virtual Private Cloud hosted within AWS.  Thus, removing any of our EC2 instances from public accessibility.  Our site is only available through our elastic load balancer. We will only allow public access to our site, via SSL, over port 443 through our elastic load balancer. Our site does not store or distribute any PII, or classified data.  To monitor system health, an authenticated admin page was developed for an FDA administrator. This page allows, the Admin to create and publish alerts shown on the main site banner and view system health metrics. |


The link to the metrics page can be found at the bottom left of the home screen or by clicking http://52.1.100.220:8080/jigsaw/#/login/

To login - 

![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/user-centric-design/design_evolution_images/metric_login.jpg?raw=true>)

View as an FDA Administrator of Metrics Main Page 

![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/user-centric-design/design_evolution_images/metric_main_page.jpg?raw=true>)

Thread View

![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/user-centric-design/design_evolution_images/metric_threadinfo.jpg?raw=true>)

An FDA Administrator has a built in capability to post Alerts to the main FDA Food Recalls page.  This Alert banner can be modified via the settings on the authenticated Administrative page. When the text is entered and the Administrator hits save, the main screen banner will be populated.  See screen displays below:

![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/user-centric-design/design_evolution_images/Metric_setting.jpg?raw=true>)



[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)






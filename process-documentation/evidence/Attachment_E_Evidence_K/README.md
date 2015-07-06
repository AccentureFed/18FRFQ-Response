![alt tag](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/agile-process-photos/response-images/proposal-header.png?raw=true)

[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)

|#|criteria|evidence|
|-------|---------------|------------------|
|k|wrote unit tests for their code |For the backend development we utilized JUnit and Spock. Spock is the framework for Groovy and JUnit for Java. Each component for the backend received its own unit test, especially corner cases and null. Backend developers wrote unit tests simultaneously as they were developing.  Unit tests, over 30, were run on every build in Jenkins over 125 builds to development, and over 35 builds to the production server. Jenkins ran all unit tests upon build.  Jenkins was configured to test for any code change every 15 minutes. If unit tests failed the build was not deployed and Jenkins alerted the team.  If defects and or bugs were identified, the developer would check the unit test to ensure it was correct. Bugs were logged and prioritized on the KANBAN board and committed to a sprint. For each defect that was corrected a unit test was written for that case.|


[Back to Attachment E_Evidence Criteria](https://github.com/AccentureFed/18FRFQ-Response/blob/master/process-documentation/evidence/README.md)


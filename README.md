spring-batch-5-demo
--
Spring Batch 5 documentation implies that it is no longer possible to 
run more than 1 batch job

It is true that you can only configure a single batch job to run with 
the *spring.batch.job.name*, but I found that you can still use 
a JobLauncher to start jobs. 

This repo is an example of using a JobLauncher to run multiple jobs in the same
JVM using spring-batch 5

To run the example and see 2 batch jobs execute:

Use: 
  * Java 17 

```shell
./gradlew build bootRun
```

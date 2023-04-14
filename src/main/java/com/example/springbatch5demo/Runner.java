package com.example.springbatch5demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Runner {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job jobOne;

    @Autowired
    Job jobTwo;

    @Scheduled(fixedRate = 10_000)
    public void run() throws Exception {
        log.info("Running...");
        jobLauncher.run(jobOne, new JobParameters());
        jobLauncher.run(jobTwo, new JobParameters());
    }
}

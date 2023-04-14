package com.example.springbatch5demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
@Configuration
public class BatchConfig {
    public static class IntReader extends ListItemReader<Integer> {
        public IntReader() {
            super(IntStream.range(1, 20).boxed().toList());
        }

    }

    public static class IntProcessor implements ItemProcessor<Integer, Integer> {
        private final String context;

        public IntProcessor(String context) {
            this.context = context;
        }

        @Override
        public Integer process(Integer item) throws Exception {
            log.info(context + " Processing " + item);
            Thread.sleep(100);
            return item;
        }
    }

    public static class IntWriter implements ItemWriter<Integer> {
        private final String context;

        public IntWriter(String context) {
            this.context = context;
        }

        @Override
        public void write(Chunk<? extends Integer> chunk) throws Exception {
            log.info(context + " finished a chunk of size " + chunk.size());
        }
    }

    private Job createJob(String name, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        ItemReader<Integer> reader = new IntReader();
        ItemProcessor<Integer, Integer> processor = new IntProcessor(name);
        ItemWriter<Integer> writer = new IntWriter(name);
        var step = new StepBuilder("step" + name, jobRepository)
                .<Integer, Integer>chunk(10, transactionManager)
                .allowStartIfComplete(true)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
        return new JobBuilder("job" + name, jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Job jobOne(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("Creating jobOne");
        return createJob("One", jobRepository, transactionManager);
    }

    @Bean
    public Job jobTwo(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("Creating jobTwo");
        return createJob("Two", jobRepository, transactionManager);
    }

    @Bean
    TaskExecutor taskExecutor() {
        return new TaskExecutorAdapter(Executors.newFixedThreadPool(5));
    }
}

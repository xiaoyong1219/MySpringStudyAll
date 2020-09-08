package com.dongxiayong.springbatchstart.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Create By dongxiaoyong on /2020/8/14
 * description: 任务嵌套示例
 *
 * @author dongxiaoyong
 */
@Component
public class NestedJobDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 父任务: 包含两个特殊步骤【子任务一（step1 --> step2）转换后的特殊步骤、子任务二（step3 --> stpe4）转换后的特殊步骤】
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:27
     * @return: org.springframework.batch.core.Job
     */

    @Bean
    public Job parentJob() {
        return jobBuilderFactory.get("parentJob2")
                .start(childJobOneStep())
                .next(childJobTwoStep())
                .build();
    }


    /**
     * 将子任务二转换为特殊的步骤
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:25
     * @return: org.springframework.batch.core.Step
     */

    private Step childJobOneStep() {
        return new JobStepBuilder(new StepBuilder("childJobOneStep"))
                .job(childJobOne())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 将子任务二转换为特殊的步骤
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:25
     * @return: org.springframework.batch.core.Step
     */

    private Step childJobTwoStep() {
        return new JobStepBuilder(new StepBuilder("childJobTwoStep"))
                .job(childJobTwo())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * 子任务一：step1 --> step2
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:23
     * @return: org.springframework.batch.core.Job
     */

    private Job childJobOne() {
        return jobBuilderFactory.get("childJobOne")
                .start(step1())
                .next(step2())
                .build();
    }

    /**
     * 子任务二：step3 --> step4
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:24
     * @return: org.springframework.batch.core.Job
     */

    private Job childJobTwo() {
        return jobBuilderFactory.get("childJobTwo")
                .start(step3())
                .next(step4())
                .build();
    }


    /**
     * 步骤一
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:34
     * @return: org.springframework.batch.core.Step
     */

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("子任务一执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 步骤二
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:34
     * @return: org.springframework.batch.core.Step
     */

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("子任务一执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 步骤三
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:37
     * @return: org.springframework.batch.core.Step
     */

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("子任务二执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 步骤四
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/14 0:37
     * @return: org.springframework.batch.core.Step
     */

    private Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("子任务二执行步骤四操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}

package com.dongxiayong.springbatchstart.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Create By dongxiaoyong on /2020/8/13
 * description: 并行job示例
 *
 * @author dongxiaoyong
 */
@Component
public class SplitJobDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 指定一个异步执行器，将flow1(step1-->step2)和flow2(step3)异步执行（也就是并行）。
     *  step3并没有在step2后才执行，说明步骤已经是并行化的（开启并行化后，并行的步骤执行顺序并不能100%确定，因为线程调度具有不确定性）
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/13 23:53
     * @return: org.springframework.batch.core.Job
     */

    @Bean
    public Job splitJob() {
        return jobBuilderFactory.get("splitJob")
                .start(flow1())
                .split(new SimpleAsyncTaskExecutor()).add(flow2())
                .end()
                .build();

    }

    private Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤一操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤二操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    private Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("执行步骤三操作。。。");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * 创建一个flow1：step1 ---> step2
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/13 23:51
     * @return: org.springframework.batch.core.job.flow.Flow
     */

    private Flow flow1() {
        return new FlowBuilder<Flow>("flow1")
                .start(step1())
                .next(step2())
                .build();
    }

    /**
     * 创建一个flow2：step3
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/13 23:52
     * @return: org.springframework.batch.core.job.flow.Flow
     */

    private Flow flow2() {
        return new FlowBuilder<Flow>("flow2")
                .start(step3())
                .build();
    }
}

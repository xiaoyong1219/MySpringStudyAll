package com.dongxiayong.springbatchstart.job;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Create By dongxiaoyong on /2020/8/13
 * description: 多步骤job示例
 *
 * @author dongxiaoyong
 */
@Component
public class MultiStepJobDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job multiStepJob() {
        return jobBuilderFactory.get("multiStepJob")
                .start(step1())
                .next(step2())
                .next(step3())
                .build();
    }

    /**
     * multiStepJob2任务先执行step1，当step1状态为完成时，接着执行step2，当step2的状态为完成时，接着执行step3。
     * ExitStatus.COMPLETED常量表示任务顺利执行完毕，正常退出，该类还包含以下几种退出状态：
     *   UNKNOWN、 EXECUTING 、 COMPLETED 、 NOOP 、 FAILED 、STOPPED
     * @Author :dongxiaoyong
     * @Date : 2020/8/13 0:14
     * @return: org.springframework.batch.core.Job
     */

    @Bean
    public Job multiStepJob2() {
        return jobBuilderFactory.get("multiStepJob2")
                .start(step1())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step2())
                .from(step2())
                .on(ExitStatus.COMPLETED.getExitCode()).to(step3())
                .from(step3()).end()
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
}

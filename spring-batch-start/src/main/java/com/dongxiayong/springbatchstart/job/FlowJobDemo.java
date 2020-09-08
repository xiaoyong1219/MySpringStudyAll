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
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

/**
 * Create By dongxiaoyong on /2020/8/13
 * description: FlowJobDemo
 * Flow的作用就是可以将多个步骤Step组合在一起然后再组装到任务Job中。
 *
 * @author dongxiaoyong
 */
@Component
public class FlowJobDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJob() {
        //Job流程中包含Flow类型的时候需要在build()方法前调用end()方法。
        return jobBuilderFactory.get("flowJob")
                .start(flow())
                .next(step3())
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
     * 创建一个flow对象，包含若干个step
     * 我们通过FlowBuilder将step1和step2组合在一起，创建了一个名为flow的Flow，然后再将其赋给任务Job。
     * 使用Flow和Step构建Job的区别是，Job流程中包含Flow类型的时候需要在build()方法前调用end()方法。
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/8/13 0:19
     * @return: org.springframework.batch.core.job.flow.Flow
     */

    private Flow flow() {
        return new FlowBuilder<Flow>("flow")
                .start(step1())
                .next(step2())
                .build();
    }
}

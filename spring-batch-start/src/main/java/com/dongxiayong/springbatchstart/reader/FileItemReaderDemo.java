package com.dongxiayong.springbatchstart.reader;

import com.dongxiayong.springbatchstart.entity.TestFileData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Create By dongxiaoyong on /2020/9/8
 * description: 文本数据读取
 *
 * @author dongxiaoyong
 */
@Component
public class FileItemReaderDemo {
    //任务创建工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //步骤创建工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job fileItemReaderJob() {
        return jobBuilderFactory.get("fileItemReaderJob2")
                .start(step())
                .build();
    }

    public Step step() {
        return stepBuilderFactory.get("step")
                .<TestFileData, TestFileData>chunk(2)
                .reader(fileItemReaderByDelimitedLineTokenizer())
                .writer(list -> list.forEach((System.out::println)))
                .build();
    }

    /**
     * 以固定分隔符处理行数据读取
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/9/8 16:42
     * @return: org.springframework.batch.item.ItemReader<com.dongxiayong.springbatchstart.entity.TestFileData>
     */

    private ItemReader<TestFileData> fileItemReaderByDelimitedLineTokenizer() {
        FlatFileItemReader<TestFileData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("file"));//设置文件资源地址
        reader.setLinesToSkip(1);//忽略第一行

        //AbstractLineTokenizer的三个实现类之一，以固定分隔符处理行数据读取
        //使用默认构造器的时候，使用逗号作分隔符，也可以通过有参构造来指定分隔符
        //DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(";");
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        //设置属性名，类似表头
        delimitedLineTokenizer.setNames("id", "field1", "field2", "field3");

        //将每行数据转换成TestFileData实体对象
        DefaultLineMapper<TestFileData> mapper = new DefaultLineMapper<>();
        //设置LineTokenizer
        mapper.setLineTokenizer(delimitedLineTokenizer);

        //设置映射方式，即读取到的文本怎么转换成对应的POJO
        mapper.setFieldSetMapper(fieldSet -> {
            TestFileData data = new TestFileData();
            data.setId(fieldSet.readInt("id"));
            data.setField1(fieldSet.readString("field1"));
            data.setField2(fieldSet.readString("field2"));
            data.setField3(fieldSet.readString("field3"));
            return data;
        });
        reader.setLineMapper(mapper);

        return reader;
    }


    /**
     * 通过指定的固定长度来截取数据
     *
     * @param
     * @Author :dongxiaoyong
     * @Date : 2020/9/8 16:42
     * @return: org.springframework.batch.item.ItemReader<com.dongxiayong.springbatchstart.entity.TestFileData>
     */

    private ItemReader<TestFileData> fileItemReaderByFixedLengthTokenizer() {
        FlatFileItemReader<TestFileData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("file"));//设置文件资源地址
        reader.setLinesToSkip(1);//忽略第一行

        //AbstractLineTokenizer的三个实现类之一，以固定分隔符处理行数据读取
        //使用默认构造器的时候，使用逗号作分隔符，也可以通过有参构造来指定分隔符
        FixedLengthTokenizer fixedLengthTokenizer = new FixedLengthTokenizer();

        //设置属性名，类似表头
        fixedLengthTokenizer.setNames("id", "field1", "field2", "field3");

        //将每行数据转换成TestFileData实体对象
        DefaultLineMapper<TestFileData> mapper = new DefaultLineMapper<>();
        //设置LineTokenizer
        mapper.setLineTokenizer(fixedLengthTokenizer);

        //设置映射方式，即读取到的文本怎么转换成对应的POJO
        mapper.setFieldSetMapper(fieldSet -> {
            TestFileData data = new TestFileData();
            data.setId(fieldSet.readInt("id"));
            data.setField1(fieldSet.readString("field1"));
            data.setField2(fieldSet.readString("field2"));
            data.setField3(fieldSet.readString("field3"));
            return data;
        });
        reader.setLineMapper(mapper);

        return reader;
    }
}

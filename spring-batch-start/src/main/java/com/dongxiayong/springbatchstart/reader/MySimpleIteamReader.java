package com.dongxiayong.springbatchstart.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Create By dongxiaoyong on /2020/8/14
 * description: reader
 *   Spring Batch读取数据通过ItemReader接口的实现类来完成，
 *  包括FlatFileItemReader文本数据读取、StaxEventItemReader XML文件数据读取、JsonItemReader JSON文件数据读取、JdbcPagingItemReader数据库分页数据读取等实现，
 *  更多可用的实现可以参考：https://docs.spring.io/spring-batch/docs/4.2.x/reference/html/appendix.html#itemReadersAppendix，本文只介绍这四种比较常用的读取数据方式。
 * @author dongxiaoyong
 */
public class MySimpleIteamReader implements ItemReader<String> {

    private Iterator<String> iterator;

    public MySimpleIteamReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 数据一个接着一个读取
        return iterator.hasNext() ? iterator.next() : null;
    }
}

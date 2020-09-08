package com.dongxiayong.springbatchstart.entity;

/**
 * Create By dongxiaoyong on /2020/9/8
 * description: 文本实体
 *
 * @author dongxiaoyong
 */
public class TestFileData {
    /**
     * id属性
     */
    private int id;
    /**
     * 文本属性1
     */
    private String field1;
    /**
     * 文本属性2
     */
    private String field2;
    /**
     * 文本属性3
     */
    private String field3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return "TestFileData{" +
                "id=" + id +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                '}';
    }

    public TestFileData() {
        super();
    }

    public TestFileData(int id, String field1, String field2, String field3) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }
}

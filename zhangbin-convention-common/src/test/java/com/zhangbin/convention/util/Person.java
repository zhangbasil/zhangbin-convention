package com.zhangbin.convention.util;

import com.zhangbin.convention.field.Mask;

/**
 * @author zhangbin
 * @Type Person
 * @Desc
 * @date 2019-04-04
 * @Version V1.0
 */
public class Person {

    private Integer id;

    private String name;

    private Person person;

    private boolean man;

    @Mask(left = 3, right = 4)
    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }
}

package com.zhangbin.convention.util;

/**
 * @author zhangbin
 * @Type PersonDTO
 * @Desc
 * @date 2019-04-04
 * @Version V1.0
 */
public class PersonDTO {

    private Long id;

    private String name;

    private PersonDTO person;

    private Boolean man;

    private String mobile;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public Boolean getMan() {
        return man;
    }

    public void setMan(Boolean man) {
        this.man = man;
    }
}

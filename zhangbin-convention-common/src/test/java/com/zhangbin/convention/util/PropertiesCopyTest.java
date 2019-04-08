package com.zhangbin.convention.util;

import java.util.Arrays;
import java.util.List;


/**
 * @author zhangbin
 * @Type PropertiesCopyTest
 * @Desc
 * @date 2019-04-08
 * @Version V1.0
 */
public class PropertiesCopyTest {


    public static void main(String[] args) {
        Person person = new Person();
        person.setId(1000);
        person.setName("张斌");
        person.setMan(true);
        person.setMobile("17602181809");
        person.setPerson(person);
        List<Person> people = Arrays.asList(person, person);
        List<PersonDTO> personDTOS = PropertiesCopy.copyBeans(people, PersonDTO.class);
        for (PersonDTO personDTO : personDTOS) {
            System.out.println("personDTO.getMobile() = " + personDTO.getMobile());
        }

    }
}

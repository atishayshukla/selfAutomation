package com.atishay.webui.Group;

import org.testng.annotations.BeforeClass;

/**
 * Created by DTBR6182 on 2016-02-22.
 */
public class ParentTest {

    @BeforeClass(groups = {"one"})
    public void first() {
        System.out.println("In One -- Before class");
    }

    @BeforeClass(groups = {"two"})
    public void second() {
        System.out.println("In two -- Before class");
    }
}

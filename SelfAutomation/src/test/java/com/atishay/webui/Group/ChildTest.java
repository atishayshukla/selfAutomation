package com.atishay.webui.Group;

import org.testng.annotations.Test;

/**
 * Created by DTBR6182 on 2016-02-22.
 */
public class ChildTest extends ParentTest {

    @Test(groups = {"one"})
     public void testOne(){
        System.out.println("Test  -- One com.atishay.webui.Group");
    }

    @Test(groups = {"two"})
    public void testTwo(){
        System.out.println("Test  -- two com.atishay.webui.Group");
    }
}

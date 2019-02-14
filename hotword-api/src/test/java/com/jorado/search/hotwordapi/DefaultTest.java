package com.jorado.search.hotwordapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public class DefaultTest {

    @Test
    public void test() {
        String companyName = "中银国际证券有限责任";
        int index = companyName.indexOf("公司");
        String shortName = companyName.substring(0, index + 2);
        System.out.println(shortName);
    }
}

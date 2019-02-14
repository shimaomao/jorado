package com.jorado.search.hotwordservice;

import com.jorado.search.core.task.Handler;
import com.jorado.search.hotwordservice.taskhandles.CompanyNameSyncHandler;
import com.jorado.search.hotwordservice.taskhandles.MatchRowsUpdateHandler;
import org.junit.Test;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public class DefaultTest {

    //@Test
    public void matchRowsUpdate() {
        new MatchRowsUpdateHandler().handle("2");
        System.out.println("over");
    }


    //@Test
    public void onlineCompanyUpdate() {
        Handler handler = new CompanyNameSyncHandler();
        handler.handle();
        System.out.println("over");
    }

    @Test
    public void test() {
        String companyName = "中银国际证券有限责任";
        int index = companyName.indexOf("公司");
        String shortName = companyName.substring(0, index + 2);
        System.out.println(shortName);
    }
}

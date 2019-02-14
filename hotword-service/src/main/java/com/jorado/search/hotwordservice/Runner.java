package com.jorado.search.hotwordservice;

import com.jorado.elasticjobextend.jobs.JobDispatcher;
import com.jorado.logger.EventClient;
import com.jorado.zookeeper.LoadConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 系统启动时初始化操作
 */
@Component
@Order(1)
public class Runner implements ApplicationRunner {

    private LoadConfig config = null;

    @Override
    public void run(ApplicationArguments var1) {

        config = LoadConfig.newInstance("jobs", () -> {
            try {

                EventClient.getDefault().submitLog("Start jobs begin.....");

                Map jobsMap;
                if (config != null) {
                    jobsMap = (Map) config.getProperties();

                    JobDispatcher.newInstance(jobsMap);
                }

            } catch (Exception ex) {
                EventClient.getDefault().submitException("Start jobs error", ex);
            } finally {
                EventClient.getDefault().submitLog("Start jobs over");
            }
            return null;
        });
    }
}
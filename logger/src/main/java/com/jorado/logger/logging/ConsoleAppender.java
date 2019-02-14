package com.jorado.logger.logging;

import com.jorado.logger.util.StringUtils;
import org.apache.log4j.spi.LoggingEvent;

public class ConsoleAppender extends org.apache.log4j.ConsoleAppender {

    private String allow = "";
    private String notallow = "";

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getNotallow() {
        return notallow;
    }

    public void setNotallow(String notallow) {
        this.notallow = notallow;
    }

    @Override
    public void append(LoggingEvent event) {
        if (StringUtils.isNullOrWhiteSpace(event.categoryName)) {
            super.append(event);
            return;
        }

        if (!StringUtils.isNullOrWhiteSpace(allow) && !event.categoryName.equalsIgnoreCase(allow)) {
            return;
        } else if (!StringUtils.isNullOrWhiteSpace(notallow) && event.categoryName.equalsIgnoreCase(notallow)) {
            return;
        }
        super.append(event);
    }
}

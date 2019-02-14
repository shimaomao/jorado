package com.jorado.httpclient;

import com.jorado.logger.util.StringUtils;

/**
 * Created by len.zhang on 2015/4/13 17:17.
 * http输出contenttype类型
 */
public enum MediaType {

    JSON {
        @Override
        public String toString() {
            return ContentType.JSON;
        }
    }, XML {
        @Override
        public String toString() {
            return ContentType.XML;
        }
    }, TEXT {
        @Override
        public String toString() {
            return ContentType.TEXT;
        }
    };


    public static MediaType parse(String type) {

        if (StringUtils.isNullOrWhiteSpace(type))
            return MediaType.TEXT;

        type = type.toLowerCase().trim();

        switch (type) {
            case ContentType.JSON:
                return MediaType.JSON;
            case ContentType.XML:
                return MediaType.XML;
            default:
                return MediaType.TEXT;
        }

    }

    static final class ContentType {
        ContentType() {
        }

        static final String JSON = "application/json";
        static final String XML = "text/xml";
        static final String TEXT = "text/plain";
    }
}

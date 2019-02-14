package com.jorado.httpclient;

/**
 * Http方法枚举
 * Created by len.zhang on 2015/4/13 17:17.
 */
public enum HttpMethod {
    GET {
        @Override
        public String toString() {
            return "GET";
        }
    }, POST {
        @Override
        public String toString() {
            return "POST";
        }
    }, PUT {
        @Override
        public String toString() {
            return "PUT";
        }
    }, DELETE {
        @Override
        public String toString() {
            return "DELETE";
        }
    };
}

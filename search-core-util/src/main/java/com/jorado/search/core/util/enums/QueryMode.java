package com.jorado.search.core.util.enums;

public enum QueryMode {
    NORMAL {
        @Override
        public String toString() {
            return "普通查询";
        }
    },
    RANGE {
        @Override
        public String toString() {
            return "区间查询";
        }
    },
    IN {
        @Override
        public String toString() {
            return "范围内查询";
        }
    },
    KEYWORD {
        @Override
        public String toString() {
            return "关键字查询";
        }
    }, COORDINATE {
        @Override
        public String toString() {
            return "地理位置查询";
        }
    }
}

package com.jorado.search.core.util.enums;

public enum QueryMode {
    NORMAL {
        @Override
        public String toString() {
            return "普通查询";
        }
    }, RANGE {
        @Override
        public String toString() {
            return "区间查询";
        }
    }, DATE_RANGE {
        @Override
        public String toString() {
            return "日期区间查询";
        }
    }, IN {
        @Override
        public String toString() {
            return "范围内查询";
        }
    }
}

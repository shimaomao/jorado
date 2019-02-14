package com.jorado.search.core.util.enums;

public enum QueryOperate {
    AND {
        @Override
        public String toString() {
            return "AND";
        }
    }, OR {
        @Override
        public String toString() {
            return "OR";
        }
    }, NOT {
        @Override
        public String toString() {
            return "NOT";
        }
    }
}

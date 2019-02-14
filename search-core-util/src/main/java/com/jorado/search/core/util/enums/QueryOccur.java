package com.jorado.search.core.util.enums;

public enum QueryOccur {

    MUST {
        @Override
        public String toString() {
            return "+";
        }
    },


    FILTER {
        @Override
        public String toString() {
            return "#";
        }
    },


    SHOULD {
        @Override
        public String toString() {
            return "";
        }
    },

    MUST_NOT {
        @Override
        public String toString() {
            return "-";
        }
    };

}

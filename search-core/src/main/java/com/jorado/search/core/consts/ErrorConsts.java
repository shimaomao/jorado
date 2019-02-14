package com.jorado.search.core.consts;

public final class ErrorConsts {
    private ErrorConsts() {
    }

    public final class Search {

        private Search() {

        }

        public static final String SEARCH_FAIL = "Search failure";
        public static final String ASSIGN_SOLR = SEARCH_FAIL + ",assign solr error,no found any available solr client";
        public static final String BEFORE_SEARCH = SEARCH_FAIL + ",before search method error";
        public static final String AFTER_SEARCH = SEARCH_FAIL + ",after search method error";
        public static final String ADJUST_QUERY = SEARCH_FAIL + ",adjust solr query error";
        public static final String CLIENT_FILTER = SEARCH_FAIL + ",client filter error";
    }

    public final class Export {

        private Export() {

        }

        public static final String EXPORT_FAIL = "Export failure";
        public static final String BEFORE_EXPORT = EXPORT_FAIL + ",before export method error";
        public static final String AFTER_EXPORT = EXPORT_FAIL + ",after export method error";
    }
}

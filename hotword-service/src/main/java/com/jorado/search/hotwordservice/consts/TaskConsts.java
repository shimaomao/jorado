package com.jorado.search.hotwordservice.consts;

public final class TaskConsts {
    private TaskConsts() {
    }

    public static final String CRON_COMPANY_NAME_SYNC = "0 0 1 * * *";
    public static final String CRON_MATCH_ROWS_UPDATE = "0 0 2 * * *";
    public static final String CRON_SOLR_OPTIMIZE = "0 0 3 * * *";
}

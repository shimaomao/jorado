package com.jorado.search.core.exception;


import com.jorado.logger.exception.CoreException;

/**
 * Created by Administrator on 15-5-20.
 * 找不到可用的solr
 */
public class NoAssignSolrException extends CoreException {
    public NoAssignSolrException() {
        super("No assign solr!");
    }

    public NoAssignSolrException(String message) {
        super(message);
    }
}

package com.jorado.search.core.exception;

import com.jorado.logger.exception.CoreException;

public class QueryAdjustException extends CoreException {

    public QueryAdjustException() {
        super("An error occurred when adjust solrquery!");
    }

    public QueryAdjustException(String message) {
        super(message);
    }
}

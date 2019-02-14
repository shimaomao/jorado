package com.jorado.search.core.exception;

import com.jorado.logger.exception.CoreException;

public class ConfigException extends CoreException {
    public ConfigException(String message) {
        super("Config error," + message);
    }
}

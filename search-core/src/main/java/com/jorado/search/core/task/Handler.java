package com.jorado.search.core.task;

import com.jorado.core.Result;

public interface Handler {

    Result handle(Object... args);
}

package com.jorado.search.core.util.condition.Factory;

import com.jorado.search.core.util.condition.Condition;

public interface ConditionFactory {
    String buildQuery(Condition condition);
}

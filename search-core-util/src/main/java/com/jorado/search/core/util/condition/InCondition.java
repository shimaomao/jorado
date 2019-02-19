package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;
import com.jorado.search.core.util.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多值查询条件
 */
public class InCondition extends Condition {

    private List<String> values;

    public InCondition(String field, List<String> values) {
        super(field);
        this.values = values;
    }

    public InCondition(String field, String... values) {
        super(field);
        List<String> vs = new ArrayList<>();
        for (String v : values) {
            vs.add(v);
        }
    }

    public static Condition newCondition(String field, List<String> values) {
        return new InCondition(field, values);
    }

    public static Condition newCondition(String field, String... values) {
        return new InCondition(field, values);
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.IN;
    }

    @Override
    public String getValue() {
        return StringUtils.joinString(values, ";");
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;
import com.jorado.search.core.util.enums.QueryOperate;
import com.jorado.search.core.util.exception.InvalidConditionException;
import com.jorado.search.core.util.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 查询条件
 */
public abstract class Condition implements Serializable, Cloneable {

    private String field;
    private String value;
    private QueryMode mode;
    private Map<String, String> fields = new LinkedHashMap<>();
    private List<Condition> orCondition = new ArrayList<>();
    private List<Condition> andCondition = new ArrayList<>();
    private List<Condition> notCondition = new ArrayList<>();

    public Condition(String query) {
        this.init(query);
    }

    public Condition(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public Condition or(Condition conditon) {
        if (conditon.isValid() && !this.equals(conditon)) {
            this.orCondition.add(conditon);
        }
        return this;

    }

    public Condition and(Condition conditon) {
        if (conditon.isValid() && !this.equals(conditon)) {
            this.andCondition.add(conditon);
        }
        return this;
    }

    public Condition not(Condition conditon) {
        if (conditon.isValid() && !this.equals(conditon)) {
            this.notCondition.add(conditon);
        }
        return this;
    }

    public boolean isValid() {
        return StringUtils.isNotNullOrWhiteSpace(field) && StringUtils.isNotNullOrWhiteSpace(value);
    }

    public String getField() {
        return field;
    }

    protected void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    protected void setValue(String value) {
        this.value = value;
    }

    public QueryMode getMode() {
        return mode;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public String toQueryString() {
        try {

            if (!isValid()) {
                return "";
            }

            this.mode = this.setMode();

            StringBuilder sb = new StringBuilder();
            sb.append(this.buildQuery());

            fields.put(field, value);

            if (!orCondition.isEmpty()) {
                sb.append(String.format(" %s ", QueryOperate.OR));
                sb.append("(");
                int index = 0;
                for (Condition condition : orCondition) {
                    if (index > 0) {
                        sb.append(String.format(" %s ", QueryOperate.OR));
                    }
                    sb.append(condition.toQueryString());
                    fields.putAll(condition.getFields());
                    index++;
                }
                sb.append(")");
            }

            if (!andCondition.isEmpty()) {
                sb.append(String.format(" %s ", QueryOperate.AND));
                sb.append("(");
                int index = 0;
                for (Condition condition : andCondition) {
                    if (index > 0) {
                        sb.append(String.format(" %s ", QueryOperate.AND));
                    }
                    sb.append(condition.toQueryString());
                    fields.putAll(condition.getFields());
                    index++;
                }
                sb.append(")");
            }

            if (!notCondition.isEmpty()) {
                sb.append(String.format(" %s ", QueryOperate.NOT));
                sb.append("(");
                int index = 0;
                for (Condition condition : notCondition) {
                    if (index > 0) {
                        sb.append(String.format(" %s ", QueryOperate.NOT));
                    }
                    sb.append(condition.toQueryString());
                    fields.putAll(condition.getFields());
                    index++;
                }
                sb.append(")");
            }
            return sb.toString();
        } catch (Throwable ex) {
            throw new InvalidConditionException("Loop condition is not allowed！！！");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Condition condition = (Condition) o;
        return Objects.equals(getField(), condition.getField()) &&
                Objects.equals(getValue(), condition.getValue()) &&
                Objects.equals(orCondition, condition.orCondition) &&
                Objects.equals(andCondition, condition.andCondition) &&
                Objects.equals(notCondition, condition.notCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getValue(), orCondition, andCondition, notCondition);
    }

    protected abstract String buildQuery();

    protected abstract QueryMode setMode();

    /**
     * 支持多条件逗号分隔
     * CITY_ID:622, INDUSTRY_ID:170000, COMPANY_TYPE:9, JOB_NATURE:4
     *
     * @param query
     */
    private void init(String query) {

        if (StringUtils.isNullOrWhiteSpace(query)) return;

        String[] innerQuerys = StringUtils.splitString(query, ",", true);

        for (int i = 0, j = innerQuerys.length; i < j; i++) {

            String q = innerQuerys[i];

            if (StringUtils.isNullOrWhiteSpace(q)) {
                continue;
            }

            String[] items = StringUtils.splitString(q, ":", true);

            if (items.length < 2) {
                continue;
            }

            if (i == 0) {
                this.setField(items[0]);
                this.setValue(items[1]);
            } else {
                this.and(new QueryCondition(items[0], items[1]));
            }
        }
    }

}

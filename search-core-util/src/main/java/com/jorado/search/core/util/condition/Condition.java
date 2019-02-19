package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

import java.io.Serializable;
import java.util.*;

/**
 * 查询条件
 */
public abstract class Condition implements Serializable, Cloneable {

    private String field;
    private List<Condition> orCondition = new ArrayList<>();
    private List<Condition> andCondition = new ArrayList<>();
    private List<Condition> notCondition = new ArrayList<>();

    public Condition(String field) {
        this.field = field;
    }

    public Condition or(Condition... conditons) {
        for (Condition condition : conditons) {
            if (!this.equals(condition)) {
                this.orCondition.add(condition);
            }
        }
        return this;
    }

    public Condition and(Condition... conditons) {
        for (Condition condition : conditons) {
            if (!this.equals(condition)) {
                this.andCondition.add(condition);
            }
        }
        return this;
    }

    public Condition not(Condition... conditons) {
        for (Condition condition : conditons) {
            if (!this.equals(condition)) {
                this.notCondition.add(condition);
            }
        }
        return this;
    }

    public String getField() {
        return field;
    }

    public List<Condition> getOrCondition() {
        return orCondition;
    }

    public List<Condition> getAndCondition() {
        return andCondition;
    }

    public List<Condition> getNotCondition() {
        return notCondition;
    }

    public abstract QueryMode getQueryMode();

    public abstract String getValue();
}

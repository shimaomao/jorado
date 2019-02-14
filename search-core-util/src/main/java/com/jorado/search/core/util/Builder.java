package com.jorado.search.core.util;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.QueryCondition;
import com.jorado.search.core.util.condition.RangeCondition;
import com.jorado.search.core.util.enums.QueryOccur;

public class Builder {

    protected SearchInfo searchInfo;

    protected Builder(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    /**
     * 设置客户端ip
     *
     * @param clientIP
     * @return
     */
    public Builder setClientIP(String clientIP) {
        this.searchInfo.setClientIP(clientIP);
        return this;
    }

    /**
     * 设置客户端标示
     *
     * @param client
     * @return
     */
    public Builder setClient(String client) {
        this.searchInfo.setClient(client);
        return this;
    }

    /**
     * 添加附加数据
     *
     * @param key
     * @param value
     * @return
     */
    public Builder addPayload(String key, Object value) {
        this.searchInfo.getPayload().put(key, value);
        return this;
    }

    public SearchInfo build() {

        //校准start
        if (this.searchInfo.getStart() < 0) {
            this.searchInfo.setStart(0);
        }

        //校准返回记录最小值
        if (this.searchInfo.getRows() < 0)
            this.searchInfo.setRows(10);

        return this.searchInfo;
    }

    @Override
    public String toString() {
        return this.searchInfo.toString();
    }

    /**
     * 生成条件字符串
     *
     * @param occur
     * @param condition
     * @return
     */
    protected String getQueryInfo(QueryOccur occur, Condition condition) {
        String queryString = condition.toQueryString();
        if (occur == QueryOccur.MUST) {
            return queryString;
        }
        return String.format("%s(%s)", occur, queryString);
    }

    /**
     * 条件验证
     *
     * @param condition
     */
    protected boolean isValidCondition(Condition condition) {
        return null != condition && condition.isValid();
    }

    protected Condition[] buildCondition(String... conditions) {
        int size = conditions.length;
        Condition[] conditionArray = new Condition[size];
        for (int i = 0, j = size; i < j; i++) {
            conditionArray[i] = new QueryCondition(conditions[i]);
        }
        return conditionArray;
    }

    protected Condition buildCondition(String field, Object value) {
        return new QueryCondition(field, value);
    }

    protected Condition buildCondition(String field, Object from, Object to) {
        return new RangeCondition(field, from, to);
    }
}

package com.jorado.search.core.util;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.Factory.ApiConditionFactory;
import com.jorado.search.core.util.condition.Factory.ConditionFactory;
import com.jorado.search.core.util.condition.Factory.SolrConditionFactory;
import com.jorado.search.core.util.condition.QueryCondition;
import com.jorado.search.core.util.condition.RangeCondition;
import com.jorado.search.core.util.enums.QueryOccur;
import com.jorado.search.core.util.util.StringUtils;

public abstract class Builder {

    protected SearchInfo searchInfo;

    protected ConditionFactory conditionFactory;

    protected Builder(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
        this.conditionFactory = new SolrConditionFactory();
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

    /**
     * 设置起始索引
     *
     * @param start
     * @return
     */
    public Builder setStart(int start) {
        this.searchInfo.setStart(start);
        return this;
    }

    /**
     * 设置返回记录数
     *
     * @param rows
     * @return
     */
    public Builder setRows(int rows) {
        this.searchInfo.setRows(rows);
        return this;
    }

    /**
     * 设置条件工厂
     *
     * @param conditionFactory
     * @return
     */
    public Builder setConditionFactory(ConditionFactory conditionFactory) {
        this.conditionFactory = conditionFactory;
        return this;
    }

    public SearchInfo build() {

        //校准start
        if (this.searchInfo.getStart() < 0) {
            this.searchInfo.setStart(0);
        }

        //校准返回记录最小值
        if (this.searchInfo.getRows() < 0) {
            this.searchInfo.setRows(10);
        }

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
        String queryString = conditionFactory.buildQuery(condition);
        if (StringUtils.isNullOrWhiteSpace(queryString)) {
            return "";
        }
        if (occur == QueryOccur.MUST) {
            return queryString;
        }
        return String.format("%s(%s)", occur, queryString);
    }
}

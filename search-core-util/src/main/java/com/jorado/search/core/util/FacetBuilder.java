package com.jorado.search.core.util;


import com.jorado.search.core.model.searchinfo.FacetSearchInfo;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.enums.QueryOccur;
import com.jorado.search.core.util.util.StringUtils;

/**
 * 搜索条件建造器
 * 方便客户端使用
 */
public final class FacetBuilder extends Builder {

    public FacetBuilder() {
        this(false);
    }

    public FacetBuilder(boolean debug) {
        this(new SearchInfo(0, 0, debug));
    }

    public FacetBuilder(SearchInfo searchInfo) {
        super(searchInfo);
    }

    /**
     * 添加统计条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public FacetBuilder addFacetQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            this.searchInfo.getFacetSearchInfo().getQuery().add(queryInfo);
        }
        return this;
    }

    public FacetBuilder addFacetQuery(String... conditions) {
        addFacetQuery(buildCondition(conditions));
        return this;
    }

    public FacetBuilder addFacetQuery(String field, Object value) {
        addFacetQuery(buildCondition(field, value));
        return this;
    }

    public FacetBuilder addFacetQuery(String field, Object from, Object to) {
        addFacetQuery(buildCondition(field, from, to));
        return this;
    }

    public FacetBuilder setFacetQuery(String query) {
        if (StringUtils.isNotNullOrWhiteSpace(query)) {
            this.searchInfo.getFacetSearchInfo().getQuery().clear();
            this.searchInfo.getFacetSearchInfo().getQuery().add(query);
        }
        return this;
    }

    public FacetBuilder clearFacetQuery() {
        this.searchInfo.getFacetSearchInfo().getQuery().clear();
        return this;
    }

    /**
     * 添加统计排除条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public FacetBuilder addNotFacetQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            this.searchInfo.getFacetSearchInfo().getQuery().add(queryInfo);
        }
        return this;
    }

    public FacetBuilder addNotFacetQuery(String... conditions) {
        addNotFacetQuery(buildCondition(conditions));
        return this;
    }

    public FacetBuilder addNotFacetQuery(String field, Object value) {
        addNotFacetQuery(buildCondition(field, value));
        return this;
    }

    public FacetBuilder addNotFacetQuery(String field, Object from, Object to) {
        addNotFacetQuery(buildCondition(field, from, to));
        return this;
    }

    /**
     * 添加统计字段
     *
     * @param fields
     * @return
     */
    public FacetBuilder addFacetField(String... fields) {
        if (null == fields) {
            return this;
        }

        for (String f : fields) {
            String[] items = StringUtils.splitString(f, ",", true);
            for (String item : items) {
                this.searchInfo.getFacetSearchInfo().getField().add(item);
            }
        }
        return this;
    }

    /**
     * 设置输出记录数
     *
     * @param limit
     * @return
     */
    public FacetBuilder setLimit(int limit) {
        this.searchInfo.getFacetSearchInfo().setLimit(limit);
        return this;
    }

    /**
     * 获取统计查询信息
     *
     * @return
     */
    public FacetSearchInfo buildFacet() {
        return this.build().getFacetSearchInfo();
    }
}

package com.jorado.search.core.util;


import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.enums.QueryOccur;
import com.jorado.search.core.util.util.StringUtils;

/**
 * 搜索条件建造器
 * 方便客户端使用
 */
public final class SearchBuilder extends Builder {

    /**
     * facet建造器
     */
    private FacetBuilder facetBuilder;

    /**
     * 分组建造器
     */
    private GroupBuilder groupBuilder;

    /**
     * 构造函数
     */
    public SearchBuilder() {
        super(new SearchInfo(0, 10, false));
    }

    /**
     * 构造函数
     *
     * @param start
     * @param rows
     */
    public SearchBuilder(int start, int rows) {
        this(start, rows, false);
    }

    public SearchBuilder(int start, int rows, boolean debug) {
        this("", start, rows, debug);
    }

    /**
     * 带关键字的构造函数
     *
     * @param keyword
     * @param start
     * @param rows
     */
    public SearchBuilder(String keyword, int start, int rows) {
        this(keyword, start, rows, false);
    }

    public SearchBuilder(String keyWord, int start, int rows, boolean debug) {
        super(new SearchInfo(StringUtils.escapeSolrString(keyWord), start, rows, debug));
        facetBuilder = new FacetBuilder(this.searchInfo);
        groupBuilder = new GroupBuilder(this.searchInfo);
    }

    /**
     * 添加查询条件
     * 可多个，多个是and关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 清空查询条件
     *
     * @return
     */
    public SearchBuilder clearQuery() {
        this.searchInfo.getQuery().clear();
        return this;
    }

    /**
     * 添加排除条件
     * 可多个，多个是and关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addNotQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 添加过滤条件
     * 可多个，多个是and关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addFilterQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getFilterQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 清空过滤条件
     *
     * @return
     */
    public SearchBuilder clearFilterQuery() {
        this.searchInfo.getFilterQuery().clear();
        return this;
    }

    /**
     * 添加过滤器排除条件
     * 可多个，多个是and关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addNotFilterQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getFilterQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 设置Facet每次返回结果数
     *
     * @param limit
     * @return
     */
    public SearchBuilder setFacetLimit(int limit) {
        this.facetBuilder.setLimit(limit);
        return this;
    }

    /**
     * 添加统计字段
     *
     * @param fields
     * @return
     */
    public SearchBuilder addFacetField(String... fields) {
        this.facetBuilder.addField(fields);
        return this;
    }

    /**
     * 添加统计条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addFacetQuery(Condition... conditions) {
        this.facetBuilder.addQuery(conditions);
        return this;
    }

    /**
     * 添加统计排除条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addNotFacetQuery(Condition... conditions) {
        this.facetBuilder.addNotQuery(conditions);
        return this;
    }

    /**
     * 清空统计条件
     *
     * @return
     */
    public SearchBuilder clearFacetQuery() {
        this.facetBuilder.clearQuery();
        return this;
    }

    /**
     * 添加分组统计字段
     *
     * @param fields
     * @return
     */
    public SearchBuilder addGroupField(String... fields) {
        this.groupBuilder.addField(fields);
        return this;
    }

    /**
     * 设置Group每次返回结果数
     *
     * @param limit
     * @return
     */
    public SearchBuilder setGroupLimit(int limit) {
        this.groupBuilder.setLimit(limit);
        return this;
    }

    /**
     * 添加分组统计条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addGroupQuery(Condition... conditions) {
        this.groupBuilder.addQuery(conditions);
        return this;
    }

    /**
     * 添加分组统计排除条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public SearchBuilder addNotGroupQuery(Condition... conditions) {
        this.groupBuilder.addNotQuery(conditions);
        return this;
    }

    /**
     * 清空分组条件
     *
     * @return
     */
    public SearchBuilder clearGroupQuery() {
        this.groupBuilder.clearQuery();
        return this;
    }

    /**
     * 设置排序模式
     *
     * @param sort
     * @return
     */
    public SearchBuilder setSort(String sort) {
        this.searchInfo.setSort(sort);
        return this;
    }

    /**
     * 设置显示字段
     *
     * @param field
     * @return
     */
    public SearchBuilder setField(String field) {
        this.searchInfo.setField(field);
        return this;
    }


}

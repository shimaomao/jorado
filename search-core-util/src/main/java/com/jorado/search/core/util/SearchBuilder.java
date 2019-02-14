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
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            this.searchInfo.getQuery().add(queryInfo);
            this.searchInfo.getFields().putAll(condition.getFields());
        }
        return this;
    }

    public SearchBuilder addQuery(String... conditions) {
        addQuery(buildCondition(conditions));
        return this;
    }

    public SearchBuilder addQuery(String field, Object value) {
        addQuery(buildCondition(field, value));
        return this;
    }

    public SearchBuilder addQuery(String field, Object from, Object to) {
        addQuery(buildCondition(field, from, to));
        return this;
    }

    public SearchBuilder setQuery(String query) {
        if (StringUtils.isNotNullOrWhiteSpace(query)) {
            this.clearQuery();
            this.searchInfo.getQuery().add(query);
        }
        return this;
    }

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
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            this.searchInfo.getQuery().add(queryInfo);
        }
        return this;
    }

    public SearchBuilder addNotQuery(String... conditions) {
        addNotQuery(buildCondition(conditions));
        return this;
    }

    public SearchBuilder addNotQuery(String field, Object value) {
        addNotQuery(buildCondition(field, value));
        return this;
    }

    public SearchBuilder addNotQuery(String field, Object from, Object to) {
        addNotQuery(buildCondition(field, from, to));
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
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            this.searchInfo.getFilterQuery().add(queryInfo);
            this.searchInfo.getFields().putAll(condition.getFields());
        }
        return this;
    }

    public SearchBuilder addFilterQuery(String... conditions) {
        addFilterQuery(buildCondition(conditions));
        return this;
    }

    public SearchBuilder addFilterQuery(String field, Object value) {
        addFilterQuery(buildCondition(field, value));
        return this;
    }

    public SearchBuilder addFilterQuery(String field, Object from, Object to) {
        addFilterQuery(buildCondition(field, from, to));
        return this;
    }

    public SearchBuilder setFilterQuery(String query) {
        if (StringUtils.isNotNullOrWhiteSpace(query)) {
            this.clearFilterQuery();
            this.searchInfo.getFilterQuery().add(query);
        }
        return this;
    }

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
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            this.searchInfo.getFilterQuery().add(queryInfo);
        }
        return this;
    }

    public SearchBuilder addNotFilterQuery(String... conditions) {
        addNotFilterQuery(buildCondition(conditions));
        return this;
    }

    public SearchBuilder addNotFilterQuery(String field, Object value) {
        addNotFilterQuery(buildCondition(field, value));
        return this;
    }

    public SearchBuilder addNotFilterQuery(String field, Object from, Object to) {
        addNotFilterQuery(buildCondition(field, from, to));
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
        this.facetBuilder.addFacetQuery(conditions);
        return this;
    }

    public SearchBuilder addFacetQuery(String... conditions) {
        this.facetBuilder.addFacetQuery(conditions);
        return this;
    }

    public SearchBuilder addFacetQuery(String field, Object value) {
        this.facetBuilder.addFacetQuery(field, value);
        return this;
    }

    public SearchBuilder addFacetQuery(String field, Object from, Object to) {
        this.facetBuilder.addFacetQuery(field, from, to);
        return this;
    }

    public SearchBuilder setFacetQuery(String query) {
        this.facetBuilder.setFacetQuery(query);
        return this;
    }

    public SearchBuilder clearFacetQuery() {
        this.facetBuilder.clearFacetQuery();
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
        this.facetBuilder.addNotFacetQuery(conditions);
        return this;
    }

    public SearchBuilder addNotFacetQuery(String... conditions) {
        this.facetBuilder.addNotFacetQuery(conditions);
        return this;
    }

    public SearchBuilder addNotFacetQuery(String field, Object value) {
        this.facetBuilder.addNotFacetQuery(field, value);
        return this;
    }

    public SearchBuilder addNotFacetQuery(String field, Object from, Object to) {
        this.facetBuilder.addNotFacetQuery(field, from, to);
        return this;
    }

    /**
     * 添加统计字段
     *
     * @param fields
     * @return
     */
    public SearchBuilder addFacetField(String... fields) {
        this.facetBuilder.addFacetField(fields);
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
        this.groupBuilder.addGroupQuery(conditions);
        return this;
    }

    public SearchBuilder addGroupQuery(String... conditions) {
        this.groupBuilder.addGroupQuery(conditions);
        return this;
    }

    public SearchBuilder addGroupQuery(String field, Object value) {
        this.groupBuilder.addGroupQuery(field, value);
        return this;
    }

    public SearchBuilder addGroupQuery(String field, Object from, Object to) {
        this.groupBuilder.addGroupQuery(field, from, to);
        return this;
    }

    public SearchBuilder setGroupQuery(String query) {
        this.groupBuilder.setGroupQuery(query);
        return this;
    }

    public SearchBuilder clearGroupQuery() {
        this.groupBuilder.clearGroupQuery();
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
        this.groupBuilder.addNotGroupQuery(conditions);
        return this;
    }

    public SearchBuilder addNotGroupQuery(String... conditions) {
        this.groupBuilder.addNotGroupQuery(conditions);
        return this;
    }

    public SearchBuilder addNotGroupQuery(String field, Object value) {
        this.groupBuilder.addNotGroupQuery(field, value);
        return this;
    }

    public SearchBuilder addNotGroupQuery(String field, Object from, Object to) {
        this.groupBuilder.addNotGroupQuery(field, from, to);
        return this;
    }

    /**
     * 添加分组统计字段
     *
     * @param fields
     * @return
     */
    public SearchBuilder addGroupField(String... fields) {
        this.groupBuilder.addGroupField(fields);
        return this;
    }

    /**
     * 设置排序模式
     *
     * @param sortMode
     * @return
     */
    public SearchBuilder setSortMode(String sortMode) {
        this.searchInfo.setSortMode(sortMode);
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
     * 设置Group每次返回结果数
     *
     * @param limit
     * @return
     */
    public SearchBuilder setGroupLimit(int limit) {
        this.groupBuilder.setLimit(limit);
        return this;
    }

}

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
     * 添加统计字段
     *
     * @param fields
     * @return
     */
    public FacetBuilder addField(String... fields) {
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
     * 添加统计条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public FacetBuilder addQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getFacetSearchInfo().getQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 添加统计排除条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public FacetBuilder addNotQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            if (StringUtils.isNotNullOrWhiteSpace(queryInfo)) {
                this.searchInfo.getFacetSearchInfo().getQuery().add(queryInfo);
            }
        }
        return this;
    }

    /**
     * 清空分组条件
     *
     * @return
     */
    public FacetBuilder clearQuery() {
        this.searchInfo.getFacetSearchInfo().getQuery().clear();
        return this;
    }
}

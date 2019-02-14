package com.jorado.search.core.util;


import com.jorado.search.core.model.searchinfo.GroupSearchInfo;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.enums.QueryOccur;
import com.jorado.search.core.util.util.StringUtils;

/**
 * 搜索条件建造器
 * 方便客户端使用
 */
public final class GroupBuilder extends Builder {

    public GroupBuilder() {
        this(false);
    }

    public GroupBuilder(boolean debug) {
        this(new SearchInfo(0, 0, debug));
    }

    public GroupBuilder(SearchInfo searchInfo) {
        super(searchInfo);
    }

    /**
     * 添加分组统计条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public GroupBuilder addGroupQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST, condition);
            this.searchInfo.getGroupSearchInfo().getQuery().add(queryInfo);
        }
        return this;
    }

    public GroupBuilder addGroupQuery(String... conditions) {
        addGroupQuery(buildCondition(conditions));
        return this;
    }

    public GroupBuilder addGroupQuery(String field, Object value) {
        addGroupQuery(buildCondition(field, value));
        return this;
    }

    public GroupBuilder addGroupQuery(String field, Object from, Object to) {
        addGroupQuery(buildCondition(field, from, to));
        return this;
    }

    public GroupBuilder setGroupQuery(String query) {
        if (StringUtils.isNotNullOrWhiteSpace(query)) {
            this.searchInfo.getGroupSearchInfo().getQuery().clear();
            this.searchInfo.getGroupSearchInfo().getQuery().add(query);
        }
        return this;
    }

    public GroupBuilder clearGroupQuery() {
        this.searchInfo.getGroupSearchInfo().getQuery().clear();
        return this;
    }

    /**
     * 添加分组统计排除条件
     * 可多个，多个是and的关系
     *
     * @param conditions
     * @return
     */
    public GroupBuilder addNotGroupQuery(Condition... conditions) {
        for (Condition condition : conditions) {
            if (!isValidCondition(condition)) {
                continue;
            }
            String queryInfo = getQueryInfo(QueryOccur.MUST_NOT, condition);
            this.searchInfo.getGroupSearchInfo().getQuery().add(queryInfo);
        }
        return this;
    }

    public GroupBuilder addNotGroupQuery(String... conditions) {
        addNotGroupQuery(buildCondition(conditions));
        return this;
    }

    public GroupBuilder addNotGroupQuery(String field, Object value) {
        addNotGroupQuery(buildCondition(field, value));
        return this;
    }

    public GroupBuilder addNotGroupQuery(String field, Object from, Object to) {
        addNotGroupQuery(buildCondition(field, from, to));
        return this;
    }

    /**
     * 添加分组统计字段
     *
     * @param fields
     * @return
     */
    public GroupBuilder addGroupField(String... fields) {
        if (null == fields) {
            return this;
        }

        for (String f : fields) {
            String[] items = StringUtils.splitString(f, ",", true);
            for (String item : items) {
                this.searchInfo.getGroupSearchInfo().getField().add(item);
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
    public GroupBuilder setLimit(int limit) {
        this.searchInfo.getGroupSearchInfo().setLimit(limit);
        return this;
    }


    /**
     * 获取分组统计查询信息
     *
     * @return
     */
    public GroupSearchInfo buildGroup() {
        return this.build().getGroupSearchInfo();
    }
}

package com.jorado.word2vec;

import com.jorado.word2vec.filter.DefaultFilter;
import com.jorado.word2vec.filter.Filter;
import com.jorado.word2vec.filter.NullFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * word过滤管理器
 */
public class FilterManager {

    /**
     * word过滤器
     */
    private Map<String, Filter> filters;

    public FilterManager() {
        this(new NullFilter(), new DefaultFilter());
    }

    public FilterManager(Filter... filters) {
        this.filters = new LinkedHashMap();
        this.addFilter(filters);
    }

    /**
     * 添加过滤器
     *
     * @param filters
     * @return
     */
    public FilterManager addFilter(Filter... filters) {
        for (Filter filter : filters) {
            String name = filter.getClass().getName();

            if (!this.filters.containsKey(name))
                this.filters.put(name, filter);
        }
        return this;
    }

    /**
     * 移除过滤器
     *
     * @param filters
     * @return
     */
    public FilterManager removeFilter(Class<?>... filters) {
        for (Class c : filters) {
            this.filters.remove(c.getName());
        }
        return this;
    }

    /**
     * 清除所有过滤器
     *
     * @return
     */
    public FilterManager clearFilter() {
        this.filters.clear();
        return this;
    }

    /**
     * 过滤文本
     *
     * @param word
     * @return
     */
    public String filter(String word) {
        for (Map.Entry<String, Filter> entry : this.filters.entrySet()) {
            word = entry.getValue().filter(word);
        }
        return word;
    }

    /**
     * 返回过滤器的数量
     *
     * @return
     */
    public int size() {
        return filters.size();
    }
}

package com.jorado.search.core.model.searchinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索条件封装
 */
public final class SearchInfo implements Serializable {

    /**
     * 查询关键字
     */
    private String keyword;

    /**
     * 索引位置
     */
    private int start;

    /**
     * 返回记录数
     * 服务提供方有限制，禁止用户传递过大的数值，默认最大为200
     */
    private int rows;

    /**
     * 客户端ip，必传
     */
    private String clientIP;

    /**
     * 业务使用方，必传
     */
    private String client;

    /**
     * 排序模式
     */
    private String sort;

    /**
     * 是否调试
     */
    private boolean debug;

    /**
     * 记录参与查询了的字段名称
     */
    private Map<String, String> fields;

    /**
     * 其他参数
     */
    private Map<String, String> params;

    /**
     * 显示字段
     */
    private String field;

    /**
     * 查询条件
     */
    private List<String> query;

    /**
     * 过滤条件
     */
    private List<String> filterQuery;

    /**
     * 统计条件
     */
    private FacetSearchInfo facetSearchInfo;

    /**
     * 分组统计条件
     */
    private GroupSearchInfo groupSearchInfo;

    /**
     * 附加数据
     */
    private Map<String, Object> payload;

    public SearchInfo() {
        this(0, 10);
    }

    public SearchInfo(int start, int rows) {
        this(start, rows, false);
    }

    public SearchInfo(int start, int rows, boolean debug) {
        this(null, start, rows, debug);
    }

    public SearchInfo(String keyword) {
        this(keyword, 0, 10);
    }

    public SearchInfo(String keyword, int start, int rows) {
        this(keyword, start, rows, false);
    }

    public SearchInfo(String keyword, int start, int rows, boolean debug) {
        this.keyword = keyword;
        this.start = start;
        this.rows = rows;
        this.debug = debug;
        this.query = new ArrayList<>();
        this.filterQuery = new ArrayList<>();
        this.facetSearchInfo = new FacetSearchInfo();
        this.groupSearchInfo = new GroupSearchInfo();
        this.fields = new LinkedHashMap<>();
        this.payload = new LinkedHashMap<>();
        this.params = new LinkedHashMap<>();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public List<String> getQuery() {
        return query;
    }

    public List<String> getFilterQuery() {
        return filterQuery;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public FacetSearchInfo getFacetSearchInfo() {
        return facetSearchInfo;
    }

    public GroupSearchInfo getGroupSearchInfo() {
        return groupSearchInfo;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public void setFacetSearchInfo(FacetSearchInfo facetSearchInfo) {
        this.facetSearchInfo = facetSearchInfo;
    }

    public void setGroupSearchInfo(GroupSearchInfo groupSearchInfo) {
        this.groupSearchInfo = groupSearchInfo;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void addData(String key, String value) {
        this.payload.put(key, value);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void set(String key, String value) {
        this.params.put(key, value);
    }

    public String get(String key) {
        return this.params.get(key);
    }

    @Override
    public String toString() {
        return "SearchInfo{" +
                "start=" + start +
                ", rows=" + rows +
                ", clientIP='" + clientIP + '\'' +
                ", client='" + client + '\'' +
                ", sort='" + sort + '\'' +
                ", debug=" + debug +
                ", fields=" + fields +
                ", params=" + params +
                ", field='" + field + '\'' +
                ", query=" + query +
                ", filterQuery=" + filterQuery +
                ", facetSearchInfo=" + facetSearchInfo +
                ", groupSearchInfo=" + groupSearchInfo +
                ", payload=" + payload +
                '}';
    }
}

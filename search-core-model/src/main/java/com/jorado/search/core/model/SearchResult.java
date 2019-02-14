package com.jorado.search.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public class SearchResult<T> implements Serializable {

    /**
     * 跟踪id
     */
    private String traceId;

    /**
     * 起始索引
     */
    private int start;

    /**
     * 返回记录数
     */
    private int rows;

    /**
     * 记录总数
     */
    private long numFound;

    /**
     * 记录内容
     */
    private List<T> docs;

    /**
     * 统计数据
     */
    private FacetResult facet;

    /**
     * 分组统计数据
     */
    private List<GroupResult<T>> group;

    /**
     * 调试信息
     */
    private List debugInfo;

    /**
     * 额外扩展数据
     */
    private Map<String, Object> payload;

    public SearchResult() {
        this(0, 10);
    }

    public SearchResult(int start, int rows) {
        this(start, rows, 0);
    }

    public SearchResult(int start, int rows, long numFound) {
        this(start, rows, 0, new ArrayList<>());
    }

    public SearchResult(int start, int rows, long numFound, List<T> docs) {
        this.start = start;
        this.rows = rows;
        this.numFound = numFound;
        this.docs = docs;
        this.facet = new FacetResult();
        this.group = new ArrayList<>();
        this.payload = new HashMap<>();
        this.debugInfo = new ArrayList<>();
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

    public List<T> getDocs() {
        return docs;
    }

    public void setDocs(List<T> docs) {
        this.docs = docs;
    }

    public long getNumFound() {
        return numFound;
    }

    public void setNumFound(long numFound) {
        this.numFound = numFound;
    }

    public FacetResult getFacet() {
        return facet;
    }

    public void setFacet(FacetResult facet) {
        this.facet = facet;
    }

    public List<GroupResult<T>> getGroup() {
        return group;
    }

    public void setGroup(List<GroupResult<T>> group) {
        this.group = group;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public List getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(List debugInfo) {
        this.debugInfo = debugInfo;
    }
}

package com.jorado.search.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jorado.logger.util.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

public abstract class Doc {

    private String explain;
    private float score;

    /**
     * 打分过程
     *
     * @return
     */
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    /**
     * 得分
     *
     * @return
     */
    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    /**
     * 唯一标示
     *
     * @return
     */
    public abstract String getPID();

    /**
     * 生成solr document模板
     *
     * @return
     */
    public String toSolrDocumentString() {

        List<String> list = new ArrayList<>();
        list.add("SolrInputDocument doc = new SolrInputDocument();");
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            String docName;
            boolean hasMeta = f.isAnnotationPresent(JsonProperty.class);
            if (hasMeta) {
                JsonProperty meta = f.getAnnotation(JsonProperty.class);
                docName = meta.defaultValue();
            } else {
                docName = f.getName();
            }
            list.add(String.format("doc.addField(\"%s\", %s.get%s());", docName, this.getClass().getSimpleName().toLowerCase(), f.getName()));
        }

        return StringUtils.joinString(list, null, null, "\r\n");
    }

    public String toObjectMapString(String modelName) {

        List<String> list = new ArrayList<>();
        list.add(String.format("%s model = new %s();", modelName, modelName));
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            String docName;
            boolean hasMeta = f.isAnnotationPresent(JsonProperty.class);
            if (hasMeta) {
                JsonProperty meta = f.getAnnotation(JsonProperty.class);
                docName = meta.defaultValue();
            } else {
                docName = f.getName();
            }
            list.add(String.format("model.set%s(getDocumentValue(doc,\"%s\",\"\"));", docName, docName));
        }

        return StringUtils.joinString(list, null, null, "\r\n");
    }

    /**
     * 转换成solr document
     *
     * @return
     * @throws IllegalAccessException
     */
    public SolrInputDocument toSolrDocument() throws IllegalAccessException {

        SolrInputDocument doc = new SolrInputDocument();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            String docName;
            boolean hasMeta = f.isAnnotationPresent(JsonProperty.class);
            if (hasMeta) {
                JsonProperty meta = f.getAnnotation(JsonProperty.class);
                docName = meta.defaultValue();
            } else {
                docName = f.getName();
            }
            f.setAccessible(true);
            doc.addField(docName, f.get(this));

        }
        return doc;
    }
}

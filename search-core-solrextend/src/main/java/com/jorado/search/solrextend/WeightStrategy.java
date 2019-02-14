package com.jorado.search.solrextend;

/**
 * 权重策略接口
 */
public interface WeightStrategy {

    /**
     * 计算得分
     *
     * @return
     */
    double score();

}

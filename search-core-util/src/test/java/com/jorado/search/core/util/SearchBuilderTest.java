package com.jorado.search.core.util;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.Factory.ApiConditionFactory;
import com.jorado.search.core.util.condition.QueryCondition;
import com.jorado.search.core.util.condition.RangeCondition;
import org.junit.Test;

public class SearchBuilderTest {

    @Test
    public void addQuery() {

        SearchBuilder searchBuilder = new SearchBuilder(0, 10, false);
        searchBuilder.setConditionFactory(new ApiConditionFactory());
        try {

            Condition q1 = new RangeCondition("q1", "1", 6);
            Condition q2 = new QueryCondition("q2", "1");

            Condition q3 = new RangeCondition("q3", "1", 5);
            Condition q4 = new QueryCondition("q4", "1");

            Condition q5 = q1.and(q2);
            Condition q6 = q3.and(q4);
            searchBuilder.addQuery(q5.or(q6));

            SearchInfo searchInfo = searchBuilder.build();

            System.out.println(searchInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void test() {

    }
}
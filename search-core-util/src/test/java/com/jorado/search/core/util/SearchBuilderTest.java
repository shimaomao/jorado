package com.jorado.search.core.util;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.QueryCondition;
import com.jorado.search.core.util.condition.RangeCondition;
import org.junit.Test;

public class SearchBuilderTest {

    @Test
    public void addQuery() {

        SearchBuilder searchBuilder = new SearchBuilder(0, 10, false);

        try {
            Condition q1 = new RangeCondition("q1", "1", 5);
            Condition q2 = new QueryCondition("q2", "1,2,3");
            Condition q3 = new QueryCondition("q3", "1");
            Condition q4 = new QueryCondition("q4");

            Condition q5 = q1.or(q2).or(q3);

            Condition q6 = q5.not(q4).or(q5);

            searchBuilder.addQuery(q5.and(q6));

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
package com.jorado.search.core.util.condition.Factory;

import com.jorado.core.util.StringUtils;
import com.jorado.search.core.util.Constants;
import com.jorado.search.core.util.condition.*;

import java.util.ArrayList;
import java.util.List;

public class SolrConditionFactory extends AbstractConditionFactory {

    @Override
    protected String toQuery(InCondition condition) {
        List<String> items = new ArrayList<>();
        String t;
        for (String value : condition.getValues()) {
            t = value.replaceAll(",", Constants.MULTI_SEPARATOR);
            String[] values = StringUtils.splitString(t, Constants.MULTI_SEPARATOR, true);

            for (String v : values) {
                items.add(String.format("%s:%s", condition.getField(), v));
            }
        }
        return StringUtils.joinString(items, " OR ");
    }

    @Override
    protected String toQuery(RangeCondition condition) {
        return String.format("%s:[%s TO %s]", condition.getField(), condition.getFrom(), condition.getTo());
    }

    @Override
    protected String toQuery(KeywordCondition condition) {
        return String.format("%s:%s", condition.getField(), StringUtils.escapeSolrString(condition.getWord()));
    }

    @Override
    protected String toQuery(CoordinateCondition condition) {
        return String.format("{!geofilt pt=%s,%s sfield=%s d=%d}", condition.getLatitude(), condition.getLongitude(), condition.getField(), condition.getDistance());
    }

    @Override
    protected String toQuery(QueryCondition condition) {
        return String.format("%s:%s", condition.getField(), condition.getValue());
    }
}

package com.jorado.search.solrextend.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数函数
 */
public class RandomFunction extends ValueSource {

    protected final double min;
    protected final double max;

    public RandomFunction(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {

        return new DoubleDocValues(this) {
            @Override
            public double doubleVal(int doc) {
                return ThreadLocalRandom.current().nextDouble(min, max);
            }

            @Override
            public String toString(int doc) {
                return description();
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomFunction that = (RandomFunction) o;
        return Double.compare(that.min, min) == 0 &&
                Double.compare(that.max, max) == 0;
    }

    @Override
    public int hashCode() {
        int h = String.valueOf(min).hashCode();
        h ^= (h << 13) | (h >>> 20);
        h += String.valueOf(max).hashCode();
        return h;
    }

    @Override
    public String description() {
        return "random(" + min + "," + max + ")";
    }
}
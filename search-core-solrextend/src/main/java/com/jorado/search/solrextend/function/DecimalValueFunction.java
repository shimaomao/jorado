package com.jorado.search.solrextend.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class DecimalValueFunction extends ValueSource {

    protected final ValueSource fieldVS;
    protected final Float value;

    public DecimalValueFunction(ValueSource valueSource, Float value) {
        this.fieldVS = valueSource;
        this.value = value;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {

        final FunctionValues fv = fieldVS.getValues(context, readerContext);

        return new DoubleDocValues(this) {
            @Override
            public double doubleVal(int doc) {
                try {
                    double doubleVal = fv.doubleVal(doc);
                    double decimalValue = Math.round(doubleVal * 1000) / 1000.0;
                    return decimalValue * value;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecimalValueFunction that = (DecimalValueFunction) o;
        return Objects.equals(fieldVS, that.fieldVS) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int h = fieldVS.hashCode();
        h ^= (h << 13) | (h >>> 20);
        h += String.valueOf(value).hashCode();
        return h;
    }

    @Override
    public String description() {
        return "dv(field," + value + ")";
    }
}

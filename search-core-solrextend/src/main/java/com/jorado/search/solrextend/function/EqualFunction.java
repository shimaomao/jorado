package com.jorado.search.solrextend.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class EqualFunction extends ValueSource {

    protected final ValueSource fieldVS;
    protected final String value;

    public EqualFunction(ValueSource fieldVS, String value) {
        this.fieldVS = fieldVS;
        this.value = value;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues fv = fieldVS.getValues(context, readerContext);
        return new IntDocValues(this) {
            @Override
            public int intVal(int doc) {
                String v = fv.strVal(doc);
                return v.equals(value) ? 1 : 0;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualFunction that = (EqualFunction) o;
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
        return String.format("eq(field,%s)", value);
    }
}
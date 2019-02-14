package com.jorado.search.solrextend.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.IntDocValues;

import java.io.IOException;
import java.util.Map;

public class IntEqualFunction extends ValueSource {

    protected final ValueSource fieldVS;
    protected final int value;

    public IntEqualFunction(ValueSource fieldVS, int value) {
        this.fieldVS = fieldVS;
        this.value = value;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues fv = fieldVS.getValues(context, readerContext);
        return new IntDocValues(this) {
            @Override
            public int intVal(int doc) {
                int v = fv.intVal(doc);
                return v == value ? 1 : 0;
            }
        };
    }

    @Override
    public int hashCode() {
        int h = fieldVS.hashCode();
        h ^= (h << 13) | (h >>> 20);
        h += String.valueOf(value).hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IntEqualFunction other = (IntEqualFunction) obj;
        if (fieldVS == null) {
            if (other.fieldVS != null)
                return false;
        } else if (!fieldVS.equals(other.fieldVS))
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String description() {
        return String.format("ieq(field,%s)", value);
    }
}
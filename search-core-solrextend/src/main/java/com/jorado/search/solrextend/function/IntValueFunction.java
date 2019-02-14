package com.jorado.search.solrextend.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.DoubleDocValues;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


public class IntValueFunction extends ValueSource {

    protected final ValueSource fieldVS;
    protected final Float[] scores;
    protected final Integer[] values;

    public IntValueFunction(ValueSource fieldVS, String value, String score) {
        this.fieldVS = fieldVS;
        String[] valuestr = value.split("-");
        String[] scorestr = score.split("-");
        values = new Integer[valuestr.length];
        scores = new Float[scorestr.length];
        for (int i = 0; i < valuestr.length; i++) {
            values[i] = Integer.parseInt(valuestr[i]);
            scores[i] = Float.parseFloat(scorestr[i]);
        }
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues functionValues = fieldVS.getValues(context, readerContext);

        return new DoubleDocValues(this) {
            @Override
            public double doubleVal(int doc) {
                try {
                    int intvalue = functionValues.intVal(doc);
                    for (int i = scores.length - 1; i >= 0; i--) {
                        if (values[i] < intvalue) {
                            return scores[i];
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValueFunction that = (IntValueFunction) o;
        return Objects.equals(fieldVS, that.fieldVS) &&
                Arrays.equals(scores, that.scores) &&
                Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fieldVS);
        result = 31 * result + Arrays.hashCode(scores);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public String description() {
        return "iv(field,value,score)";
    }
}

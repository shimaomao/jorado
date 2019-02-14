package com.jorado.search.solrextend;

import com.jorado.search.solrextend.function.*;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;


public class CoreParser extends ValueSourceParser {
    static {

        /**
         * 随机数函数
         */
        addParser("random", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {
                final double min = fp.parseDouble();
                final double max = fp.parseDouble();
                return new RandomFunction(min, max);
            }
        });

        addParser("eq", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {

                final ValueSource fieldVs = fp.parseValueSource();
                final String value = fp.parseArg();

                return new EqualFunction(fieldVs, value);
            }
        });

        addParser("ieq", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {

                final ValueSource fieldVs = fp.parseValueSource();
                final int value = fp.parseInt();

                return new IntEqualFunction(fieldVs, value);
            }
        });

        addParser("dv", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {

                final ValueSource fieldVs = fp.parseValueSource();
                final float value = fp.parseFloat();

                return new DecimalValueFunction(fieldVs, value);
            }
        });

        addParser("iv", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {

                final ValueSource fieldVs = fp.parseValueSource();
                final String value = fp.parseArg();
                final String score = fp.parseArg();

                return new IntValueFunction(fieldVs, value, score);
            }
        });

        addParser("dr", new ValueSourceParser() {
            @Override
            public ValueSource parse(FunctionQParser fp) throws SyntaxError {

                final ValueSource coordinateVS = fp.parseValueSource();
                final ValueSource rankVS = fp.parseValueSource();
                final double lng = fp.parseDouble();
                final double lat = fp.parseDouble();

                return new DistanceRankFunction(coordinateVS, rankVS, lng, lat);
            }
        });


    }

    @Override
    public ValueSource parse(FunctionQParser fp) {
        return null;
    }
}

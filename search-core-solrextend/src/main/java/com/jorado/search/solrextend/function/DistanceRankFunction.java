package com.jorado.search.solrextend.function;


import com.jorado.search.solrextend.util.GeoUtils;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.FloatDocValues;

import java.io.IOException;
import java.util.Map;

public class DistanceRankFunction extends ValueSource {

    protected final ValueSource coordinateVS;
    protected final ValueSource rankVS;
    protected final double lng;
    protected final double lat;

    public DistanceRankFunction(ValueSource coordinateVS, ValueSource rankVS, double lng, double lat) {
        this.coordinateVS = coordinateVS;
        this.rankVS = rankVS;
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public FunctionValues getValues(Map context, LeafReaderContext readerContext) throws IOException {
        final FunctionValues coordinateFV = coordinateVS.getValues(context, readerContext);
        final FunctionValues rankFV = rankVS.getValues(context, readerContext);
        return new FloatDocValues(this) {

            @Override
            public float floatVal(int doc) {
                float sorce1 = 0.1f;
                float sorce2 = 0;
                double[] lngLat = new double[]{0, 0};
                coordinateFV.doubleVal(doc, lngLat);

                if (lngLat.length == 2 && lngLat[0] != 0) {
                    double distance = GeoUtils.getDistance(lng, lat, lngLat[0], lngLat[1]);
                    if (distance < 1000)
                        sorce1 = 0.5f;
                    else if (distance < 2000)
                        sorce1 = 0.45f;
                    else if (distance < 3000)
                        sorce1 = 0.35f;
                    else if (distance < 5000)
                        sorce1 = 0.25f;
                    else if (distance < 8000)
                        sorce1 = 0.15f;
                    else if (distance > 15000) {
                        sorce1 = 0;
                    }
                }

                int rank = rankFV.intVal(doc);
                sorce2 = (rank / 10f) * 0.5f;
                return sorce1 + sorce2;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()) return false;
        DistanceRankFunction other = (DistanceRankFunction) o;
        return this.coordinateVS.equals(other.coordinateVS)
                && this.rankVS.equals(other.rankVS)
                && this.lat == other.lat && this.lng == other.lng;
    }

    @Override
    public int hashCode() {
        int h = coordinateVS.hashCode();
        h ^= (h << 13) | (h >>> 20);
        h += rankVS.hashCode();
        h ^= (h << 23) | (h >>> 10);
        h += String.valueOf(lat).hashCode();
        h ^= (h << 23) | (h >>> 10);
        h += String.valueOf(lng).hashCode();
        return h;
    }

    @Override
    public String description() {
        return "dr" + "(Coordinate,rank," + this.lng + "," + this.lat + ")";
    }

    public static void main(String[] args) {
        float sorce1 = 0.3f;
        float sorce2 = 0;
        double[] lngLat = new double[]{53.37, -2.92};

        if (lngLat.length == 2 && lngLat[0] != 0) {
            double distance = GeoUtils.getDistance(-2.89, 53.39, lngLat[0], lngLat[1]);
            if (distance < 1000)
                sorce1 = 0.5f;
            else if (distance < 2000)
                sorce1 = 0.45f;
            else if (distance < 3000)
                sorce1 = 0.35f;
            else if (distance < 5000)
                sorce1 = 0.25f;
            else if (distance < 8000)
                sorce1 = 0.15f;
            else if (distance > 15000) {
                sorce1 = 0;
            }
        }

        int rank = 1;
        sorce2 = (rank / 10f) * 0.5f;
        System.out.println(sorce1);
        System.out.println(sorce2);
    }
}

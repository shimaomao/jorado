package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

/**
 * 地理位置查询
 */
public class CoordinateCondition extends Condition {

    private double longitude;
    private double latitude;
    private int distance;

    public CoordinateCondition(String field, double longitude, double latitude, int distance) {
        super(field);
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public CoordinateCondition(String field, double longitude, double latitude) {
        this(field, longitude, latitude, 2);
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.COORDINATE;
    }

    @Override
    public String getValue() {
        return String.format("%s;%s;%d", latitude, longitude, distance);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

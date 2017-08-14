package com.matcha.model;

public class DistanceCalculator {

    public DistanceCalculator() {

    }


    public double calculateDistanceTo(Point currentPoint, Point destinationPoint) {
        double theta = currentPoint.longitude - destinationPoint.longitude;
        double dist = Math.sin(deg2rad(currentPoint.latitude)) * Math.sin(deg2rad(destinationPoint.latitude)) + Math.cos(deg2rad(currentPoint.latitude)) * Math.cos(deg2rad(destinationPoint.latitude)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    static class Point
    {
        double latitude;
        double longitude;

        public Point(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
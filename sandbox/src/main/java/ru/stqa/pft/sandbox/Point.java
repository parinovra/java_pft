package ru.stqa.pft.sandbox;

public class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p) {
        return Math.sqrt(Math.pow(Math.abs((p.x - this.x)), 2) + Math.pow(Math.abs((p.y - this.y)), 2));
    }
}

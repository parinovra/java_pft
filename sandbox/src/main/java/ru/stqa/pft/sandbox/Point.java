package ru.stqa.pft.sandbox;

public class Point {

    public static void main(String[] args) {

        double x1 = 1;
        double x2 = 2;
        double y1 = 3;
        double y2 = 4;

        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);

        System.out.println("Расстояние между двумя точками (" + x1 + ", " + y1 + ") и (" + x2 + ", " + y2 + ") = " + distance(dx,dy));
    }

    public static double distance(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}

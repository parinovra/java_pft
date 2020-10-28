package ru.stqa.pft.sandbox;

public class MyFirstProgram {

	public static void main(String[] args) {
		hello("world");
		hello("user");
		hello("Roman");

		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной "  + s.l + " = " + s.area());

		Rectangle r = new Rectangle(4, 6);
		System.out.println("Площадь прямоугольника со сторонами "  + r.a + " и " + r.b + " = " + r.area());

		Point p1 = new Point();
		p1.x = 1;
		p1.y = 3;

		Point p2 = new Point();
		p2.x = 2;
		p2.y = 4;

//		double dx = Math.abs(p2.x - p1.x);
//		double dy = Math.abs(p2.y - p1.y);

		System.out.println("Расстояние между двумя точками (" + p1.x + ", " + p1.y + ") и (" + p2.x + ", " + p2.y + ") = " + distance(p1, p2));
	}

	public static void hello(String somebody) {
		System.out.println("Hello " + somebody + "!");
	}

	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow((Math.abs(p2.x - p1.x)), 2) + Math.pow((Math.abs(p2.y - p1.y)), 2));
	}
}
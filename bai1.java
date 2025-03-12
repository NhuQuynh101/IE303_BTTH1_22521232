import java.util.Random;
import java.util.Scanner;

public class bai1 {

    public static double piApprox() {
        int totalPoints = 1000000;
        int pointsInCircle = 0;
    
        Random random = new Random();
        for (long i = 0; i < totalPoints; i++) {
            double x = random.nextDouble() * 2 - 1;
            double y = random.nextDouble() * 2 - 1;
            if (x * x + y * y <= 1) {
                pointsInCircle++;
            }
        }
        double pi = 4.0 * pointsInCircle / totalPoints;
        return pi;
    }
    
    public static double area(double r){
        return piApprox()*r*r;
    }
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Nhap ban kinh hinh tron:");
            double r = scanner.nextDouble();
            System.out.println("Dien tich hinh tron ban kinh " + r + ": " + area(r));
        }
    }
    
}
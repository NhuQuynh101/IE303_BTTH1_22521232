import java.util.Random;

public class bai2 {

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
    
    public static void main(String[] args) {
        double piValue = piApprox(); 
        System.out.println("Xap xi gia tri cua pi: " + piValue);
    }
}
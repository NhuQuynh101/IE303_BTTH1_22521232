import java.util.*;

public class bai3 {
    public static void main(String[] args) {
        List<Point> stationPoints = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(System.in)) { 
            System.out.println("Nhập số lượng trạm: ");
            int n = scanner.nextInt(); 
            
            System.out.println("Nhập tọa độ các trạm: ");
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                stationPoints.add(new Point(x, y));
            }
        }
        
        List<Point> boundaryStations = findBoundary(stationPoints);
        
        System.out.println("Các trạm được sử dụng làm trạm cảnh báo:");
        for (Point station : boundaryStations) {
            System.out.println(station.x + " " + station.y);
        }
    }
    
    public static List<Point> findBoundary(List<Point> stationPoints) {
        stationPoints.sort(Comparator.comparingInt((Point p) -> p.x).thenComparingInt(p -> p.y));
        List<Point> hull = new ArrayList<>();
    
        for (Point station : stationPoints) {
            while (hull.size() >= 2 && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), station) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(station);
        }
        
        int hullSize = hull.size() + 1;
        for (int i = stationPoints.size() - 1; i >= 0; i--) {
            Point station = stationPoints.get(i);
            while (hull.size() >= hullSize && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), station) <= 0) {
                hull.remove(hull.size() - 1);
            }
            hull.add(station);
        }
        
        hull.remove(hull.size() - 1); 
        return hull;
    }
    
    private static int cross(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }
    
    public static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

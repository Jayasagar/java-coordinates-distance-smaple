/*
|
|
|
| + (1,4)   + (4,4)
|
|     
|       + (3,2)
|                       + (5,1)
|________________________

Given a set of coordinates [(x1,y2), ..., (xn,yn)], 
create a testable application that determines:
 - the two closest points
 - the two most distant points
Use your knowledge of DDD, OOP and Clean Code.
Start with tests or implementation, whatever is better for you.
*/

// sqrt((x1 - x2)^2 + (y1 - y2)^2)

// [(1,2) , (2,3) , (3, 4)]

import org.junit.*;
import org.junit.runner.*;
import java.util.List;
import java.util.ArrayList;
import com.google.common.math.DoubleMath;

public class Solution {
  
  class Result {
    private Point p1;
    private Point p2;
    private double distance;
    
   public Point getPoint1() {
     return this.p1;
   }
    
   public Point getPoint2() {
     return this.p2;
   }
    
   public void setPoint1(Point p1) {
     this.p1 = p1;
   }
    
   public void setPoint2(Point p2) {
     this.p2 = p2;
   }
    
   public double getDistance() {
     return this.distance;
   }
    
   public void setDistance(double distance) {
     this.distance = distance;
   }
    
   // TODO: Fix equals and hashcode 
    
   @Override 
   public String toString(){//overriding the toString() method  
     StringBuilder sb = new StringBuilder("");
     sb.append("distance:" + this.distance);
     if (this.p1 != null) {
       sb.append(", p1:"+p1.x +"," + p1.y);
     }
     
     if (this.p2 != null) {
       sb.append(", p2:"+p2.x +"," + p2.y);
     }
     return sb.toString();
   }  
    
  } 
  
  class Point {
    private double x;
    private double y;
    
    Point(double x, double y) {
      this.x = x;
      this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {  
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Point)) { 
            return false; 
        } 
  
        Point c = (Point) o; 

        return c.x == this.x && c.y == this.y; 
    } 
    
    @Override
    public int hashCode() {
      // TODO: Think about better solution ?
      return (int)(x - y);
    }
  }
  
  public double getDistance(Point p1, Point p2) {
    return Math.sqrt((Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2)));
  }
  
  /**
  **/
  public Result findClosestPoints(List<Point> coordinates) {
    if (coordinates == null || coordinates.size() < 2) {
      throw new IllegalArgumentException();
    }
    
    double minDistance = -1;
    Result minDistanceResult = new Result();
    
    for (int i = 0; i<coordinates.size() - 1; i++) {
      for (int j = i; j<coordinates.size(); j++) {
        if (j == i) {
          continue;
        }
        
        double distance = getDistance(coordinates.get(i), coordinates.get(j));
        if(minDistance == -1 || distance < minDistance) {
          minDistance = distance;
          minDistanceResult.setPoint1(coordinates.get(i));
          minDistanceResult.setPoint2(coordinates.get(j));
          minDistanceResult.setDistance(minDistance);
        }
        
      }
    }
    
    return minDistanceResult;
  }
  
  @Test
  public void testGetDistanceValueToZero() {
    Solution s = new Solution();
    Point p1 = new Point(1, 2);
    Point p2 = new Point(1, 2);
    double distance = s.getDistance(p1, p2);
    Assert.assertTrue(distance == 0.0);
  }
  
  @Test
  public void testGetDistanceShouldWork() {
    Solution s = new Solution();
    Point p1 = new Point(4, 2);
    Point p2 = new Point(2, 1);
    double distance = s.getDistance(p1, p2);
    Assert.assertTrue(DoubleMath.fuzzyEquals(distance, 2.236067, 4));
    
  }
  
  @Test
  public void testGetDistanceShouldBeSameIrrespectiveOfInputPointsOrder() {
    Solution s = new Solution();
    Point p1 = new Point(4, 2);
    Point p2 = new Point(2, 1);
    double distance1 = s.getDistance(p1, p2);
    double distance2 = s.getDistance(p2, p1);
    Assert.assertTrue(distance1 == distance2);
    
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testFindClosestPointsShouldThrowExceptionOnEmptyCorordinates() {
    Solution s = new Solution();
    s.findClosestPoints(new ArrayList<Point>());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testFindClosestPointsShouldThrowExceptionOnSinglePoint() {
    Solution s = new Solution();
    Point p1 = new Point(1, 2);
    List<Point> points = new ArrayList<Point>();
    points.add(p1);
    s.findClosestPoints(points);
    
  }
  
  @Test
  public void testFindClosestPointsShouldWorkFor2Points() {
    Solution s = new Solution();
    Point p1 = new Point(1, 2);
    Point p2 = new Point(2, 1);
    List<Point> points = new ArrayList<Point>();
    points.add(p1);
    points.add(p2);
    Result r = s.findClosestPoints(points);
    
    Assert.assertTrue(r.p1.equals(p1));
    Assert.assertTrue(r.p2.equals(p2));
  }
  
  @Test
  public void testFindClosestPointsShouldWorkFor3Points() {
    Solution s = new Solution();
    Point p1 = new Point(1, 2);
    Point p2 = new Point(2, 1);
    Point p3 = new Point(1, 1);
    List<Point> points = new ArrayList<Point>();
    points.add(p1);
    points.add(p2);
    points.add(p3);
    Result r = s.findClosestPoints(points);
    
    Assert.assertTrue(r.p1.equals(p1));
    Assert.assertTrue(r.p2.equals(p3));
  }
  
  public static void main(String[] args) {
    JUnitCore.main("Solution");
  }
}

package pstoriz.desacore.util;

//Class contains a vector, has 2 coordinates, and is an doubleeger
public class Vector2d {

   private double x, y;
   
   public Vector2d(Vector2d vector){
      set(vector.x, vector.y);
   }
   
   public Vector2d(){
      set(0, 0);
   }
   
   public Vector2d(double x, double y){
      set(x, y);
   }
   
   public void set(double x, double y){
      this.x = x;
      this.y = y;
   }
   
   public double getX(){
      return x;
   }
   public double getY(){
      return y;
   }
   
   public Vector2d setX(double x){
      this.x = x;
      return this;

   }
   public Vector2d setY(double y){
      this.y = y;
      return this;
   }
   
   public Vector2d add(Vector2d vector){
      this.x += vector.x;
      this.y += vector.y;
      return this;
   }
   public Vector2d subtract(Vector2d vector){
      this.x -= vector.x;
      this.y -= vector.y;
      return this;
   }
   
   public boolean equals(Object object){
      if(!(object instanceof Vector2d)) return false;
      Vector2d vec = (Vector2d) object;
      if(vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
      return false;
   }
   
}
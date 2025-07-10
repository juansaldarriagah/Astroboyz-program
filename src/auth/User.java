package auth;
import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected int points;

public User (String username, String password){
    this.username=username;
    this.password=password;
    this.points=0
}
public String getUsername(){
    return username;
}
public void setUsername(){
    this.username=username;
}
public String getpassword(){
    return password;
}
public void setPassword(String password){
    this.password=password;
}
public int getPoints(){
    return points;
} 
public void setPoints(int points){
    this.points=points;
}
public void addpoints (int points){
    this.points+=points;
}
public void substractpoints (int points){
    this.points=Math.max(0,this.puntos-puntos);
}
    @Override 
    public String toString(){
        return "Usuario: "+ Username + "| Puntos: "+points);
    }
}

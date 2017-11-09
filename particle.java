import java.util.*;
import java.awt.Color;
public class particle
{
    public particle(double x_ini,double y_ini,double vx_ini,double vy_ini,double mu_ini,double r_ini,Color color_ini)
    {
        x = x_ini;
        y = y_ini;
        vx = vx_ini;
        vy = vy_ini;
        ax = 0;
        ay = 0;
        mu = mu_ini;
        color = color_ini;
        trail = new double[][] {{x,y}};
        r = r_ini;
        d = 2*r;
    }
    public void gravitate(particle other,double dt)
    {
        double r = Math.sqrt((x - other.x)*(x - other.x) + (y - other.y)*(y - other.y));
        double ang = Math.atan((y - other.y)/(x - other.x));
        if(x < other.x)
        {
            ax = ax + Math.abs(dt*other.mu*Math.cos(ang)/(r*r));
        }
        else
        {
            ax = ax - Math.abs(dt*other.mu*Math.cos(ang)/(r*r));
        }
        if(y < other.y)
        {
            ay = ay + Math.abs(dt*other.mu*Math.sin(ang)/(r*r));
        }
        else
        {
            ay = ay - Math.abs(dt*other.mu*Math.sin(ang)/(r*r));
        }
    }
    public boolean collided(particle other)
    {
        double xDisp = x - other.x;
        double yDisp = y - other.y;
        double distance = Math.sqrt(xDisp*xDisp + yDisp*yDisp);
        return distance <= r + other.r;
    }
    public void iterate(double dt,int maxTrailLength)
    {
        double[][] tempTrail = new double[maxTrailLength][2];
        if(trail.length < maxTrailLength)
        {
            tempTrail = new double[trail.length + 1][2];
        }
        vx = vx + ax;
        vy = vy + ay;
        x = x + vx*dt;
        y = y + vy*dt;
        tempTrail[0][0] = x;
        tempTrail[0][1] = y;
        for(int i = 1;i < tempTrail.length;i++)
        {
            tempTrail[i] = trail[i - 1];
        }
        trail = tempTrail;
    }
    public void resetAccel()
    {
        ax = 0;
        ay = 0;
    }
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double ax;
    public double ay;
    public double mu;
    public double[][] trail;
    public double r;
    public double d;
    public Color color;
}

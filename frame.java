import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class frame extends JPanel implements ActionListener
{
    Timer timer = new Timer(1,this);
    Graphics2D g2D;
    Graphics g;
    particle[] particles;
    double dt = 0.4;
    double dtTemp = 0.4;
    double camX = 0;
    double camY = 0;
    double zoom = 4;
    boolean followingCOM = true;
    boolean paused = false;
    boolean showVel = false;
    boolean showAccel = false;
    boolean showTrail = true;
    boolean showBody = true;
    int height;
    int width;
    int maxTrailLength = 256;
    public frame(int width_ini,int height_ini,String fileName)
    {
        width = width_ini;
        height = height_ini;
        particles = file.load("frame1.txt");
        if(!fileName.equals("null"))
        {
            particles = file.load(fileName);
        }
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g2D = (Graphics2D) g;
        setBackground(Color.BLACK);
        for(int i = 0;i < particles.length;i++)
        {
            g2D.setStroke(new BasicStroke((float)(particles[i].r*zoom/4)));
            if(showTrail)
            {
                int maxTrailLengthTemp = maxTrailLength;
                for(int j = 0;j < particles[i].trail.length - 1;j++)
                {
                    g2D.setColor(new Color(particles[i].color.getRed(),particles[i].color.getGreen(),particles[i].color.getBlue(),255*(particles[i].trail.length - j)/particles[i].trail.length));
                    Line2D line = new Line2D.Double((camX + particles[i].trail[j][0])*zoom + 0.5*width,(camY - particles[i].trail[j][1])*zoom + 0.5*height,(camX + particles[i].trail[j + 1][0])*zoom + 0.5*width,(camY - particles[i].trail[j + 1][1])*zoom + 0.5*height);
                    g2D.draw(line);
                }
            }
        }
        if(followingCOM)
        {
            double muSum = 0;
            double comX = 0;
            double comY = 0;
            for(int i = 0;i < particles.length;i++)
            {
                muSum = muSum + particles[i].mu;
                comX = comX + particles[i].x*particles[i].mu;
                comY = comY + particles[i].y*particles[i].mu;
            }
            comX = comX/muSum;
            comY = comY/muSum;
            camX = -comX;
            camY = comY;
        }
        for(int i = 0;i < particles.length;i++)
        {
            if(showBody)
            {
                Ellipse2D ballDraw = new Ellipse2D.Double((camX + particles[i].x - particles[i].r)*zoom + 0.5*width,(camY - particles[i].r - particles[i].y)*zoom + 0.5*height,particles[i].d*zoom,particles[i].d*zoom);
                g2D.setColor(particles[i].color);
                g2D.fill(ballDraw);
            }
            g2D.setStroke(new BasicStroke(1));
            if(showVel)
            {
                Line2D velocityVector = new Line2D.Double((camX + particles[i].x)*zoom + 0.5*width,(camY - particles[i].y)*zoom + 0.5*height,(camX + particles[i].x + 20*particles[i].vx)*zoom + 0.5*width,(camY - particles[i].y - 20*particles[i].vy)*zoom + 0.5*height);
                g2D.setColor(particles[i].color.darker());
                g2D.draw(velocityVector);
            }
            if(showAccel)
            {
                Line2D accelerationVector = new Line2D.Double((camX + particles[i].x)*zoom + 0.5*width,(camY - particles[i].y)*zoom + 0.5*height,(camX + particles[i].x + 200*particles[i].ax/dt)*zoom + 0.5*width,(camY - particles[i].y - 200*particles[i].ay/dt)*zoom + 0.5*height);
                g2D.setColor(particles[i].color.darker());
                g2D.draw(accelerationVector);
            }
        }
        timer.start();
    }
    public void actionPerformed(ActionEvent e)
    {
        dtTemp = dt;
        if(!paused)
        {
            for(int i = 0;i < particles.length;i++)
            {
                particles[i].resetAccel();
                for(int j = 0;j < particles.length;j++)
                {
                    if(i != j)
                    {
                        if(particles[i].collided(particles[j]) && false)
                        {
                            particles[j].x = (particles[j].x*particles[j].mu + particles[i].x*particles[i].mu)/(particles[j].mu + particles[i].mu);
                            particles[j].y = (particles[j].y*particles[j].mu + particles[i].y*particles[i].mu)/(particles[j].mu + particles[i].mu);
                            particles[j].mu = particles[j].mu + particles[i].mu;
                            particles[j].vx = (particles[j].vx*particles[j].mu + particles[i].vx*particles[i].mu)/(particles[j].mu + particles[i].mu);
                            particles[j].vy = (particles[j].vy*particles[j].mu + particles[i].vy*particles[i].mu)/(particles[j].mu + particles[i].mu);
                            particles[j].r = Math.sqrt(particles[j].r*particles[j].r + particles[i].r*particles[i].r);
                            particles[j].d = 2*particles[j].r;
                            particle[] tempParticles = new particle[particles.length - 1];
                            int tempIndex = 0;
                            for(int k = 0;k < particles.length;k++)
                            {
                                if(i != k)
                                {
                                    tempParticles[tempIndex] = particles[k];
                                    tempIndex++;
                                }
                            }
                            particles = tempParticles;
                        }
                        else
                        {
                            particles[i].gravitate(particles[j],dtTemp);
                        }
                    }
                }
            }
            for(int i = 0;i < particles.length;i++)
            {
                particles[i].iterate(dtTemp,maxTrailLength);
            }
            repaint();
        }
    }
}
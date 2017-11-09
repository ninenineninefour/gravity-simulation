import java.io.*;
import java.util.*;
import java.lang.*;
import java.awt.Color;
public class file
{
    public static particle[] load(String fileName)
    {
        particle[] particles = new particle[1000];
        try
        {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int i = 0;
            while(line != null)
            {
                Scanner sc = new Scanner(line);
                sc.useDelimiter(",");
                particles[i] = new particle(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),sc.nextDouble(),Color.RED);
                Color tempColor = new Color(sc.nextInt(),sc.nextInt(),sc.nextInt());
                particles[i].color = tempColor;
                line = br.readLine();
                i++;
            }
            fr.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }
        
        int particlesSize = 0;
        while(particles[particlesSize] != null)
        {
            particlesSize++;
        }
        particle[] particlesTemp = new particle[particlesSize];
        for(int j = 0;j < particlesSize;j++)
        {
            particlesTemp[j] = particles[j];
        }
        particles = particlesTemp;
        return particles;
    }
    public static void save(String fileName,particle[] particles)
    {
        try
        {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i = 0;i < particles.length;i++)
            {
                particle tp = particles[i];
                fw.write(tp.x + "," + tp.y + "," + tp.vx + "," + tp.vy + "," + tp.mu + "," + tp.r + "," + tp.color.getRed() + "," + tp.color.getGreen() + "," + tp.color.getBlue());
            }
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}

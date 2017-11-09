import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
public class main
{
    public static void main(String args[])
    {
        controlWindow ctrl = new controlWindow();
        JFrame f = new JFrame();
        f.add(ctrl);
        f.setSize(300,800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Controls");
        f.setVisible(true);
        ctrl.entryField.setText("default");
    }
}
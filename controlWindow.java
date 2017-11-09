import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class controlWindow extends JPanel implements ActionListener
{
    public frame frame;
    public JButton pushZoomIn = new JButton("Zoom++");
    public JButton pushZoomOut = new JButton("Zoom--");
    public JButton pushFollowCOM = new JButton("Unfollow COM");
    public JButton pushFast = new JButton("Faster");
    public JButton pushPause = new JButton("Pause");
    public JButton pushSlow = new JButton("Slower");
    public JButton pushVel = new JButton("Show velocity");
    public JButton pushAccel = new JButton("Show acceleration");
    public JButton pushTrail = new JButton("Hide trails");
    public JButton pushTrailLong = new JButton("Longer trails");
    public JButton pushTrailShort = new JButton("Shorter trails");
    public JButton pushBody = new JButton("Hide bodies");
    public JTextField entryField = new JTextField("                                         ");
    public JButton pushLoad = new JButton("Load");
    public JButton pushSave = new JButton("Save");
    public JButton pushNext = new JButton("Next");
    public JButton pushPrevious = new JButton("Previous");
    public JButton pushEdit = new JButton("Edit particles");
    JFrame f;
    public int currentFrame = 1;
    public controlWindow()
    {
        add(pushZoomIn);
        pushZoomIn.addActionListener(this);
        add(pushZoomOut);
        pushZoomOut.addActionListener(this);
        add(pushFollowCOM);
        pushFollowCOM.addActionListener(this);
        add(pushFast);
        pushFast.addActionListener(this);
        add(pushPause);
        pushPause.addActionListener(this);
        add(pushSlow);
        pushSlow.addActionListener(this);
        add(pushVel);
        pushVel.addActionListener(this);
        add(pushAccel);
        pushAccel.addActionListener(this);
        add(pushTrail);
        pushTrail.addActionListener(this);
        add(pushTrailLong);
        pushTrailLong.addActionListener(this);
        add(pushTrailShort);
        pushTrailShort.addActionListener(this);
        add(pushBody);
        pushBody.addActionListener(this);
        add(entryField);
        add(pushLoad);
        pushLoad.addActionListener(this);
        //add(pushSave);
        //pushSave.addActionListener(this);
        add(pushNext);
        pushNext.addActionListener(this);
        add(pushPrevious);
        pushPrevious.addActionListener(this);
        //add(pushEdit);
        //pushEdit.addActionListener(this);
        frame = new frame(1000,1000,"null");
        f = new JFrame();
        f.setBackground(Color.BLACK);
        f.add(frame);
        f.setVisible(true);
        f.setSize(frame.width,frame.height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Gravity Simulation");
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == pushZoomIn)
        {
            frame.zoom = frame.zoom*2;
        }
        if(e.getSource() == pushZoomOut)
        {
            frame.zoom = frame.zoom/2;
        }
        if(e.getSource() == pushFollowCOM)
        {
            if(frame.followingCOM)
            {
                pushFollowCOM.setText("Follow COM");
                frame.followingCOM = false;
            }
            else
            {
                pushFollowCOM.setText("Unfollow COM");
                frame.followingCOM = true;
            }
        }
        if(e.getSource() == pushPause)
        {
            if(frame.paused)
            {
                pushPause.setText("Pause");
                frame.paused = false;
            }
            else
            {
                pushPause.setText("Play");
                frame.paused = true;
            }
        }
        if(e.getSource() == pushFast)
        {
            frame.dt = frame.dt*2;
        }
        if(e.getSource() == pushSlow)
        {
            frame.dt = frame.dt/2;
        }
        if(e.getSource() == pushVel)
        {
            if(frame.showVel)
            {
                pushVel.setText("Show velocity");
                frame.showVel = false;
            }
            else
            {
                pushVel.setText("Hide velocity");
                frame.showVel = true;
            }
        }
        if(e.getSource() == pushAccel)
        {
            if(frame.showAccel)
            {
                pushAccel.setText("Show acceleration");
                frame.showAccel = false;
            }
            else
            {
                pushAccel.setText("Hide acceleration");
                frame.showAccel = true;
            }
        }
        if(e.getSource() == pushTrail)
        {
            if(frame.showTrail)
            {
                pushTrail.setText("Show trails");
                frame.showTrail = false;
            }
            else
            {
                pushTrail.setText("Hide trails");
                frame.showTrail = true;
            }
        }
        if(e.getSource() == pushTrailLong || e.getSource() == pushSlow)
        {
            frame.maxTrailLength = frame.maxTrailLength*2;
        }
        if(e.getSource() == pushTrailShort || e.getSource() == pushFast)
        {
            if(frame.maxTrailLength > 1)
            {
                frame.maxTrailLength = frame.maxTrailLength/2;
            }
        }
        if(e.getSource() == pushBody)
        {
            if(frame.showBody)
            {
                pushBody.setText("Show bodies");
                frame.showBody = false;
            }
            else
            {
                pushBody.setText("Hide bodies");
                frame.showBody = true;
            }
        }
        if(e.getSource() == pushLoad)
        {
            f.remove(frame);
            f.dispose();
            frame.particles = file.load(entryField.getText() + ".txt");
            f.setBackground(Color.BLACK);
            f.add(frame);
            f.setVisible(true);
            f.setSize(frame.width,frame.height);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Gravity Simulation");
        }
        if(e.getSource() == pushNext)
        {
            currentFrame++;
            loadFrame();
        }
        if(e.getSource() == pushPrevious)
        {
            currentFrame--;
            loadFrame();
        }
        //if(e.getSource() == pushSave)
        //{
        //    file.save(entryField.getText() + ".txt",frame.particles);
        //}
    }
    public void loadFrame()
    {
        frame.paused = true;
        f.remove(frame);
        f.dispose();
        frame.particles = file.load("frame" + currentFrame + ".txt");
        entryField.setText("frame" + currentFrame + ".txt");
        frame.dt = 0.4;
        frame.dtTemp = 0.4;
        frame.camX = 0;
        frame.camY = 0;
        frame.zoom = 2;
        frame.maxTrailLength = 256;
        f.setBackground(Color.BLACK);
        f.add(frame);
        f.setVisible(true);
        f.setSize(frame.width,frame.height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Gravity Simulation");
        frame.paused = false;
    }
}
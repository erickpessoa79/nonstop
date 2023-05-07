/*
 */
package com.pessoaoliveira.nonstop.mouse;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author erick.pessoa@hotmail.com
 */
public class NSMouse implements Runnable {
    private Thread thread;
    private String threadName;
    private Robot robot;
    private Point point;
    private Point initial;
    private JDialog dialog;
    private JLabel label;
    private int rdl = 1000;
    private boolean showImage;
    
    public NSMouse() {
        this.threadName = "thread-nonstop-mouse";
    }

    public void setShowImage(boolean show) {
        if(show) startDialog();
        this.showImage = show;
    }

    public boolean isShowImage() {
        return showImage;
    }
    
    public void start() {
        thread = new Thread(this, threadName);
        thread.start();
    }
        
    @Override
    public void run() {
        try {
            threadName += "-start";
            robot = new Robot();
            point = location();
            move(100);
            threadName = threadName.replace("-start", "-stop");
        } catch (InterruptedException | AWTException ex) {
            Logger.getLogger(NSMouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void move(int pixels) throws InterruptedException {
        if(showImage) dialog.setVisible(true);
        point = location();
//        System.out.println(new Date()+" "+point); //debug
        int fim = point.x + pixels;
        int x=0, y=point.x;
        while(y++ < fim) {
            robot.mouseMove(y, point.y);
            if(showImage) imageMove(x++, 0);
//            Color pixelColor = robot.getPixelColor(point.x, point.y);
//            System.out.println(pixelColor + " " + point.x);
        }
          Thread.sleep(rdl);
        fim -= pixels;
        while(y-- > fim) {
            robot.mouseMove(y, point.y);
            if(showImage) imageMove(x--, 0);
        }
        if(showImage) dialog.setVisible(false);
    }
    
    private void startDialog() {
        point = MouseInfo.getPointerInfo().getLocation();
        BufferedImage image;
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        image.flush();
        Graphics2D g = image.createGraphics();
        g.setColor(Color.blue);
        g.fillRect(0, 0, 100, 100);
        g.dispose();
        ImageIcon icon = new ImageIcon(image);
        label = new JLabel(icon);
        dialog = new JDialog();
        dialog.setUndecorated(true);
        
        dialog.getRootPane().setOpaque (false);
        dialog.getContentPane().setBackground (new Color (0, 0, 0, 0));
        dialog.setBackground(new Color (0, 0, 0, 0));
        
        dialog.add(label);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setSize(200, 100);
        dialog.setLocation(point.x, point.y);
        label.setHorizontalAlignment(SwingConstants.LEFT);
    }
    
    public void closeDialog() {
        dialog.removeAll();
        dialog.dispose();
    }
    
    public void imageMove(int x, int y) {
        label.setLocation(x, y);
    }
    
    public boolean stop() {
        System.out.println(location());
        System.out.println(point);
        System.out.println(point.x == location().x);
        return true;//point == location();
    }
    
    private Point location() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}

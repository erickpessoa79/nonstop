/*
 */
package com.pessoaoliveira.nonstop;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Date;
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
public class Nonstop {
    private Robot robot;
    private Point point;
    private JDialog dialog;
    private JLabel label;
    private Point initial;
    private int rdl = 1000;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int delay = 1 * 60 * 1000;
        
        Nonstop non = new Nonstop();
        try {
            non.image();
            while (true) {
                Thread.sleep(delay);
                non.move(100);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
    }
    
    public Nonstop() {
        try {
            robot = new Robot();
            point = MouseInfo.getPointerInfo().getLocation();
            initial = MouseInfo.getPointerInfo().getLocation();
            /*robot.setAutoDelay(250);
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);*/
        } catch (AWTException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void move(int mv) throws InterruptedException {
        dialog.setVisible(true);
        point = MouseInfo.getPointerInfo().getLocation();
//        System.out.println(new Date()+" "+point); //debug
        int fim = point.x + mv;
        int x=0, y=0;
        while(point.x < fim) {
            robot.mouseMove(point.x + 1, point.y);
            point = MouseInfo.getPointerInfo().getLocation();
            imageMove(point.x - initial.x, 0);
//            Color pixelColor = robot.getPixelColor(point.x, point.y);
//            System.out.println(pixelColor + " " + point.x);
        }
        Thread.sleep(rdl);
        fim = point.x - mv;
        while(point.x > fim) {
            robot.mouseMove(point.x - 1, point.y);
            point = MouseInfo.getPointerInfo().getLocation();
            imageMove(point.x - initial.x, 0);
        }
        imageRemove();
    }
    
    public void image() throws InterruptedException {
        point = MouseInfo.getPointerInfo().getLocation();
        BufferedImage image;
        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
//        Graphics2D g = image.createGraphics();
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
//        dialog.setVisible(true); //debug
    }
    
    public void imageMove(int x, int y) {
//        dialog.setLocation(x, y);
        label.setLocation(x, y);
    }
    
    public void imageRemove() {
        dialog.setVisible(false);
    }
}

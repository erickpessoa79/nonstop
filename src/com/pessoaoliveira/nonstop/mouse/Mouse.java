/*
 */
package com.pessoaoliveira.nonstop.mouse;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class Mouse implements Runnable {
    private Thread thread;
    private Robot robot;
    private JDialog dialog;
    private JLabel label;
    private final int delay = 1000;
    private boolean showImage;
    private final List<Object[]> lst;
    private int[] size;
    
    public Mouse() {
        lst = new ArrayList<>();
        setSize(new int[]{45, 35});
    }

    public final void setSize(int[] size) {
        //int[] {width, height}
        this.size = size;
    }
    
    public void setShowImage(boolean show) {
        if(show) {
            startDialog();
            addImage();
        }
        this.showImage = show;
    }

    public boolean isShowImage() {
        return showImage;
    }
    
    public void start() {
        thread = new Thread(this, "thread-nonstop-mouse");
        thread.start();
    }
        
    @Override
    public void run() {
        try {
            robot = new Robot();
            lst.add(0, new Object[]{new Date(), getLocation().x, getLocation().y});
            if(lst.size()>1) {
                if(lst.size()>2) lst.remove(2);
                
                Object[] curr = lst.get(0);
                Object[] prev = lst.get(1);
                boolean chk0 = ((Date) curr[0]).getTime()
                        == ((Date) prev[0]).getTime();
                boolean chk1 = curr[1].equals(prev[1])
                        && curr[2].equals(prev[2]);

    //            Date d0 = (Date) lst.get(0)[0];
    //            Date d1 = (Date) lst.get(1)[0];
    //            System.out.println(
    //                    (lst.get(0)[1].equals(lst.get(1)[1])
    //                    && lst.get(0)[2].equals(lst.get(1)[2]))
    //                    + " " + (d0.getTime()-d1.getTime()));
    //            lst.forEach(l -> 
    //                System.out.println(lst.size()+" "+Arrays.toString(l))
    //            );
                if(!chk0 && chk1) move(100);
            }
        } catch (AWTException ex) {
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void move(int pixels) {
//        System.out.println(new Date()+" " + getLocation()); //debug
        try {
            if(showImage) {
                dialog.setLocation(getLocation().x+20, getLocation().y+20);
                dialog.setVisible(true);
            }
            int end = getLocation().x + pixels;
            int x = 0, y = getLocation().x;
            while(y++ < end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) imageMove(x++, 0);
            }
            Thread.sleep(delay);//robot.delay(rdl);
            end -= pixels;
            while(y-- > end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) imageMove(x--, 0);
            }
            if(showImage) dialog.setVisible(false);
        } catch (InterruptedException ex) {
            closeDialog();
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void startDialog() {
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.LEFT);
        dialog = new JDialog();
        dialog.setUndecorated(true);
        
        dialog.getRootPane().setOpaque(false);
        dialog.getContentPane().setBackground(new Color (0, 0, 0, 0));
        dialog.setBackground(new Color (0, 0, 0, 0));
        
        dialog.add(label);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setSize(size[0] + 100, size[1]);
    }
    
    public void closeDialog() {
        if(dialog != null) {
            dialog.removeAll();
            dialog.dispose();
        }
    }
    
    private void addImage() {
        BufferedImage image = new BufferedImage(size[0],
                size[1], BufferedImage.TRANSLUCENT);
//        image.flush();

        Graphics2D g = image.createGraphics();
        g.setColor(new Color (0, 0, 255, 0xff/2));//(RED, GREEN, BLUE, ALPHA)
        int[][] cord = arrow();

        Polygon polygon = new Polygon(cord[0], cord[1], cord[0].length);
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        g.dispose();
        
        ImageIcon icon = new ImageIcon(image);
        icon.getImage().flush();
        
        label.setIcon(icon);
        label.revalidate();
        label.repaint();
    }
    
    private void imageMove(int x, int y) {
        label.setLocation(x, y);
    }
    
    private Point getLocation() {
        return MouseInfo.getPointerInfo().getLocation();
    }
    
    private int[][] arrow() {
        /*
            x,y cordinates type
            from top-left (0,0 cordinate) to bottom-right
            from start point to end point
        */
        int[][] aLeft = {
            {0,15},
            {20,0}, {20,10}, {40,10}, {40,20}, {20,20}, {20,30},
            {0,15}
        };
        int[][] aRight = {
            {0,10},
            {20,10}, {20,0}, {40,15}, {20,30}, {20,20}, {0,20},
            {0,10}
        };
        int[] x = new int[aLeft.length];
        int[] y = new int[aLeft.length];
        
        for(int i = 0; i < aLeft.length; i++) {
            x[i] = aLeft[i][0];
            y[i] = aLeft[i][1];
        }
//        int[] x = {0, 20,20,40,20,20, 0};
//        int[] y = {10,10, 0,15,30,20,20};
        return new int[][] {x, y};
    }
    
    private void temp() {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                addImage();
//            }
//        });
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        URL url = this.getClass().getResource("../gui/images/icon.png");
//        Image img = toolkit.createImage(url);
//        Cursor cursor = toolkit.createCustomCursor(img, new Point(1,1), "cursor");
//        dialog.setCursor(cursor);
//        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        Color pixelColor = robot.getPixelColor(point.x, point.y);
//        System.out.println(pixelColor + " " + point.x);
    }
}

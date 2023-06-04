package com.pessoaoliveira.nonstop.beans;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick.pessoa@hotmail.com
 */
public class Mouse implements Runnable {
    private Thread thread;
    private Robot robot;
    private final int delay = 1000;
    private boolean showImage;
    private final List<Object[]> lst;
    private Images images;
    
    public Mouse() {
        lst = new ArrayList<>();
        try {
            robot = new Robot();
            images = new Images();
            images.setSize(new int[]{45, 35});
        } catch (AWTException ex) {
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Images getImages() {
        return images;
    }
    
    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
        if(showImage) 
            images.startDialog();
        else
            images.closeDialog();
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
        lst.add(0, new Object[]{new Date(), getLocation()});
        if(lst.size()>1) {
            if(lst.size()>2) lst.remove(2);

            Object[] curr = lst.get(0);
            Object[] prev = lst.get(1);
            if(!curr[0].equals(prev[0])
                    && curr[1].equals(prev[1]))
                move(100);
            
            System.out.println(String.format("%s %s, %s %s",
                    curr[0], prev[0], curr[1], prev[1]));
        }
    }
    
    private void move(int pixels) {
//        System.out.println(new Date()+" " + getLocation()); //debug
        try {
            if(showImage) {
                images.setLocation(getLocation().x-40, getLocation().y-10);
                images.setVisible(true);
                images.setImage(1);
            }
            int end = getLocation().x + pixels;
            int x = 0, y = getLocation().x;
            while(y++ < end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) images.imageMove(x++, 0);
            }
            Thread.sleep(delay);//robot.delay(rdl);
            if(showImage) {
                images.setLocation(getLocation().x-100, getLocation().y-10);
                images.setImage(2);
            }
            end -= pixels;
            while(y-- > end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) images.imageMove(x--, 0);
            }
            Thread.sleep(delay);//robot.delay(rdl);
            if(showImage) images.setVisible(false);
        } catch (InterruptedException ex) {
            images.closeDialog();
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Point getLocation() {
        return MouseInfo.getPointerInfo().getLocation();
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
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("mmss");
//                File file = new File("screen"+sdf.format(new Date())+".png");
//                ImageIO.write(((BufferedImage) curr[2]), "png", file);
//            } catch (IOException ex) {
//                Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }
}

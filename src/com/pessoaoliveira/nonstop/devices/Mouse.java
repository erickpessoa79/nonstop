package com.pessoaoliveira.nonstop.devices;

import com.pessoaoliveira.nonstop.images.Images;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Images images;
    
    public Mouse() {
        lst = new ArrayList<>();
        try {
            robot = new Robot();
            setSize(new int[]{45, 35});
            images = new Images();
        } catch (AWTException ex) {
            Logger.getLogger(Mouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param size
     *      int[] {width, height}
     */
    public final void setSize(int[] size) {
        this.size = size;
    }
    
    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
        if(showImage) startDialog();
        
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
            
//            String format = String.format("%s %s, %s %s",
//                    curr[0], prev[0], curr[1], prev[1]);
//            System.out.println(format);
        }
    }
    
    private void move(int pixels) {
//        System.out.println(new Date()+" " + getLocation()); //debug
        try {
            if(showImage) {
                dialog.setLocation(getLocation().x-40, getLocation().y-10);
                dialog.setVisible(true);
                images.setImage(label, size, 1);
            }
            int end = getLocation().x + pixels;
            int x = 0, y = getLocation().x;
            while(y++ < end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) images.imageMove(label, x++, 0);
            }
            Thread.sleep(delay);//robot.delay(rdl);
            if(showImage) {
                dialog.setLocation(getLocation().x-100, getLocation().y-10);
                images.setImage(label, size, 2);
            }
            end -= pixels;
            while(y-- > end) {
                robot.mouseMove(y, getLocation().y);
                if(showImage) images.imageMove(label, x--, 0);
            }
            Thread.sleep(delay);//robot.delay(rdl);
            if(showImage) {
                dialog.setVisible(false);
            }
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
//        dialog.getContentPane().setBackground(Color.BLUE);
//        dialog.setBackground(Color.BLUE);

        dialog.add(label);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setSize(size[0] + 100, size[1]);
//        System.out.println(dialog.getInsets().top);
//        System.out.println(dialog.getInsets().bottom);
//        System.out.println(dialog.getHeight());
        
//        Cursor cursor = dialog.getCursor();
//        BufferedImage cursorImg = new BufferedImage(16, 16,
//                BufferedImage.TYPE_INT_ARGB);
//        Cursor blankCursor = Toolkit.getDefaultToolkit()
//                .createCustomCursor(cursorImg, new Point(0, 0), null);
//        dialog.setCursor(blankCursor);
////        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    public void closeDialog() {
        if(dialog != null) {
            dialog.removeAll();
            dialog.dispose();
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

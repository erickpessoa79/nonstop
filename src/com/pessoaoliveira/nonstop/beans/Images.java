package com.pessoaoliveira.nonstop.beans;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author erick
 */
public class Images {
    private JDialog dialog;
    private JLabel label;
    private int[] size;
    
    public void startDialog() {
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
    
    /**
     *
     * @param size
     *      int[] {width, height}
     */
    public final void setSize(int[] size) {
        this.size = size;
    }
    
    public void setLocation(int x, int y) {
        dialog.setLocation(x, y);
    }
    
    public void setVisible(boolean visible) {
        dialog.setVisible(visible);
    }
    
    public void closeDialog() {
        if(dialog != null) {
            dialog.removeAll();
            dialog.dispose();
        }
    }
    
    public void setImage(int mode) {
        switch(mode) {
            case 1:
                label.setIcon(arrow(size, false));
                break;
            case 2:
                label.setIcon(arrow(size, true));
                break;
        }
        label.revalidate();
        label.repaint();
    }
    
    public void imageMove(int x, int y) {
        label.setLocation(x, y);
    }
    
    private ImageIcon arrow(int[] size, boolean left) {
        /*
            x,y cordinates type
            from top-left (0,0 cordinate) to bottom-right
        */
        
        int[][] a = left ? new int[][] {
            {0,15},
            {20,0}, {20,10}, {40,10}, {40,20}, {20,20}, {20,30},
            {0,15}
        }: new int[][] {
            {0,10},
            {20,10}, {20,0}, {40,15}, {20,30}, {20,20}, {0,20},
            {0,10}
        };
        int[] x = new int[a.length], y = new int[a.length];
        
        for(int i = 0; i < a.length; i++) {
            x[i] = a[i][0];
            y[i] = a[i][1];
        }
        int[][] cord = new int[][] {x, y};
        Polygon polygon = new Polygon(cord[0], cord[1], cord[0].length);
        
        BufferedImage image = new BufferedImage(size[0],
                size[1], BufferedImage.TRANSLUCENT);
//        image.flush();
        Graphics2D g = image.createGraphics();
        g.setColor(new Color (0, 0, 255, 0xff/2));//(RED, GREEN, BLUE, ALPHA)
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        g.dispose();
        
        ImageIcon icon = new ImageIcon(image);
        icon.getImage().flush();
        return icon;
    }
}

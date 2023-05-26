package com.pessoaoliveira.nonstop.images;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author erick
 */
public class Images {
    
    public ImageIcon arrow(int[] size, boolean left) {
        /*
            x,y cordinates type
            from top-left (0,0 cordinate) to bottom-right
            from start point to end point
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
    
    public void setImage(JLabel label, int[] size, int mode) {
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
    
    public void imageMove(JLabel label, int x, int y) {
        label.setLocation(x, y);
    }
}

package com.pessoaoliveira.nonstop.beans;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class Screen {
    
    public Screen() {}
    
    public BufferedImage screenshot() {
        BufferedImage screen = null;
        try {
            GraphicsEnvironment graphicsEnv = GraphicsEnvironment
                  .getLocalGraphicsEnvironment();
            GraphicsDevice[] graphicsDevices = graphicsEnv.getScreenDevices();
            Rectangle max = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getMaximumWindowBounds();
//            System.out.println(max);
    //        Toolkit.getDefaultToolkit().getScreenSize()
            int screenWidth = 0, screenHeight = 0;
            for (GraphicsDevice screens : graphicsDevices) {
                DisplayMode mode = screens.getDisplayMode();
                screenWidth += mode.getWidth();

                if (mode.getHeight() > screenHeight) {
                    screenHeight = mode.getHeight();
                }
            }
            screen = new Robot().createScreenCapture(max);
        } catch (AWTException ex) {
            Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ImageIO.write(image, "png", new File("/screenshot.png"));
        return screen;
    }
    
    public boolean compare(BufferedImage[] screen) {
        int counter=0;
        for (int i = 0; i < screen[0].getWidth(); i++) {
            for (int j = 0; j < screen[0].getHeight(); j++) {
                if (screen[0].getRGB(i, j) != screen[1].getRGB(i, j)) {
                    counter++;
                }
            }
        }
        double p = ((double) counter)/
                ((screen[0].getWidth()*screen[0].getHeight()));
        System.out.println(String.format("%s%s", p, counter));
        return counter < 1000;
    }
}

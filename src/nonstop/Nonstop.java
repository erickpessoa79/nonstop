/*
 */
package nonstop;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author f2965513
 */
public class Nonstop {

    private Robot robot;
    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) {
        int delay = 1 * 60 * 1000;
        while (true) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
            }
            Nonstop nonBreak = new Nonstop();
        }
    }
    
    public Nonstop() {
        try {
            robot = new Robot();
            /*robot.setAutoDelay(250);
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.keyRelease(KeyEvent.VK_WINDOWS);*/
            
            Point location = MouseInfo.getPointerInfo().getLocation();
            int newX = 0;
            for (int i = 0; i < 20; i++) {
                newX = location.x + i;
                robot.mouseMove(newX, location.y);
            }
            /*location = MouseInfo.getPointerInfo().getLocation();
            for (int i = 0; i < 20; i++) {
                newX = location.x - i;
                Thread.sleep(50);
                robot.mouseMove(newX, location.y);
            }*/
        } catch (AWTException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}

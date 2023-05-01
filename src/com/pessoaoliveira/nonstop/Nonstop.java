/*
autor: erick
*/
package com.pessoaoliveira.nonstop;

import com.pessoaoliveira.nonstop.mouse.NSMouse;
import java.awt.Point;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author erick.pessoa@hotmail.com
 */
public class Nonstop {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int delay = 1 * 60 * 1000; //in minutes
        
        try {
            NSMouse ns = new NSMouse();
            ns.image();
            while (true) {
                Thread.sleep(delay);
                ns.start();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}

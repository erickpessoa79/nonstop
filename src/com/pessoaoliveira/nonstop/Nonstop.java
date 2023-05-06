/*
autor: erick
*/
package com.pessoaoliveira.nonstop;

import com.pessoaoliveira.nonstop.gui.Tray;
import com.pessoaoliveira.nonstop.mouse.NSMouse;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick.pessoa@hotmail.com
 */
public class Nonstop {
    private final Tray tray;
    private NSMouse ns;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int delay = 1 * 60 * 1000; //in minutes, default: 1
        try {
            if(args.length>0) {
                System.out.println(
                    "args: "+Arrays.toString(args)
                );
                delay = Integer.parseInt(args[0]) * 60 * 1000;
            } else {
                System.out.println(
                    "usage:\nnonstop interval"
                );
            }
            
            if(delay == 0) delay = 10000; //min: 10 
            System.out.println("\tinterval "+delay+"ms");
            
            Nonstop nonstop = new Nonstop();
            nonstop.play();

            System.out.println("started");
        } catch (NumberFormatException ex) {
            Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Nonstop() {
        tray = new Tray();
    }
    
    public void play() {
        ScheduledExecutorService executor = Executors
                .newSingleThreadScheduledExecutor();
        
        ns = new NSMouse();
//        ns.startDialog();
//        ns.setShowImage(true);
        tray.setNsmouse(ns);
        
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                ns.start();
            }
        };
        
        executor.scheduleAtFixedRate(runn, 1, 10, TimeUnit.SECONDS);
        tray.setExecutor(executor);
    }
}

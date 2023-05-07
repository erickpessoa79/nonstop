/*
*   utility to prevent the operating system from sleeping
*   https://github.com/erickpessoa79/nonstop
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
    private final String TITLE = "NONStop";
    private final String version = "v0.1.1-beta.1";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int delay = 1 * 60 * 1000; //in minutes, default: 1
        
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
        
        try {    
            if(delay == 0) delay = 10000; //min: 10 
            System.out.println("\tscheduled at "
                    + (delay<60000?(delay/1000)+"s":(delay/60000)+"m")+" rate");
            
            Nonstop nonstop = new Nonstop();
            nonstop.play();
        } catch (NumberFormatException ex) {
            Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Nonstop() {
        tray = new Tray(TITLE + " " + version);
    }
    
    public void play() {
        ScheduledExecutorService executor = Executors
                .newSingleThreadScheduledExecutor();
        
        ns = new NSMouse();
//        ns.setShowImage(true);
        tray.setNsmouse(ns);
        
        Runnable runn = () -> {
            ns.start();
        };
        
        executor.scheduleAtFixedRate(runn, 1, 10, TimeUnit.SECONDS);
        tray.setExecutor(executor);
    }
}

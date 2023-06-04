/*
*   utility to prevent the operating system from sleeping
*   https://github.com/erickpessoa79/nonstop
*/
package com.pessoaoliveira.nonstop;

import com.pessoaoliveira.nonstop.gui.Tray;
import com.pessoaoliveira.nonstop.beans.Mouse;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick.pessoa@hotmail.com
 */
public class Nonstop {
    private Mouse mouse;
    private final String TITLE = "NONStop";
    private final String version = "v0.1.1-beta.1";
    private final int period;
    private final Tray tray;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int per = 1 * 60;//in minutes, default: 1
        
        if(args.length>0) {
            System.out.println(
                "args: "+Arrays.toString(args)
            );
            per = Integer.parseInt(args[0]) * 60;
        } else {
            System.out.println(
                "usage:\n\tjava -jar nonstop.jar [period]"
            );
        }
        
        try {    
            if(per == 0) per = 10; //min: 10s 
            System.out.println("\tscheduled at "
                    + (per<60?per+"s":(per/60)+"m")+" rate");
            
            Nonstop nonstop = new Nonstop(per);
            nonstop.play();
        } catch (NumberFormatException ex) {
            Logger.getLogger(Nonstop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Nonstop(int period) {
        this.period = period;
        tray = new Tray(TITLE + " " + version);
    }
    
    public void play() {
        mouse = new Mouse();
//        mouse.setShowImage(true);
        tray.setMouse(mouse);
        
//        Runnable runn = new Runnable() {@Override public void run() {}};
        Runnable runnable = () -> {mouse.start();};
        tray.setRunnable(runnable);
        
//        ScheduledExecutorService executor = Executors
//                .newScheduledThreadPool(5);
        ScheduledExecutorService executor = Executors
                .newSingleThreadScheduledExecutor();
        ScheduledFuture<?> schedule = executor
                .scheduleAtFixedRate(runnable, 1, period, TimeUnit.SECONDS);
        
        tray.setDelay(period);
        tray.setSchedule(schedule);
        tray.setExecutor(executor);
    }
}

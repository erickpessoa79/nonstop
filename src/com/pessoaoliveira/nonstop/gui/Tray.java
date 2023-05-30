package com.pessoaoliveira.nonstop.gui;

import com.pessoaoliveira.nonstop.beans.Mouse;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Date;
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
public class Tray {
    private SystemTray tray;
    private Mouse mouse;
    private ScheduledExecutorService executor;
    private ScheduledFuture<?> schedule;
    private Runnable runnable;
    private int delay;
    
    public Tray(String tooltip) {
        if(SystemTray.isSupported()) {
            try {
                tray = SystemTray.getSystemTray();
                
                URL img = this.getClass().getResource("images/icon.png");
                Image image = Toolkit.getDefaultToolkit().createImage(img);
                TrayIcon trayIcon = new TrayIcon(image);
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip(tooltip);
                
                trayIcon.setPopupMenu(menu());
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(Tray.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void setExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
    }
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setSchedule(ScheduledFuture<?> schedule) {
        this.schedule = schedule;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public final PopupMenu menu() {
        final PopupMenu menu = new PopupMenu();
        MenuItem exit = new MenuItem("Exit");
        Menu period = new Menu("Period");
        MenuItem min = new MenuItem("minimal");
        MenuItem def = new MenuItem("default");
        MenuItem cst = new MenuItem("custom");
        
        exit.addActionListener((ActionEvent e) -> {
            executor.shutdown();
            if(mouse.isShowImage()) mouse.getImages().closeDialog();
            TrayIcon[] icons = tray.getTrayIcons();
            for(TrayIcon icon: icons)
                tray.remove(icon);
        });
        
        min.addActionListener((ActionEvent e) -> {
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);
        });
        def.addActionListener((ActionEvent e) -> {
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);
        });
        cst.addActionListener((ActionEvent e) -> {
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, delay, TimeUnit.SECONDS);
        });
        
        period.add(min);
        period.add(def);
        period.add(cst);
        menu.add(period);
        menu.addSeparator();
        menu.add(exit);
        
        return menu;
    }
}

package com.pessoaoliveira.nonstop.gui;

import com.pessoaoliveira.nonstop.beans.Mouse;
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.net.URL;
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
    private CheckboxMenuItem min, def, cst;
    
    public Tray(String tooltip) {
        if(SystemTray.isSupported()) {
            try {
                tray = SystemTray.getSystemTray();
                
                URL img = this.getClass().getResource("images/icon.png");
                Image image = Toolkit.getDefaultToolkit().createImage(img);
                TrayIcon icon = new TrayIcon(image);
                icon.setImageAutoSize(true);
                icon.setToolTip(tooltip);
                
                icon.setPopupMenu(menu());
                tray.add(icon);
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
        switch(delay) {
            case 0:
                min.setState(true);
                break;
            case 60:
                def.setState(true);
                break;
            default:
                cst.setEnabled(true);
                cst.setState(true);
        }
    }
    
    private PopupMenu menu() {
        final PopupMenu menu = new PopupMenu();
        MenuItem exit = new MenuItem("Exit");
        Menu period = new Menu("Period");
//        MenuItem min = new MenuItem("minimal");
//        MenuItem def = new MenuItem("default");
//        MenuItem cst = new MenuItem("custom");
        min = new CheckboxMenuItem("minimal");
        def = new CheckboxMenuItem("default");
        cst = new CheckboxMenuItem("custom");
        cst.setEnabled(false);
        CheckboxMenuItem animated = new CheckboxMenuItem("Animated");
        
        exit.addActionListener((ActionEvent e) -> {
            executor.shutdown();
            if(mouse.isShowImage()) mouse.getImages().closeDialog();
            TrayIcon[] icons = tray.getTrayIcons();
            for(TrayIcon icon: icons)
                tray.remove(icon);
        });
        
//        min.addActionListener((ActionEvent e) -> {});
//        def.addActionListener((ActionEvent e) -> {});
//        cst.addActionListener((ActionEvent e) -> {});
        min.addItemListener((ItemEvent e) -> {
            min.setState(true);def.setState(false);cst.setState(false);
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);
        });
        def.addItemListener((ItemEvent e) -> {
            min.setState(false);def.setState(true);cst.setState(false);
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.MINUTES);
        });
        cst.addItemListener((ItemEvent e) -> {
            min.setState(false);def.setState(false);cst.setState(true);
            schedule.cancel(true);
            schedule = executor
                    .scheduleAtFixedRate(runnable, 1, delay, TimeUnit.SECONDS);
        });
        animated.addItemListener((ItemEvent e) -> {
            if(e.getStateChange() == 1)
                mouse.setShowImage(true);
            else
                mouse.setShowImage(false);
        });
        
        period.add(min);
        period.add(def);
        period.add(cst);
        menu.add(period);
        menu.addSeparator();
        menu.add(animated);
        menu.addSeparator();
        menu.add(exit);
        
        return menu;
    }
}

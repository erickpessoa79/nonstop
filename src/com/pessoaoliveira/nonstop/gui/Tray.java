/*
 */
package com.pessoaoliveira.nonstop.gui;

import com.pessoaoliveira.nonstop.mouse.NSMouse;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class Tray {
    private SystemTray tray;
    private NSMouse nsmouse;
    private ScheduledExecutorService executor;
    
    public Tray() {
        if(SystemTray.isSupported()) {
            try {
                tray = SystemTray.getSystemTray();
                
                URL img = this.getClass().getResource("images/icon.png");
                Image image = Toolkit.getDefaultToolkit().createImage(img);
                TrayIcon trayIcon = new TrayIcon(image);
                trayIcon.setImageAutoSize(true);
                
                trayIcon.setPopupMenu(menu());
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(Tray.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setNsmouse(NSMouse nsmouse) {
        this.nsmouse = nsmouse;
    }

    public void setExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
    }
    
    public final PopupMenu menu() {
        final PopupMenu menu = new PopupMenu();
        MenuItem item = new MenuItem("Exit");
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executor.shutdown();
//                nsmouse.closeDialog();
                TrayIcon[] trayIcons = tray.getTrayIcons();
                for(TrayIcon tray_icon: trayIcons)
                    tray.remove(tray_icon);
//                System.exit(0);
            }
        });

//        menu.addSeparator();
        menu.add(item);
        
        return menu;
    }
}

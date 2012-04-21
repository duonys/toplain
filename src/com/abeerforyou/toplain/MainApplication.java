package com.abeerforyou.toplain;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.abeerforyou.toplain.util.TrayIconManager;


public class MainApplication {
    
	public void proc() {
		setLookAndFeel();
		
		// Your OS supports system tray.
		if (TrayIconManager.isSupported()) {
			final TrayIconManager trayIconManager = new TrayIconManager();
			
			// Called when users click icon on system tray.
			MouseListener listener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int id = e.getID();
					if (id == MouseEvent.MOUSE_CLICKED) {
						// light button
						if (SwingUtilities.isLeftMouseButton(e)) {
							// Gets text from your clip board.
							Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
							String text = null;
							try {
								text = (String)clipboard.getData(DataFlavor.stringFlavor);
							} catch (Exception e2) {
								// no text data
							}
							// Sets plain text to clip board.
							if (text != null) {
								StringSelection selection = new StringSelection(text);
								clipboard.setContents(selection, null);
								// Displays message.
								if (trayIconManager != null) {
									trayIconManager.displayMessage("toplain", "Succeed!", MessageType.INFO);
								}
							}
							else {
								// Displays message.
								if (trayIconManager != null) {
									trayIconManager.displayMessage("toplain", "no text data", MessageType.WARNING);
								}
							}
						}
					}
				}
			};
			
			String tooltip = "toplain";
			PopupMenu popupMenu = createPopupMenu(trayIconManager);
			
			// Fail in *.jar if you use File.separator here.
			Image image = trayIconManager.createImageFromProjectRoot("res/tray_icon.png");
			trayIconManager.addTrayIcon(image, tooltip, popupMenu, listener);
			trayIconManager.displayMessage("toplain", "Click this icon to simplify rich text in your clip board to plain one.", MessageType.NONE);
		}
		// Your OS does not supports system tray.
		else {
			JOptionPane.showMessageDialog(null, "System tray is not supported on your machine.");
		}
	}
	
	private void setLookAndFeel() {
		// Sets look and feel to OS native.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private PopupMenu createPopupMenu(final TrayIconManager trayIconManager) {
		PopupMenu popupMenu = new PopupMenu();
		
		MenuItem getTrayItem = new MenuItem("About");
		getTrayItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "toplain\n\nClick toplian icon on system tray to simplify rich text in your clip board to plain one.\n\nCopyright (C) 2012 abeerforyou.com");
			}
		});
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trayIconManager.removeTrayIcon();
				System.exit(0);
			}
		});
		popupMenu.add(getTrayItem);
		popupMenu.add(exitItem);
		
		return popupMenu;
	}
	
	
	public static void main(String[] args) {
		MainApplication app = new MainApplication();
		app.proc();
	}
}

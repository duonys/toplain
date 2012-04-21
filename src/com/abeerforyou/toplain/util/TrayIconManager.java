package com.abeerforyou.toplain.util;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;


public class TrayIconManager {
	private TrayIcon m_trayIcon;
	private SystemTray m_tray;
	private boolean m_added;
	
	
	public TrayIconManager() {
		m_tray = SystemTray.getSystemTray();
	}
	
	public static boolean isSupported() {
		return SystemTray.isSupported();
	}
	
	public void addTrayIcon(Image image, String tooltip, PopupMenu popupMenu, MouseListener listener) {
		if (!m_added) {
			// Creates TrayIcon.
			TrayIcon trayIcon = new TrayIcon(image, tooltip, popupMenu);
			trayIcon.setImageAutoSize(true);
			trayIcon.addMouseListener(listener);
			// Sets TrayIcon on SystemTray.
			try {
				m_tray.add(trayIcon);
				m_trayIcon = trayIcon;
				m_added = true;
			} catch (AWTException awte) {
				awte.printStackTrace();
			}
		}
	}
	
	public void removeTrayIcon() {
		if (m_tray != null) {
			m_tray.remove(m_trayIcon);
			m_added = false;
		}
	}
	
	public void setImage(Image image) {
		if (m_trayIcon != null && !image.equals(m_trayIcon.getImage())) {
			m_trayIcon.setImage(image);
		}
	}
	
	public void setTooltip(String tooltip) {
		if (m_trayIcon != null && !tooltip.equals(m_trayIcon.getToolTip())) {
			m_trayIcon.setToolTip(tooltip);
		}
	}
	
	public void setPopupMenu(PopupMenu popupMenu) {
		if (m_trayIcon != null && !popupMenu.equals(m_trayIcon.getPopupMenu())) {
			m_trayIcon.setPopupMenu(popupMenu);
		}
	}
	
	public void displayMessage(String caption, String text, TrayIcon.MessageType messageType) {
		if (m_trayIcon != null && (caption != null || text != null)) {
			m_trayIcon.displayMessage(caption, text, messageType);
		}
	}
	
	public Image createImageFromProjectRoot(String path) {
		Image image = null;
		
		;URL url = getClass().getClassLoader().getResource(path);
		image = new ImageIcon(url).getImage();
		
		return image;
	}
}

package com.weebly.marlookup.main;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Starter {

	private static final String updateURL = "http://pastebin.com/raw.php?i=xKFXUfy5";

	public static void main(String[] args) {
		System.out.println("Starting Up. (c) by Marenthyu");
		System.out.println("Checking for Update...");
		checkForUpdate();
		System.out.println("Update Check Complete.");
		
		System.out.println("Setting LookAndFeel...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done. Loading Up Main Window.");

	 MainWindow programm = new MainWindow();

	}
	
	public static void	checkForUpdate() {
		try {
			String newestVersion = URLConnectionReader.getText(updateURL);
			if (newestVersion.equals(MainWindow.VERSION)) {
				System.out.println("Version is up to date!");
			} else {
				System.out.println("Update found!");
				int selection = JOptionPane.showConfirmDialog(null, "Update to version "+newestVersion+" found, your Version is: "+MainWindow.VERSION+"\nDo you want to update now?","Update found!",JOptionPane.YES_NO_OPTION);
				System.out.println(selection);
				if (selection == 0) {
					openUrl("http://marlookup.weebly.com");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void openUrl(String url) throws IOException, URISyntaxException {
		  if(java.awt.Desktop.isDesktopSupported() ) {
		        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

		        if(desktop.isSupported(java.awt.Desktop.Action.BROWSE) ) {
		          java.net.URI uri = new java.net.URI(url);
		              desktop.browse(uri);
		        }
		      } 
		}

}

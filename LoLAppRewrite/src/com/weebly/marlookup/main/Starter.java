package com.weebly.marlookup.main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Starter {

	private static final String updateURL = "http://pastebin.com/raw.php?i=xKFXUfy5";

	public static void main(String[] args) {
		System.out.println("Starting Up. (c) by Marenthyu");
		System.out.println("Checking for Update...");
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	 MainWindow programm = new MainWindow();

	}
	
	public void	checkForUpdate() {
		try {
			String newestVersion = URLConnectionReader.getText(updateURL);
			if (newestVersion.equals(MainWindow.VERSION)) {
				System.out.println("Version is up to date!");
			} else {
				System.out.println("Update found!");
				int selection = JOptionPane.showConfirmDialog(null, "Update to version "+newestVersion+" found, your Version is: "+MainWindow.VERSION+"\nDo you want to update now?","Update found!",JOptionPane.YES_NO_OPTION);
				System.out.println(selection);
				if (selection == 0) {
					this.openUrl("http://marlookup.weebly.com");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

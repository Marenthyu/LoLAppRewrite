package com.weebly.marlookup.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.*;

public class MainWindow extends JFrame implements ActionListener {
	
	static String VERSION = "5.0DEV";
	String apikey;
	String ddragonVersion;
	JTextField input = new JTextField("Enter a summoner Name...");
	Container c = new Container();
	JLabel serverlabel = new JLabel("Server:"), summonericon = new JLabel();
	String[] serverlist = {"br","eune","euw","kr","lan","las","na","oce","ru","tr"};
	JComboBox serverselector = new JComboBox(serverlist);
	JButton loadplayerbutton = new JButton("Load Info");
	Player lastPlayer;
	
	public MainWindow() {
		//Basic Window Setup
		System.out.println("Basic Window Setup");
		
		setSize(400,400);
		setTitle("MarLookUp v"+VERSION);
		setVisible(true);
		setLayout(null);
		
		this.setBackground(Color.getHSBColor(26, 68, 66));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println("Basic Window Setup done.");
		
		//TextArea Setup
		System.out.println("TextArea Setup");
		input.setBounds(20, 50, 250, 25);
		input.setFont(new Font("Arial", 5, 12));
		
		System.out.println("TextArea Setup done.");
		
		//ServerLabel Setup
		System.out.println("ServerLabel Setup");
		serverlabel.setFont(new Font("Arial", 25, 20));
		serverlabel.setBounds(20, 15, 75, 25);
		
		System.out.println("ServerLabel Setup done.");
		
		//Setup ServerSelector
		System.out.println("ServerSelector Setup");
		serverselector.setBounds(90, 15, 75, 25);
		System.out.println("ServerSelector Setup done.");
		
		
		//Button Setup
		System.out.println("Button Setup");
		loadplayerbutton.setBounds(275, 50, 100, 25);
		loadplayerbutton.addActionListener(this);
		
		System.out.println("Button Setup done.");
		
		
		summonericon = new JLabel("you shouldnt see this");
		summonericon.setVisible(false);
		summonericon.setBounds(20, 400-188, 128, 128);
		
		
		//Container adds
		System.out.println("Adding Stuff to container...");
		c.add(input);
		c.add(serverlabel);
		c.add(serverselector);
		c.add(loadplayerbutton);
		c.add(summonericon);
		
		System.out.println("done adding to container.");
		
		//set content pane
		System.out.println("setting content pane");
		this.setContentPane(c);
		System.out.println("Content Pane set.");
		
		System.out.println("Setting defaul Button");
		getRootPane().setDefaultButton(loadplayerbutton);
		System.out.println("Set Default button");
		
		
		//load api key
		System.out.println("Loading Api key from pastebin...");
		updateApiKey();
		System.out.println("Updated API Key.");
		
		//Get current ddragon version
		System.out.println("Getting Current DD Version");
		ddragonVersion = this.getCurrentDDragonVersion();
		System.out.println("Got current version.");
		
		
		System.out.println("MainWindow Initialised");
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==loadplayerbutton) {
			System.out.println("Load Player: "+input.getText());
			lastPlayer = new Player(apikey, serverselector.getSelectedItem().toString(), input.getText());
			if (!lastPlayer.name.equals("ERROR"))
			loadIcon(lastPlayer);
			else JOptionPane.showMessageDialog(null, "Summoner couldn't be found. Check your spelling!");
		}
		
	}
	private void updateApiKey() {
		try {
			apikey = URLConnectionReader.getText("http://pastebin.com/raw.php?i=Ke7LVRRN");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getCurrentDDragonVersion() {
		String ret= null;
		JSONArray versionlist = null;
		try {
			versionlist = JsonReader.readJsonArrayFromUrl("https://ddragon.leagueoflegends.com/api/versions.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ret = versionlist.get(0).toString();
		
		return ret;
	}
	
	private void loadIcon(Player p) {
		try {
			System.out.println("Loading icon for player "+p.name);
			
			int id = p.getIconId();
			summonericon.setVisible(false);
			BufferedImage image = ImageIO.read(new URL("http://ddragon.leagueoflegends.com/cdn/"+ddragonVersion+"/img/profileicon/"+id+".png"));
			summonericon = new JLabel(new ImageIcon(image));
			summonericon.setBounds(20, 400-188, 128, 128);
			c.add(summonericon);
			summonericon.setVisible(true);
			System.out.println("Done loading");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

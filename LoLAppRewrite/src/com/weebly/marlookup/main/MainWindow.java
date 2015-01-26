package com.weebly.marlookup.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.*;

import com.weebly.marlookup.player.Player;

public class MainWindow extends JFrame implements ActionListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8032145162599583133L;
	static String VERSION = "5.0DEV";
	String apikey;
	String ddragonVersion;
	JTextField input = new JTextField("Enter a summoner Name...");
	Container c = new Container();
	JLabel serverlabel = new JLabel("Server:"), summonericon = new JLabel(), namelabel = new JLabel("Summoner Name: : "), idlabel = new JLabel("Summoner ID: "), levellabel = new JLabel("Level: "), revisionlabel = new JLabel("Revision Date: ");
	String[] serverlist = {"br","eune","euw","kr","lan","las","na","oce","ru","tr"};
	JComboBox<String> serverselector = new JComboBox<String>(serverlist);
	JButton loadplayerbutton = new JButton("Load Info");
	Player lastPlayer;
	Font generalfont = new Font("Arial", 25, 20);
	
	public MainWindow() {
		//Basic Window Setup
		
		System.out.println("Basic Window Setup");
		setVisible(false);
		setSize(600,400);
		setTitle("MarLookUp v"+VERSION);
		
		setLayout(null);
		
		this.setBackground(Color.getHSBColor(26, 68, 66));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		System.out.println("Basic Window Setup done.");
		
		//TextArea Setup
		System.out.println("TextArea Setup");
		input.setBounds(20, 50, 450, 25);
		input.setFont(new Font("Arial", 5, 12));
		
		System.out.println("TextArea Setup done.");
		
		//ServerLabel Setup
		System.out.println("ServerLabel Setup");
		serverlabel.setFont(generalfont);
		serverlabel.setBounds(20, 15, 75, 25);
		
		System.out.println("ServerLabel Setup done.");
		
		//Setup ServerSelector
		System.out.println("ServerSelector Setup");
		serverselector.setBounds(90, 15, 75, 25);
		System.out.println("ServerSelector Setup done.");
		
		
		//Button Setup
		System.out.println("Button Setup");
		loadplayerbutton.setBounds(475, 50, 100, 25);
		loadplayerbutton.addActionListener(this);
		
		System.out.println("Button Setup done.");
		
		System.out.println("Preparing Labels...");
		
		summonericon = new JLabel("you shouldnt see this");
		summonericon.setVisible(false);
		summonericon.setBounds(20, 400-188, 128, 128);
		
		namelabel.setFont(generalfont);
		namelabel.setBounds(20, 185, 580, 25);
		namelabel.setVisible(false);
		namelabel.addMouseListener(this);
		
		idlabel.setFont(generalfont);
		idlabel.setBounds(160, 212, 440, 25);
		idlabel.setVisible(false);
		idlabel.addMouseListener(this);
		
		levellabel.setFont(generalfont);
		levellabel.setBounds(160, 237, 440, 25);
		levellabel.setVisible(false);
		levellabel.addMouseListener(this);
		
		revisionlabel.setFont(generalfont);
		revisionlabel.setBounds(160, 262, 440, 25);
		revisionlabel.setVisible(false);
		revisionlabel.addMouseListener(this);
		
		System.out.println("Done setting Labels.");
		
		
		
		//Container adds
		System.out.println("Adding Stuff to container...");
		c.add(input);
		c.add(serverlabel);
		c.add(serverselector);
		c.add(loadplayerbutton);
		c.add(summonericon);
		c.add(namelabel);
		c.add(idlabel);
		c.add(levellabel);
		c.add(revisionlabel);
		
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
		
		setVisible(true);
		
		System.out.println("MainWindow Initialised");
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==loadplayerbutton) {
			System.out.println("Load Player: "+input.getText());
			lastPlayer = new Player(apikey, serverselector.getSelectedItem().toString(), input.getText());
			if (!lastPlayer.name.equals("ERROR"))
			loadInformation(lastPlayer);
			else JOptionPane.showMessageDialog(null, "Summoner couldn't be found. Check your spelling and the selected Server!");
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
	
	private void loadInformation(Player p) {
		
		try {
			System.out.println("Loading icon for player "+p.name);
			
			int id = p.getIconId();
			summonericon.setVisible(false);
			BufferedImage image = ImageIO.read(new URL("http://ddragon.leagueoflegends.com/cdn/"+ddragonVersion+"/img/profileicon/"+id+".png"));
			summonericon = new JLabel(new ImageIcon(image));
			summonericon.setBounds(20, 400-188, 128, 128);
			c.add(summonericon);
			summonericon.setVisible(true);
			
			System.out.println("Done loading Icon; Loading Other Info...");
			
			namelabel.setText("Summoner Name: "+p.name);
			namelabel.setVisible(true);
			idlabel.setText("Summoner ID: "+p.summonerID);
			idlabel.setVisible(true);
			levellabel.setText("Level: "+p.level);
			levellabel.setVisible(true);
			Date d = new Date(p.revisionDate);
			revisionlabel.setText("Revision Date: "+d.toString());
			revisionlabel.setVisible(true);
			
			
			System.out.println("Done loading information.");
			
			this.repaint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void copyToClipboard(String stuff) {
		
		StringSelection stringSelection = new StringSelection (stuff);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource()==revisionlabel) {
			copyToClipboard(revisionlabel.getText().replace("Revision Date: ", ""));
		}
		if (e.getSource()==namelabel) {
			copyToClipboard(namelabel.getText().replace("Summoner Name: ", ""));
		}
		if (e.getSource()==idlabel) {
			copyToClipboard(idlabel.getText().replace("Summoner ID: ", ""));
		}
		if (e.getSource()==levellabel) {
			copyToClipboard(levellabel.getText().replace("Level: ", ""));
		}
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}

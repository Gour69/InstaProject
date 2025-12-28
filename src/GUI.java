import javax.swing.*;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class GUI extends JFrame{
	private JPanel panel = new JPanel();
	private JButton followBtn = new JButton("Select following file: ");
	private JButton followerBtn = new JButton("Select followers file: ");
	private JButton compareBtn = new JButton("Find the unfollowers");
	private JFileChooser fc = new JFileChooser();
	private File followingFile;
	private File followersFile;	
	private JLabel statusLabel = new JLabel("Please select both files to continue");
	
	public GUI() {
		panel.add(followBtn);
		panel.add(followerBtn);
		panel.add(compareBtn);
		compareBtn.setVisible(false);
		this.setContentPane(panel);
		
		ButtonListener listener = new ButtonListener();
		followBtn.addActionListener(listener);
		followerBtn.addActionListener(listener);
		compareBtn.addActionListener(listener);
		
		
		this.setSize(200,200);
		this.setVisible(true);
		
	}
	
	private void processUnfollowers() {
		HashSet<String> followerSet = new HashSet<>();
		File unf = new File("unf.txt");
		
		try {
			//1. Process followers CSV
			Scanner followerScanner = new Scanner(followersFile);
			if(followerScanner.hasNextLine()) followerScanner.nextLine();
			while(followerScanner.hasNextLine()) {
				String line = followerScanner.nextLine().trim();
				if(!line.isEmpty()) {
					// Split by comma: index 0 is "id", index 1 is userName
					String[] parts = line.split(",");
					if(parts.length > 1) {
						//Remove double quotes from userName
						String user = parts[1].replace("\"", "").trim();
						followerSet.add(user);
					}
				}
			}
			
			//2. Process Following CSV and Compare
			BufferedWriter writer = new BufferedWriter(new FileWriter(unf));
			Scanner followingScanner = new Scanner(followingFile);
			if(followingScanner.hasNextLine()) followingScanner.nextLine(); // Skip header row

			while(followingScanner.hasNextLine()) {
				String line = followingScanner.nextLine().trim();
				if(!line.isEmpty()) {
					String[] parts = line.split(",");
					if(parts.length > 1) {
						String acc = parts[1].replace("\"", "").trim();
						
						if(!followerSet.contains(acc)) {
							writer.write("@" + acc + System.lineSeparator());
						}
					}
				}
			}
			followingScanner.close();
			writer.close();
			
			//Auto open result
			if(java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().open(unf);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
				
	public class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() ==  followBtn) {
				//1. open JFileChooser
				int returnVal = fc.showOpenDialog(panel);
				if(returnVal == JFileChooser.OPEN_DIALOG) {
					followingFile = fc.getSelectedFile();
				}
				//3. Check if compareBtn should be visible
				if(followersFile!=null && followingFile!=null)
					compareBtn.setVisible(true);
			} else if(e.getSource() == followerBtn) {
				int returnVal = fc.showOpenDialog(panel);
				if(returnVal == JFileChooser.OPEN_DIALOG) {
					followersFile = fc.getSelectedFile();
				}
				//3. Check if compareBtn should be visible
				if(followersFile!=null && followingFile!=null)
					compareBtn.setVisible(true);
			} else if(e.getSource() ==  compareBtn) {
				processUnfollowers();
			}
			
		}
		
	}
	

	
	
	
}

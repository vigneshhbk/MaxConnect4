import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;

//This is the class containing the main method
public class MaxConnect4 {
	//Summary - Execution starts here
	//Parameter - args - takes a string array of command line arguments
	public static void main(String[] args){
		String gameTitle = "Max-Connect 4";
		try{
			//Creates a text output file named Report
		    PrintStream writeOutput = new PrintStream(new FileOutputStream("Report.txt"));
			JFrame mainMenu = new MainMenu(writeOutput);
			mainMenu.setTitle(gameTitle);
			mainMenu.setSize(600, 600);
			mainMenu.setLayout(new GridLayout(4, 1));
			mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainMenu.setLocationRelativeTo(null);
			mainMenu.setVisible(true);
		}
		catch(Exception e){
			System.err.println("Error: "+ e.getMessage());
		}
	}
}

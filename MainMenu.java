import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.PrintStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//Class for displaying the main menu
@SuppressWarnings("serial")
public class MainMenu extends JFrame implements ActionListener{
	public static final String RED = "Red";
	public static final String GREEN = "Green";
	public static final String PLAYER = "Player";
	public static final String AI = "AI";
	private String strUserColor = RED;
	private String strFirstTurn = PLAYER;
	private PrintStream writeOutput;
	
	private JLabel jlblTitle = new JLabel("Welcome to MaxConnect4");
	private JLabel jlblUserColor = new JLabel("Choose color");
	private JLabel jlblFirstTurn = new JLabel("First Turn");
	private JRadioButton jrbtnRed = new JRadioButton(RED, true);
	private JRadioButton jrbtnGreen = new JRadioButton(GREEN);
	private JRadioButton jrbtnPlayer = new JRadioButton(PLAYER, true);
	private JRadioButton jrbtnAI = new JRadioButton(AI);
	private ButtonGroup btnColor = new ButtonGroup();
	private ButtonGroup btnTurn = new ButtonGroup();
	private JButton btnStart = new JButton("Start");
	private JPanel headerPanel = new JPanel();
	private JPanel userColorPanel = new JPanel();
	private JPanel userTurnPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	//Summary - Parameterized constructor for MainMenu class
	//Parameter - writeOutput - handler for the output file
	public MainMenu(PrintStream writeOutput){
		jrbtnRed.setActionCommand(RED);
		jrbtnGreen.setActionCommand(GREEN);
		
		btnColor.add(jrbtnRed);
		btnColor.add(jrbtnGreen);
		
		jrbtnPlayer.setActionCommand(PLAYER);
		jrbtnAI.setActionCommand(AI);

		btnTurn.add(jrbtnPlayer);
		btnTurn.add(jrbtnAI);
		
		btnStart.setSize(50, 20);
		btnStart.addActionListener(this);
		
		userColorPanel.setLayout(new FlowLayout());
		userTurnPanel.setLayout(new FlowLayout());
		
		headerPanel.add(jlblTitle);
		userColorPanel.add(jlblUserColor);
		userColorPanel.add(jrbtnRed);
		userColorPanel.add(jrbtnGreen);
		userTurnPanel.add(jlblFirstTurn);
		userTurnPanel.add(jrbtnPlayer);
		userTurnPanel.add(jrbtnAI);
		buttonPanel.add(btnStart);
		add(headerPanel);
		add(userColorPanel);
		add(userTurnPanel);
		add(buttonPanel);
		
		jrbtnRed.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					strUserColor = RED;
				}
			}
		});
		
		jrbtnGreen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					strUserColor = GREEN;
				}
			}
		});
		
		jrbtnPlayer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					strFirstTurn = PLAYER;
				}
			}
		});
		
		jrbtnAI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == 1){
					strFirstTurn = AI;
				}
			}
		});
		
		this.writeOutput = writeOutput;
	     
	}
	
	//Summary - Method to handle to button click event of start button
	//Parameter - e - holds the action event command arguments
	@Override
	public void actionPerformed(ActionEvent e) {
		Start();
	}
	
	//Summary - Method to start the game based on user options
	public void Start(){
		JFrame maxConnect4 = new GameBoard(strUserColor, strFirstTurn, writeOutput);
		maxConnect4.setTitle("Max Connect 4");
		maxConnect4.setSize(600, 600);
		maxConnect4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		maxConnect4.setLocationRelativeTo(null);
		maxConnect4.setVisible(true);
	}
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//Class for displaying the game board
@SuppressWarnings("serial")
public class GameBoard extends JFrame implements ActionListener {

	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	
	private String userColor = "Red";
	private String aiColor = "Green";
	private String turn = "Player";
	private JButton[] buttons = new JButton[COLUMNS];
	private Cell[][] cells = new Cell[ROWS][COLUMNS];
	private int pieceCount;
	JLabel jlblTurn = new JLabel();
    private final int[] firstAvailableRow;
    JLabel jlblScore = new JLabel();
    private PrintStream writeOutput;
	
    //Summary - Parameterized Constructor for GameBoard class
    //Parameter - userColor - contains information on the color of user's piece
    //Parameter - firstTurn - contains information on who is going to play first
    //Parameter - writeOutput - handler for the output text file
	public GameBoard(String userColor, String firstTurn, PrintStream writeOutput){
		this.writeOutput = writeOutput;
        firstAvailableRow = new int[COLUMNS];
		this.pieceCount = 0;
		//initialize and add all cells to the panel
		JPanel panel = new JPanel(new GridLayout(ROWS, COLUMNS, 0, 0));
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMNS; j++){
				cells[i][j] = new Cell();
				panel.add(cells[i][j]);
			}
		}
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, COLUMNS, 0, 0));
		for(int i = 0; i< COLUMNS; i++){
			buttons[i] = new JButton(String.valueOf(i+1));
			buttons[i].setActionCommand(String.valueOf(i));
			buttons[i].addActionListener(this);
			buttonPanel.add(buttons[i]);
			firstAvailableRow[i] = ROWS - 1;
		}
		
		this.userColor = userColor;
		this.aiColor = userColor == MainMenu.RED ? MainMenu.GREEN : MainMenu.RED;
		this.turn = firstTurn;
		
		jlblTurn.setText(firstTurn + "'s turn to play");
		jlblScore.setText("Green 0 - Red 0");
		
		buttonPanel.setBorder(new LineBorder(Color.yellow, 1));
		panel.setBorder(new LineBorder(Color.red, 1));
		jlblTurn.setBorder(new LineBorder(Color.yellow, 1));
		jlblScore.setBorder(new LineBorder(Color.yellow, 1));
		
		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		labelPanel.add(jlblTurn);
		labelPanel.add(jlblScore);
		
		//add all panels to the frame
		add(buttonPanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		add(labelPanel, BorderLayout.SOUTH);
		
		if(this.turn == MainMenu.AI){
			MakeAIPlay();
		}
	}
	
	//Summary - Method to check the availability of a particular column
	//Parameter - column - specifies the column whose availability is to be checked
	//Returns true if column is available, false otherwise
	public boolean isColumnAvailable(int column) {
        return firstAvailableRow[column] != -1;
    }

	//Summary - Method to get the first available row in a particular column
	//Parameter - column - specifies the column whose first available row is to be returned
	//Returns an integer representing the first available row in a given column
    public int getFirstAvailableRow(int column) {
        return firstAvailableRow[column];
    }
	
    //Summary - Method to get the cell grid
    //Returns an array of Cell objects
	public Cell[][] getGameBoard() 
	{
		return this.cells;
	}
	
	//Summary - Method to get the piece count
	//Returns an integer representing the total number of pieces placed in the game board
	public int getPieceCount() 
	{
		return this.pieceCount;
	}
	
	//Summary - Method to calculate the score of a particular colored piece
	//Parameter - color - specifies the color of the piece whose score is to be computed
	//Returns an integer representing the score of a particular colored piece
	public int Score(String color) {
		int playerScore = 0;
		//check horizontally
		for( int i = 0; i < ROWS; i++ ){
			for( int j = 0; j < 4; j++ ){
				if( ( this.cells[i][j].getToken() == color ) &&
						( this.cells[ i ][ j+1 ].getToken() == color ) &&
						( this.cells[ i ][ j+2 ].getToken() == color ) &&
						( this.cells[ i ][ j+3 ].getToken() == color ) ){
					playerScore++;
				}
			}
		}

		//check vertically
		for( int i = 0; i < 3; i++ ) {
			for( int j = 0; j < COLUMNS; j++ ) {
				if( ( this.cells[ i ][ j ].getToken() == color ) &&
						( this.cells[ i+1 ][ j ].getToken() == color ) &&
						( this.cells[ i+2 ][ j ].getToken() == color ) &&
						( this.cells[ i+3 ][ j ].getToken() == color ) ) {
					playerScore++;
				}
			}
		}

		//check diagonally - backs lash ->	\
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 4; j++ ) {
				if( ( this.cells[ i ][ j ].getToken() == color ) &&
						( this.cells[ i+1 ][ j+1 ].getToken() == color ) &&
						( this.cells[ i+2 ][ j+2 ].getToken() == color ) &&
						( this.cells[ i+3 ][ j+3 ].getToken() == color ) ) {
					playerScore++;
				}
			}
		}

		//check diagonally - forward slash -> /
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 4; j++ ) {
				if( ( this.cells[ i+3 ][ j ].getToken() == color ) &&
						( this.cells[ i+2 ][ j+1 ].getToken() == color ) &&
						( this.cells[ i+1 ][ j+2 ].getToken() == color ) &&
						( this.cells[ i ][ j+3 ].getToken() == color ) ) {
					playerScore++;
				}
			}
		}

		return playerScore;
	}
	
	//Summary - Method to check if a move is valid in a column
	//Parameter - column - specifies the column in which the validity of a move is to be checked
	//Returns true if the move is valid, false otherwise
	public boolean isValidPlay( int column ) {
		if ( !( column >= 0 && column <= COLUMNS ) ) {
			return false;
		} else if( this.cells[0][ column ].getToken() != null ) {
			return false;
		} else {
			return true;
		}
	}
	
	//Summary - Method to place a piece in a column
	//Parameter - column - specifies the column in which the piece is to be placed
	//Returns true if the piece is successfully placed, false otherwise
	public boolean playPiece( int column ) {
		if( !this.isValidPlay( column ) ) {
			return false;
		}
		else {
			for( int i = 5; i >= 0; i-- ) {
				if( this.cells[i][column].getToken() == null ) {
					if(this.getTurn() == MainMenu.PLAYER){
						this.cells[i][column].setToken(userColor);
						++this.pieceCount;
					}
					else{
						this.cells[i][column].setToken(aiColor);
						writeOutput.println("AI placed a " + aiColor + 
								" piece at row " + (i+1) + ", column " + (column+1));
				        --firstAvailableRow[column];
						++this.pieceCount;
					}

					return true;
				}
			}

			return false;
		}
	}
	
	//Summary - Method to place a piece in a column temporarily
	//Parameter - column - specifies the column in which the piece is to be placed
	//Parameter - mark - specifies the color of the piece that is to be placed
	//Returns an integer representing the row in which the piece is placed
    public int mark(int column, String mark) throws IllegalArgumentException {
        int row = firstAvailableRow[column];
        if (row < 0) {
            throw new IllegalArgumentException(
                "Column " + (column + 1) + " is already full.");
        }
        cells[row][column].setToken(mark);
        --firstAvailableRow[column];
        ++this.pieceCount;
        return row;
    }
	
    //Summary - Method to remove the top-most piece from a column
    //Parameter - column - specifies the column from which the piece is to be removed
    public void removePiece(int column) throws IllegalArgumentException {
        int row = firstAvailableRow[column];
        if (row >= 6) {
            throw new IllegalArgumentException(
                "Column " + (column + 1) + " is already empty.");
        }
        row = ++firstAvailableRow[column];
        --this.pieceCount;
        this.cells[row][column].setToken(null);
    }
	
    //Summary - Method to check if the grid is full
    //Returns true if grid is full, false otherwise
	public boolean isFull(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMNS; j++){
				if(this.cells[i][j].getToken() == null){
					return false;
				}
			}
		}
		
		return true;
	}
	
	//Summary - Getter for turn
	//Returns turn
	public String getTurn() {
		return this.turn;
	}
	
	//Summary - Getter for user color
	//Returns user color
	public String getUserColor() {
		return this.userColor;
	}
	
	//Summary - Setter for turn
	//Parameter - turn - specifies the value of turn
	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	//Summary - Setter for user color
	//Parameter - userColor - specifies the user color
	public void setUserColor(String userColor) {
		this.userColor = userColor;
	}

	//Summary - Method to handle all the button click event
	//Parameter - e - holds the action event command arguments
	@Override
	public void actionPerformed(ActionEvent e) {
		Play(e);
	}
	
	//Summary - Method to perform a move
	//Parameter - e -holds the action event command arguments
	public void Play(ActionEvent e){
		int column = Integer.parseInt(e.getActionCommand());
		int greenScore = 0, redScore = 0;
		int availableRow = -1;
		if(this.turn != null){
			for(int i = 5; i >= 0; i--){
				if(this.cells[i][column].getToken() == null){
					this.cells[i][column].setToken(userColor);
					writeOutput.println("Player placed a " + userColor + 
							" piece at row " + (i+1) + ", column " + (column+1));
			        --firstAvailableRow[column];
			        ++this.pieceCount;
					availableRow = i;
					break;
				}
			}
		}
		
		if(isFull()){
			jlblTurn.setText("Game over");
			greenScore = Score(MainMenu.GREEN);
			redScore = Score(MainMenu.RED);
			Report(greenScore, redScore);
			this.turn = null;
		}
		else if(availableRow == -1){
			jlblTurn.setText("Invalid move! Please choose a different column");
		}
		else{
			turn = turn == MainMenu.PLAYER ? MainMenu.AI : MainMenu.PLAYER;
			jlblTurn.setText(this.turn + "'s turn");
			if(this.turn == MainMenu.AI){
				MakeAIPlay();
			}
		}
	}
	
	//Summary - Method to perform an AI move
	public void MakeAIPlay(){
		int greenScore =0, redScore = 0;
		if (getPieceCount() < 42) {
			AI aiPlayer = new AI(this);
			int col = aiPlayer.findBestPlay(this);
			playPiece(col);
			this.turn = MainMenu.PLAYER;
			jlblTurn.setText(this.turn + "'s turn");
			greenScore = Score(MainMenu.GREEN);
			redScore = Score(MainMenu.RED);
			jlblScore.setText("Green " + greenScore + " - Red " + redScore);
			repaint();
		}
		if(isFull()){
			jlblTurn.setText("Game over");
			Report(greenScore, redScore);
			this.turn = null;
		}
	}
	
	//Summary - Method to declare the winner and write output to the output file
	//Parameter - greenScore - holds the score of the green piece player
	//Parameter - redScore - holds the score of the red piece player
	public void Report(int greenScore, int redScore){
		String result;
		if(greenScore == redScore){
			result = "Game over - Match Tied(" + redScore + "-" + greenScore + ")";
		}
		else if(greenScore < redScore){
			result = "Game over - Red Wins(" + redScore + "-" + greenScore + ")";
		}
		else{
			result = "Game over - Green Wins(" + greenScore + "-" + redScore + ")";
		}
		
		writeOutput.println(result);
		jlblTurn.setText(result);
		writeOutput.close();
	}
}

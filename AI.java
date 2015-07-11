//Class for the AI player
public class AI {
    private final GameBoard gameBoard;
    private int depth;
    private int thresholdPieceCount;
	
    //Summary - Parameterized constructor for AI class
    //Parameter - gameBoard - holds the GameBoard object
	public AI(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		this.depth = 5;
		this.thresholdPieceCount = 37;
	}
	
	//Summary - Method to find the best possible move - implements minmax algorithm with alpha beta pruning
	//Parameter - gameBoard - holds the GameBoard object
	//Returns an integer representing the column which is the best possible move
	public int findBestPlay(GameBoard gameBoard){
		int playChoice = 0;
		if (gameBoard.getUserColor() == MainMenu.RED) {
			int v = Integer.MAX_VALUE;
			for (int i = 0; i < GameBoard.COLUMNS; i ++) {
				if (gameBoard.isValidPlay(i)) {
					gameBoard.mark(i, MainMenu.GREEN);
					int value = Max(gameBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
					gameBoard.removePiece(i);
					if (v > value || gameBoard.getPieceCount() > thresholdPieceCount) {
						playChoice = i;
						v = value;
					}
				}
			}
		} else {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < GameBoard.COLUMNS; i++) {
				if (gameBoard.isValidPlay(i)) {
					gameBoard.mark(i, MainMenu.RED);
					int value = Min(gameBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
					gameBoard.removePiece(i);
					if (v < value || gameBoard.getPieceCount() > thresholdPieceCount) {
						playChoice = i;
						v = value;
					}
				}
			}
		}
		return playChoice;
	}
	
	//Summary - Method for calculating best possible score for the maximizing player
	//Parameter - currentGame - contains the GameBoard object
	//Parameter - depthLevel - specifies the depth level of the minmax algorithm
	//Parameter - alpha - specifies the alpha value for pruning the minmax algorithm
	//Parameter - beta - specifies the beta value for pruning the minmax algorithm
	//Returns an integer corresponding to the best possible score for the maximizing player
	public int Max(GameBoard currentGame, int depthLevel, int alpha, int beta) {
		if (currentGame.getPieceCount() < 42 && depthLevel > 0) {
			int v = Integer.MIN_VALUE;
			for (int i = 0; i < GameBoard.COLUMNS; i ++) {
				if (currentGame.isValidPlay(i)) {
					gameBoard.mark(i, MainMenu.RED);
					int value = Min(gameBoard, depthLevel - 1, alpha, beta);
					gameBoard.removePiece(i);
					if (v < value) {
						v = value;
					}
					if (v >= beta) {
						return v;
					}
					if (alpha < v) {
						alpha = v;
					}
				}
			}
			return v;
		} else {
			return currentGame.Score(MainMenu.RED) - currentGame.Score(MainMenu.GREEN);
		}
	}

	//Summary - Method for calculating best possible score for the minimizing player
	//Parameter - currentGame - contains the GameBoard object
	//Parameter - depthLevel - specifies the depth level of the minmax algorithm
	//Parameter - alpha - specifies the alpha value for pruning the minmax algorithm
	//Parameter - beta - specifies the beta value for pruning the minmax algorithm
	//Returns an integer corresponding to the best possible score for the minimizing player
	public int Min(GameBoard currentGame, int depthLevel, int alpha, int beta) {
		if (currentGame.getPieceCount() < 42 && depthLevel > 0) {
			int v = Integer.MAX_VALUE;
			for (int i = 0; i < GameBoard.COLUMNS; i ++) {
				if (currentGame.isValidPlay(i)) {
					gameBoard.mark(i, MainMenu.GREEN);
					int value = Max(gameBoard, depthLevel - 1, alpha, beta);
					gameBoard.removePiece(i);
					if (v > value) {
						v = value;  			
					}
					if (v <= alpha) {
						return v;
					}
					if (beta > v) {
						beta = v;
					}
				}
			}
			return v;
		} else {
			return currentGame.Score(MainMenu.RED) + - currentGame.Score(MainMenu.GREEN);
		}
	}
}

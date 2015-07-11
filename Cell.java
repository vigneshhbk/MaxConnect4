import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//Class representing the cell object
@SuppressWarnings("serial")
public class Cell extends JPanel {
		private String token = null;

		//Summary - default constructor for Cell class
		public Cell(){
			setBorder(new LineBorder(Color.black, 1));
			this.setToken(null);
		}
		
		//Summary - Getter for token
		//Returns token
		public String getToken(){
			return token;
		}
		
		//Summary - Setter for token
		//Parameter - strToken - contains the value for token
		public void setToken(String strToken){
			token = strToken;
			Display();
		}
		
		//Summary - Method to display the updated game board
		public void Display(){
			repaint();
		}
		
		//Summary - Method to paint the updated game board
		//Parameter - g - contains the graphic class object
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(token == MainMenu.RED){
				g.setColor(Color.red);
				g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
			}
			else if(token == MainMenu.GREEN){
				g.setColor(Color.green);
				g.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
			}
		}
	}

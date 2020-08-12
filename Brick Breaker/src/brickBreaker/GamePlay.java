package brickBreaker;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener , KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean play = false; //game should not start by itself
	private int score = 0;
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1 ;
	private int ballYdir = -2;
	private MapGenerator map;
	
	
	public GamePlay() {
		
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
		
	}
	
	
	public void paint (Graphics g) {
		
		//background
		
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		
		g.setColor(Color.YELLOW);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(692,0,3,592);

		
		//score
		
		g.setColor(Color.white);
		g.setFont(new Font ("serif", Font.BOLD,25));
	    g.drawString(""+score, 590, 30);	
	
	    //paddle
	    g.setColor(Color.green);
	    g.fillRect(playerX, 550, 100, 8);
	
	    //ball
	    g.setColor(Color.yellow);
	    g.fillOval(ballposX, ballposY, 20, 20);
	    
	    //to know game is over i.e. totalbrick is 0
	    if(totalBricks <=0 )
	    {
	    	play = false;
	    	ballXdir = 0;
	    	ballYdir = 0;
	    	g.setColor(Color.red);
			g.setFont(new Font ("serif", Font.BOLD,30));
		    g.drawString("YOU WON :", 260, 300);	
			g.setFont(new Font ("serif", Font.BOLD,20));
            g.drawString("PRESS ENTER TO RESTART", 230, 350);	

         }
	    
	    //game over
	    
	    if(ballposY > 570 )
	    {
	    	play = false;
	    	ballXdir = 0;
	    	ballYdir = 0;
	    	g.setColor(Color.red);
			g.setFont(new Font ("serif", Font.BOLD,30));
		    g.drawString("GAME OVER. SCORE:", 190, 300);	
			g.setFont(new Font ("serif", Font.BOLD,20));
            g.drawString("PRESS ENTER TO RESTART", 230, 350);	

         }
	    

	    g.dispose();
	
	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		if(play) {
			
			//to detect the intersection of the ball
			if( new Rectangle (ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550, 100,8))) {
				ballYdir = -ballYdir;
			}
			
			//need to iterate  through each and every brick
			
	A: for(int i=0; i<map.map.length;i++) {
		
		for(int j=0; j<map.map[0].length;j++) {
			
			if(map.map[i][j]>0) {
				int brickX = j*map.brickWidth+80;
				int brickY = i*map.brickHeight+50;
				int brickWidth = map.brickWidth;
				int brickHeight = map.brickHeight;
				
				//Creating rectangle around that brick
				Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
				Rectangle ballRect = new Rectangle (ballposX, ballposY,20,20);
				Rectangle brickRect = rect;
				
			if(ballRect.intersects(brickRect)) {
				map.setBrickValue(0,i,j);
				totalBricks--;
				score += 5;
				
				if(ballposX +19<=brickRect.x || ballposX+1>=brickRect.x + brickRect.width) {
					ballXdir = -ballXdir;
				}
				else {
					ballYdir = -ballYdir;
				}
				
				break A;
				
				
			}
		}
			
	}
}
			
			
			
		ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX<0) {
				ballXdir = -ballXdir;
				}
			
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			} 
			
		}
		
		
		/*ballposX += ballXdir;
		ballposY += ballYdir;
		
		if(ballposX<0) {
			ballXdir = -ballXdir;
			}
		
		if(ballposY < 0) {
			ballYdir = -ballYdir;
		}
		
		if(ballposX > 70) {
			ballXdir = -ballXdir;
		}*/
		
		
		repaint() ;
		
		
		
		
	}
	
	

	
	
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			
			if(playerX >= 600) {
				
				playerX=600;
			}
			else {
				moveRight();
				
			}
			
			
		}
		
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			
			if(playerX<10) {
				playerX=10;
			}
			else {
				moveLeft();
			}
		}
			
			
			
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir= -1;
				ballYdir= -2;
				playerX= 310;
				score=0;
				totalBricks= 21;
				
				map = new MapGenerator(3,7);
				repaint();
				
			}
			
			
		}
			
			
	}
	
	public void moveRight() {
		//if right is pressed then it should move 20 px to right side
		
		play=true;
		playerX +=20;
	}
	
	public void moveLeft() {
		
		play = true;
		playerX -=20;
		
	}
	
	
	
	
	
}




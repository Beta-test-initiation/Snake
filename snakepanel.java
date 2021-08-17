package snake;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class snakepanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyparts = 6;
	int appleseaten = 0;
	int applex;
	int appley;
	char direction = 'r';
	boolean running = false;
	Timer timer;
	Random random;
	
	snakepanel(){
		
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
		
	}
	
	public void startGame() {
		
		addapple();
		running= true;
		timer = new Timer(DELAY,this);
		timer.start();
		

	}
	
	public void paintComponent(Graphics g ) {
		
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g ) {
		if(running) {
			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);

			}
			g.setColor(new Color(0x11FFEE));
			g.fillOval(applex,  appley, UNIT_SIZE, UNIT_SIZE);

			//draw snake

			for (int i=0; i<bodyparts;i++) {
				if (i==0) {
					g.setColor(new Color(0xCC6B5C));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(0xFF6F59));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			//To display the score of the game
			g.setColor(Color.GREEN);
			g.setFont(new Font("Ink Free", Font.BOLD, 30));
			//creating intance of font metrics to align the text at the very center of the screen
			
			FontMetrics metric = getFontMetrics(g.getFont());
			g.drawString("Score: "+appleseaten, (SCREEN_WIDTH - metric.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize());
		}
		else {
			gameover(g);
		}
		
	}
	
	public void addapple() {
		
		//create coordinates for a random apple
		applex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		for ( int i =bodyparts; i >0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch (direction) {
		
		case 'u' :
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'd' :
			y[0] = y[0] + UNIT_SIZE;
			break;	
		case 'l' :
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'r' :
			x[0] = x[0] + UNIT_SIZE;
			
			
		
		}
		
		
	}
	
	public void checkapple() {
		
		if ((x[0]== applex) && (y[0] == appley)) {
			bodyparts++;
			appleseaten++;
			addapple();
		}
		
	}
	
	public void check_collision() {
		//checks if head collides with body
		for ( int i = bodyparts; i>0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// if head touches left border
		if (x[0]<0) {
			running = false;
		}
		// if head touches right border
		if (x[0]>SCREEN_WIDTH) {
			running = false;
		}
		
		// if head touches top border
		if (y[0]<0) {
			running = false;
		}
		// if head touches left border
		if (y[0]>SCREEN_HEIGHT) {
			running = false;
		}		
		
		if (!running) {
			timer.stop();
			
		}
	}
	
	public void gameover(Graphics g) {
		//Score
		//To display the score of the game
		g.setColor(Color.GREEN);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		//creating intance of font metrics to align the text at the very center of the screen
		
		FontMetrics metric2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+appleseaten, (SCREEN_WIDTH - metric2.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize()+30);
		
		
		//Game over text
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		//creating intance of font metrics to align the text at the very center of the screen
		
		FontMetrics metric = getFontMetrics(g.getFont());
		g.drawString("Game Over :( ", (SCREEN_WIDTH - metric.stringWidth("Game Over :("))/2, SCREEN_HEIGHT/2);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkapple();
			check_collision();
			
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT : 
				if (direction!='r') {
					direction = 'l';
				break;
				}
			case KeyEvent.VK_RIGHT: 
				if (direction!='l') {
					direction = 'r';
				break;	
				}
			case KeyEvent.VK_UP: 
				if (direction!='d') {
					direction = 'u';
				break;	
				}	
			case KeyEvent.VK_DOWN: 
				if (direction!='u') {
					direction = 'd';
				break;	
				}
				
			}
		}
	}
}



			




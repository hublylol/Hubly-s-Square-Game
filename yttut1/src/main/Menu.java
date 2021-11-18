package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import enemies.BasicEnemy;
import enemies.HardEnemy;
import main.Game.STATE;
import otherObjects.GameObject;
import otherObjects.Handler;
import otherObjects.ID;
import otherObjects.MenuParticle;
import otherObjects.Player;

public class Menu extends MouseAdapter{
	
	Handler handler;
	private HUD hud;
	private Color col;
	private Random r = new Random();
	
	public static boolean playerChroma = false;
	public static float colourSpeed = 1;
	private int colourSlider = 300;
	
	public static int volume = 50;
	public static int volumeX = 400;
	public static int volumeY = 101;
	
	public static float trailTime = 50;
	public static int trailX = 400;
	
	private int red = 255;
	private int green = 0;
	private int blue = 0;
	
	private int savedRed, savedGreen, savedBlue;
	
	private boolean[] rectangle = new boolean [5];	
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.hud = hud;
		this.handler = handler;
		
		rectangle[0] = false;
		rectangle[1] = false;
		rectangle[2] = false;
		rectangle[3] = false;
		rectangle[4] = false;

	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Menu) {
			// play button
			if (mouseOver(mx, my, Game.WIDTH/2 - 220,160, 200, 64)){
				Game.gameState = STATE.Select;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
			
			// help button
			if (mouseOver(mx, my, Game.WIDTH/2 - 220, 275, 200, 64)) {
				Game.gameState = STATE.Help;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
			
			// settings button
			if (mouseOver(mx, my, Game.WIDTH/2 + 10,160, 200, 64)) {
				Game.gameState = STATE.Settings;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
			
			// quit button
			if (mouseOver(mx, my, Game.WIDTH/2-100,400, 200, 64)) {
				System.exit(1);
			}
		}
		
		if(Game.gameState == STATE.Select) {
			// normal button
			if (mouseOver(mx, my, Game.WIDTH/2-100,150, 200, 64)){
				hud.setLevel(1);
				hud.setScore(0);
				hud.setTotalScore(0);
				HUD.redHealth = 0;
				HUD.redOverHealth = 0;
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				Game.diff = 0;
				
			}
			
			// hard button
			if (mouseOver(mx, my, Game.WIDTH/2-100,230, 200, 64)) {
				hud.setLevel(1);
				hud.setScore(0);
				hud.setTotalScore(0);
				Game.gameState = STATE.Game;
				Handler.spd += 2;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				Game.diff = 1;
				
			}
			
			//dev mode
			if (mouseOver(mx, my, Game.WIDTH/2-100,310, 200, 64)) {
				hud.setLevel(1);
				hud.setScore(0);
				hud.setTotalScore(0);
				Game.gameState = STATE.Game;
				Handler.spd += 5;
				handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				Game.diff = 2;
			}
			
			// back button
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				Game.gameState = STATE.Menu;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
			
		}
		
		// back button for help
		if (Game.gameState == STATE.Help) {
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				Game.gameState = STATE.Menu;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
		}
		
		if (Game.gameState == STATE.Settings) {
			volumeX = (int) Game.clamp(volumeX, 300, 500);
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				Game.gameState = STATE.Menu;
				for(int i = 0; i < 5; i++) rectangle[i] = false;
				return;
			}
			if(mouseOver(mx,my,172, 254, 20, 20)) {
				if(playerChroma) {
					playerChroma = false;
					Game.red = savedRed;
					Game.green = savedGreen;
					Game.blue = savedBlue;
				}else {
					playerChroma = true;
					savedRed = Game.red;
					savedGreen = Game.green;
					savedBlue = Game.blue;
					Game.red = 255;
					Game.green = 0;
					Game.blue = 0;
				}
			}
			if(mouseOver(mx,my,622, 227, 20, 20) && playerChroma == false) {
				Game.red = r.nextInt(256);
				Game.green = r.nextInt(256);
				Game.blue = r.nextInt(256);
			}
			
		}
		
		if (Game.gameState == STATE.End) {
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				if(Game.diff != 2) {
					FileHandler.makeFile(null);
					FileHandler.readFile(null, false);
				}
				Game.gameState = STATE.Select;
				hud.setLevel(1);
				hud.setScore(0);
				hud.setTotalScore(0);
				for(int i = 0; i < 5; i++) rectangle[i] = false;
			}
		}
		if (Game.gameState == STATE.Finished) {
			if (mouseOver(mx,my,Game.WIDTH/2-100,395, 200, 64)) {
				
				Game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				hud.setTotalScore(0);
				HUD.bounds = 0;
				HUD.overBounds = 0;
				handler.clearEnemies();
				for(int i = 0; i < handler.getObject().size(); i++) {
					GameObject tempObject = handler.getObject().get(i);
					if(tempObject.getId() == ID.Player) {
						handler.removeObject(tempObject);
					}
				}
				for(int i = 0; i < 30; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
				}
				for(int i = 0; i < 5; i++) rectangle[i] = false;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, volumeX - 7, volumeY, 14, 20)) {
			volumeX = mx - 3;
			volumeX = (int) Game.clamp(volumeX, 300, 500);
			volume = (volumeX - 300) / 2;
			
			AudioPlayer.getMusic("music").stop();
			AudioPlayer.getMusic("music").loop(1,(float) volume/200);
		}
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, trailX - 7, 160, 14, 20)) {
			trailX = mx - 3;
			trailX = (int) Game.clamp(trailX, 300, 500);
			trailTime = (trailX - 300) / 2;
		}
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, (int) colourSlider - 7, 320, 14, 20) && playerChroma) {
			colourSlider = mx - 2;
			colourSlider = (int) Game.clamp(colourSlider, 300, 500);
			colourSpeed = ((colourSlider - 260)/10) - 3;
		}
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, (int) Game.red + 333, 267, 14, 20) && playerChroma == false) {
			Game.red = mx - 340;
		}
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, (int) Game.green + 333, 307, 14, 20) && playerChroma == false) {
			Game.green = mx - 340;
		}
		if(Game.gameState == STATE.Settings && mouseOver(mx, my, (int) Game.blue + 333, 347, 14, 20) && playerChroma == false) {
			Game.blue = mx - 340;
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if (Game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, Game.WIDTH/2 - 220,160, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
			if(mouseOver(mx, my, Game.WIDTH/2 - 220, 275, 200, 64)) {
				rectangle[1] = true;
			}else rectangle[1] = false;
			if(mouseOver(mx, my, Game.WIDTH/2 + 10,160, 200, 64)) {
				rectangle[2] = true;
			}else rectangle[2] = false;
			if(mouseOver(mx, my, Game.WIDTH/2 + 10, 275, 200, 64)) {
				rectangle[3] = true;
			}else rectangle[3] = false;
			if(mouseOver(mx, my, Game.WIDTH/2-100,400, 200, 64)) {
				rectangle[4] = true;
			}else rectangle[4] = false;
		}
		if(Game.gameState == STATE.Help) {
			if(mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
		}
		if(Game.gameState == STATE.Select) {
			// normal button
			if (mouseOver(mx, my, Game.WIDTH/2-100,150, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
			// hard button
			if (mouseOver(mx, my, Game.WIDTH/2-100,230, 200, 64)) {
				rectangle[1] = true;
			}else rectangle[1] = false;
			//dev mode
			if (mouseOver(mx, my, Game.WIDTH/2-100,310, 200, 64)) {
				rectangle[2] = true;
			}else rectangle[2] = false;
			// back button
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				rectangle[3] = true;
			}else rectangle[3] = false;
		}
		if(Game.gameState == STATE.End) {
			if (mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
		}
		if(Game.gameState == STATE.Settings) {
			if(mouseOver(mx, my, Game.WIDTH/2-100,395, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
			if(mouseOver(mx,my,172, 254, 20, 20)) {
				rectangle[1] = true;
			}else rectangle[1] = false;
			if(mouseOver(mx,my,622, 227, 20, 20) && playerChroma == false) {
				rectangle[2] = true;
			}else rectangle[2] = false;
		}
		if(Game.gameState == STATE.Finished) {
			if(mouseOver(mx,my,Game.WIDTH/2-100,395, 200, 64)) {
				rectangle[0] = true;
			}else rectangle[0] = false;
		}
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public void tick() {
	if(red >= 255 && blue <= 0 && green < 255) green = green + 2;
	if(green >= 255 && blue <= 0 && red > 0) red = red - 2;
	if(red <= 0 && green >= 255 && blue < 255) blue = blue + 2;
	if(red <= 0 && green > 0 && blue >= 255) green = green - 2;
	if(red < 255 && green <= 0 && blue >= 255) red = red + 2;
	if(red >= 255 && green <= 0 && blue > 0) blue = blue - 2;
	
	red = (int) Game.clamp(red, 0, 255);
	green = (int) Game.clamp(green, 0, 255);
	blue = (int) Game.clamp(blue, 0, 255);
	
	col = new Color(red, green, blue);
	
	}
	
	public void render(Graphics g) {
		if(Game.gameState == STATE.Menu) {
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("The square game!", 150, 75);
			
			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2 - 220,160, 200, 64);
			if(rectangle[1] == true) g.fillRect(Game.WIDTH/2 - 220, 275, 200, 64);
			if(rectangle[2] == true) g.fillRect(Game.WIDTH/2 + 10,160, 200, 64);
			if(rectangle[3] == true) g.fillRect(Game.WIDTH/2 + 10, 275, 200, 64);
			if(rectangle[4] == true) g.fillRect(Game.WIDTH/2-100,400, 200, 64);
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(Game.WIDTH/2 - 220,160, 200, 64);
			g.drawString("Play", 205, 205);
			
			g.drawRect(Game.WIDTH/2 - 220, 275, 200, 64);
			g.drawString("Help", 205, 320);
			
			g.drawRect(Game.WIDTH/2 + 10,160, 200, 64);
			g.drawString("Settings", 413, 205);
			
			g.drawRect(Game.WIDTH/2 + 10, 275, 200, 64);
			g.drawString("Highest Scores", 377, 320);
			
			g.drawRect(Game.WIDTH/2-100,400, 200, 64);
			g.drawString("Quit", 325, 445);
		}else if (Game.gameState == STATE.Help) {
			g.setColor(Color.white);
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help menu", Game.WIDTH/2-125, 75);
			
			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2-100,395, 200, 64);
			
			g.setColor(Color.white);
			
			g.setFont(fnt2);
			g.drawString("Use WASD to control the white square", 90, 150);
			g.drawString("Try to dodge all the other objects", 120, 190);
			g.drawString("Otherwise you will take damage and die", 80, 230);
			g.drawString("Have Fun! :)", Game.WIDTH/2-85, 300);
			
			g.drawRect(Game.WIDTH/2-100,395, 200, 64);
			g.drawString("Back", 325, 440);
		}else if (Game.gameState == STATE.End) {
			g.setColor(Color.white);
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 
			
			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2-100,395, 200, 64);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", Game.WIDTH/2-140, 75);
			
			g.setFont(fnt2);
			g.drawString("Your total score was: " + HUD.totalScore, 135, 200);
			g.drawString("You also reached level: " + hud.getLevel(), 135, 270);
			
			g.drawRect(Game.WIDTH/2-100,395, 200, 64);
			g.drawString("Try again", 295, 440);
		}else if(Game.gameState == STATE.Select) {
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 
			
			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2-100,150, 200, 64);
			if(rectangle[1] == true) g.fillRect(Game.WIDTH/2-100,230, 200, 64);
			if(rectangle[2] == true) g.fillRect(Game.WIDTH/2-100,310, 200, 64);
			if(rectangle[3] == true) g.fillRect(Game.WIDTH/2-100,395, 200, 64);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Select difficulty", 170, 75);
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(Game.WIDTH/2-100,150, 200, 64);
			g.drawString("Normal", 310, 195);
			
			g.drawRect(Game.WIDTH/2-100,230, 200, 64);
			g.drawString("Hard", 325, 275);
			
			g.drawRect(Game.WIDTH/2-100,310, 200, 64);
			g.drawString("Dev", 325, 355);
			
			g.drawRect(Game.WIDTH/2-100,395, 200, 64);
			g.drawString("Back", 325, 440);
		}else if(Game.gameState == STATE.Finished) {
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 

			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2-100,395, 200, 64);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Well done!", 170, 75);
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("gg you won", 270, 300);
			
			g.drawRect(Game.WIDTH/2-100,395, 200, 64);
			g.drawString("Back", 325, 440);
		}else if(Game.gameState == STATE.Settings) {
			Font fnt = new Font("arial", 1, 50); 
			Font fnt2 = new Font("arial", 1, 30); 
			Font fnt3 = new Font("arial", 1, 18); 
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Settings", Game.WIDTH/2 - 100, 75);
			
			g.setColor(col);
			if(rectangle[0] == true) g.fillRect(Game.WIDTH/2-100,395, 200, 64);
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawString("Music Volume:", 75, 120);
			g.drawString(volume * 2 + "%", 520, 120);
			g.fillRect(300, 108, 200, 5);
			
			g.drawString("Trail Length:", 75, 180);
			g.drawString(trailTime * 2 + "%", 520, 180);
			g.fillRect(300, 168, 200, 5);
			
			g.drawString("Player Colour:", 75, 240);
			
			g.setFont(fnt3);
			g.drawString("Chroma:", 75, 270);
			g.fillRect(170, 252, 24, 24);
			if(trailTime * 2 > 100) {
				g.drawString("Trail length longer than 100% not recommened on low end PCs", 75, 205);
			}
			
			g.setColor(Game.playerColour);
			g.fillRect(90, 300, 64, 64);
			
			if(playerChroma) {
				g.setColor(Color.white);
				g.setFont(fnt2);
				g.drawString((90 + (colourSpeed * 10))+ "%", 520, 340);
				g.drawString("Chroma Speed:", 292, 295);
				g.fillRect(300, 327, 200, 5);
			}else {
				g.setFont(fnt2);
				g.setColor(Color.white);
				g.drawString("Red:", 222, 285);
				g.drawString("Green:", 222, 325);
				g.drawString("Blue:", 222, 365);
				g.fillRect(340, 273, 255, 5);
				g.fillRect(340, 313, 255, 5);
				g.fillRect(340, 353, 255, 5);
				g.drawString(Game.red + "", 620, 285);
				g.drawString(Game.green + "", 620, 325);
				g.drawString(Game.blue + "", 620, 365);
				g.setFont(fnt3);
				g.drawString("Random:", 520, 240);
				g.fillRect(620, 225, 24, 24);
				g.setColor(Color.gray);
				g.fillRect(622, 227, 20, 20);
				g.setColor(col);
				if(rectangle[2]) g.fillRect(622, 227, 20, 20);
			}
			
			g.setColor(Color.gray);
			g.fillRect(172, 254, 20, 20);
			g.fillRect(trailX, 161, 5, 20);
			g.fillRect(volumeX, volumeY, 5, 20);
			if(playerChroma) {
				g.fillRect((int) colourSlider, 320, 5, 20);
			}else {
				g.fillRect((int) Game.red + 340, 267, 5, 20);
				g.fillRect((int) Game.green + 340, 307, 5, 20);
				g.fillRect((int) Game.blue + 340, 347, 5, 20);
			}
			
			g.setColor(col);
			if(rectangle[1]) g.fillRect(172, 254, 20, 20);
			
			g.setColor(Color.yellow);
			if(playerChroma) {
				g.fillArc(147, 240, 50, 30, 290, 15);
				g.fillArc(176, 204, 50, 80, 210, 10);
			}
			
			g.setFont(fnt2);
			g.setColor(Color.white);
			g.drawRect(Game.WIDTH/2-100,395, 200, 64);
			g.drawString("Back", 325, 440);
		}
		
	}
}

package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import enemies.BasicEnemy;
import enemies.EnemyBoss;
import otherObjects.Handler;
import otherObjects.ID;
import otherObjects.KeyInput;
import otherObjects.MenuParticle;
import otherObjects.Player;
import otherObjects.Spawn;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -5121218976008684398L;
	
	public static final int WIDTH = 720, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	public static boolean paused = false;
	public static int diff = 0;
	public static int direction = 1;
	public static Color playerColour;
	
	// 0 = normal
	// 1 = hard
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Menu menu;
	private Spawn spawner;
	private Shop shop;
	private Color col;
	private String ready = "";
	
	private int frames;
	private long timer;
	
	public static int red = 255;
	public static int green = 255;
	public static int blue = 255;

	
	public enum STATE {
		End,
		Finished,
		Game,
		Help,
		Menu,
		Select,
		Settings,
		Shop
	};
	
	public static STATE gameState = STATE.Menu;
	
	public static BufferedImage sprite_sheet;
	
	
	public Game(){
		
		handler = new Handler();
		r = new Random();
		hud = new HUD();
		shop = new Shop(handler, hud, this);
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.addMouseListener(shop);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop(1,(float) 0.5);
		
		new Window(WIDTH, HEIGHT, "The Square game!", this);
		
		spawner = new Spawn(handler, hud, this);
		
		//play button
		if(gameState == STATE.Game)
		{
			
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 75), r.nextInt(Game.HEIGHT - 75), ID.BasicEnemy, handler));
		}else {
			this.requestFocus();
			for(int i = 0; i < 30; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
	}
	
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		timer = System.currentTimeMillis();
		frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(Menu.playerChroma) {
			if(red >= 255 && blue <= 0 && green < 255) green = (int) (green + Menu.colourSpeed);
			if(green >= 255 && blue <= 0 && red > 0) red = (int) (red - Menu.colourSpeed);
			if(red <= 0 && green >= 255 && blue < 255) blue = (int) (blue + Menu.colourSpeed);
			if(red <= 0 && green > 0 && blue >= 255) green = (int) (green - Menu.colourSpeed);
			if(red < 255 && green <= 0 && blue >= 255) red = (int) (red + Menu.colourSpeed);
			if(red >= 255 && green <= 0 && blue > 0) blue = (int) (blue - Menu.colourSpeed);
		}
		
		red = (int) clamp(red, 0, 255);
		green = (int) clamp(green, 0, 255);
		blue = (int) clamp(blue, 0, 255);
		
		playerColour = new Color(red, green, blue);
		
		if(hud.getLevel() == 51) gameState = STATE.Finished;
		if (gameState == STATE.Game) {
			
			if(paused == false) {
				hud.tick();
				spawner.tick();
				handler.tick();
				if(HUD.HEALTH <= 0) {
					gameState = STATE.End; 
					HUD.HEALTH = 100;
					EnemyBoss.bossDone = true;
					Handler.spd = 5;
					HUD.bounds = 0;
					HUD.overBounds = 0;
					hud.setScore(0);
					shop.setBuyValue(1000, 1000, 1000);
					handler.clearEnemies();
					for(int i = 0; i < 30; i++) {
						handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
					}
				}
			}
		
		}else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Finished || gameState == STATE.Help|| gameState == STATE.Settings) {
			menu.tick();
			handler.tick();
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0,0, WIDTH, HEIGHT);
		
		g.setColor(Color.white);
		if(System.currentTimeMillis() - timer > 1000) {
			g.drawString("Frames: " + frames, 15, 92);
		}
		
		

		if(paused == true) {
			g.setColor(Color.white);
			g.drawString("PAUSED", 100,100);
		}
		
		if (gameState == STATE.Game) {
			if(hud.getLevel() == 15 || hud.getLevel() == 29) ready = "Get Ready for the onslaught!";
			else if(hud.getLevel() == 9|| hud.getLevel() == 24) ready = "Boss fight coming real soon";
			else ready = "";
			Font fnt2 = new Font("arial", 0, 32);
			g.setFont(fnt2);
			g.drawString(ready, 160, 150);
			Font fnt = new Font("arial", 0, 11);
			g.setFont(fnt);
			handler.render(g);
			hud.render(g);
		}else if (gameState == STATE.Shop) { 
			handler.render(g);
			shop.render(g);
		}else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select || gameState == STATE.Finished || gameState == STATE.Settings) {
			handler.render(g);
			menu.render(g);
		}
		

			
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float x, float min, float max) {
		if(x >= max)
			return x = max;
		else if(x <= min)
			return x = min;
		else
			return x;
	}
	
	public static void main(String args[]) {
		new Game();
	}
	
	public static boolean isNumeric(String str) {
		return str != null && str.matches("[0-9.]+");
	}


}

package main;

import java.awt.Color;
import java.awt.Graphics;


public class HUD {
	
	
	public static int bounds = 0;
	public static int overBounds = 0;
	public static float HEALTH = 100;
	public static float OVERHEALTH = 0;
	public static float damageTaken = 0;
	public static float redDamage = 0;
	public static float redOverDamage = 0;
	public static float redHealth = 0;
	public static float redOverHealth = 0;
	public static int score = 0;
	public static int redHealthTimer = 0;
	
	private float greenValue = 255;
	private int level = 1;
	public static int totalScore = 0;
	
	public void tick() {
		HEALTH = Game.clamp(HEALTH, 0, 100 + (bounds / 2));
		bounds = (int) Game.clamp(bounds, 0, 475);
		greenValue = HEALTH * 2;
		greenValue = Game.clamp(greenValue, 0, 255);
		overBounds = (int) Game.clamp(overBounds, 0, 674);
		OVERHEALTH = Game.clamp(OVERHEALTH, 0, 337);
		 
		score++;
		totalScore++;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200 + bounds, 32);
		g.setColor(new Color(75, (int)greenValue, 0));
		g.fillRect(15, 15, (int) (HEALTH * 2), 32);
		g.setColor(Color.pink);
		g.fillRect(15, 15, (int) (OVERHEALTH * 2), 32);
		g.setColor(Color.red);
		if(redHealthTimer > 30) {
			if (OVERHEALTH <= 2) {
				g.setColor(Color.red);
				g.fillRect((int) (HEALTH * 2) + 15, 15, (int) (redHealth * -2), 32);
			}else if(OVERHEALTH > 2){
				g.setColor(Color.yellow);
				g.fillRect((int) (OVERHEALTH * 2) + 15, 15, (int) (redOverHealth * -2), 32);
			}
			
			redHealthTimer--;
		} else if(redHealthTimer > 0) {
			if (OVERHEALTH <= 2) {
				g.setColor(Color.red);
				g.fillRect((int) (HEALTH * 2) + 15, 15, (int) (redHealth * -2), 32);
			}else if(OVERHEALTH > 2){
				g.setColor(Color.yellow);
				g.fillRect((int) (OVERHEALTH * 2) + 15, 15, (int) (redOverHealth * -2), 32);
			}
			redHealth -= redHealth/20;
			redOverHealth -= redOverHealth/20;
			redHealthTimer--;
		}
		if(redHealthTimer <= 0) {
			redOverHealth = 0;
			redHealth = 0;
			redHealthTimer = 0;
		}
		
		
		
		
		
		g.setColor(Color.white);
		g.drawRect(15, 15, 199 + bounds, 32);
		
		g.drawString("Score: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 78);
		g.drawString("Space for Shop", 15, 92);
	}
	
	public void setTotalScore(int score) {
		this.totalScore = score;
	}
	
	public void setScore(int score) {
		HUD.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

}

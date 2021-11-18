package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import main.Game.STATE;
import otherObjects.Handler;

public class Shop extends MouseAdapter{
	
	Handler handler;
	HUD hud;
	Game game;
	
	private int B1 = 1000;
	private int B2 = 1000;
	private int B3 = 1000;
	
	private boolean confirm = false;
	private boolean clicked = false;
	private boolean nocreds = false;
	private int timer = 300;
	private String tempString;
	
	public Shop(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.game = game;
		this.hud = hud;
		new Random();
		
	}

	
	public void render(Graphics g) {
 		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 48));
		g.drawString("Shop", Game.WIDTH/2-50, 50);
		
		// box 1
		g.setFont(new Font("arial", 0, 16));
		g.setColor(Color.green);
		g.fillRect(100, 130, 200, 80);
		g.setColor(Color.red);
		g.fillRect(225, 140, 5, 60);
		g.fillRect(225, 167, 21, 5);
		g.fillRect(243, 140, 5, 60);
		g.fillRect(253, 140, 5, 60);
		g.fillRect(253, 167, 21, 5);
		g.fillRect(253, 140, 21, 5);
		g.fillRect(273, 140, 5, 32);
		g.setColor(Color.black);
		g.fillRect(280, 137, 5, 21);
		g.fillRect(272, 144, 21, 5);
		g.drawString("Upgrade Health", 110, 160);
		g.drawString("Cost: " + B1, 110, 180);
		
		// box 2
		g.setColor(Color.cyan);
		g.fillRect(100, 230, 200, 80);
		g.setColor(Color.blue);
		g.fillArc(185, 260, 80, 90, 50, 15);
		g.fillArc(185, 190, 80, 90, -65, 15);
		g.fillArc(205, 260, 80, 90, 50, 15);
		g.fillArc(205, 190, 80, 90, -65, 15);
		g.fillArc(225, 260, 80, 90, 50, 15);
		g.fillArc(225, 190, 80, 90, -65, 15);
		g.setColor(Color.black);
		g.drawString("Upgrade Speed", 110, 260);
		g.drawString("Cost: " + B2, 110, 280);
		
		// box 3
		g.setColor(Color.green);
		g.fillRect(100, 330, 200, 80);
		g.setColor(Color.red);
		g.fillRect(240, 340, 15, 60);
		g.fillRect(218, 362, 60, 15);
		g.setColor(Color.black);
		g.drawString("Refill Health", 110, 360);
		g.drawString("Cost: " + B3, 110, 380);
		
		
		g.setColor(Color.white);
		
		try {
			//g.drawString("Current Score: " + hud.getScore(), Game.WIDTH/2-50, 80);
			g.drawString("Cureent Score: " + hud.getScore(), Game.WIDTH/2-70, 80);
		} catch (Exception e) {
			e.printStackTrace();
		}
		g.drawString("Press Space to go back", Game.WIDTH/2-70, 110);
		
		g.setFont(new Font("arial", 0, 32));
		g.drawString("Click on the", 420, 150);
		g.drawString("Rectangle to purchase!", 350, 180);
		g.setFont(new Font("arial", 0, 16));
		
		if(nocreds == true) {
			tempString = "You have no credits";
			g.drawString(tempString, 400, 450);
			timer--;
			if(timer <= 0) {
				tempString = "";
				nocreds = false;
				timer = 300;
			}
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Shop) {
			// box 1
			if(mx >= 100 && mx <= 300) {
				if(my >= 130 && my <= 210) {
					// you selected box 1
					if(HUD.bounds <= 474) {
						if(hud.getScore() >= B1) {
							clicked = true;
							if(mx >= 100 && mx <= 300) {
								if(my >= 130 && my <= 210) {
									if(clicked == true) confirm = true;
								}
							}
							if(confirm == true) {
								hud.setScore(hud.getScore() - B1);
								B1 += 500;
								HUD.bounds += 40;
								HUD.HEALTH = (100 + (HUD.bounds / 2));
								HUD.redHealth =0;
								HUD.redOverDamage = 0;
							}
						}else {
							nocreds = true;
						}
					} else if(HUD.overBounds <= 674) {
						if(hud.getScore() >= B1) {
							clicked = true;
							if(mx >= 100 && mx <= 300) {
								if(my >= 130 && my <= 210) {
									if(clicked == true) confirm = true;
								}
							}
							if(confirm == true) {
								hud.setScore(hud.getScore() - B1);
								B1 += 500;
								HUD.overBounds += 20;
								HUD.HEALTH = (100 + (HUD.bounds / 2));
								HUD.OVERHEALTH = HUD.overBounds /2;
								HUD.redOverDamage = 0;
								HUD.redHealth = 0;
							}
						}else {
							nocreds = true;
						}
					}
					
				}
			}
			
			// box 3
			if(mx >= 100 && mx <= 300) {
				if(my >= 230 && my <= 310) {
					// you selected box 2
					if(hud.getScore() >= B2) {
						hud.setScore(hud.getScore() - B2);
						B2 += 750;
						Handler.spd++;
					} else nocreds = true;
				}
			}
			
			// box 3
			if(mx >= 100 && mx <= 300) {
				if(my >= 330 && my <= 410) {
					// you selected box 3
					if(hud.getScore() >= B3) {
						hud.setScore(hud.getScore() - B3);
						HUD.HEALTH = (100 + (HUD.bounds / 2));
						if(HUD.overBounds > 0) HUD.OVERHEALTH = (HUD.overBounds /2);
						HUD.redHealth = 0;
						HUD.redOverDamage = 0;
						
					}else nocreds = true;
				}
			}
		}
		
	}
	
	public void setBuyValue(int B1, int B2, int B3) {
		this.B1 = B1;
		this.B2 = B2;
		this.B3 = B3;
	}
}

package otherObjects;

import java.awt.Graphics;
import java.util.LinkedList;

import main.Game;
import main.HUD;

public class Handler {
	
	private LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public static int spd = 5;
	HUD hud;
	
	public synchronized void tick() {
		for(int i = 0; i < getObject().size(); i++) {
			GameObject tempObject = getObject().get(i);
			
			tempObject.tick();
		}
	}
	
	public synchronized void render(Graphics g) {
		for(int i = 0; i < getObject().size(); i++) {
			GameObject tempObject = getObject().get(i);
			
			tempObject.render(g);
		}
	}
	
	public synchronized void clearEnemies() {
		for(int i = 0; i < getObject().size(); i++) {
			GameObject tempObject = getObject().get(i);
			
			if(tempObject.getId() == ID.Player) {
				getObject().clear();
				if(Game.gameState != Game.STATE.End)
				addObject(new Player((int) tempObject.getX(), (int) tempObject.getY(), ID.Player, this));
			}
				
		}
	}
	
	public synchronized void addObject(GameObject object) {
		this.getObject().add(object);
	}
	
	public synchronized void removeObject(GameObject object) {
		this.getObject().remove(object);
	}

	public LinkedList<GameObject> getObject() {
		return object;
	}

	public void setObject(LinkedList<GameObject> object) {
		this.object = object;
	}

}

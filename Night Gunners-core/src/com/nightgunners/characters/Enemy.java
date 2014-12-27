package com.nightgunners.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
	int x,y;
	int velX,velY;
	Texture texture;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		x+=velX;
		y+=velY;
		checkCol();
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
	
	//checks for collision
	public void checkCol() {
		
	}
	
	// * GETTERS * \\
	public int getX() {return x;}
	public int getY() {return y;}
	
	// * SETTERS * \\
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	
	
	
	// * COLLISION * \\
	public Rectangle getBounds() {
		Rectangle r;
		r = new Rectangle(getX()-10, getY()-10, 20, 20);
		return r;
	}
	
}
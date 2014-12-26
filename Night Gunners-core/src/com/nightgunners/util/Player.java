package com.nightgunners.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import java.awt.Rectangle;


public class Player {
	
	int x, y;
	int velX, velY;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		x+=velX;
		y+=velY;
		checkKeys();
	}
	
	public void checkKeys() {
		
		/* W */
		if(Gdx.input.isKeyPressed(Keys.W)) {
			velY=4;
		}
		
		/* A */
		if(Gdx.input.isKeyPressed(Keys.A)) {
			velX=-4;
		}
		
		/* S */
		if(Gdx.input.isKeyPressed(Keys.S)) {
			velY=-4;
		}
		
		/* D */
		if(Gdx.input.isKeyPressed(Keys.D)) {
			velX=4;
		}
		
		if(!Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A))
			velX = 0;
		
		if(!Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S))
			velY = 0;
		
		
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
package com.nightgunners.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nightgunners.characters.Player;
import com.nightgunners.game.GameScreen;

public class Bullet {
	   
	   static final float BULLET_SPEED = 800;
	   static final Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	   
	   Vector3 position, velocity, acceleration;
	   Texture sprite;
	   
	   public Vector3 target;
	   static Rectangle Bb;
	   Player player;
	   
	   public Bullet(Texture sprite, Player player) {
		  this.sprite = sprite;
		  this.player = player;
		  reset();
	      Bb = new Rectangle(0, 0, 10, 10);
	      acceleration = new Vector3(0, -400, 0); // gravity
	      
	   }
	   
	   private void reset() {
	      position = new Vector3(player.getX() + 20, player.getY() + 20, 0);
	      target = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);//Gdx.graphics.getHeight()  -1- Gdx.input.getY(), 0);
	      GameScreen.cam.unproject(target);
	     // GameScreen.cam.unproject(position);
	      
	     // GameScreen.cam.unproject(acceleration);
	      velocity = target.cpy().sub(position).nor().scl(BULLET_SPEED); // fire towards target
	    //  GameScreen.cam.unproject(velocity);
	   }
	   
	   public void render(SpriteBatch batch) {
	      batch.draw(sprite, position.x, position.y);
	      Bb.setX(position.x);
	      Bb.setY(position.y);
	   }
	   
	   public void update(float delta) {
		   //TODO: Add box collsion
		   
	      // simple Euler integration
	      position.add(velocity.cpy().scl(delta));
	   }
	}
package com.nightgunners.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

	public static int TILE_SIZE = 32;
	
	protected Texture texture;
	
	//public boolean solid;
	//public boolean grassTile;
	//public boolean goalTile;
	//public boolean waterTile;
	//public boolean sodaTile;
	//public boolean stoneTile;
	public int x, y;

	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}

	
	public void render(SpriteBatch spriteBatch){
		spriteBatch.draw(texture, x, y, TILE_SIZE, TILE_SIZE);
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
}
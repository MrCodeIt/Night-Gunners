package com.nightgunners.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {

	public static int TILE_SIZE = 32;

	public boolean solid;
	public boolean grassTile;
	public boolean goalTile;
	public boolean waterTile;
	public boolean sodaTile;
	public boolean stoneTile;
	public int x, y;

	public Tile(int x, int y, boolean solid, boolean grassTile, boolean goalTile, boolean waterTile, boolean sodaTile, boolean stoneTile){
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.grassTile = grassTile;
		this.goalTile = goalTile;
		this.waterTile = waterTile;
		this.sodaTile = sodaTile;
		this.stoneTile = stoneTile;
	}

	
	public void render(SpriteBatch spriteBatch, Texture tex){
		spriteBatch.draw(tex, x, y, TILE_SIZE, TILE_SIZE);
	}
	
}
package com.nightgunners.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nightgunners.characters.Enemy;
import com.nightgunners.entities.Tile;
import com.nightgunners.entities.TileEnemy;
import com.nightgunners.entities.TileGoal;
import com.nightgunners.entities.TileGrass;
import com.nightgunners.entities.TileSoda;
import com.nightgunners.entities.TileStone;
import com.nightgunners.entities.TileWater;
import com.nightgunners.util.Assets;
import com.nightgunners.util.TileColors;

public class Level {
	private GameScreen game;
	private Tile[][] tiles;
	private int width, height;
	
	public Level (GameScreen game, String path) {
		this.game = game;
		loadLevel(path);
	}
	
	public void loadLevel(String path) {
		Pixmap pixels = new Pixmap(Gdx.files.internal(path));
		this.width = pixels.getWidth();
		this.height = pixels.getHeight();
		int colorValue;
		tiles = new Tile[width][height];
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++) {
				colorValue = pixels.getPixel(x, y);
				
				if(colorValue == TileColors.grassTile){
					tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				
				//SODA
				}else if(colorValue == TileColors.sodaTile){
					tiles[x][y] = new TileSoda(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				
				//GOAL
				}else if(colorValue == TileColors.goal){
					game.setGoalTileX(x);
					game.setGoalTileY(y);
					tiles[x][y] = new TileGoal(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				
				//STONE
				}else if(colorValue == TileColors.stoneTile){
					tiles[x][y] = new TileStone(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				
				//SPAWN
				}else if(colorValue == TileColors.playerSpawn){
					game.getPlayer().setX(x * Tile.TILE_SIZE);
					game.getPlayer().setY(y * Tile.TILE_SIZE);
					tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				//WATER
				}else if(colorValue == TileColors.waterTile){
					tiles[x][y] = new TileWater(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
				//ENEMY (on stone)
				}else if(colorValue == TileColors.enemystoneTile) {
					tiles[x][y] = new TileEnemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
					tiles[x][y].setTexture(Assets.stoneTex);
					game.getEnemies().add(new Enemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
					
				//ENEMY (on grass)
				}else if(colorValue == TileColors.enemygrassTile) {
					tiles[x][y] = new TileEnemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
					tiles[x][y].setTexture(Assets.grassTex);
					game.getEnemies().add(new Enemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
					
				//DEFAULT
				}else{
					tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);

				}
			}
		}
	}
	
	public void render(SpriteBatch spriteBatch) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(tiles[x][y] != null) {
					tiles[x][y].render(spriteBatch);
				}
			}
		}
	}
}

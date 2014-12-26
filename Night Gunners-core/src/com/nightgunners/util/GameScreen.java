package com.nightgunners.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nightgunners.game.NightGunners;
import com.nightgunners.entities.Bullet;
import com.nightgunners.entities.Tile;
import com.nightgunners.entities.TileGrass;
import com.nightgunners.entities.TileStone;
import com.nightgunners.entities.TileWater;
import com.nightgunners.entities.TileGoal;
import com.nightgunners.entities.TileSoda;

public class GameScreen implements Screen{
	
	SpriteBatch batch;
	NightGunners ng;
	boolean isPaused = false;
	public static OrthographicCamera cam;
	
	//SPRITES
	Sprite player;
	Player p;
	
	//ENTITIES
	ArrayList<Bullet> bullets;
	Tile[][] tiles;
	
	int width, height;
	int goalTileX, goalTileY;
	int doorTileX, doorTileY;
	
	//TILE TEXTURES
	Texture waterTex;
	Texture sodaTex;
	Texture goalGrassTex;
	Texture grassTex;
	Texture stoneTex;
	
	Texture penis;
	
	//LEVELS
	int lvl = 0;
	
	public GameScreen(NightGunners ng){
        this.ng = ng;
	}

	public void render(float delta) {
		if(!isPaused) {
	    Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		cam.position.x = player.getX();
		cam.position.y = player.getY();
	 
	 
		batch.begin();
			//BACKGROUNDS
			for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(tiles[x][y] != null){
					//if water tile
					if(tiles[x][y].waterTile){
						tiles[x][y].render(batch, waterTex);}
					//if grass tile
					if(tiles[x][y].grassTile){
						tiles[x][y].render(batch, grassTex);}
					//if goal tile
					if(tiles[x][y].goalTile){
						tiles[x][y].render(batch, goalGrassTex);}
					//if stone tile
					if(tiles[x][y].stoneTile){
						tiles[x][y].render(batch, stoneTex);}
					//if soda tile
					if(tiles[x][y].sodaTile){
						tiles[x][y].render(batch, sodaTex);}
					
				}}}
			//CHARACTERS
			player.draw(batch);
			batch.draw(penis, 100, 100);
			
			//ENTITIES
			for (Bullet b : bullets) {
				
	         	b.update(delta);
	         	b.render(batch);
	      	}
			
		batch.end();
	    }
	}

	public void update(float delta) {
		if(!isPaused){
			p.update();
			player.setPosition(p.getX(), p.getY());
			checkInput();
			mouseMove();
		}
	}
	
	void checkInput() {
		//MOUSE
		if(Gdx.input.isButtonPressed(1)) {
			
			bullets.add(new Bullet(new Texture("Bullet.png"), p));
		}
		
	}
	void mouseMove() {
		//float xOffset = (Gdx.graphics.getWidth() / 2) - (player.getX() + (player.getWidth() / 2));
	    //  float yOffset =  (Gdx.graphics.getHeight() / 2)  - (player.getY() + (player.getHeight() / 2));
	      float mouseX = Gdx.input.getX(); //+ xOffset;
	      float mouseY = (Gdx.graphics.getHeight() - Gdx.input.getY()) ;//+ yOffset;
	      
	      float radiansToMouse = (float) Math.atan2(mouseX -  (Gdx.graphics.getWidth() / 2) ,  (Gdx.graphics.getHeight() / 2)  - mouseY);
	         float degreesToMouse = 55.2957795f*radiansToMouse;
	         player.setRotation(degreesToMouse);
	       
	}

	public void show() {
	initMusic();
	initImages();
	initEntities();
	initLevel();
	cam = new OrthographicCamera();
	cam.zoom = 2;
	cam.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	}
	
	
	void initLevel() {
		try {
			BufferedImage image = ImageIO.read(this.getClass().getResource("level_DEMO.png"));
			width = image.getWidth();
			height = image.getHeight();
			
			tiles = new Tile[width][height];
			
			for(int x = 0; x < image.getWidth(); x++){
				for(int y = 0; y < image.getHeight(); y++){
					System.out.println(tiles[x][y]);
					//GRASS
					if(image.getRGB(x, y) == TileColors.grassTile){

						tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, false, false, false, false);
					//SODA
					}else if(image.getRGB(x, y) == TileColors.sodaTile){

						tiles[x][y] = new TileSoda(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, false, false, false, true, false);
					//GOAL
					}else if(image.getRGB(x, y) == TileColors.goal){

						goalTileX = x;
						goalTileY = y;
						tiles[x][y] = new TileGoal(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, true, false, false, false);
					//STONE
					}else if(image.getRGB(x, y) == TileColors.stoneTile){
						
						tiles[x][y] = new TileStone(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, false, false, false, false, true);
					//SPAWN
					}else if(image.getRGB(x, y) == TileColors.playerSpawn){

						p.setX(x * Tile.TILE_SIZE);
						p.setY(y * Tile.TILE_SIZE);
						tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, false, false, false, false);
					//WATER
					}else if(image.getRGB(x, y) == TileColors.waterTile){

						tiles[x][y] = new TileWater(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, true, false, false, true, false, false);
					//DEFAULT
					}else{

						tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, false, false, false, false);

					}
					System.out.println(tiles[x][y]);

				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	void initEntities() {
		//CHARACTERS
		p = new Player(10,20);
		
		
		//OBJECTS
		bullets = new ArrayList<Bullet>();
		
	}

	void initMusic() {
	
	}

	void initImages() {
		batch = new SpriteBatch();	
		player = new Sprite(new Texture("Gripper_EMPTY.png"));
		penis = new Texture(Gdx.files.internal("Thug_GUNNER.png"));
		
		//TILES
		waterTex = new Texture(Gdx.files.internal("tile_WATER_dark.png"));
		grassTex = new Texture(Gdx.files.internal("tile_GRASS_dark.png"));
		goalGrassTex = new Texture(Gdx.files.internal("tile_GRASS_dark.png"));
		sodaTex = new Texture(Gdx.files.internal("tile_GRASS_dark_SODA.png"));
		stoneTex = new Texture(Gdx.files.internal("tile_STONE_dark.png"));
		
	}
	
	public void resize(int width, int height) {
		
	}

	
	public void hide() {

	}


	public void pause() {
		isPaused = true;
	}


	public void resume() {
		isPaused = false;
	}


	public void dispose() {
	
	}
}

//TODO: Add tile array (Phasedd)
//TODO: Add UI
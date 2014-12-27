package com.nightgunners.game;

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
import com.nightgunners.util.ScreenShaker;
import com.nightgunners.util.TileColors;
import com.nightgunners.characters.Enemy;
import com.nightgunners.characters.Player;
import com.nightgunners.entities.Bullet;
import com.nightgunners.entities.Tile;
import com.nightgunners.entities.TileEnemy;
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
	public static Player p;
	ArrayList<Sprite> snapbackEnemy;
	ArrayList<Enemy> enemies;
	
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
	
	//TOOLS
	ScreenShaker ss;
	boolean shot = false;
	
	//LEVELS
	String lvlImg = "level_DEMO.png";
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
			System.out.println(p.getX() + "pladf" + p.getY());
			for(Enemy e: enemies) {
				Sprite s = new Sprite(new Texture("Thug_GUNNER.png"));
				s.setPosition(e.getX(), e.getY());
				s.draw(batch);
			}
			
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
			ss.tick(delta);
			checkInput();
			mouseMove();
			checkHit();
		}
	}
	
	void checkHit() {
		for(Bullet b: bullets) {
			for(Enemy e: enemies) {
				if(b.Bb.overlaps(e.getBounds())) {e.setX(50000);} //send the enemy far far away when hit
				
			}
			
		}
	}
	
	void checkInput() {
		//MOUSE
		if(Gdx.input.isButtonPressed(1) && !shot) {
			shot = true;
			//if(bullets per click = 1)
			bullets.add(new Bullet(new Texture("Bullet.png"), p));
			ss.rumble(10, 1);
		}
		
		if(!Gdx.input.isButtonPressed(1) && shot) {
			shot = false;
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
			if(lvl == -1) {lvlImg = "level_DEMO.png";}
			if(lvl == 0) {lvlImg = "level_0.png";}
			BufferedImage image = ImageIO.read(this.getClass().getResource(lvlImg)); //NOTE: java.awt is drawn
																					 //from different coords so
																					 //make all buffered images
																				 	 //upside-down or libGdx 
																					 //will draw them upside-down
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
					
					//ENEMY (on stone)
					}else if(image.getRGB(x, y) == TileColors.enemystoneTile) {
						tiles[x][y] = new TileEnemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, false, false, false, false, true);
						enemies.add(new Enemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
						
					//ENEMY (on grass)
					}else if(image.getRGB(x, y) == TileColors.enemygrassTile) {
						tiles[x][y] = new TileEnemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, false, false, false, false);
						enemies.add(new Enemy(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE));
						
					//DEFAULT
					}else{

						tiles[x][y] = new TileGrass(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, false, true, false, false, false, false);

					}

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
		enemies = new ArrayList<Enemy>();
	//	enemies.add(new)
		
		
		//OBJECTS
		bullets = new ArrayList<Bullet>();
		
		//TOOLS
		ss = new ScreenShaker();
		
	}

	void initMusic() {
	
	}

	void initImages() {
		batch = new SpriteBatch();	
		player = new Sprite(new Texture("Gripper_EMPTY.png"));
		snapbackEnemy = new ArrayList<Sprite>();
		
		
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
//TODO: Add gun enum with intensity, name, etc
//TODO: Fix collision bounds for bullet and enemy
//TODO: Increase performance by rendering what's in square'
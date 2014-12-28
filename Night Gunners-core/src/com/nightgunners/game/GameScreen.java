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
import com.nightgunners.characters.Enemy;
import com.nightgunners.characters.Player;
import com.nightgunners.entities.Bullet;
import com.nightgunners.entities.Tile;
import com.nightgunners.entities.TileEnemy;
import com.nightgunners.entities.TileGoal;
import com.nightgunners.entities.TileGrass;
import com.nightgunners.entities.TileSoda;
import com.nightgunners.entities.TileStone;
import com.nightgunners.entities.TileWater;
import com.nightgunners.util.ScreenShaker;
import com.nightgunners.util.TileColors;

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



	//TOOLS
	ScreenShaker ss;
	boolean shot = false;

	//LEVELS
	String lvlImg = "level_DEMO.png";
	int lvl = 0;

	private Level level;

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
			level.render(batch);
			//CHARACTERS
			player.draw(batch);
		//	System.out.println(p.getX() + "pladf" + p.getY());
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

		level = new Level(this, "levels/level_0.png");
	
		cam = new OrthographicCamera();
		cam.zoom = 2;
		cam.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
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

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public Player getPlayer() {
		return p;
	}

	public int getGoalTileX() {
		return goalTileX;
	}

	public void setGoalTileX(int goalTileX) {
		this.goalTileX = goalTileX;
	}

	public int getGoalTileY() {
		return goalTileY;
	}

	public void setGoalTileY(int goalTileY) {
		this.goalTileY = goalTileY;
	}

	public int getDoorTileX() {
		return doorTileX;
	}

	public void setDoorTileX(int doorTileX) {
		this.doorTileX = doorTileX;
	}

	public int getDoorTileY() {
		return doorTileY;
	}

	public void setDoorTileY(int doorTileY) {
		this.doorTileY = doorTileY;
	}

}

//TODO: Add tile array (Phasedd)
//TODO: Add UI
//TODO: Add gun enum with intensity, name, etc
//TODO: Fix collision bounds for bullet and enemy
//TODO: Increase performance by rendering what's in square'
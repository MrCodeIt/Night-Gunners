package com.nightgunners.game;

import com.badlogic.gdx.Game;
import com.nightgunners.util.GameScreen;

public class NightGunners extends Game
{
	
	GameScreen gs;
	@Override
    public void create()
    {
		gs = new GameScreen(this);
		setScreen(gs);
		
    }
}
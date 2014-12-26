package com.nightgunners.game;

import com.badlogic.gdx.Game;

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
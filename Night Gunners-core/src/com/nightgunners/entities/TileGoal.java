package com.nightgunners.entities;

import com.nightgunners.util.Assets;

public class TileGoal extends Tile{
	
	public TileGoal(int x, int y) {
		super(x,  y);
		texture = Assets.goalGrassTex;
	}

}
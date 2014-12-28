package com.nightgunners.entities;

import com.nightgunners.util.Assets;

public class TileWater extends Tile{

	public TileWater(int x, int y) {
		super(x,  y);
		texture = Assets.waterTex;
		//animationOldTime = Timer.getTime();
	}
}
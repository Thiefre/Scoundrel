package dev.game.tiles;

import java.awt.image.BufferedImage;

import dev.game.Assets;

public class WaterTile extends Tile
{

	public WaterTile(int id) 
	{
		super(Assets.tiles.get(70), id);	
	}
	public boolean isSolid()
	{
		return true;
	}
}

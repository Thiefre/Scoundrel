package dev.game.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.imageio.ImageIO;

import dev.game.Assets;
import dev.game.entities.Player;
import dev.game.graphics.Sprite;
import dev.game.graphics.SpriteSheet;

public class Tile
{
	
	protected BufferedImage texture;
	protected final int id;
	public static final int TILEWIDTH = 128, TILEHEIGHT = 128;
	public Integer[] solid = {33, 0, 1, 2 ,3 ,4, 9, 11, 12, 13, 18, 19, 20};
	private HashSet<Integer> solidFinder = new HashSet<Integer>(Arrays.asList(solid));
	
	public static Tile[] tiles = new Tile[256];
	
	public Tile(BufferedImage texture, int id)
	{	
		this.texture = texture;
		this.id = id;
	}

	public static Tile[] getSpriteSheet(BufferedImage[] sprites)
	{
		for(int i = 0; i < sprites.length; i++)
		{
			tiles[i] = new Tile(sprites[i], i);
		}
		return tiles;
	}
	
	public static Tile[] fastTiles()
	{
		for(int i = 0; i<Assets.tiles.size(); i++)
		{
			tiles[i] = new Tile(Assets.tiles.get(i), i);
		}
		return tiles;
	}
	
	public void update()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public int getID()
	{
		return id;
	}
	
	public boolean equals(Object o)
	{
		Tile t = (Tile) o;
		if(this.id == t.id)
		{
			return true;
		}
		else return false;
	}
	
	public int hashCode()
	{
		return id;
	}
	
	public boolean isSolid()
	{
		for(int x : solidFinder)
		{
			if(this.id == x)
			{
				return true;
			}
		}
		return false;
	}
}

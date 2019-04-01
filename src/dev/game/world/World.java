package dev.game.world;

import java.awt.Graphics;

import dev.game.Assets;
import dev.game.Handler;
import dev.game.graphics.SpriteSheet;
import dev.game.tiles.Tile;
import dev.game.utils.Utils;

public class World
{
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	public World(Handler handler, String path)
	{
		this.handler = handler;
		loadWorld(path);
	}
	
	private void loadWorld(String path)
	{
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		int counter = 0;
		for(int y = 0; y< height; y++)
		{
			for(int x = 0; x<width; x++)
			{
				tiles[x][y] = Utils.parseInt(tokens[counter+4]);
				counter++;
			}
		}
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y>= height)
		{
			return Tile.fastTiles()[0];
		}
		Tile t = Tile.getSpriteSheet(Assets.sprites.crop(32, 32))[tiles[x][y]];
		if(t == null)
		{
			return Tile.fastTiles()[0];
		}
		return t;
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void update()
	{
	}
	public void render(Graphics g)
	{
		int xStart = (int) Math.max(0, (handler.getCamera().getxOffset()/Tile.TILEWIDTH));
		int xEnd =(int) Math.min(width,(handler.getCamera().getxOffset()+handler.getWidth())/Tile.TILEWIDTH +1);
		int yStart = (int) Math.max(0, (handler.getCamera().getyOffset()/Tile.TILEHEIGHT));;
		int yEnd = (int) Math.min(height,(handler.getCamera().getyOffset()+handler.getHeight())/Tile.TILEHEIGHT +1);;
		
		for(int y = yStart; y<yEnd; y++)
		{
			for(int x = xStart; x< xEnd; x++)
			{
				getTile(x,y).render(g, (int) (x*Tile.TILEWIDTH - handler.getCamera().getxOffset()), (int) (y*Tile.TILEWIDTH-handler.getCamera().getyOffset()));
			}
		}
		loadWorld("res/worlds/world1.txt");
	}
}

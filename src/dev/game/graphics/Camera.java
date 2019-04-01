package dev.game.graphics;

import dev.game.Handler;
import dev.game.entities.Entity;
import dev.game.tiles.Tile;

public class Camera 
{
	private float xOffset, yOffset;
	private Handler handler;
	
	public Camera(Handler handler, float xOffset, float yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.handler = handler;
	}
	
	public void checkBlankSpace()
	{
		if(xOffset < 0)
		{
			xOffset = 0;
		}
		else if(xOffset > handler.getWorld().getWidth()*Tile.TILEWIDTH - handler.getWidth())
		{
			xOffset = handler.getWorld().getWidth()*Tile.TILEWIDTH - handler.getWidth();
		}
		if(yOffset < 0)
		{
			yOffset = 0;
		}
		else if(yOffset > handler.getWorld().getHeight()*Tile.TILEHEIGHT - handler.getHeight())
		{
			yOffset = handler.getWorld().getHeight()*Tile.TILEHEIGHT - handler.getHeight();
		}
	}
	
	public void centerOnEntity(Entity e)
	{
		xOffset = e.getX() - handler.getWidth()/2 + e.getHeight()/2;
		yOffset = e.getY() - handler.getHeight()/2 + e.getWidth()/2;
	}
	
	public void move(float dx, float dy)
	{
		xOffset += dx;
		yOffset += dy;
	}
	
	public float getxOffset() 
	{
		return xOffset;
	}

	public void setxOffset(float xOffset) 
	{
		this.xOffset = xOffset;
	}

	public float getyOffset() 
	{
		return yOffset;
	}

	public void setyOffset(float yOffset)
	{
		this.yOffset = yOffset;
	}
	
}

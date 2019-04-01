package dev.game.graphics;

import java.awt.image.BufferedImage;

import dev.game.Assets;
import dev.game.Handler;

public class Sprite
{
	protected float x;
	protected float y;
	protected int width;
	protected int height;
	public BufferedImage image;
	protected Handler handler;
	
	public Sprite(Handler handler, float x, float y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
	}

	public void loadImage(BufferedImage image)
	{
		this.image = image;
	}
	
	
	public void getImageDimensions()
	{
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	public BufferedImage getImage()
	{
		return image;
	}
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}

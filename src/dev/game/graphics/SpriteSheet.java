package dev.game.graphics;

import java.awt.image.BufferedImage;

import dev.game.tiles.Tile;

public class SpriteSheet
{
	private BufferedImage image;
	private BufferedImage[] spriteSheet = new BufferedImage[256];
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	public BufferedImage[] crop(int width, int height)
	{
		int counter = 0;
		for(int j = 0; j<image.getHeight()/height; j++)
		{
			for(int i = 0; i<image.getWidth()/width;i++)
			{
				spriteSheet[counter] = image.getSubimage(i*width, j*height, width, height);
				counter++;
			}
		}
		return spriteSheet;
	}
}

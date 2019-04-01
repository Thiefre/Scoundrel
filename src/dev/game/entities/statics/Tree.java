package dev.game.entities.statics;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Assets;
import dev.game.Handler;

public class Tree extends StaticEntity
{

	public Tree(Handler handler, float x, float y) 
	{
		super(handler, x, y, Assets.tree.getWidth()*3, Assets.tree.getHeight()*3);
	}

	public void update()
	{
		takeDamage(0);
	}
	
	public void render(Graphics g)
	{
		if(getDamageTaken())
		{
			g.fillRect((int)(x - handler.getCamera().getxOffset()), (int)(y - handler.getCamera().getyOffset()), width, height);
		}
		g.drawImage(Assets.tree, (int)(x - handler.getCamera().getxOffset()), (int)(y - handler.getCamera().getyOffset()), width, height, null);
	}
}

package dev.game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.game.Handler;
import dev.game.SkillBase;
import dev.game.graphics.Sprite;

public abstract class Entity extends Sprite
{
	protected Rectangle bounds;
	protected int chealth = 100;
	protected int mhealth = 100;
	protected boolean damageTaken = false;
	int i = 0;
	
	public Entity(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x,y,width,height);
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	
	public int getMaxHealth()
	{
		return mhealth;
	}
	
	public int getHealth()
	{
		return chealth;
	}
	
	public boolean checkXHitBox(Rectangle hitBox, Entity e)
	{
		if(hitBox.getX() > e.getX())
		{
			if(hitBox.getX() < e.getX()+e.getWidth())
			{
				return true;
			}
		}
		else if(hitBox.getX() < e.getX())
		{
			if(e.getX() < hitBox.getX()+hitBox.getWidth())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean checkYHitBox(Rectangle hitBox, Entity e)
	{
			if(hitBox.getY()+hitBox.getHeight() > e.getY()+e.getHeight())			
			{
				if(e.getY()+e.getHeight() > hitBox.getY())
				{
					return true;
				}
			}
			else if(hitBox.getY()+hitBox.getHeight() < e.getY()+e.getHeight())
			{
				if(hitBox.getY()+hitBox.getHeight() > e.getY())
				{
					return true;
				}
			}
		return false;
	}
	
	public void hit(Rectangle hitBox)
	{
		for(Entity e: handler.getGame().getState().getEntities())
		{
			if(checkXHitBox(hitBox, e) && checkYHitBox(hitBox, e))
			{
				e.takeDamage(10);
			}
		}
		System.out.println("hit" + i++);
	}	
	
	public boolean getDamageTaken()
	{
		return damageTaken;
	}
	
	public int takeDamage(int damage)
	{
		chealth -= damage;
		if(damage > 0)
		{
		damageTaken = true;
		}
		else damageTaken = false;
		return chealth;
	}
	public int hashCode()
	{
		return (int)(x+y+width+height);
	}
	public boolean equals(Object o)
	{
		Entity me = (Entity)o;
		if(this.x == me.x)
		{
			if(this.y == me.y)
			{
				if(this.width == me.width)
				{
					if(this.height == me.height)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}

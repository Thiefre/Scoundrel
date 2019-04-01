package dev.game.entities;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import dev.game.Handler;
import dev.game.graphics.Animation;
import dev.game.tiles.Tile;

public class NPC extends Character{
	public ArrayList<String> abilities = new ArrayList<String>();
	public int damage;
	public int i = -1;
	public String job;
	private boolean moving = false;
	private boolean facingLeft;
	private Animation cAnimation;
	Random test = new Random();
	
	public NPC(Handler handler, int x , int y, int width, int height)
	{
		super(handler, x, y, width, height);
		bounds.x = width/3+16;
		bounds.y = height/2 + 18;
		bounds.width = width/5;
		bounds.height = height/3;
		this.setStats(test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+5);
	}
	public NPC(String name)
	{
		super(null,0,0,0,0);
		this.name = name;
		this.setStats(test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+1, test.nextInt(4)+5);
	}
	
	public void setNPCLVL(int level)
	{
		this.level = level;
	}
	
	public void setdx(int dx)
	{
		this.dx = dx;
	}
	public void setdy(int dy)
	{
		this.dy = dy;
	}
	
	public int[] setRandomStats()
	{
		return this.setStats(test.nextInt(8)+1, test.nextInt(8)+1, test.nextInt(8)+1, test.nextInt(8)+1, test.nextInt(5)+5);
	}
	
	public void AImove()
	{
		dx = 0;
		dy = 0;
			if(handler.getGame().getState().getPlayer().getX()/Tile.TILEWIDTH > this.getX()/Tile.TILEWIDTH)
			{
				dx = speed;
				facingLeft = false;
			}
			if(handler.getGame().getState().getPlayer().getX()/Tile.TILEWIDTH < this.getX()/Tile.TILEWIDTH)
			{
				dx = -speed;
				facingLeft = true;
			}
			if(handler.getGame().getState().getPlayer().getY()/Tile.TILEHEIGHT > this.getY()/Tile.TILEHEIGHT)
			{
				dy = speed;
			}
			if(handler.getGame().getState().getPlayer().getY()/Tile.TILEHEIGHT < this.getY()/Tile.TILEHEIGHT)
			{
				dy = -speed;
			}
			if(handler.getGame().getState().getPlayer().getX()/Tile.TILEWIDTH == this.getX()/Tile.TILEWIDTH)
			{
				dx = 0;
			}
			if(handler.getGame().getState().getPlayer().getY()/Tile.TILEHEIGHT == this.getY()/Tile.TILEHEIGHT)
			{
				dy = 0;
			}
			if(dx != 0 || dy != 0)
			{
				moving = true;
			}
			else 
			{
				moving = false;
			}
		move();
	}
	
	public void loadNPC()
	{
		if(moving == true)
		{
			cAnimation = myAnimations.playerRun;
		}
		else
		{
			cAnimation = myAnimations.playerIdle;
		}
		loadImage(cAnimation.getSprite());
	}
	
	@Override
	public void update() {
		AImove();
		loadNPC();
		cAnimation.update();
		takeDamage(0);
		
	}

	@Override
	public void render(Graphics g) {
		if(getDamageTaken())
		{
			g.fillRect((int)(x - handler.getCamera().getxOffset()), (int)(y - handler.getCamera().getyOffset()), width, height);
		}
		if(facingLeft == true)
		{
			g.drawImage(getImage(), (int)(getX() - handler.getCamera().getxOffset() + getWidth()), (int)(getY() - handler.getCamera().getyOffset()), -getWidth(), getHeight(), null);
		}
		else
		{
			g.drawImage(getImage(), (int)(getX() - handler.getCamera().getxOffset()), (int)(getY() - handler.getCamera().getyOffset()), getWidth(), getHeight(), null);
		}
	}
}

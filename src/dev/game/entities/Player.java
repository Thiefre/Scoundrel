package dev.game.entities;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import dev.game.Handler;
import dev.game.graphics.Animation;
import dev.game.graphics.AnimationSequence;

public class Player extends Character
{
	public String name;
	public String job;
	public boolean attacking;
	public boolean pressed;
	public Animation run = myAnimations.playerRun;
	public Animation idle = myAnimations.playerIdle;
	public AnimationSequence attackSeries = myAnimations.attackSeries;
	public Scanner inp = new Scanner(System.in);
	public Rectangle hitBox = new Rectangle((int)(getX()+getWidth()/2), getY(),100,150);
	
	public Player(Handler handler, int x , int y, int width, int height)
	{
		super(handler, x, y, width, height);
		bounds.x = width/3+16;
		bounds.y = height/2 + 18;
		bounds.width = width/5;
		bounds.height = height/3;
		speed = 8;
		
	}
	public Player()
	{
		super(null, 0,0,0,0);
	}
	
	public boolean isAttacking()
	{
		if(handler.getInputManager().space)
		{
			return true;
		}
		attacking = false;
		return false;
	}
	
	public void setPlayerName()
	{
		System.out.println("What would you like to name your character?");
		name = inp.next();
		this.setName(name);
	}
	
	private void moveTogether()
	{
		if(lookingLeft)
		{
			hitBox.x = (int)(getX());
		}
		if(!lookingLeft)
		{
			hitBox.x = (int)(getX()+getWidth()/2);
		}
		hitBox.x += dx;
		hitBox.y += dy;
		hitBox.y = getY();
	}
	
	private void move(int x, int y)
	{
		if(lookingLeft)
		{
			dx = -x;
			moveX();
		}
		else 
		{
			dx = x;
			moveX();
		}
		dy = y;
		moveY();
	}
	
	private void playerMove()
	{
		move();
		moveTogether();
		handler.getCamera().centerOnEntity(this);
	}	
	public Animation attackSequence()
	{
		Animation attack = attackSeries.get(attackSeries.getCurrentAnimation());
		if(pressed)
		{
			attackSeries.update();
			if(attackSeries.sequenceChange() == false)
			{
				attack.update();
			}
			if(attackSeries.sequenceChange() == true && !isAttacking())
			{
				pressed = false;
				attackSeries.restart();
			}
		}
		if(attackSeries.sequenceChange() || attackSeries.isFirstHit())
		{
			hit(hitBox);
		}
		if(attack == attackSeries.get(2))
		{
			if(lookingLeft)
			{
				move(speed+(speed/4), 0);
			}
			else
			{
				move(speed+(speed/4), 0);
			}
		}
		return attack;
	}
	
	private BufferedImage checks()
	{
		
		BufferedImage checking = idle.getSprite();
		
		if(pressed == true && isAttacking() == false)
		{
			checking = attackSequence().getSprite();
			attacking = true;
		}
		else if(isAttacking() == true)
		{
			checking = attackSequence().getSprite();
			attacking = true;
			pressed = true;
		}
		else if(isMoving() == false)
		{
			idle.update();
			checking = idle.getSprite();
		}
		else if(isMoving() == true)
		{
			run.update();
			checking = run.getSprite();
		}
		return checking;
	}
	
	private void getInput()
	{
		dx = 0;
		dy = 0;
		speed = 8;
		run.setDelay(10);
		if(handler.getInputManager().shift)
		{
			if(attacking == false)
			{
				speed = 11;
				run.setDelay(4);
			}
		}
		if(handler.getInputManager().up)
		{
			if(attacking == true)
			{
				dy = -4;
			}
			else
			{
				dy = -speed;
			}
		}
		if(handler.getInputManager().down)
		{
			if(attacking == true)
			{
				dy = 4;
			}
			else
			{
				dy = speed;
			}
		}
		if(handler.getInputManager().left)
		{
			if(attacking == true)
			{
				lookingLeft = true;
			}
			else
			{
				lookingLeft = true;
				dx = -speed;
			}
		}
		if(handler.getInputManager().right)
		{
			if(attacking == true)
			{
				lookingLeft = false;
			}
			else
			{
				lookingLeft = false;
				dx = speed;
			}
		}
	}
	
	@Override
	public void update() 
	{	
		getInput();
		playerMove();
		loadImage(checks());
	}
	
	@Override
	public void render(Graphics g) 
	{
		if(lookingLeft)
		{
			g.drawImage(getImage(), (int)(getX() - handler.getCamera().getxOffset() + getWidth()), (int)(getY() - handler.getCamera().getyOffset()), -getWidth(), getHeight(), null);
		}
		if(!lookingLeft)
		{
			g.drawImage(getImage(), (int)(getX() - handler.getCamera().getxOffset()), (int)(getY() - handler.getCamera().getyOffset()), getWidth(), getHeight(), null);
		}
	}
	

}

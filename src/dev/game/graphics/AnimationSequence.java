package dev.game.graphics;

import java.util.ArrayList;

public class AnimationSequence extends ArrayList<Animation>
{
	private static final long serialVersionUID = 1L;
	private int totalAnimations;
	private int currentAnimation;
	private boolean stopped;
	private boolean change;
	private int firstAnimation;
	private boolean firstHit;
	
	public AnimationSequence(ArrayList<Animation> sequence)
	{
		for(Animation a : sequence)
		{
			this.add(a);
		}
		totalAnimations = 2;
		currentAnimation = 0;
		stopped = false;
	}
	
	public boolean sequenceChange()
	{
		return change;
	}
	public boolean isFirstHit()
	{
		return firstHit;
	}
	public ArrayList<Animation> getSequence()
	{
		return this;
	}
	
	public void setCurrentAnimation(int currentAnimation)
	{
		this.currentAnimation = currentAnimation;
	}
	
	public int getCurrentAnimation()
	{
		return currentAnimation;
	}
	
	public void restart()
	{
		if(this.size() == 0)
		{
			return;
		}
		stopped = false;
		currentAnimation = 0;
	}
	
	public void stop()
	{
		this.stopped = true;
		this.currentAnimation = 0;
	}
	
	public void pause()
	{
		this.stopped = true;
	}
	
	public void start()
	{
		this.stopped = false;
	}
	
	public void update()
	{
		if(!stopped)
		{
			change = false;
			firstHit = false;
			if(this.get(currentAnimation) == this.get(0) && firstAnimation == 0)
			{
				firstHit = true;
				firstAnimation++;
			}
			if(totalAnimations > currentAnimation)
			{
				if(this.get(currentAnimation).getChange())
				{
					change = true;
					this.get(currentAnimation).setChange(false);
					this.get(currentAnimation).restart();
					currentAnimation++;
				}
			}
			else if(this.get(currentAnimation).getChange() == true)
			{
				change = true;
				this.get(currentAnimation).setChange(false);
				this.get(currentAnimation).restart();
				currentAnimation = 0;
			}

		}
	}
}

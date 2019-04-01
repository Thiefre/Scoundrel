package dev.game.graphics;

import java.awt.image.BufferedImage;
import java.util.*;

public class Animation extends ArrayList<BufferedImage>
{
	private static final long serialVersionUID = 1L;
	private int frameCount;
	private int frameDelay;
	private int currentFrame;
	private int animationDirection;
	private int totalFrames;
	private boolean stopped;
	public boolean change;
	
	
	public Animation(ArrayList<BufferedImage> frames, int frameDelay)
	{
		//Initializing frames
		for(int i = 0; i<frames.size(); i++)
		{
			this.add(frames.get(i));
		}
		this.frameDelay = frameDelay;
		
		//Initializing starting variables
		frameCount = 0;
		currentFrame = 0;
		animationDirection = 1;
		totalFrames = this.size()-1;
		stopped = false;
	}
	
	public ArrayList<BufferedImage> getFrames()
	{
		return this;
	}
	public int getDelay()
	{
		return frameDelay;
	}
	
	public void setCurrentFrame(int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	public void setDelay(int frameDelay)
	{
		this.frameDelay = frameDelay;
	}
	public boolean getChange()
	{
		return change;
	}
	public void setChange(boolean change)
	{
		this.change = change;
	}
	
	public void restart()
	{
		if(this.size() == 0)
		{
			return;
		}
		stopped = false;
		currentFrame = 0;
	}
	public void stop()
	{
		this.stopped = true;
		this.frameCount = 0;
		this.currentFrame = 0;
	}
	
	public BufferedImage getSprite()
	{
		return this.get(currentFrame);
	}
	public void update()
	{
		if (!stopped)
		{
            frameCount++;

            if (frameCount > frameDelay)
            {
                frameCount = 0;
                currentFrame += animationDirection;
                if (currentFrame > totalFrames)
                {
                	change = true;
                    currentFrame = 0;
                }
                else if (currentFrame < 0)
                {
                    currentFrame = totalFrames;
                }
            }
        }
	}
}

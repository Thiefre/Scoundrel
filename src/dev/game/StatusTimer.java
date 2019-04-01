package dev.game;

public class StatusTimer 
{
	private int turns;
	private int currentTurn = 0;
	
	public StatusTimer(int turns)
	{
		this.turns = turns;
	}
	
	public int getDuration()
	{
		return turns - currentTurn;
	}
	
	public void tick()
	{
		if(currentTurn == turns)
		{
			return;
		}
		else currentTurn++;
	}
	
	public void reset()
	{
		currentTurn = 0;
	}
}


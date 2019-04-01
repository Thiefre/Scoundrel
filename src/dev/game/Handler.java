package dev.game;

import dev.game.display.Screen;
import dev.game.graphics.Camera;
import dev.game.input.InputManager;
import dev.game.world.World;

public class Handler 
{
	private GameDriver game;
	private World world;
	
	public Handler(GameDriver game)
	{
		this.game = game;
	}
	public Camera getCamera()
	{
		return game.getCamera();
	}
	
	public InputManager getInputManager()
	{
		return game.getInputManager();
	}
	
	public int getWidth()
	{
		return game.getWidth();
	}
	public int getHeight()
	{
		return game.getHeight();
	}
	
	public GameDriver getGame() 
	{
		return game;
	}

	public void setGame(GameDriver game)
	{
		this.game = game;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world)
	{
		this.world = world;
	}
}

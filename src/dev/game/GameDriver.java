package dev.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import dev.game.display.Screen;
import dev.game.graphics.Camera;
import dev.game.input.InputManager;
import dev.game.state.GameState;
import dev.game.state.MenuState;
import dev.game.state.State;

public class GameDriver implements Runnable
{
	private Thread thread;
	private BufferStrategy bs;
	private Graphics g;
	private Screen screen;
	private boolean running;
	
	public String title;
	private int width;
	private int height;
	public long tick;
	
	
	//States
	private State gameState;
	private State menuState;
    
	//Input
	private InputManager inputManager;
	
	//Camera
	private Camera camera;
	
	//Handler
	private Handler handler;
	
	public GameDriver(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		running = false;
	}

	
	private void init()
	{
		handler = new Handler(this);
		inputManager = new InputManager();
		screen = new Screen(title, width, height);
		screen.getFrame().addKeyListener(inputManager);
		try {
			Assets.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		camera = new Camera(handler,0,0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(gameState);
	}
	
	
	public synchronized void start()
	{
		if(running)
		{
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop()
	{
		if(!running)
		{
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void update()
	{
		inputManager.update();
		if(State.getState() != null)
		{
			State.getState().update();
		}
	}
	
	private void render()
	{
		bs = screen.getFrame().getBufferStrategy();
		if(bs == null)
		{
			screen.getFrame().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		if(State.getState() != null)
		{
			State.getState().render(g);
		}
		bs.show();
		g.dispose();
	}

	@Override
	// Figure out how to create NPC movements
	public void run()
	{
		init();
		long lastTime = System.nanoTime();
		int fps = 60;
		// 1 sec(in nano) has this many fps
		double timePerTick = 1000000000 / fps;
		double timeDiff = 0;
		long now;
		long timer = 0;
		int ticks = 0;
		
		while(running)
		{
			now = System.nanoTime();
			//Difference in time since last nanoTime check divided by timePerTick.
			timeDiff += (now - lastTime)/timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if(timeDiff >= 1)
			{
			update();
			render();
			ticks++;
			timeDiff--;
			}
			if(timer >= 1000000000)
			{
				this.tick = ticks;
				ticks = 0;
				timer = 0;
			}
		}
	}
	
	public GameState getState()
	{
		return (GameState) gameState;
	}
	public InputManager getInputManager()
	{
		return inputManager;
	}
	public Camera getCamera()
	{
		return camera;
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


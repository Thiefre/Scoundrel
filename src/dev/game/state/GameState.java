package dev.game.state;

import java.awt.Graphics;
import java.util.HashSet;

import dev.game.Handler;
import dev.game.entities.Entity;
import dev.game.entities.NPC;
import dev.game.entities.Player;
import dev.game.entities.statics.Tree;
import dev.game.world.World;

public class GameState extends State
{
	private Player player;
	private World world;
	private Tree tree;
	private NPC npc;
	
	private HashSet<Entity> entities;
	
	public GameState(Handler handler)
	{
		super(handler);
		world = new World(handler, "res/worlds/world1.txt");
		handler.setWorld(world);
		player = new Player(handler, 800, 640, 200,148);
		tree = new Tree(handler, 800,640);
		npc = new NPC(handler, 600, 340, 200, 148);
		
		entities = new HashSet<Entity>();
		entities.add(tree);
		entities.add(npc);
	}

	
	public void update() 
	{
		world.update();
		for(Entity e : entities)
		{
			if(e.getHealth() > 0)
			{
				e.update();
			}
		}
		player.update();
	}

	public void render(Graphics g) 
	{
		world.render(g);
		for(Entity e : entities)
		{
			if(e.getHealth() > 0)
			{
				e.render(g);
			}
		}
		player.render(g);
	}
	
	public HashSet<Entity> getEntities()
	{
		return entities;
	}
	public Player getPlayer()
	{
		return player;
	}
}

package dev.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import dev.game.graphics.Animation;
import dev.game.graphics.AnimationSequence;
import dev.game.graphics.SpriteSheet;

public class Assets
{
	public Animation playerIdle, playerRun, playerAttack1, playerAttack2, playerAttack3;
	public AnimationSequence attackSeries;
	public static ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	public static BufferedImage sheet;
	public static SpriteSheet sprites;
	public static BufferedImage tree;
	public static SkillBase dataBase = new SkillBase();
	
	public static void init() throws IOException
	{
		initTiles();
		initStatics();
	}
	
	public static void initStatics() throws IOException
	{
		File treeFile = new File(Assets.class.getResource("/props/generic-rpg-tree02.png").getPath());
		tree = ImageIO.read(treeFile);
	}
	
	public static void initTiles() throws IOException
	{
		//RPG Tiles
		File resources = new File(Assets.class.getResource("/tiles").getPath());
		for(File x : resources.listFiles())
		{
			tiles.add(ImageIO.read(x));
		}
		//Dungeon Tiles
		File readSheet = new File(Assets.class.getResource("/tileset_complet.png").getPath());
		sheet = ImageIO.read(readSheet);
		sprites = new SpriteSheet(sheet);
	}
	
	public void initPlayerAnimations() throws IOException
	{
		ArrayList<BufferedImage> holder = new ArrayList<BufferedImage>();
		
		//Player Animations
		File readRunning = new File(Assets.class.getResource("/sprites/adventurer-running").getPath());
		for(File x : readRunning.listFiles())
		{
			holder.add(ImageIO.read(x));
		}
		playerRun = new Animation(holder, 10);
		holder.clear();
		
		File readIdle = new File(Assets.class.getResource("/sprites/adventurer-idle").getPath());
		for(File x : readIdle.listFiles())
		{
			holder.add(ImageIO.read(x));
		}
		playerIdle = new Animation(holder, 25);
		holder.clear();
		
		File readAttack1 = new File(Assets.class.getResource("/sprites/adventurer-attack").getPath());
		for(File x : readAttack1.listFiles())
		{
			holder.add(ImageIO.read(x));
		}
		playerAttack1 = new Animation(holder, 4);
		holder.clear();
		
		File readAttack2 = new File(Assets.class.getResource("/sprites/adventurer-attack2").getPath());
		for(File x : readAttack2.listFiles())
		{
			holder.add(ImageIO.read(x));
		}
		playerAttack2 = new Animation(holder, 1);
		holder.clear();
		
		File readAttack3 = new File(Assets.class.getResource("/sprites/adventurer-attack3").getPath());
		for(File x : readAttack3.listFiles())
		{
			holder.add(ImageIO.read(x));
		}
		playerAttack3 = new Animation(holder, 3);
		holder.clear();
		attackSeries = new AnimationSequence(new ArrayList<Animation>(Arrays.asList(playerAttack1,playerAttack2,playerAttack3)));
	}
	
}

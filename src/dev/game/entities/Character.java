package dev.game.entities;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import dev.game.Status;
import dev.game.Assets;
import dev.game.Handler;
import dev.game.Skill;
import dev.game.tiles.Tile;

public class Character extends Entity
{

	protected String job;
	protected int baseStat;
	protected String name;
	protected int[] stats = new int[5];
	protected boolean learned;
	protected int level = 1;
	protected int exp = 0;
	protected Random xprandom = new Random();
	protected int currentxp;
	protected ArrayList<Integer> xpTable = new ArrayList<Integer>();
	protected Random test = new Random();
	protected Assets myAnimations;

	protected float movementSpeed = 4;
	protected float dx, dy;
	
	private ArrayList<Skill> moveSet = new ArrayList<Skill>();
	private ArrayList<Status> statusEffects = new ArrayList<Status>();
	
	//Status effects
	
	public boolean moving;
	public boolean pressing = false;
	public boolean lookingLeft= false;
	
	public int speed = 5;

	//Do max health initialization on level up/stat change!!!
	public Character(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x,y,width, height);
		myAnimations = new Assets();
		try {
			myAnimations.initPlayerAnimations();
		} catch (IOException e) {
			e.printStackTrace();
		}
		chealth = 100;
		mhealth = 100;
		
		xpTable.add(3);
		xpTable.add(5);
		for( int i = 2; i<=50; i++)
		{
			exp = (xpTable.get(i-1)+4)+xpTable.get(i-2)/3;
			xpTable.add(exp);
		}
	}
	
	public boolean isMoving()
	{
		if(dx != 0 || dy != 0)
		{
			moving = true;
		}
		else
		{
			moving = false;
		}
		return moving;
	}
	
	public void move()
	{
		moveX();
		moveY();

	}
	
	public void moveX()
	{
		if(dx > 0)
		{
			int tx = (int) (x + dx + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y+bounds.y+bounds.height)/Tile.TILEHEIGHT))
			{
				x+=dx;
			}
			else
			{
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
		}
		else if(dx < 0)
		{
			int tx = (int) (x + dx + bounds.x) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx, (int) (y+bounds.y)/Tile.TILEHEIGHT) && !collisionWithTile(tx, (int) (y+bounds.y+bounds.height)/Tile.TILEHEIGHT))
			{
				x+=dx;
			}
			else
			{
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}
	}
	public void moveY()
	{
		if(dy < 0)
		{
			int ty = (int) (y+dy+bounds.y)/Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x+bounds.x)/Tile.TILEHEIGHT, ty) && !collisionWithTile((int) (x+bounds.x+bounds.width)/Tile.TILEHEIGHT, ty) )
			{
				y += dy;
			}
			else
			{
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		}
		if(dy > 0)
		{
			int ty = (int) (y+dy+bounds.y+bounds.height)/Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x+bounds.x)/Tile.TILEHEIGHT, ty) && !collisionWithTile((int) (x+bounds.x+bounds.width)/Tile.TILEHEIGHT, ty) )
			{
				y += dy;
			}
			else
			{
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y)
	{
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public void setDy(float dy)
	{
		this.dy = dy;
	}
	public void setDx(float dx)
	{
		this.dx = dx;
	}
	public float getDy()
	{
		return dy;
	}
	
	public float getDx()
	{
		return dx;
	}
	
	public float getSpeed()
	{
		return movementSpeed;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	public String getName()
	{
		return "[LV "+level+"]" + name;
	}
	public Character getCharacter()
	{
		
		System.out.println("This character is a " + job);
		return this;
	}
	public int[] getStats()
	{
		return stats;
	}
	
	public int healthScaling()
	{
		mhealth = mhealth+(stats[0]*2);
		chealth = mhealth;
		return mhealth;
	}
	public int[] increaseStats(int str, int dex, int intel, int luk, int speed)
	{
		stats[0]+=str;
		stats[1]+=dex;
		stats[2]+=intel;
		stats[3]+=luk;
		stats[4]+=speed;
		this.healthScaling();
		return stats;
	}
	public int[] decreaseStats(int str, int dex, int intel, int luk, int speed)
	{
		stats[0]-=str;
		stats[1]-=dex;
		stats[2]-=intel;
		stats[3]-=luk;
		stats[4]-=speed;
		this.healthScaling();
		return stats;
	}
	
	public int[] setStats(int str, int dex, int intel, int luk, int speed)
	{
		stats[0]=str;
		stats[1]=dex;
		stats[2]=intel;
		stats[3]=luk;
		stats[4]=speed;
		this.healthScaling();
		return stats;
	}
	
	public int getLevel()
	{
		return level;
	}
	public void gainXP(Character c)
	{
		currentxp+= (xprandom.nextInt(2)+3)*(c.getLevel()+this.getLevel());
		System.out.println("You have gained "+currentxp+" experience!");
		checkXP();
	}
	public void checkXP()
	{
		System.out.println("You have "+ currentxp+"/"+ xpTable.get(level-1)+" EXP");
	}
	public boolean levelUp()
	{
		while(xpTable.get(level-1) <= currentxp)
		{
			level++;
			System.out.println("You have leveled up to LV"+ level+ "!");
			this.increaseStats(1, 1, 1, 1, 1);
			currentxp = currentxp - xpTable.get(level-2);
			if(currentxp<xpTable.get(level-2))
			{
			checkXP();
			}
			healthScaling();
		}
		return true;
	}
	
	public boolean battle(Character c, Character e)
	{
		if(c.getHealth()>0 && e.getHealth()>0)
		{
		return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean wasDamaged()
	{
		return damageTaken;
	}
	
	public void learnSkill(Skill skill)
	{
		moveSet.add(skill);
		System.out.println("You have learned "+ skill.getName()+"!");
	}

	public ArrayList<Skill> getSkills()
	{
		return moveSet;
	}
	
	public ArrayList<Status> getStatus()
	{
		return statusEffects;
	}
	
	public boolean isDisabled()
	{
		if(statusEffects.contains(Status.SLEEP))
		{
			return true;
		}
		else return false;
	}
	
	public boolean hasSkill(Skill skill)
	{
		for(Skill s : moveSet)
		{
			if(skill == s)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isPlayer()
	{			
		if(this instanceof Player)
		{
		return true;
		}
		else return false;
		
		}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
	}
}

/*
 * 	public int useSkill(String skill, Character c)
	{
		abilityUsed = false;
		damage = 0;
		//OP Skill
		if(this.getThis().skillLearned("potato") && skill.equalsIgnoreCase("potato"))
		{
			damage = 999999999;
			c.endStatus();
			System.out.println("You find a potato emanating a divine presence. You throw the holy potato, dealing "+ damage+" damage. "+ c.getName()+" is completely destroyed!");
		}
		//Backstab //FIX THIS (DMG SCALING AND RANGE)
		else if(this.getThis().skillLearned("Backstab") && skill.equalsIgnoreCase("Backstab"))
		{
			if(c.dodge == false)
			{
			damage = 10;
			c.bleed = true;
			c.bleedDamage = 5;
			}
			combo = true;
			abilityUsed = true;
		}
		//Sleep Powder
		else if(this.getThis().skillLearned("Sleep") && skill.equalsIgnoreCase("Sleep"))
		{
			if(c.sleep == false)
			{
					c.sleep = true;
					System.out.println(getName()+" gags his enemy with a cloth of Sleeping Powder!");
			}
			else if(c.sleepTimer<=2)
			{
				System.out.println(getName()+ " is already under the effects of Sleep Powder!");
			}
			abilityUsed = true;
		}
		//Agility
		else if(this.getThis().skillLearned("Agility") && skill.equalsIgnoreCase("Agility"))
		{
			
			if(agility == false)
			{
					increaseStats(0, 0, 0, 2, 5);
					System.out.println(getName()+" feels the effects of Agility coursing through his veins.");
					agility = true;
			}
			else if(timer <=2)
			{
				System.out.println(getName()+" has already used the effects of Agility!");
			}
			abilityUsed = true;
		}
		//Punch
		else if(this.getThis().skillLearned("Punch") && skill.equalsIgnoreCase("Punch"))
		{
			if(c.dodge == false)
			{
			damage = 10;
			}
			abilityUsed = true;
		}
		//Fireball
		else if(this.getThis().skillLearned("Fireball")&& skill.equalsIgnoreCase("Fireball"))
		{
			if(c.dodge == false)
			{
			damage = 10;
			if(test.nextInt(3)==1)
			{
				c.burning = true;
				c.burnDmg = 6;
			}
			}
			abilityUsed = true;
		}
		//Eviscerate
		else if(this.getThis().skillLearned("Eviscerate")&& skill.equalsIgnoreCase("Eviscerate"))
		{
			if(c.dodge == false)
			{
			if(combo == true)
			{
				damage = 30;
			}
			else
			{
				damage = 15;
			}
			}
			abilityUsed = true;
			combo = false;
		}
		//Stealth
		else if(this.getThis().skillLearned("Stealth")&& skill.equalsIgnoreCase("Stealth"))
		{
			if(stealth == false)
			{
				stealth = true;
				dodge = true;
				System.out.println(getName() +" slowly blends in with the surroundings, hiding from plain sight");
			}
			else if(stealth == true)
			{
				System.out.println(getName() +" is already in stealth!");
			}
		}
		else 
		{
			System.out.println(getName() +" doesn't know that skill!");
		}
		
		//Stealth/Dodge effects
		if(c.stealth == true)
		{
			if(c.stealth == true && c.damageTaken == true)
			{
					damage = 0;
					endStatus();
				
			}
		}
		if(stealth == true)
		{
			stealthTimer++;
			if(stealthTimer>2)
			{
				System.out.println("Stealth wears off");
				stealth = false;
				dodge = false;
				stealthTimer = 0;
			}
			if(abilityUsed == true)
			{
				System.out.println(getName()+" attacks "+ c.getName()+" from the shadows!");
				stealth = false;
				dodge = false;
				stealthTimer = 0;
			}
		}
		if(c.sleep == true)
		{
			if(c.damageTaken == true)
			{
				if(c.bleed == true)
				{
					System.out.println(c.getName()+" woke up from bleed!");
				}
				else
				{
				System.out.println(c.getName()+" woke up from the attack!");
				}
				c.sleep = false;
				c.sleepTimer = 0;
			}
		}
		
		
		//Text usage
		
		//Skills affected by dodge
		if(c.dodge == false)
		{
			if(this.getThis().skillLearned("Backstab") && skill.equalsIgnoreCase("Backstab"))
			{
				System.out.println( getName()+" stabs his enemy in the back and deals " + damage + " damage and bleeds his enemy!");
			}	
			
			else if(this.getThis().skillLearned("Punch") && skill.equalsIgnoreCase("Punch"))
			{
				System.out.println( getName()+" dealt " + damage + " damage!");
			}
			
			else if(this.getThis().skillLearned("Fireball")&& skill.equalsIgnoreCase("Fireball"))
			{
				System.out.println(getName()+" shoots a fiery ball of flame towards "+ c.getName() + " for "+damage+" damage!");
				if (burning == true)
				{
					System.out.println(c.getName() +" burns after the fiery attack!");
				}
			}
			else if(this.getThis().skillLearned("Eviscerate") && skill.equalsIgnoreCase("Eviscerate"))
			{
				System.out.println(getName()+" uses Eviscerate and deals "+ damage+" damage!");
			}
		}
		
		
		if(c.dodge == true)
		{
			System.out.println(c.getName()+" dodges "+skill+" due to the effects of stealth!");
		}
		
		return damage;
	}
 * 
 */

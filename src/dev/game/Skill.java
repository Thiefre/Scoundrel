package dev.game;

import java.util.ArrayList;
import java.util.Arrays;

public class Skill implements Comparable<Skill>
{
	private int id;
	private Type type;
	private String name;
	private int damage = 0;
	private ArrayList<Status> effects = new ArrayList<Status>();
	
	Skill(int id, Type type, String name)
	{
		this.id = id;
		this.type = type;
		this.name = name;
	}
	
	public int getID()
	{
		return id;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public ArrayList<Status> getEffects()
	{
		return effects;
	}
	
	public void initAttack(int damage, ArrayList<Status> effects)
	{
		if(type == Type.ATTACK)
		{
			this.damage = damage;
			this.effects = effects;
		}
	}
	public void initBuffs(ArrayList<Status> effects)
	{
		if(type == Type.BUFF)
		{
			this.effects = effects;
		}
	}
	public void initDebuffs(ArrayList<Status> effects)
	{
		if(type == Type.DEBUFF)
		{
			this.effects = effects;
		}
	}
	
	public boolean equals(Object o)
	{
		Skill that = (Skill) o;
		if(this.compareTo(that) == 0)
		{
			return true;
		}
		return false;
	}
	
	public int compareTo(Skill that)
	{
		if(id == that.id)
		{
			return 0;
		}
		else return id - that.id;
	}
}

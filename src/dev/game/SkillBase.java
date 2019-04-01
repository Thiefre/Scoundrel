package dev.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import dev.game.entities.Character;
import dev.game.graphics.Animation;
import dev.game.graphics.AnimationSequence;

public class SkillBase extends ArrayList<Skill>
{
	
	private int damage = 0;
	
	StatusTimer bleedTimer = new StatusTimer(3);
	StatusTimer burnTimer = new StatusTimer(3);
	StatusTimer sleepTimer = new StatusTimer(4);
	StatusTimer agilityTimer = new StatusTimer(4);
	StatusTimer stealthTimer = new StatusTimer(3);
	
	public SkillBase()
	{		
		initSkills();
	}
	
	public void initSkills()
	{
		add(new Skill(0, Type.ATTACK, "Potato"));
		add(new Skill(1, Type.ATTACK, "Backstab"));
		add(new Skill(2, Type.ATTACK, "Punch"));
		add(new Skill(3, Type.ATTACK, "Fireball"));
		add(new Skill(4, Type.DEBUFF, "Sleep"));
		add(new Skill(5, Type.BUFF, "Agility"));
		add(new Skill(6, Type.ATTACK, "Eviscerate"));
		add(new Skill(7, Type.BUFF, "Stealth"));
		this.get(0).initAttack(9999, null);
		this.get(1).initAttack(10, new ArrayList<Status>(Arrays.asList(Status.BLEED)));
		this.get(2).initAttack(4, null);
		this.get(3).initAttack(7, new ArrayList<Status>(Arrays.asList(Status.BURN)));
		this.get(4).initDebuffs(new ArrayList<Status>(Arrays.asList(Status.SLEEP)));
		this.get(5).initBuffs(new ArrayList<Status>(Arrays.asList(Status.AGILITY)));
		this.get(6).initAttack(20,new ArrayList<Status>(Arrays.asList(Status.COMBO)));
		this.get(7).initBuffs(new ArrayList<Status>(Arrays.asList(Status.STEALTH)));
		
	}
	
	public void applyEffects(Character c)
	{
		if(c.getStatus().contains(Status.BLEED))
		{
			bleedTimer.tick();
			if(bleedTimer.getDuration() == 0)
			{
				System.out.println("Stopped bleeding");
				bleedTimer.reset();
				c.getStatus().remove(Status.BLEED);
			}
			else
			{
				c.takeDamage(5);
				System.out.println(c.getName()+" bleeds for "+ 5+ " damage.");
			}
		}
		if(c.getStatus().contains(Status.BURN))
		{
			burnTimer.tick();
			if(burnTimer.getDuration() == 0)
			{
				System.out.println("Stopped burning");
				burnTimer.reset();
				c.getStatus().remove(Status.BURN);
			}
			else
			{
				System.out.println(c.getName()+" burns for" + 5 +" damage.");
				c.takeDamage(5);
			}
		}
		if(c.getStatus().contains(Status.SLEEP))
		{
			sleepTimer.tick();
			if(sleepTimer.getDuration() == 0 || c.wasDamaged())
			{
				System.out.println("Sleep Powder has worn off of"+ c.getName() +"!");
				c.getStatus().remove(Status.SLEEP);
				sleepTimer.reset();
			}
			else
			{
				System.out.println(c.getName()+" is sleeping");
			}
		}
		
	}
	
	public int useSkill(Skill skill, Character c, Character e)
	{
		damage = 0;
		if(c.hasSkill(skill) && skill.getType() == Type.ATTACK)
		{
			damage = skill.getDamage();
			if(skill.getEffects() != null)
			{
				for(Status s : skill.getEffects())
				{
					e.getStatus().add(s);
				}
			}
		}
		else if(c.hasSkill(skill) && skill.getType() == Type.BUFF)
		{
			if(skill.getEffects() != null)
			{
				for(Status s : skill.getEffects())
				{
					c.getStatus().add(s);
				}
			}
		}
		else if(c.hasSkill(skill) && skill.getType() == Type.DEBUFF)
		{
			if(skill.getEffects() != null)
			{
				for(Status s : skill.getEffects())
				{
					e.getStatus().add(s);
				}
			}
		}
		else
		{
			System.out.println("You don't know that skill!");
		}
		if(skill != null)
		{
			System.out.println(c.getName()+" used "+ skill.getName()+"!");
		}
		if(damage > 0)
		{
			System.out.println(c.getName()+" dealt "+ damage+" damage!");
		}
		return damage;
			
	}
}

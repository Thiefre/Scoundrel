package dev.game;
import java.util.Scanner;

import dev.game.entities.NPC;
import dev.game.entities.Player;

import java.util.ArrayList;
import java.util.Random;
public class Game 
{
	ArrayList<Scanner> z = new ArrayList<Scanner>();
	public String job;
	public String name;
	public boolean battleIndicator = false;
	Player real;
	public boolean gameover = false;
	Random ai = new Random();
	public Skill aiSkill;
	Random random = new Random();
	public String text;
	public int turn = 1;
	public int tempHP;
	public int tempEHP;
	public Skill skill;
	
	
	public Game()
	{
		GameDriver driver = new GameDriver("Scoundrel", 1280, 720);
		driver.start();
		try {
			adventure();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void fight(NPC n) throws InterruptedException
	{
		System.out.println("What would you like to do?\nFight or flee?"); //Fight entry
		text = z.get(0).next();
		battleIndicator = false;
		while(battleIndicator == false)
		{
		if(text.equalsIgnoreCase("Fight"))
		{
		while(real.battle(real, n) == true) //Start of battle sequence; Keeps the fight looping while either side has more than 0 health
		{
			System.out.println("[Turn "+ turn+"]"); //Start of turn, checks speed, and allows one to choose skills
			if(real.getStats()[4] >= n.getStats()[4])
			{
				if(real.isDisabled() == true)
				{
						Thread.sleep(1500);
				}
				else {
					System.out.println("Choose a skill:");
					System.out.print("[");
					for(Skill s : real.getSkills())
					{
						if(real.getSkills().indexOf(s) == real.getSkills().size()-1)
						{
							System.out.println(s.getName()+"]");
						}
						else System.out.print(s.getName()+", ");
					}
					text = z.get(0).next();
					for(Skill s : real.getSkills())
					{
						if(text.equalsIgnoreCase(s.getName()))
						{
							skill = s;
						}
					}
					n.takeDamage(Assets.dataBase.useSkill(skill, real, n));
					 
				}
			if(n.getHealth()>0)
			{
				if(n.isDisabled() == true) //Checks for crowd control effects
				{
						Thread.sleep(1500);
				}
				else
				{
					if(n.getSkills().size() == 1)
					{
						aiSkill = n.getSkills().get(0);
					}
					else
					{
					aiSkill = n.getSkills().get(ai.nextInt(n.getSkills().size()));
					}
					
					real.takeDamage(Assets.dataBase.useSkill(aiSkill, n, real));
					  
				}
			}
			if(real.getHealth()<0) //Death sequence
			{
				System.out.println("You have died! Game over!");
				System.exit(1);
			}
			if(n.getHealth()<0) //Enemy death sequence
			{
				System.out.println("You have defeated "+n.getName()+"!");
				real.gainXP(n);
				real.levelUp();
				break;
			}
			}
			else if(real.getStats()[4]<n.getStats()[4]) //Change in fight sequence when speed of enemy is higher
			{
				if(n.getHealth()>0)
				{
					if(n.isDisabled() == true)
					{
							Thread.sleep(1500);
					}
					else
					{
						if(n.getSkills().size() == 1)
						{
							aiSkill = n.getSkills().get(0);
						}
						else
						{
						aiSkill = n.getSkills().get(ai.nextInt(n.getSkills().size()));
						}
						
						real.takeDamage(Assets.dataBase.useSkill(aiSkill,n,real));
						  
					}
				}
				System.out.println("You have "+ real.getHealth()+"/"+real.getMaxHealth()+" health");
				if(real.getHealth()<0)
				{
					System.out.println("You have died! Game over!");
					System.exit(1);
				}
				if(real.isDisabled()==true)
				{
						Thread.sleep(1500);
				}
				else
				{
					System.out.println("Choose a skill:\n");
					System.out.print("[");
					for(Skill s : real.getSkills())
					{
						if(real.getSkills().indexOf(s) == real.getSkills().size()-1)
						{
							System.out.println(s.getName()+"]");
						}
						else System.out.print(s.getName()+", ");
					}
					text = z.get(0).next();
					for(Skill s : real.getSkills())
					{
						if(text.equalsIgnoreCase(s.getName()))
						{
							skill = s;
						}
					}
					n.takeDamage(Assets.dataBase.useSkill(skill, real, n));
					 
				}
				if(n.getHealth()<0)
				{
					System.out.println("You have defeated "+n.getName()+"!");
					real.gainXP(n);
					real.levelUp();
					break;
				}
			}
			turn++;
			Assets.dataBase.applyEffects(real);
			Assets.dataBase.applyEffects(n);
			System.out.println("You have "+ real.getHealth()+"/"+real.getMaxHealth()+" health");
			System.out.println("The enemy has "+ n.getHealth()+"/"+n.getMaxHealth()+" health");
			 
		}
		battleIndicator = true;
		}
		else if(text.equalsIgnoreCase("Flee")) //Fleeing sequence
		{
			if(real.getStats()[4]>n.getStats()[4]+3) //Fleeing success
			{
				battleIndicator = true;
				System.out.println("You have succesfully fled!");
			}
			else
			{
				System.out.println("You are unable to flee! Stand and fight!"); //Fleeing failed
				aiSkill = n.getSkills().get(ai.nextInt(n.getSkills().size()));
				
				real.takeDamage(Assets.dataBase.useSkill(aiSkill,n,real));
				  
				System.out.println("You have "+ real.getHealth()+"/"+real.getMaxHealth()+" health");
				while(real.battle(real, n) == true) //Start of battle sequence (Same as before)
				{
					if(real.getStats()[4] >= n.getStats()[4])
					{
						if(real.isDisabled()==true)
						{
								Thread.sleep(1500);
						}
						else
						{
							System.out.println("[Turn "+ turn+"]");
							System.out.println("Choose a skill:\n");
							System.out.print("[");
							for(Skill s : real.getSkills())
							{
								if(real.getSkills().indexOf(s) == real.getSkills().size()-1)
								{
									System.out.println(s.getName()+"]");
								}
								else System.out.print(s.getName()+", ");
							}
							text = z.get(0).next();
							for(Skill s : real.getSkills())
							{
								if(text.equalsIgnoreCase(s.getName()))
								{
									skill = s;
								}
							}
							n.takeDamage(Assets.dataBase.useSkill(skill, real, n));
							 
						}
					if(n.getHealth()>0)
					{
						if(n.isDisabled() == true)
						{
								Thread.sleep(1500);
						}
						else
						{
							if(n.getSkills().size() == 1)
							{
								aiSkill = n.getSkills().get(0);
							}
							else
							{
							aiSkill = n.getSkills().get(ai.nextInt(n.getSkills().size()));
							}
							
							real.takeDamage(Assets.dataBase.useSkill(aiSkill,n, real));
							  
						}
					}
					
					if(real.getHealth()<0)
					{
						System.out.println("You have died! Game over!");
						System.exit(1);
					}
					if(n.getHealth()<0)
					{
						System.out.println("You have defeated "+n.getName()+"!");
						real.gainXP(n);
						real.levelUp();
						break;
					}
					}
					else if(real.getStats()[4]<n.getStats()[4])
					{
						if(n.getHealth()>0)
						{
						System.out.println("[Turn "+ turn+"]");
						if(n.isDisabled() == true)
						{
								Thread.sleep(1500);
						}
						else
						{
							if(n.getSkills().size() == 1)
							{
								aiSkill = n.getSkills().get(0);
							}
							else
							{
							aiSkill = n.getSkills().get(ai.nextInt(n.getSkills().size()));
							}
							
							real.takeDamage(Assets.dataBase.useSkill(aiSkill,n, real));
							  
						}
						}
						System.out.println("You have "+ real.getHealth()+"/"+real.getMaxHealth()+" health");
						if(real.getHealth()<0)
						{
							System.out.println("You have died! Game over!");
							System.exit(1);
						}
						if(real.isDisabled()==true)
						{
								Thread.sleep(1500);
						}
						else
						{
							System.out.println("Choose a skill:\n");
							System.out.print("[");
							for(Skill s : real.getSkills())
							{
								if(real.getSkills().indexOf(s) == real.getSkills().size()-1)
								{
									System.out.println(s.getName()+"]");
								}
								else System.out.print(s.getName()+", ");
							}
							text = z.get(0).next();
							for(Skill s : real.getSkills())
							{
								if(text.equalsIgnoreCase(s.getName()))
								{
									skill = s;
								}
							}
							n.takeDamage(Assets.dataBase.useSkill(skill, real, n));
							  
						}
						if(n.getHealth()<0)
						{
							System.out.println("You have defeated "+n.getName()+"!");
							real.gainXP(n);
							real.levelUp();
							break;
						}
					}
					turn++;
					Assets.dataBase.applyEffects(real);
					Assets.dataBase.applyEffects(n);
					System.out.println("You have "+ real.getHealth()+"/"+real.getMaxHealth()+" health");
					System.out.println("The enemy has "+ n.getHealth()+"/"+n.getMaxHealth()+" health");
					 
				}
				battleIndicator = true;
			}
		}
		else
		{
			System.out.println("You must choose fight or flee!"); //Input other than fight or flee
			text = z.get(0).next();
		}
		}
		turn = 1;
			Thread.sleep(1000);
	}
	public void adventure() throws InterruptedException
	{

		z.add(new Scanner(System.in));
		System.out.println("Welcome to Scoundrel(NAME WIP)!\nYou can choose between 4 classes:\nThief, Mage, Warrior, and Archer.\nWhat will you choose? ");
		while(battleIndicator == false)
		{
		job = z.get(0).next();
		if(job.equalsIgnoreCase("Thief"))
		{
			Player meme = new Player();
			real = meme;
			real.setStats(3, 5, 4, 7, 8);
			real.learnSkill(Assets.dataBase.get(1));
			battleIndicator = true;
		}
		else if(job.equalsIgnoreCase("Mage"))
		{
			Player mem = new Player();
			real = mem;
			real.setStats(2, 4, 8, 5, 5);
			real.learnSkill(Assets.dataBase.get(3));

			battleIndicator = true;
		}
		else if(job.equalsIgnoreCase("Warrior"))
		{
			Player mee = new Player();
			real = mee;
			battleIndicator = true;
		}
		else if(job.equalsIgnoreCase("Archer"))
		{
			Player mme = new Player();
			real = mme;
			battleIndicator = true;
		}
		else
		{
			System.out.println("Character not made, class not available!");


		}
		real.setPlayerName();
		}
		
		System.out.println("Hello "+ real.getName()+ ", welcome to Vexed!");

		System.out.println("Your journey will now begin.");
		real.learnSkill(Assets.dataBase.get(0));
		real.learnSkill(Assets.dataBase.get(4));

		System.out.println("A small light in the center of a darkness begins to intensify.");

		System.out.println("You wake up and take in a light breath of air.");

		System.out.println("The air feels unusually moist, damp even.");

		System.out.println("You open your eyes to take in your surroundings, finding youself alone in a small hut.");

		System.out.println("Suddenly, the door is being furiously slammed on.");

		System.out.println("A mysterious hooded figure, wielding a dagger, kicks the door open.");
		NPC howard = new NPC("Howard");
		howard.learnSkill(Assets.dataBase.get(2));
		System.out.println("You grab a small steak knife lying at your bedside from last night's dinner.");

		System.out.println("You yell and question the hooded figure, to find no response.");

		System.out.println("The figure takes a few steps back as he suddenly disappears from sight.");
		System.out.println("You enter a fighting stance, slightly panicked.");
		this.fight(howard);

	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
	}
}


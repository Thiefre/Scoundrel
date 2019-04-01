package dev.game.entities.statics;

import dev.game.Assets;
import dev.game.Handler;
import dev.game.entities.Entity;
import dev.game.graphics.Animation;

public abstract class StaticEntity extends Entity
{
	public StaticEntity(Handler handler, float x, float y, int width, int height)
	{
		super(handler, x, y, width, height);
	}
	
}

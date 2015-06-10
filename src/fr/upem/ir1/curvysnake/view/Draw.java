package fr.upem.ir1.curvysnake.view;

import java.awt.Color;
import java.awt.geom.RectangularShape;

import fr.umlv.zen5.ApplicationContext;
import fr.upem.ir1.curvysnake.controller.Bonus;
import fr.upem.ir1.curvysnake.controller.BonusAvailable;
import fr.upem.ir1.curvysnake.controller.Entry;

public final class Draw {
	
	public static ApplicationContext context;
		
	public static void draw(RectangularShape body) {

		context.renderFrame(graphics -> {
			graphics.setColor(Color.GREEN);
			graphics.fill(body);
		});

	}
	public static void undraw(RectangularShape queu){

		if(queu==null){
			return;
		}
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.fill(queu);
		});
	}
	public static void drawBonus(Entry<RectangularShape,Bonus> bonus){
		if(bonus==null){
			return;
		}
		if(bonus.getValue().isA(BonusAvailable.NEXT_HOPE))
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLUE);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.SPEED_INCREASE))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.YELLOW);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.SPEED_DECREASE))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.ORANGE);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.INVERSE_DIRECTION))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.SIZE_DECREASE))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.PINK);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.SIZE_INCREASE))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.CYAN);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.ERASE_ALL))
			context.renderFrame(graphics -> {
			graphics.setColor(Color.GRAY);
			graphics.fill(bonus.getKey());
		});
		else if(bonus.getValue().isA(BonusAvailable.WALL_THROUGH))
			context.renderFrame(graphics -> {
				graphics.setColor(Color.MAGENTA);
				graphics.fill(bonus.getKey());
			});
		else{
			context.renderFrame(graphics -> {
				graphics.setColor(Color.RED);
				graphics.fill(bonus.getKey());
			});	
		}
	}

}

package com.po;

import java.awt.*;
import java.util.Random;
/**
 * 血块类
 * @author lancer
 *
 */
public class BloodFly {
	private int x;
	private int y;
	private int w;
	private int h;
	
	private TankClient tc;
	
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}

	int step = 0;
	/*
	 * 随机位置生成抖动的血块
	 */
	Random r = new Random();
	int q = r.nextInt(700) + 50;
	int z = r.nextInt(500) + 50;
	private int[][] position = {
				{q, z},	{q+1, z+1},	{q+2, z+2},	{q+3, z+3},	{q+4, z+4},	{q+5, z+5},	
				{q+4, z+5},	{q+3, z+5},	{q+2, z+5},	{q+1, z+5},	{q, z+5}, 
				{q, z+4}, {q, z+3}, {q, z+2}, {q, z+1}
								};
	
	public BloodFly() {
		x = position[0][0];
		y = position[0][1];
		w =15;
		h =15;
		
	}


	public void draw(Graphics g) {
		
		Random r = new Random();
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillOval(x, y, w, h);
		g.setColor(c);
		
		move();
	}


	private void move() {
		step++;
		if(step == position.length) {
			step = 0;
		}
		x = position[step][0];
		y = position[step][1];
	}
	
	
	public boolean eatByTank(Tank t) {
		if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
			t.setBlood(100);
			new Audio(2);
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	public boolean hide(Obstacle obj) {
		if(this.isLive() && obj.isLive() && this.getRect().intersects(obj.getRect())) {
			return true;
		} 
		return false;
	}
	


	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
}

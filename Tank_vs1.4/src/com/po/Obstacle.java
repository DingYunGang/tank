package com.po;

import java.awt.Rectangle;

public class Obstacle {
	int x;
	int y;
	
	
	protected boolean live = true;
	public static final int WIDTH = 15;
	public static final int HEIGHT = 15;
	private TankClient tc;
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y,WIDTH, HEIGHT);
	}
}

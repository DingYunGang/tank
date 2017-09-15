package com.po;

import java.awt.*;
/**
 * ºÓÁ÷Àà
 * @author lancer
 *
 */
public class Home {
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

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	public Home(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(this.isLive() && this.tc.myTank.isLive())
		g.drawImage(tk.getImage(Home.class.getClassLoader().getResource(
				"images/home.jpg")), x, y, null);
	}
	
	public boolean shootByMissile(Missile m) {
		if(this.isLive() && m.isLive() && this.getRect().intersects(m.getRect())) {
			m.setLive(false);
			this.setLive(false);
			return true;
		}
		return false;
	
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
		
	}
	
}

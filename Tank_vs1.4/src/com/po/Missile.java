 package com.po;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.po.Tank.Direction;
/**
 * 子弹类
 * @author lancer
 *
 */
public class Missile {
	
	private int x;
	private int y;
	
	public static final int XSTEEP = 15;
	public static final int YSTEEP = 15;
	
	public static final int MISSILE_WIDTH = 10;
	public static final int MISSILE_HEIGHT = 10;
	
	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}




	private boolean good;
	
	Direction dir;
	private TankClient tc;
	
	
private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	private static Image[] tankImages = null;
	static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//静态代码区，最先执行，用于初始化变量
	static {
		//class被调用时首先被执行（static）
		tankImages = new Image[] {
		 
						//通过classpath找到image的路径
						tk.getImage(Missile.class.getClassLoader().getResource("images/bulletL.gif")),
//						tk.getImage(Tank.class.getClassLoader().getResource("images/bulletLU.gif")),
						tk.getImage(Missile.class.getClassLoader().getResource("images/bulletU.gif")),
//						tk.getImage(Tank.class.getClassLoader().getResource("images/bulletRU.gif")),
						tk.getImage(Missile.class.getClassLoader().getResource("images/bulletR.gif")),
//						tk.getImage(Tank.class.getClassLoader().getResource("images/bulletRD.gif")),
						tk.getImage(Missile.class.getClassLoader().getResource("images/bulletD.gif")),
//						tk.getImage(Tank.class.getClassLoader().getResource("images/bulletLD.gif")),
//						tk.getImage(Tank.class.getClassLoader().getResource("images/bulletU.gif"))
						
		};
		
		imgs.put("L", tankImages[0]);
//		imgs.put("LU", tankImages[1]);
		imgs.put("U", tankImages[1]);
//		imgs.put("RU", tankImages[3]);
		imgs.put("R", tankImages[2]);
//		imgs.put("RD", tankImages[5]);
		imgs.put("D", tankImages[3]);
//		imgs.put("LD", tankImages[7]);
//		imgs.put("STOP", tankImages[2]);
		
	}
	
	
	
	
	
	public Missile(int x, int y,Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	public boolean isLive() {
		return live;
	}

	public void draw(Graphics g){
		
		if(!live) {
			tc.missiles.remove(this);
			return;
		}

		
		switch(dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y+5, null);
			break;

		case U:
			g.drawImage(imgs.get("U"), x+2, y, null);
			break;

		case R:
			g.drawImage(imgs.get("R"), x, y+5, null);
			break;

		case D:
			g.drawImage(imgs.get("D"), x+1, y, null);
			break;

		case STOP:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
			
		}
		
		
		move();
		
	}
	/**
	 * 子弹移动方法
	 */
	private void move() {
		switch (dir) {
		case L:
			x -= XSTEEP;
			break;
		case R:
			x += XSTEEP;
			break;
		case U:
			y -= YSTEEP;
			break;
		case D:
			y += YSTEEP;
			break;

		}
		/*
		 * 判断子弹出界，出界则设为false
		 */
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
			live = false;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
	}
	/**
	 * 子弹撞击坦克
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
//			new Audio(3);
			if(t.isGood()) {
				
				t.setBlood(t.getBlood() - 10);
				if(t.getBlood() <= 0)  { new Audio(3);  t.setLive(false); }
			}
			else {
				new Audio(1);
				t.setLive(false);
			}
			
			Boom boom = new Boom(x, y, tc);
			tc.booms.add(boom);
			return true;
		} 
		return false;
	}
	/**
	 * 敌方子弹互射
	 * @param tanks
	 * @return
	 */
	public boolean hitTanks(List<Tank> tanks){
		for(int i = 0; i < tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitHome(Home home) {
		if(this.getRect().intersects(home.getRect())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 子弹撞墙 
	 * @param wall
	 * @return
	 */
	
	
	
	public boolean hitBrick(Wall br) {
		if(this.live && br.isLive() && this.getRect().intersects(br.getRect())) {
			this.live = false;
			br.setLive(false);
			return true;
			
		}
		return false;
	}
}

package com.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.*;
/**
 * 坦克类
 * @author lancer
 *
 */
public class Tank {
	/**
	 * 坦克速度
	 */
	public static int XSTEEP = 6;
	public static int YSTEEP = 6;
	/**
	 * 坦克宽
	 */
	public static final int TANK_WIDTH = 30;
	public static final int TANK_HEIGHT = 30;
	/*
	 * 坦克生存状况
	 */
	private boolean live = true;
	/*
	 * 坦克头上血条
	 */
	private BloodTip bl = new BloodTip();

	private int blood = 100;

	public int getBlood() {
		return blood;
	}


	public void setBlood(int blood) {
		this.blood = blood;
	}


	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}
	
	/*
	 * 初始化塔克总控制台，最好每个类都加上
	 */
	TankClient tc;
	
	/*
	 * 布尔，好/坏 坦克
	 */
	private boolean good;
	

	public boolean isGood() {
		return good;
	}
	
	/**
	 * 坦克位置
	 */
	private int x;
	private int y;
	
	/*
	 * 旧的坦克位置
	 */
	private int OldX;
	private int OldY;

	private static Random random = new Random();
	/**
	 * 随机生成24以内的整数，+3，赋值给step
	 */
	private int step = random.nextInt(25) + 3;
	
	
	
	private boolean aL = false, aR = false, aU = false, aD = false;
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.U;
	
	
	//拿到默认工具包
			private static Toolkit tk = Toolkit.getDefaultToolkit();
			
			
			private static Image[] tankImages = null;
			static Map<String, Image> imgs = new HashMap<String, Image>();
			
			//静态代码区，最先执行，用于初始化变量
			static {
				//class被调用时首先被执行（static）
				tankImages = new Image[] {
				 
								//通过classpath找到image的路径
								tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
								tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
								tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
								tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif")),
								
				};
				
				imgs.put("L", tankImages[0]);
				imgs.put("U", tankImages[1]);
				imgs.put("R", tankImages[2]);
				imgs.put("D", tankImages[3]);
				
			}
	
	public Tank(int x, int y, boolean good) {
		super();
		this.x = x;
		this.y = y;
		this.OldX = x;
		this.OldY = y;
		this.good = good;
	}
	
	
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}


	/**
	 * 坦克自己画自己
	 * @param g
	 */
	public void draw(Graphics g){
		
		if(!live) {
			if(!good) {
				tc.enemyTanks.remove(this);
			}
			return;
		}
		

		
		if(good) bl.draw(g);
		
		move();
		/*
		 * 炮筒方向
		 */
		switch (ptDir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;

		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;

		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;

		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
	
			}
	}
	/**
	 * 坦克移动
	 */
	public void move(){
		
		this.OldX = x;
		this.OldY = y;

	
		
		
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

		case STOP:
			break;
		default:
			break;
		}
		
		if(this.dir != Direction.STOP) this.ptDir = this.dir;
		/*
		 * 判断坦克出界
		 */
		if(x < 0) {
			step = 0;
			x = 0;
		}
		if(y < 45) {
			step = 0;
			y = 45;
		}
		if(x + TANK_WIDTH > TankClient.GAME_WIDTH) {
			step = 0;
			stay();
			x = TankClient.GAME_WIDTH - TANK_WIDTH;
		}
		if(y + TANK_HEIGHT > TankClient.GAME_HEIGHT) {
			step = 0;
			stay();
			y = TankClient.GAME_HEIGHT - TANK_HEIGHT;
		}
		
		if(!good) {
			
			Direction[] dirs = Direction.values();
			
			if(step == 0) {
				
				step = random.nextInt(25) + 3;
				int r = random.nextInt(dirs.length);
				dir = dirs[r];
			}
			
			step--;
			
			if(random.nextInt(300) > 292) this.fire();
			
		}

		
	}
	/**
	 * 坦克停止的方法
	 */
	public void stay() {
		x = this.OldX;
		y = this.OldY;
	}
	
	BloodFly bb = new BloodFly();
	/**
	 * 按键按下去
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
//		case KeyEvent.VK_F1:
//			bb.isLive();
//			break;
		case KeyEvent.VK_X:
			fire();
			break;
		case KeyEvent.VK_F2:
			if(!this.live) {
				this.live = true;
				this.blood = 100;
			}
			break;
		case KeyEvent.VK_SPACE:
			terminator();
			break;
		
		case KeyEvent.VK_LEFT:
			aL = true;
			break;
		case KeyEvent.VK_RIGHT:
			aR = true;
			break;
		case KeyEvent.VK_UP:
			aU = true;
			break;
		case KeyEvent.VK_DOWN:
			aD = true;
			break;
	
		}
		LocationDirection();
	}
	/**
	 * 当前位置
	 */
	public void LocationDirection(){
		if(aL && !aR && !aU && !aD) dir = Direction.L;
		else if(!aL && aR && !aU && !aD) dir = Direction.R;
		else if(!aL && !aR && aU && !aD) dir = Direction.U;
		else if(!aL && !aR && !aU && aD) dir = Direction.D;
		else if(!aL && !aR && !aU && !aD) dir = Direction.STOP;
		
	}
	/**
	 * 按键释放
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			aL = false;
			break;
		case KeyEvent.VK_RIGHT:
			aR = false;
			break;
		case KeyEvent.VK_UP:
			aU = false;
			break;
		case KeyEvent.VK_DOWN:
			aD = false;
			break;
		}
		
		LocationDirection();
		
	}
	/**
	 * 子弹发射
	 * @return
	 */
	public Missile fire(){
		
		if(!live) return null;
		
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
		Missile missile = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(missile);
		return missile;
	}
	/**
	 * 带参的子弹发射方法，实现终结技能
	 * @param dir
	 * @return
	 */
	public Missile fire(Direction dir){
		
		if(!live) return null;
		
		int x = this.x + Tank.TANK_WIDTH/2 - Missile.MISSILE_WIDTH/2;
		int y = this.y + Tank.TANK_HEIGHT/2 - Missile.MISSILE_HEIGHT/2;
		Missile missile = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(missile);
		return missile;
	}
	/**
	 * 包围坦克的框框（看不见），为碰撞检测做准备
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}
	/**
	 * 坦克撞墙的碰撞检测
	 * @param wall
	 * @return
	 */
	

	

	/**
	 * 坦克与坦克相撞的碰撞检测
	 * @param tanks
	 * @return
	 */
	
	public boolean collidesWithWall(River tempr) {
		if(this.live && this.getRect().intersects(tempr.getRect())) {
			step = 0;
			this.stay();
			return true;
		}
		return false;
	}
	
	
	public boolean collidesWithWall(MetalWall tempw) {
		if(this.live && this.getRect().intersects(tempw.getRect())) {
			step = 0;
			this.stay();
			return true;
		}
		return false;
	}
	
	public boolean collidesWithWall(Wall tempr) {
		if(this.live && this.getRect().intersects(tempr.getRect())) {
			step = 0;
			this.stay();
			return true;
		}
		return false;
	}
	
	
	public boolean collideToTanks(List<Tank> tanks) {
		
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if (this != tank) {
				if (this.live && tank.isLive() && this.getRect().intersects(tank.getRect())) {
					this.stay();
					tank.stay();
					return true;
				}
			}

		}
		return false;
	}
	public boolean collideToTank(Tank t) {
		if(this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
			this.stay();
			step = 0;
		}
		return false;
	}
	/**s
	 * 坦克的终结技能
	 */
	public void terminator() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 4; i++) {
			fire(dirs[i]);
		}
	}
	/**
	 * 坦克头上的血条
	 * @author 41389
	 *
	 */
	private class BloodTip {
		public void draw(Graphics g) {
			Color c =g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 10, TANK_WIDTH, 5);
			
			g.setColor(Color.GREEN);
			int inner = TANK_WIDTH * blood / 100;
			g.fillRect(x + 1, y - 9, inner - 1, 4);
			
			g.setColor(c);
		}
	}
	/**
	 * 坦克吃血块
	 * @param b
	 * @return
	 */

}

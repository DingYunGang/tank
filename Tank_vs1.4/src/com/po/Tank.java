package com.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.*;
/**
 * ̹����
 * @author lancer
 *
 */
public class Tank {
	/**
	 * ̹���ٶ�
	 */
	public static int XSTEEP = 6;
	public static int YSTEEP = 6;
	/**
	 * ̹�˿�
	 */
	public static final int TANK_WIDTH = 30;
	public static final int TANK_HEIGHT = 30;
	/*
	 * ̹������״��
	 */
	private boolean live = true;
	/*
	 * ̹��ͷ��Ѫ��
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
	 * ��ʼ�������ܿ���̨�����ÿ���඼����
	 */
	TankClient tc;
	
	/*
	 * ��������/�� ̹��
	 */
	private boolean good;
	

	public boolean isGood() {
		return good;
	}
	
	/**
	 * ̹��λ��
	 */
	private int x;
	private int y;
	
	/*
	 * �ɵ�̹��λ��
	 */
	private int OldX;
	private int OldY;

	private static Random random = new Random();
	/**
	 * �������24���ڵ�������+3����ֵ��step
	 */
	private int step = random.nextInt(25) + 3;
	
	
	
	private boolean aL = false, aR = false, aU = false, aD = false;
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.U;
	
	
	//�õ�Ĭ�Ϲ��߰�
			private static Toolkit tk = Toolkit.getDefaultToolkit();
			
			
			private static Image[] tankImages = null;
			static Map<String, Image> imgs = new HashMap<String, Image>();
			
			//��̬������������ִ�У����ڳ�ʼ������
			static {
				//class������ʱ���ȱ�ִ�У�static��
				tankImages = new Image[] {
				 
								//ͨ��classpath�ҵ�image��·��
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
	 * ̹���Լ����Լ�
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
		 * ��Ͳ����
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
	 * ̹���ƶ�
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
		 * �ж�̹�˳���
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
	 * ̹��ֹͣ�ķ���
	 */
	public void stay() {
		x = this.OldX;
		y = this.OldY;
	}
	
	BloodFly bb = new BloodFly();
	/**
	 * ��������ȥ
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
	 * ��ǰλ��
	 */
	public void LocationDirection(){
		if(aL && !aR && !aU && !aD) dir = Direction.L;
		else if(!aL && aR && !aU && !aD) dir = Direction.R;
		else if(!aL && !aR && aU && !aD) dir = Direction.U;
		else if(!aL && !aR && !aU && aD) dir = Direction.D;
		else if(!aL && !aR && !aU && !aD) dir = Direction.STOP;
		
	}
	/**
	 * �����ͷ�
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
	 * �ӵ�����
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
	 * ���ε��ӵ����䷽����ʵ���սἼ��
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
	 * ��Χ̹�˵Ŀ�򣨿���������Ϊ��ײ�����׼��
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, TANK_WIDTH, TANK_HEIGHT);
	}
	/**
	 * ̹��ײǽ����ײ���
	 * @param wall
	 * @return
	 */
	

	

	/**
	 * ̹����̹����ײ����ײ���
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
	 * ̹�˵��սἼ��
	 */
	public void terminator() {
		Direction[] dirs = Direction.values();
		for(int i = 0; i < 4; i++) {
			fire(dirs[i]);
		}
	}
	/**
	 * ̹��ͷ�ϵ�Ѫ��
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
	 * ̹�˳�Ѫ��
	 * @param b
	 * @return
	 */

}

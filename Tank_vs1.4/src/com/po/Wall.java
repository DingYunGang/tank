package com.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class Wall extends Obstacle{

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private TankClient tc;
	
	private static Image[] brickImages = null;
	static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//��̬������������ִ�У����ڳ�ʼ������
	static {
		//class������ʱ���ȱ�ִ�У�static��
		brickImages = new Image[] {
		 
						//ͨ��classpath�ҵ�image��·��
						tk.getImage(Wall.class.getClassLoader().getResource("images/commonWall.gif")),
		};
		
	}
	

	public Wall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	public void draw(Graphics g) {
		g.drawImage(brickImages[0], x, y, WIDTH, HEIGHT, null);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y,WIDTH, HEIGHT);
	}
	
	public boolean hittedByMissile(Missile m) {
		if(this.live && m.isLive() && getRect().intersects(m.getRect())) {
			this.setLive(false);
			m.setLive(false);
			return true;
		}
		return false;
	}
	
	public boolean hittedByTank(Tank t) {
		if(this.live && t.isLive() && getRect().intersects(t.getRect())) {
			t.stay();
			return true;
		}
		return false;
	}
}

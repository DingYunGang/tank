package com.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class River extends Obstacle{
	
	
	private TankClient tc;
	



private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	private static Image[] riverImages = null;
	static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//��̬������������ִ�У����ڳ�ʼ������
	static {
		//class������ʱ���ȱ�ִ�У�static��
		riverImages = new Image[] {
				//ͨ��classpath�ҵ�image��·��
				tk.getImage(River.class.getClassLoader().getResource("images/river.jpg")),
		};
		
		
	}
	


	public River(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	public void draw(Graphics g) {

		g.drawImage(riverImages[0], x, y, WIDTH, HEIGHT, null);
	}
	


	
	public boolean hittedByTank(Tank t) {
		if(this.live && t.isLive() && getRect().intersects(t.getRect())) {
			t.stay();
			return true;
		}
		return false;
	}
}

package com.po;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class Tree extends Obstacle{
	
	 
	private TankClient tc;
 
	
 

private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	
	private static Image[] treeImages = null;
	static Map<String, Image> imgs = new HashMap<String, Image>();
	
	//��̬������������ִ�У����ڳ�ʼ������
	static {
		//class������ʱ���ȱ�ִ�У�static��
		treeImages = new Image[] {
				//ͨ��classpath�ҵ�image��·��
				tk.getImage(Tree.class.getClassLoader().getResource("images/tree.gif")),
		};
		
		
	}
	
 
	public Tree(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	}
	
	public void draw(Graphics g) {

		g.drawImage(treeImages[0], x, y, WIDTH, HEIGHT, null);
	}
}

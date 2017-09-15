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
	
	//静态代码区，最先执行，用于初始化变量
	static {
		//class被调用时首先被执行（static）
		treeImages = new Image[] {
				//通过classpath找到image的路径
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

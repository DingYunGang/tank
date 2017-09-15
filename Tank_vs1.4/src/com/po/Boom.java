package com.po;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * 爆炸类
 * @author lancer
 *
 */
public class Boom {
	private int x;
	private int y;
	
	private boolean live = true;
	
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imgs = { // 存储爆炸图片 从小到大的爆炸效果图
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom001.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom001.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom002.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom002.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom003.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom003.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom004.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom004.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom005.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom005.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom006.png")),
			tk.getImage(Boom.class.getClassLoader().getResource(
					"images/boom006.png")),
		
	 };
	
//	int[] con = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
	
	int step = 0;
	
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}



	public void draw(Graphics g){
		
		if(!live){
			tc.booms.remove(this);
			return;
		}
		if(step == imgs.length){
			live = false;
			step = 0;
			return;
		}
		
		g.drawImage(imgs[step], x-70, y-70, null);
		step++;
		
	}
}

package com.po;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
public class MetalWall extends Obstacle{

	
		private TankClient tc;
		
		public static final int WIDTH = 20;
		public static final int HEIGHT = 20;
		

	
		
	private static Toolkit tk = Toolkit.getDefaultToolkit();
		
		
		private static Image[] metalWallImages = null;
		static Map<String, Image> imgs = new HashMap<String, Image>();
		
		//静态代码区，最先执行，用于初始化变量
		static {
			//class被调用时首先被执行（static）
			metalWallImages = new Image[] {
					tk.getImage(MetalWall.class.getClassLoader().getResource("images/metalWall.gif")),
			};
			
		
			
			
		}
		
		public MetalWall(int x, int y, TankClient tc) {
			this.x = x;
			this.y = y;
			this.tc = tc;	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
		}
		
		public void draw(Graphics g) {

			g.drawImage(metalWallImages[0], x, y, WIDTH, HEIGHT, null);
		}
		

		public boolean hittedByMissile(Missile m) {
			if(this.live && m.isLive() && getRect().intersects(m.getRect())) {
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



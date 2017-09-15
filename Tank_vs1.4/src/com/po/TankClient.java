package com.po;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import javax.swing.JOptionPane;
/**
 * 
 * 由原来的八个方向改为四个方向
 */
public class TankClient extends Frame implements ActionListener {
	/**
	 * 整个坦克的宽度
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * 整个坦克的高度
	 */
	public static final int GAME_HEIGHT = 600;
	
	public static boolean printable = true;
	
	Tank myTank = new Tank(250, 550, true, Direction.STOP, this);

	Home home = new Home(380,570,50, 50, this);//14
	
	List<Tank> enemyTanks = new ArrayList<Tank>();
	List<Boom> booms = new ArrayList<Boom>();
	List<Missile> missiles = new ArrayList<Missile>(); 
	List<Wall> walls = new ArrayList<Wall>();
	List<MetalWall> metalWalls = new ArrayList<MetalWall>();
	List<River> rivers = new ArrayList<River>();
	List<Tree> trees = new ArrayList<Tree>();
	List<BloodFly> bs = new ArrayList<BloodFly>();
	Image backScreen = null;
	
	Random r = new Random();
	
	/*
	 * 菜单栏
	 * 游戏		暂停/继续		帮助
	 * 重新开始	暂停			游戏说明
	 * 退出		继续			
	 */
	MenuBar button = null;
	Menu button1 = null, button2 = null, button3 = null;
	MenuItem button1_1 = null, button1_2= null, button2_1 = null, button2_2 = null,
			button3_1 = null;
	 

	/**
	 * paint方法，主画笔
	 */
	public void paint(Graphics g) {
		
		g.drawString("可见子弹数量：" + missiles.size(), 10, 60);
		g.drawString("敌方坦克数量：" + enemyTanks.size(), 10, 75);
		g.drawString("生命值：" + myTank.getBlood(), 10, 90);
	
		if(enemyTanks.size() <= 0 && home.isLive() && myTank.isLive()) {
			Color c = g.getColor(); // 设置参数
			g.setColor(Color.RED);
			Font f = g.getFont();
			g.setFont(new Font(" ", Font.BOLD, 60));
			g.drawString("  你赢了！ ",290, 370);
			missiles.clear();
			g.setFont(f);
			g.setColor(c);
			booms.clear();
			missiles.clear();
			printable = false;

		}
		if( !home.isLive() || !myTank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 60)); // 判断是否赢得比赛
			enemyTanks.clear();
			walls.clear();
			metalWalls.clear();
			rivers.clear();
			trees.clear();
			bs.clear();
			myTank.setLive(false);
			g.drawString("你输了！ ", 310, 300);
			
			g.setFont(f);
		}
		for(int i = 0; i < missiles.size(); i++){
			Missile missile = missiles.get(i);
			missile.hitTanks(enemyTanks);
			missile.hitTank(myTank);
			home.shootByMissile(missile);

			

			
			for(int j=0; j<walls.size(); j++) {
				Wall tmpBrick = walls.get(j);
				if(tmpBrick.hittedByMissile(missile)) {
					walls.remove(j);
				}
			}
			for(int j=0; j<metalWalls.size(); j++) {
				MetalWall tmpm = metalWalls.get(j);
				tmpm.hittedByMissile(missile);
				
			}
			
		
			missile.draw(g);
			
			
		}
		
		for(int i = 0;i<enemyTanks.size();i++){
			Tank tank = enemyTanks.get(i);
			

			tank.collideToTanks(enemyTanks);
			tank.draw(g);
			for(int j=0; j<walls.size(); j++) {
				Wall tmpBrick = walls.get(j);
				tmpBrick.hittedByTank(tank);
			}
			
			for(int j=0; j<metalWalls.size(); j++) {
				MetalWall tmpm = metalWalls.get(j);
				tmpm.hittedByTank(tank);
			}
			
			for(int j=0; j<rivers.size(); j++) {
				River tmpr = rivers.get(j);
				tmpr.hittedByTank(tank);
			}
		}
		
		for(int i = 0; i<booms.size(); i++){
			Boom boom = booms.get(i);
			boom.draw(g);
		}


		for(int j=0; j<walls.size(); j++) {
			Wall tmpBrick = walls.get(j);
			tmpBrick.hittedByTank(myTank);
		}
		
		for(int j=0; j<metalWalls.size(); j++) {
			MetalWall tmpm = metalWalls.get(j);
			tmpm.hittedByTank(myTank);
		}
		for(int j=0; j<rivers.size(); j++) {
			River tmpr = rivers.get(j);
			tmpr.hittedByTank(myTank);
		}
		for(int i=0; i<bs.size(); i++) {
			BloodFly tmpb =bs.get(i);
			if(tmpb.eatByTank(myTank) ) {
				bs.remove(i);
//				Random t =new Random();
//				while(bs.size() <= 0) {
//				if( r.nextInt(100) > 50)
				 bs.add(new BloodFly());
//				}
			 }
		}
		
		
			for(int i=0; i<bs.size(); i++) {
				BloodFly tempb = bs.get(i);
				
				for(int j=0; j<walls.size(); j++) {
					Wall tempw = walls.get(j);
					if(tempb.hide(tempw)) {
						bs.remove(i);
						bs.add(new BloodFly());
					}
				}
				
				for(int j=0; j<metalWalls.size(); j++) {
					MetalWall tempm = metalWalls.get(j);
					if(tempb.hide(tempm)) {
						bs.remove(i);
						bs.add(new BloodFly());
					}
				}
				for(int j=0; j<rivers.size(); j++) {
					River tempr = rivers.get(j);
					if(tempb.hide(tempr)) {
						bs.remove(i);
						bs.add(new BloodFly());
					}
				}
				for(int j=0; j<trees.size(); j++) {
					Tree tempt = trees.get(j);
					if(tempb.hide(tempt)) {
						bs.remove(i);
						bs.add(new BloodFly());
					}
				}
				
			
			}
			
			for(int i=0; i<enemyTanks.size(); i++) {
				 
				Tank tempt = enemyTanks.get(i);
				
				for(int j=0; j<metalWalls.size(); j++) {
					MetalWall tempm = metalWalls.get(j);
					tempt.collidesWithWall(tempm);
				}
				
				for(int j=0; j<walls.size(); j++) {
					Wall tempw = walls.get(j);
					tempt.collidesWithWall(tempw);
				}
				
				
				for(int j=0; j<rivers.size(); j++) {
					River tempr = rivers.get(j);
					tempt.collidesWithWall(tempr);
				}
				
				tempt.collideToTank(myTank);
				
				
				
				
			}
			
	
		 
	
		
		myTank.draw(g);

		for(int i=0; i<bs.size(); i++) {
			BloodFly tempb = bs.get(i);
		tempb.draw(g);
		}

		for(int i=0; i<walls.size(); i++) {		
			Wall tempBrick = walls.get(i);
			tempBrick.draw(g);
		}
		for(int i=0; i<metalWalls.size(); i++) {
			MetalWall tempmw = metalWalls.get(i);
			tempmw.draw(g);
		}
		for(int j=0; j<rivers.size(); j++) {
			River tmpr = rivers.get(j);
			tmpr.draw(g);
		}
		for(int j=0; j<trees.size(); j++) {
			Tree tmps = trees.get(j);
			tmps.draw(g);
		}
		home.draw(g);
			
//			
	}
	
	/**
	 * 使用repaint时，在调用paint方法之前，调用update方法，使用双缓冲，解决闪烁。
	 */
	public void update(Graphics g) {
		
		if (backScreen == null) {
			backScreen = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		
		Graphics abackScreen = backScreen.getGraphics();
		Color c = abackScreen.getColor();
		abackScreen.setColor(Color.LIGHT_GRAY);
		abackScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		abackScreen.setColor(c);
		paint(abackScreen);
		g.drawImage(backScreen, 0, 0, null);
		
	}
	/**
	 * 显示塔克主窗口
	 */
	public void lauchFrame() {
		
//		
//		
		button = new MenuBar();
		button1 = new Menu("游戏");
		button2 = new Menu("暂停/继续");
		button3 = new Menu("帮助");
		
		button1.setFont(new Font("黑体", Font.BOLD, 15));// 设置菜单显示的字体
		button2.setFont(new Font("黑体", Font.BOLD, 15));// 设置菜单显示的字体
		button3.setFont(new Font("黑体", Font.BOLD, 15));// 设置菜单显示的字体
		button1.setFont(new Font("黑体", Font.BOLD, 13));// 设置菜单显示的字体
		button2.setFont(new Font("黑体", Font.BOLD, 13));// 设置菜单显示的字体
		button1_1 = new MenuItem("重新开始");
		button1_2 = new MenuItem("退出游戏");
		button2_1 = new MenuItem("暂停");
		button2_2 = new MenuItem("继续");
		button3_1 = new MenuItem("游戏说明");
		
		button1_1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		button1_2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		button2_1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		button2_2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		button3_1.setFont(new Font("TimesRoman", Font.BOLD, 15));



		button1.add(button1_1);
		button1.add(button1_2);
		button2.add(button2_1);
		button2.add(button2_2);
		button3.add(button3_1);
		button.add(button1);
		button.add(button2);
		button.add(button3);

		button1_1.addActionListener(this);
		button1_1.setActionCommand("NewGame");
		button1_2.addActionListener(this);
		button1_2.setActionCommand("Exit");
		button2_1.addActionListener(this);
		button2_1.setActionCommand("Stop");
		button2_2.addActionListener(this);
		button2_2.setActionCommand("Continue");
		button3_1.addActionListener(this);
		button3_1.setActionCommand("help");

		this.setMenuBar(button);
		this.setVisible(true);
		
		
		for(int i = 0; i < 10; i++) {			//添加敌军坦克
			if(i < 6) enemyTanks.add(new Tank(20 + 50 * (i + 1), 50, false, Direction.D, this));
			else if(i < 10) enemyTanks.add(new Tank(570 + 50 * (i - 6), 50,  false, Direction.D, this));

		}
		
		new Audio(0);
		for (int i = 0; i < 6; i++) {
			trees.add(new Tree(180 + 15 * i, 180, this));
			trees.add(new Tree(180 + 15 * i, 195, this));
			trees.add(new Tree(525 + 15 * i, 180, this));
			trees.add(new Tree(525 + 15 * i, 195, this));
		}
		for (int i = 0; i < 8; i++) {
			trees.add(new Tree(150 + 15 * i, 210, this));
			trees.add(new Tree(520 + 15 * i, 210, this));
			trees.add(new Tree(150 + 15 * i, 240, this));
			trees.add(new Tree(150 + 15 * i, 255, this));
			trees.add(new Tree(520 + 15 * i, 240, this));
			trees.add(new Tree(520 + 15 * i, 255, this));
		}
		for (int i = 0; i < 10; i++) {
			trees.add(new Tree(0 + 15 * i, 390, this));
			trees.add(new Tree(0 + 15 * i, 405, this));
			trees.add(new Tree(0 + 15 * i, 420, this));
			trees.add(new Tree(0 + 15 * i, 335, this));
		}
		for (int i = 0; i < 10; i++) {
			trees.add(new Tree(650 + 15 * i, 390, this));
			trees.add(new Tree(650 + 15 * i, 405, this));
			trees.add(new Tree(650 + 15 * i, 420, this));
			trees.add(new Tree(650 + 15 * i, 335, this));
			trees.add(new Tree(650 + 15 * i, 540, this));
			trees.add(new Tree(650 + 15 * i, 555, this));
			trees.add(new Tree(650 + 15 * i, 570, this));
			trees.add(new Tree(650 + 15 * i, 585, this));
		}
		
		for (int i = 0; i < 7; i++) {
			rivers.add(new River(270 + 15 * i, 210, this));
			rivers.add(new River(285 + 15 * i, 210, this));
			rivers.add(new River(410 + 15 * i, 210, this));
			rivers.add(new River(425 + 15 * i, 210, this));
		}

		
		for (int i = 0; i < 12; i++) {
			walls.add(new Wall(0 + 15 * i, 180, this));
			walls.add(new Wall(0 + 15 * i, 195, this));
			walls.add(new Wall(615 + 15 * i, 180, this));
			walls.add(new Wall(615 + 15 * i, 195, this));
		}
		for (int i = 0; i < 4; i++) {
			walls.add(new Wall(150 + 15 * i, 270, this));
			walls.add(new Wall(150 + 15 * i, 285, this));
			walls.add(new Wall(590 + 15 * i, 270, this));
			walls.add(new Wall(590 + 15 * i, 285, this));
		}
		for (int i = 0; i < 8; i++) {
			walls.add(new Wall(300, 90 + 15 * i, this));
			walls.add(new Wall(315, 90 + 15 * i, this));
			walls.add(new Wall(480, 90 + 15 * i, this));
			walls.add(new Wall(495, 90 + 15 * i, this));
		}
		for (int i = 0; i < 28; i++) {
			walls.add(new Wall(190 + 15 * i, 400, this));
			walls.add(new Wall(190 + 15 * i, 415, this));
			walls.add(new Wall(190 + 15 * i, 430, this));
			walls.add(new Wall(190 + 15 * i, 445, this));
		}
		for (int i = 0; i < 6; i++) {
			walls.add(new Wall(350 + 15 * i, 540, this));
			walls.add(new Wall(350 + 15 * i, 555, this));
		}
		for (int i = 0; i < 2; i++) {
			walls.add(new Wall(350 + 15 * i, 570, this));
			walls.add(new Wall(350 + 15 * i, 585, this));
		}
		for (int i = 0; i < 2; i++) {
			walls.add(new Wall(410 + 15 * i, 570, this));
			walls.add(new Wall(410 + 15 * i, 585, this));
		}
	
		for (int i = 0; i < 8; i++) {
			metalWalls.add(new MetalWall(50 + 15 * i, 100, this));
			metalWalls.add(new MetalWall(50 + 15 * i, 115, this));
			metalWalls.add(new MetalWall(620 + 15 * i, 100, this));
			metalWalls.add(new MetalWall(620 + 15 * i, 115, this));
		}
		for (int i = 0; i < 12; i++) {
			metalWalls.add(new MetalWall(0 + 15 * i, 300, this));
			metalWalls.add(new MetalWall(0 + 15 * i, 315, this));
			metalWalls.add(new MetalWall(620 + 15 * i, 300, this));
			metalWalls.add(new MetalWall(620 + 15 * i, 315, this));
		}
		for (int i = 0; i < 16; i++) {
			metalWalls.add(new MetalWall(390, 45 + 15 * i, this));
		}
		bs.add(new BloodFly());			//添加血块
		
		this.setLocation(300, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent a) {
				System.exit(0);
			}

		});
		
		
		
		this.setResizable(false);
		
		
		
//		this.setBackground(Color.GRAY);
		new Thread(new PaintThread()).start();
		
		this.addKeyListener(new KeyController());
		this.setVisible(true);
		
		
	}
	/**
	 * 主函数，调用其他方法
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lauchFrame();
	}
	/**
	 * 线程类，实现重画和睡眠
	 * @author lancer
	 *
	 */
	private class PaintThread implements Runnable {

		public void run() {
			while (printable) {
				repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	/**
	 * 键盘监听器
	 * @author lancer
	 *
	 */
	public class KeyController extends KeyAdapter{
		
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
				this.dispose();
				TankClient tc = new TankClient();
				tc.lauchFrame();

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
		
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}
		} else if (e.getActionCommand().equals("Exit")) {
				System.out.println("退出");
				System.exit(0);

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，X键盘发射，space键强化版子弹，",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		}
	}

}

package com.po;

import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio implements Runnable {
	private String url = "resources/";
	private int type = 0;

	public Audio(int type) {
		this.type = type;
		switch (type) {
		case 0: // 背景音效
			url = url + "music.mid";
			break;
		case 1: // 爆炸音效
			url = url + "explode.wav";
			break;
		case 2: // 吃血块音效
			url = url + "levelup.wav";
			break;
		case 3: // 我方坦克死亡
			url = url + "over.wav";
			break;
		}
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		AudioStream ais = null;
		InputStream is = null;
		try {
			if (type == 0) { // 循环播放
				while (true) {
					is = ClassLoader.getSystemResourceAsStream(url);
					ais = new AudioStream(is);
					AudioPlayer.player.start(ais);
					Thread.sleep(106000);
				}
			} else { // 只播放一次
				is = ClassLoader.getSystemResourceAsStream(url);
				ais = new AudioStream(is);
				AudioPlayer.player.start(ais);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) { // 关闭输入流
					is.close();
				}
				if (ais != null) { // 关闭音频流
					ais.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
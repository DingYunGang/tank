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
		case 0: // ������Ч
			url = url + "music.mid";
			break;
		case 1: // ��ը��Ч
			url = url + "explode.wav";
			break;
		case 2: // ��Ѫ����Ч
			url = url + "levelup.wav";
			break;
		case 3: // �ҷ�̹������
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
			if (type == 0) { // ѭ������
				while (true) {
					is = ClassLoader.getSystemResourceAsStream(url);
					ais = new AudioStream(is);
					AudioPlayer.player.start(ais);
					Thread.sleep(106000);
				}
			} else { // ֻ����һ��
				is = ClassLoader.getSystemResourceAsStream(url);
				ais = new AudioStream(is);
				AudioPlayer.player.start(ais);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) { // �ر�������
					is.close();
				}
				if (ais != null) { // �ر���Ƶ��
					ais.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
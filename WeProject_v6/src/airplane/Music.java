package airplane;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Music {
	File music1 = new File("music\\bgm1.wav");
	File music2 = new File("music\\windows.wav");
	File music3 = new File("music\\enemy1_down.wav");
	File music4 = new File("music\\nuclear1.wav");
	File music5 = new File("music\\BOSS_bgm.wav");
	File music6 = new File("music\\score.wav");
	File music7 = new File("music\\暴走音效.wav");
	File music8 = new File("music\\alert.wav");
	
	AudioClip game_mucic = null;//游戏背景音乐
	AudioClip windows_music = null;//大厅界面bgm
	AudioClip boom_music = null;//击中敌机爆炸bgm
	AudioClip boss_boom = null;//击败BOSS爆炸bgm
	AudioClip boss_bgm = null;//击败BOSS_bgm
	AudioClip award_bgm = null;//获得奖励bgm
	AudioClip outbreak_bgm = null;//暴走bgm
	AudioClip alert_bgm = null;//boss来临警告音效
	
	Music() {
		try {
			game_mucic = Applet.newAudioClip(music1.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			windows_music = Applet.newAudioClip(music2.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			boom_music = Applet.newAudioClip(music3.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			boss_boom = Applet.newAudioClip(music4.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			boss_bgm = Applet.newAudioClip(music5.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			award_bgm = Applet.newAudioClip(music6.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			outbreak_bgm = Applet.newAudioClip(music7.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		try {
			alert_bgm = Applet.newAudioClip(music8.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

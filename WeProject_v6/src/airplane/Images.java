package airplane;
/**图片工具类*/

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Images {

  //公开的   静态的      数据类型       变量
	public static BufferedImage[] sky;//天空图片
	public static BufferedImage[] bullet;//子弹图片
	public static BufferedImage[] heros;//英雄机图片
	public static BufferedImage[] airs;//小敌机图片
	public static BufferedImage[] bairs;//大敌机图片
	public static BufferedImage[] bees;//小蜜蜂图片
	public static BufferedImage protect;//保护罩图片
	public static BufferedImage smallhero;//援助飞机图片
	public static BufferedImage[] boss;//boss图片
	public static BufferedImage[] blood;//boss血条
	public static BufferedImage[] boms;//爆炸图片
	public static BufferedImage clearance;//通过背景
	public static BufferedImage black; //全黑背景(滚动使用)
	public static BufferedImage[] load;//加载
	
	public static BufferedImage start;//启动图片
	public static BufferedImage pause;//暂停图
	public static BufferedImage gameOver;//游戏结束图
	
	static {//静态初始化
		  sky = new BufferedImage[3];
		  sky[0] = readImage("image/background.jpg");
		  sky[1] = readImage("image/background2.jpg");
		  sky[2] = readImage("image/background3.jpg");
		  
		  bullet= new BufferedImage[2];
		  bullet[0] = readImage("image/bullet.png");
		  bullet[1] = readImage("image/bullet1.png");
		  heros = new BufferedImage[2];
		  heros[0] = readImage("image/hero0.png");
		  heros[1] = readImage("image/hero1.png");
		  smallhero = readImage("image/sHero.png");
		  
		  protect = readImage("image/protect.png");
		  airs = new BufferedImage[2];
		  bairs = new BufferedImage[2];
		  bees = new BufferedImage[2];
		  airs[0] = readImage("image/smenemyair01.png");
		  airs[1] = readImage("image/smenemyair01_1.png");
		  
		  bairs[0] = readImage("image/bigenemyair1.png");
		  bairs[1] = readImage("image/bigenemyair1_1.png");
		  
		  bees[0] = readImage("image/bee01.png");
		  bees[1] = readImage("image/bee01_1.png");
		  
		  boms = new BufferedImage[6];
		  for (int i = 0; i < boms.length; i++) {
			  boms[i] = readImage("image/bom-"+(i+1)+".png");
		  }
		  
		  
		  start = readImage("image/start.png");
		  pause = readImage("image/pause.png");
		  gameOver = readImage("image/gameover.png");
		  
		  boss = new BufferedImage[6];
		  boss[0] = readImage("image/boss1.png");
		  boss[1] = readImage("image/boss1_1.png");
		  boss[2] = readImage("image/boss2.png");
		  boss[3] = readImage("image/boss2_1.png");
		  boss[4] = readImage("image/boss3.png");
		  boss[5] = readImage("image/boss3_1.png");

		  blood = new BufferedImage[21];
		  for (int i = 0; i < blood.length; i++) {
			blood[i] = readImage("image/blood/血条"+i+".png");
		}
		  
		  clearance = readImage("image/缅怀.png");//通关背景
		  black = readImage("image/black.jpg");//全黑
		  load = new BufferedImage[2];
		  load[0] = readImage("image/load.png");
		  load[1] = readImage("image/loado.png");
	}
	
	//读取图片
	public static BufferedImage readImage(String fileName){///------此方法不要求掌握，知道就行
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}

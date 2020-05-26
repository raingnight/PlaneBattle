package airplane;

import java.awt.image.BufferedImage;

/**英雄机是飞行物**/
public class Hero extends FlyingObject{

	//命
	private int life;
	
	//火力
	private int fire;
	
	//保护罩
	private int protect;
	
	public Hero(){
		super(97,139,190,400);
		protect = 0;
		life = 3;
		fire =0;
	}
	/**重写step()英雄机图片切换*/
	public void step() {
	}
	
	private int index = 0;//用于图片切换
	/**重写getImage()方法*/
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//若活着的
			return Images.heros[index++%2];//仍何数求余与2不是0，就是1
		}
		return null;
	}
	
	public BufferedImage getImage(int i) {
		return Images.protect;
	}
	
	/**英雄机发射子弹对象*/
	public Bullet[] shoot() {
		int xStep = this.width/2;
		int yStep = 10;
		if(fire>0) {//双发
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(x+xStep-43, y-yStep,false);
			bs[1] = new Bullet(x+2*xStep-48, y-yStep,false);
			fire-=2;
			return bs;
		}else {//单发
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(x+xStep-23, y-yStep,false);
			return bs;
		}
	}
	
	/**英雄机随着鼠标移动*/
	public void moveTo(int x,int y) {
		this.x = x - this.width/2;//英雄机的x = 鼠标的x-英雄机的宽/2
		this.y = y - this.height/2;//英雄机的y = 鼠标的y-英雄机的高/2
	}
	
	/**英雄机增加命数*/
	public void addLife() {
		life++;//增加命数1
	}
	
	/**返回命*/
	public int getLife() {
		return life;
	}
	/**英雄机增加火力值*/
	public void addFire() {
		fire+=40;//增加火力值40
	}
	
	/**英雄机和敌机碰撞减命一条*/
	public void subtractLife() {
		life--;
	}
	
	/**火力值清零*/
	public void clearFire() {
		fire = 0;
	}
	
	/**获得保护罩*/
	public int getProtect() {
		return protect;
	}
	
	public void addProtect() {
		protect = 1;
	}
	
	/**失去保护罩*/
	public void subProtect() {
		protect = 0;
	}
	
	/**获取援助小飞机*/
	public SmallHero[] getHeros() {
		SmallHero[] smallHero = new SmallHero[2];
		smallHero[0] = new SmallHero(this.x-20,this.y+100);
		smallHero[1] = new SmallHero(this.x+this.width-20,this.y+100);
		return smallHero;
	}
}

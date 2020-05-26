package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;

import org.omg.PortableInterceptor.INACTIVE;

/**小敌机是飞行物**/
public class Airplane extends FlyingObject implements Enemy{

	private int speed;//移动速度；
	
	public Airplane() {
		super(100,95);
		speed= 2;
	}
	
	/**重写step() 小敌机移动**/
	public void step() {
		y+= speed;
	}

	private int index = 0;
	private int flyindex = 0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//若活着
			return Images.airs[flyindex++%2];//如果活着就返回第一张
		}else if(isDead()){//若死了
			BufferedImage img = Images.boms[index++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;//删除状态时，不返回图片
	}

	/**重写getScore()得分*/
	public int getScore() {
		return 1;
	}
	
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 90;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x+2*xStep-5, y+yStep,true);
		return bs;
	}
}

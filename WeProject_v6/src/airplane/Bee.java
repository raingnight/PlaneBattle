package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;
/**小蜜蜂是飞行物*/
public class Bee extends FlyingObject implements Award{
	private int xSpeed;//x坐标移动速度
	private int ySpeed;//y坐标移动速度
	private int awardType;//奖励类型
	public Bee(){
		super(100,73);
		Random r = new Random();//随机数对象
		xSpeed= 1;
		ySpeed= 2;
		awardType = r.nextInt(4);//0到3之间的随机数
	}
	/**重写step()小蜜蜂移动*/
	public void step() {
		x += xSpeed;
		y += ySpeed;
		if(x<=0 || x >= World.WIDTH-this.width ) {//若x<=0或者x>=World.WIDTH-this.width,则说明到边框了
			xSpeed *= -1;//切换方向(正变负，负变正)
		}
	}
	
	private int index = 0;
	private int flyindex = 0;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {//若活着
			return Images.bees[flyindex++%2];//如果活着就返回第一张
		}else if(isDead()){//若死了
			BufferedImage img = Images.boms[index++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;//删除状态时，不返回图片
	}
	@Override
	public int getAwardType() {
		return awardType;//返回奖励类型
	}
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 70;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x+2*xStep-5, y+yStep,true);
		return bs;
	}
}

package airplane;

import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * boss是飞行物
 */
public class Boss extends FlyingObject{

	private int speed = 2;
	private int xSpeed = 1;
	private int level;
	
	public Boss() {
		super(200, 244);
		x = 150;
	}
	
	/**
	 * boss的移动
	 */
	public void step() {
		if(y<=100) {
			y+=speed;
		}else {
			speed = 0;
			x -= xSpeed;
			if(x<=0 || x >= World.WIDTH-this.width ) {
				xSpeed *= -1;//切换方向(正变负，负变正)
			}
		}
	}

	/**
	 * 生成boss的子弹
	 */
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 50;
		Random r = new Random();
		int type = r.nextInt(10);//选择子弹发射类型
		Bullet[] bs = new Bullet[10];
		if(level == 0) {//判断关卡是否是第一关。
			if(type <= 6){
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}else {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ i * yStep,true);
				}
			}
		}else if(level == 1) {//判断关卡是否是第二关。
			if(type <= 5) {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ i * yStep,true);
				}
			}else{
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}
		}else {
			
			if(type <= 3) {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ 2 * yStep,true);
				}
			}else if(type <= 6){
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ (10-i) * yStep,true);
				}
			}else {
				for (int i = 0; i < bs.length; i++) {
					bs[i] = new Bullet(x+i*xStep-5, y+ 2 * yStep,true);
				}
			}
			
		}
		
		
		return bs;
	}
	
	/**
	 * 重载getImage方法
	 * 根据关卡，返回boss的图片
	 */
	private int index = 0;
	private int bomindex = 0;
	public BufferedImage getImage(int level) {
		this.level = level;
		
		if(isLife()) {//若活着
			if(level == 0) {
				return Images.boss[index++%2];
			}else if(level == 1){
				return Images.boss[(index++%2)+2];
			}else {
				return Images.boss[index++%2+4];
			}
				
		}else if(isDead()){//若死了
			BufferedImage img = Images.boms[bomindex++];
			if(index == Images.boms.length) {
				state = REMOVE;
			}
			return img;
		}

		return null;//删除状态时，不返回图片
		
	}
	

	/**
	 * 返回boss的血条
	 */
	public BufferedImage getBloodImage(int i) {
		return Images.blood[i];
	}

	/**击败boss返回getScore()得分*/
	public int getScore() {
		return 20;
	}
	/**
	 * 重写超类里的抽象方法
	 */
	public BufferedImage getImage() {
		return null;
	}

}

package airplane;

import java.awt.image.BufferedImage;

/**子弹是飞行物*/
public class Bullet extends FlyingObject{

	
	private int speed = 3;//移动速度；
	private boolean isEnem = true;//判断子弹是否是敌机子弹
	public Bullet(int x ,int y,boolean isEnem) {
		super(43,43,x,y);
		this.isEnem=isEnem;
	}
	
	/**重写step()子弹移动*/
	public void step() {
		if(isEnem) {
			y+=speed;
		}else {
			y-=speed;
		}
	}

	/**重写getImage()方法*/
	@Override
	public BufferedImage getImage() {
		/*
		 * 若活着，则返回bullet图片
		 * 若死了的，状态改为删除，不反悔图片
		 * 若删除的，则不反悔图片
		 */
		 if(isLife()) {
			 if(isEnem) {
				 return Images.bullet[1];
			}else {
				 return Images.bullet[0];
				}	 
		}else if(isDead()) {
				state = REMOVE;
		}
		return null;//死了和删除都不返回图片7

	}
	
	/**重写outOfBounds()检测子弹是否出界*/
	public boolean outOfBounds() {
		return this.y<=-this.height || this.y >= World.HEIGHT;
	}
	
	public boolean isEnem() {
		return isEnem;
	}
	
}

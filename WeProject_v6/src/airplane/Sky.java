package airplane;

import java.awt.image.BufferedImage;

/**天空**/
public class Sky extends FlyingObject{

	
	private int speed;//移动速度；
	private int y1; //第二张图片的y坐标
	
	public Sky(){

		super(World.WIDTH,World.HEIGHT,0,0);
		speed = 1;
		y1 = -World.HEIGHT;
	}
	/**重写step()天空移动*/
	public void step() {
		y += speed;//y+(向下)
		y1 += speed;//y1+(向下)
		if(y>=World.HEIGHT) {
			y = -World.HEIGHT;
		}
		if(y1>=World.HEIGHT) {
			y1 = -World.HEIGHT;
		}
	}
	
	/**重写getImage()方法*/
	public BufferedImage getImage(int level) {
		return Images.sky[level];//直接返回sky图片即可
	}
	public int getY1() {
		return y1;//返回y1坐标
	}
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}

package airplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SmallHero extends FlyingObject {
	public int fire;
	private int speed;
	
	public SmallHero(int x,int y) {
		super(38, 74,x,y);
		fire = 30;
		speed = 5;
	}


	public void step(int x,int y) {
		this.x = x;
		this.y = y;


	}

	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return Images.smallhero;	
		}
		return null;
	}
	
	public void getLife() {
		this.state = LIFE;
	}


	@Override
	public void step() {	
	}
	

	public Bullet[] shoot() {
		Bullet[] bs = new Bullet[1];
		if (fire>1) {
			bs[0] = new Bullet(this.x-5, this.y-10, false);
			fire-=1;
			return bs;
		}else {
			bs[0] = new Bullet(this.x-5, this.y-10, false);
			goDead();
			bs[0].goDead();
			return bs;
		}
		
	}
	
	
}

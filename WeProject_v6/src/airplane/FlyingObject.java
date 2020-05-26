package airplane;

import java.util.Random;
import java.awt.image.BufferedImage;

/**飞行物**/
public abstract class FlyingObject {

		public static final int LIFE = 0;//活着的
		public static final int DEAD = 1;//死了的
		public static final  int REMOVE = 2;//删除的
		protected int state = LIFE;//当前状态（默认为活着）
		/*
		 * 获取图片使需要去考虑对象的状态，因为在不同状态下获取
		 *的图片是不一样的每个对象都有一个状态，意味着状态为对象
		 *所共有的属性，所以设计在超类中 ，状态一般都是固定的，
		 *所以都设计为常量，同时设计一个state变量来表示当前的状态
		 *------在FlyingObject中设计LIFE，DEAO，REMOVE三个常量，设计
		 *state变量表示当前状态
		 */
		
		//大小
		protected int width;
		protected int height;
		
		//坐标++
		protected int x;
		protected int y;
	
		/**专门给小敌机、大敌机、小蜜蜂提供的*/
		public FlyingObject(int width,int height) {
			this.width = width;
			this.height = height;
			Random r = new Random();//随机数对象
			x = r.nextInt(World.WIDTH-width);//0到（窗口宽-小敌机宽）的随机数。
			y = -height;
		}
		
		/**专门给英雄机，天空，子弹提供的*/
		public FlyingObject(int width,int height,int x,int y) {
			this.width = width;
			this.height = height;
			this.x = x;
			this.y = y;
		}
		
		/**飞行物移动**/
		public abstract void step();
		
		/**获取对象呢的图片*/
		public abstract BufferedImage getImage();
		
		/**判断对象是否活着的*/
		public boolean isLife() {
			return state == LIFE;//若当前状态为LIFE，表示对象是活着的，放回true，否者返回false
		}
		
		/***/
		public boolean isDead() {
			return state == DEAD;//若对象当前状态是DEAD，表示对象是死的，返回true，否则返回false
		}
		
		/***/
		public boolean isRemove() {
			return state == REMOVE;//若对象当前状态是REMOVE，表示对象是死的，返回true，否则返回false
		}
		
		/**检测敌机是否出界*/
		public boolean outOfBounds() {
			return this.y>=World.HEIGHT;
		}
		
		/**检测敌人与子弹/英雄机的碰撞*/
		public boolean  hit(FlyingObject other) {
			int x1 = this.x-other.width;//x1：敌人的x-英雄机或子弹的宽
			int x2 = this.x+this.width;//x2:敌人的x+敌人的宽
			int y1 = this.y-other.height;//y1：敌人的y-英雄机或子弹的高
			int y2 = this.y+this.height;//y2：敌人的y+的人的高
			int x = other.x;//英雄机或子弹的x
			int y = other.y;//英雄机或子弹的y
			return x>=x1 && x<=x2&&y>=y1&&y<=y2;//x在x1和x2之间，y在y1和y2之间
		}
		
		/**飞行物去死*/
		public void goDead() {
			state = DEAD;//将当前状态改为DEAD
		}
		
		public Bullet[] shoot() {
			Bullet[] bs = new Bullet[1];
			return bs;
		}
		
}

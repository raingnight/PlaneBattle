package airplane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/** 整个游戏窗口 **/
public class World extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 512;// 窗口宽
	public static final int HEIGHT = 768;// 窗口高

	public static final int START = 0;// 启动状态
	public static final int RUNNING = 1;// 运行
	public static final int PAUSE = 2;// 暂停
	public static final int GAME_OVER = 3;// 结束
	protected static final int BOSS = 4;// BOSS来临
	private static final int NEXT_LEVEL =5;// 下一关
	private static final int CLEARANCE = 6;
	private int level = 2;// 关卡
	private int blood = 20;
	private boolean isOver = false;

	private int y = 0;
	
	private int state = START;// 当前状态（默认为启动状态）

	private static Music music = new Music();// bgm对象

	private Boss boss;
	private Sky sky = new Sky();// 天空对象
	private Hero hero = new Hero();// 英雄机对象
	private FlyingObject[] enemies = {};// 敌人(小敌机、大敌机、小蜜蜂)数组
	private Bullet[] bullets = {};// 子弹数组
	private SmallHero[] smallHero = null;//援助小飞机数组
	

	/** 创建敌人（大敌机，小敌机，小蜜蜂）对象 */
	public FlyingObject nextOne() {
		Random r = new Random();
		int type = r.nextInt(30);
		
		if (((score >= 20&&score<=30) && boss == null)||((score>=80&&score<=90) && boss == null)||(score>=120&&score<=130)&& boss==null) {
			music.game_mucic.stop();// 战斗bgm停止
			music.alert_bgm.play();
			music.boss_bgm.loop();// boss专属bgm开始循环
			return new Boss();
		}

		if (type < 8 && boss == null) {//0-8返回大敌机
			return new BigAirplane();
		} else if (type < 25 && boss == null) {//8-25小敌机
			return new Airplane();
		} else {//25-30返回小蜜蜂
			return new Bee();
		}
	}

	private int enterIndex = 0;

	// 敌机（小敌机，大敌机，小蜜蜂）入场
	public void enterAction() {// 每10毫秒走一次
		enterIndex++;
		if (enterIndex % 100 == 0) {// 每1000毫秒走一次
			FlyingObject obj = nextOne();// 获取敌人对象
			enemies = Arrays.copyOf(enemies, enemies.length + 1);
			enemies[enemies.length - 1] = obj;
		}
	}

	private int shootIndex = 0;// 入场计数

	/** 子弹入场 */
	public void shootAction() {// 每10毫秒走一次
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// bs长度来扩容
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 追加数组
			if (smallHero != null) {
				Bullet[] smallbs1 = smallHero[0].shoot();
				Bullet[] smallbs2 = smallHero[1].shoot();

				bullets = Arrays.copyOf(bullets, bullets.length + smallbs1.length);// smallbs1长度来扩容
				System.arraycopy(smallbs1, 0, bullets, bullets.length - smallbs1.length, smallbs1.length);// 追加数组
				bullets = Arrays.copyOf(bullets, bullets.length + smallbs2.length);// smallbs2长度来扩容
				System.arraycopy(smallbs2, 0, bullets, bullets.length - smallbs2.length, smallbs2.length);// 追加数组
			}

		}
		if (shootIndex % 200 == 0) {
			for (int i = 0; i < enemies.length; i++) {
				Bullet[] bs = enemies[i].shoot();
				bullets = Arrays.copyOf(bullets, bullets.length + bs.length);// bs长度来扩容
				System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);// 追加数组
			}
		}
	}

	/** 飞行物移动 */
	public void stepAction() {
		sky.step();
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}

		for (int i = 0; i < enemies.length; i++) {

			if (enemies[i] instanceof Boss) {
				boss = (Boss) enemies[i];
				boss.step();
				continue;
			} 
			enemies[i].step();
		}

		if (smallHero != null) {
			smallHero[0].step(hero.x - 20, hero.y + 100);
			smallHero[1].step(hero.x + hero.width - 20, hero.y + 100);
		}

		
	}

	/** 删除越界的和状态是删除状态子弹和敌机----避免内存泄漏 */
	public void outOfBoundsAction() {// 没10毫秒走一次
		int index = 0;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for (int i = 0; i < enemies.length; i++) {
			FlyingObject f = enemies[i];
			if (!f.outOfBounds() && !f.isRemove()) {
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);

		Bullet[] bslives = new Bullet[bullets.length];
		int stIndex = 0;
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds() && !b.isRemove()) {
				bslives[stIndex] = b;
				stIndex++;
			}
		}
		bullets = Arrays.copyOf(bslives, stIndex);
	}

	/** 子弹与敌人和敌机子弹碰撞 */
	private int score = 0;
	private int bulletsBangsBoss = 0;

	public void bulletBangAction() {
		for (int i = 0; i < bullets.length; i++) {// 遍历所有子弹
			Bullet b = bullets[i];

			if (b.isEnem()) {// 如果是敌机的子弹
				for (int j = i; j < bullets.length; j++) {
					if (!bullets[j].isEnem() && bullets[j].isLife() && b.isLife() && bullets[j].hit(b)) {
						b.goDead();
						bullets[j].goDead();
					}
				}
			} else {// 否则就是我方的子弹
				for (int j = 0; j < enemies.length; j++) {// 遍历所有敌人
					FlyingObject f = enemies[j];

					if (b.isLife() && f.isLife() && f.hit(b)) {
						b.goDead();// 子弹去死

						if (f instanceof Enemy) {
							f.goDead();// 敌人去死
							music.boom_music.play();// 子弹爆炸声

							Enemy e = (Enemy) f;
							score += e.getScore();
						}

						if (f instanceof Award) {
							f.goDead();// 敌人去死
							music.boom_music.play();// 子弹爆炸声
							music.award_bgm.play();//得到奖励音效
							
							Award a = (Award) f;
							int type = a.getAwardType();
							switch (type) {
							case Award.FIRE:
								hero.addFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
							case Award.PROTECT:
								if (hero.getProtect() == 0) {
									hero.addProtect();
								}
								break;
							case Award.SMALLHERO:
								music.outbreak_bgm.play();
								smallHero = hero.getHeros();
							}
						}

						if (f instanceof Boss) {
							bulletsBangsBoss++;
							if (bulletsBangsBoss % 5 ==0 ) {
								blood--;
							}
							
							if (bulletsBangsBoss == 100) {
								boss.goDead();
								bulletsBangsBoss=0;
								blood = 20;
								score += boss.getScore();
								
								music.boss_boom.play();// BOSS死亡爆炸声
								music.boss_bgm.stop();// 专属BOSS_bgm停止
								music.game_mucic.loop();//主bgm循环
							}

						}
					}
				}
			}
		}
	}

	/** 英雄机与敌机和敌方子弹碰撞 */
	public void heroBangAction() {
		for (int j = 0; j < enemies.length; j++) {// 遍历所有敌人
			FlyingObject f = enemies[j];
			if (hero.isLife() && f.isLife() && f.hit(hero)) {
				if (!(f instanceof Boss)) {
					f.goDead();// 敌人去死
				} 
				if (hero.getProtect() == 1) {
					hero.subProtect();
				} else {
					//hero.subtractLife();
					hero.clearFire();
				}
			}
		}
		for (int i = 0; i < bullets.length; i++) {
			Bullet bs = bullets[i];
			if (bs.isEnem()) {
				if (bs.isLife() && hero.isLife() && hero.hit(bs)) {
					bs.goDead();
					if (hero.getProtect() == 1) {
						hero.subProtect();
					} else {
						//hero.subtractLife();
						hero.clearFire();
					}
				}
			}
		}
	}

	/** 检测游戏结束 */
	public void checkGameOverAction() {
		if (hero.getLife() <= 0) {
			state = GAME_OVER;
			music.game_mucic.stop();
			music.boss_bgm.stop();
			music.award_bgm.stop();
			music.boom_music.stop();
			music.boss_boom.stop();
			music.windows_music.loop();
		}
		if (boss!=null&&boss.isDead()&&state==RUNNING) {
			state = NEXT_LEVEL;
		}
	}

	public void action() {
		music.windows_music.loop();// 游戏未开始前的bgm开启

		// 侦听对象
		MouseAdapter m = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动事件 **/
			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {// 仅仅在运行状态下执行
					int x = e.getX();// 获取鼠标的x坐标
					int y = e.getY();// 获取鼠标的y坐标
					hero.moveTo(x, y);// 让英雄机随着鼠标移动
				}

			}

			/** 重写mouseClicked鼠标点击事件 **/
			public void mouseClicked(MouseEvent e) {
				switch (state) {// 根据当前状态做不同的处理
				case START:// 启动状态时
					state = RUNNING;
					music.game_mucic.loop();//当鼠标点击成为运行状态时，战斗bgm响起
					break;
				case GAME_OVER:// 游戏结束时
					score = 0;
					level = 0;
					blood = 20;
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					boss = null;
					smallHero = null;
					break;
				}
			}

			/** 重写mouseExited()鼠标移出事件 */
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {// 运行状态时
					music.game_mucic.stop();
					state = PAUSE;// 变为暂停状态
				}
			}

			/** 重写mouseEntered()鼠标移入事件 */
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {// 暂停状态时
					music.game_mucic.loop();
					state = RUNNING;// 变为运行状态
				}
			}
		};
		
		
		this.addMouseListener(m);// 将侦听器装到面板上
		this.addMouseMotionListener(m);// 将侦听器装到面板上

		
		Timer timer = new Timer();// 定时器
		int intervel = 10;// 定时间隔(以毫秒为单位)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {// 表示定时干的事
				if (state == RUNNING) {// 仅仅在运行状态下执行
					music.windows_music.stop();//当状态变为运行状态，大厅界面bgm停止
					enterAction();// 敌机（小敌机，大敌机，小蜜蜂,boss）入场
					shootAction();
					stepAction();
					outOfBoundsAction();
					bulletBangAction();
					heroBangAction();
					checkGameOverAction();
				}
				if (state == NEXT_LEVEL) {
					if (level==2) {
						state = CLEARANCE;
					}else {
						level += 1;
						enemies = new FlyingObject[0];
						bullets = new Bullet[0];
						boss = null;
						isOver = true;
						state = RUNNING;
					}
				}
				repaint();// 重画
				if(isOver) {
					try {
						Thread.sleep(1000);
						Thread.sleep(1000);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}		
				}
				
				if (state == CLEARANCE) {
					level = 0;
					blood = 0;
					sky = null;
					hero = null;
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					boss = null;
					smallHero = null;
				}
				
			}
		}, intervel, intervel);// 计划表
	}

	/** 重写paint()方法：g是画笔 */
	private int timeIndex = 0;

	public void paint(Graphics g) {
		if (state!=CLEARANCE) {
	
		g.drawImage(sky.getImage(level), sky.x, sky.y, this);
		g.drawImage(sky.getImage(level), sky.x, sky.getY1(), this);

		g.drawImage(hero.getImage(), hero.x, hero.y, this);
		for (int i = 0; i < enemies.length; i++) {
			FlyingObject f = enemies[i];
			if(f instanceof Boss) {
				
				g.drawImage(boss.getImage(level), boss.x, boss.y, this);
				g.drawString("BOSS: ", 350, 35);
				g.drawImage(boss.getBloodImage(blood), 400, 20, this);
				continue;
			}
			g.drawImage(f.getImage(), f.x, f.y, this);
		}
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.x, b.y, this);
		}
		if (hero.getProtect() == 1) {
			g.drawImage(hero.getImage(1), hero.x - 50, hero.y - 30, null);
		}
		if (smallHero != null) {
			g.drawImage(smallHero[0].getImage(), smallHero[0].x, smallHero[0].y, null);
			g.drawImage(smallHero[1].getImage(), smallHero[1].x, smallHero[1].y, null);
		}
		
		if(level != 0&&isOver) {
		
			g.drawImage(Images.black, 0, 0,this);
			g.drawImage(Images.load[0], 100, 250,this);
			isOver = false;
		}
		g.drawString("SCORE: " + score, 10, 25);
		g.drawString("LIFE: " + hero.getLife(), 10, 45);

		switch (state) {
		case START:
			g.drawImage(Images.start, 0, 0, 530, 800, this);
			break;
		case PAUSE:
			g.drawImage(Images.pause, 0, 0, 530, 800, this);
			break;
		case GAME_OVER:
			g.drawImage(Images.gameOver, 0, 0, 530, 800, this);
			break;
		}
		}else {
			
			g.drawImage(Images.clearance, 0, y--,530,800, this);

		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("飞机大战");
		World world = new World();
		frame.add(world);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); // 1)设置窗口可见 2）尽快的调用paint()方法
		frame.setResizable(false);// 禁止拖动窗口改变大小

		music.windows_music.loop();// 登录界面BGM开启
		world.action();
	}

}

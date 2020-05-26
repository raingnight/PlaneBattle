package airplane;

public interface Award {

	public int FIRE = 0;//活力值
	public int LIFE = 1;//命
	public int PROTECT = 2;//保护罩
	public int SMALLHERO = 3;//援助飞机
	
	public int getAwardType();
}

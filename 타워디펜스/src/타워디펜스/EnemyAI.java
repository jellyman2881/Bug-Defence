package 타워디펜스;

public class EnemyAI {
	public static EnemyRoute route;
	
	public static EnemyAIMove moveAI;
	public static int basePosX;
	public static int basePosY;
	public int id;
	
	
	public EnemyAI(Level level) {
		
		route = new EnemyRoute(level);// 벌레들이 이동해야하는 경로를 받아옴
		
		
		// 최종목적지인 base 좌표를 받아옴
		basePosX= route.base.xPos;
		basePosY= route.base.yPos;
		
		moveAI= new EnemyAIMove(0);
		
	}
	public EnemyAI(int id) {
		this.id = id;
				
	}
	public EnemyRoute getRoute() {
		return route;
		
	}
}

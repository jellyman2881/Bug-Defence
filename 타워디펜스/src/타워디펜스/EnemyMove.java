package Ÿ�����潺;

public class EnemyMove{
	User user;
	Screen screen;
	Base base;
	public EnemyMove[] enemyMap;
	Enemy enemy;
	
	double xPos;
	double yPos;
	
	boolean attack;
	int routePosX;
	int routePosY;
	int id;
	int health;
	
	public EnemyMove( Enemy enemy, SpawnPoint spawnPoint) {
		
		this.enemy = enemy;
		
 		this.routePosX = spawnPoint.getX();
		this.routePosY = spawnPoint.getY();
		
		
		this.xPos = spawnPoint.getX()*40; 
		this.yPos = spawnPoint.getY()*40;
		int id=enemy.id;
		this.attack = false;
		this.health = enemy.health;
		
		
		
		
	}
	
	public EnemyMove update() {
		EnemyMove currentEnemy = this;
		
		//���� ��ǥ������ �����ϸ� ���� �ʾҴ��� ȭ�鿡�� ���־���
		if(currentEnemy.xPos==800&&currentEnemy.yPos==480) {
			
	
			
			
			
			return this;
		}
		if(currentEnemy.health <=0) {
		
			return null;
			
		} 
	
		
		
		return currentEnemy;
		
		
	}
}

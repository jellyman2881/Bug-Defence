package Å¸¿öµðÆæ½º;

public class TowerMissile extends Tower {

	public TowerMissile(int id, int cost, int range, int damage, int maxAttackTime, int maxAttackDelay) {
		super(id, cost, range, damage, maxAttackTime, maxAttackDelay);
		
	}

	public void towerAttack(int x, int y,EnemyMove enemy) {
		for(int i =0; i<Screen.missiles.length; i++) {
		if(Screen.missiles[i] == null) {
			Screen.missiles[i]=new Missile(40+x*40,40+y*40,1,3,enemy);
			break;
		}
			
		}
		}
	}


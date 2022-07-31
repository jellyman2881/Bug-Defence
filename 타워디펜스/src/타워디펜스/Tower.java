package 타워디펜스;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Tower implements Cloneable {

	public Image texture;
	public String textureFile = "";
	public static final Tower[] towerList = new Tower[36];
	public static final Tower blueTower = new TowerLightning(0, 10, 2, 1, 200, 500).getTextureFile("blueTower");
	public static final Tower greenTower = new TowerLightning(1, 25, 3, 2, 6, 15).getTextureFile("greenTower");
	public static final Tower lightningTower = new TowerLightning(2, 50, 2, 10, 100, 3000).getTextureFile("LightningTower");
	public static final Tower yelloTower = new TowerLightning(3, 75, 4, 3, 100, 2000).getTextureFile("yelloTower");
	
	public static final Tower missileTower= new TowerMissile(4,400,2,5,100,100).getTextureFile("missileTower");;
	public int id;
	public int cost;
	public int range;

	public int damage;
	public int attackTime;
	public int attackDelay; //

	public int maxAttackTime;   //공격 시간
	public int maxAttackDelay;	//공격 후 딜레이

	public int FIRST = 1; // 목표지점까지 가장 가까운 적을 먼저 공격
	public int RANDOM = 2; // 랜덤으로 공격
	public int attackStrategy = RANDOM;
public boolean selected;
	public EnemyMove target;

	public Tower(int id, int cost, int range, int damage, int maxAttackTime, int maxAttackDelay) {

		if (towerList[id] != null) {
			System.out.println("[TOWERInitialization] Two towers with same id!" + id);
		} else {
			towerList[id] = this;
			this.id = id;
			this.cost = cost;
			this.range = range;
			this.damage = damage;
			
			this.maxAttackDelay= maxAttackDelay;
			this.maxAttackTime = maxAttackTime;
			
			this.attackTime = 0;
			this.attackDelay = 0;

		}
	}

	public EnemyMove calculateEnemy(EnemyMove[] enemies, int x, int y) {
		EnemyMove[] enemiesInRange = new EnemyMove[enemies.length];

		int towerX = x;
		int towerY = y;
		int towerRadius = this.range;
		int enemyRadius = 1;

		int enemyX;
		int enemyY;

		for (int i = 0; i < enemies.length; i++) {
			if (enemies[i] != null) {
				enemyX = (int) enemies[i].xPos / 40;
				enemyY = (int) enemies[i].yPos / 40;

				int dx = enemyX - towerX;
				int dy = enemyY - towerY;

				int dradius = towerRadius + enemyRadius;

				if ((dx * dx) + (dy * dy) < (dradius * dradius)) {
					enemiesInRange[i] = enemies[i];

				}
			}
		}

		if (this.attackStrategy == RANDOM) {
			int totalEnemies = 0;
			
			for (int i = 0; i < enemiesInRange.length; i++) {
				if (enemiesInRange[i] == null) {
					totalEnemies++;
				}
			}
			if (totalEnemies > 0) {
				int enemy = new Random().nextInt(totalEnemies);
				int enemiesTaken = 0;
				int i = 0;
				
				while (true) {
					if (enemiesTaken == enemy && enemiesInRange != null) {
						return enemiesInRange[i];
					}

					if (enemiesInRange != null) {
						enemiesTaken++;
					}
					i++;

				}
			}

		}
		return null;
	}
	public abstract void towerAttack(int x, int y,EnemyMove enemy);

	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Tower getTextureFile(String str) {
		this.textureFile = str;

		this.texture = new ImageIcon("res/tower/" + this.textureFile + ".png").getImage();

		return null;
	}
}

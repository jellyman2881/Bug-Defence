package 타워디펜스;

import java.util.Random;

public class Wave {
	Screen screen;

	int waveNumber = 0;

	int enemiesThisRound = 0;
	Enemy enemy;
	int currentDelay = 0;
	int spawnRate = 300;
	int pointThisRound;
	int currentPoints;
	int maxPoints;
	int healthup=0;
	boolean waveSpawning;

	public Wave(Screen screen) {
		this.screen = screen;

	}

	public void nextWave() {
		
		this.waveNumber++;
		this.pointThisRound = this.waveNumber*10 ;// wave마다 등장하는 벌레 수 증가
		this.currentPoints = 0;
		

		this.waveSpawning = true;
		System.out.println("wave: " + this.waveNumber);
		

		for (int i = 0; i < this.screen.enemyMap.length; i++) {
			this.screen.enemyMap[i] = null;

		}
		enemy.enemy1.health+=1 ;// wave가 증가할 수록 체력이 늘어남
		enemy.enemy2.health+=1 ;
		enemy.enemy3.health+=1 ;
		enemy.enemy4.health+=1 ;
		enemy.enemy5.health+=1 ;
	
	}

	public void spawnEnemies() {

		if (this.currentPoints < this.pointThisRound) {
			if (currentDelay < spawnRate) {
				
				currentDelay+=Screen.speed;
			} else {
				currentDelay = 0;
				System.out.println("Wave enemy spawned");

				int[] enemiesSpawnableID = new int[Enemy.enemyList.length];
				int enemiesSpawneableSoFar = 0;

				for (int i = 0; i < Enemy.enemyList.length; i++) {
					if (Enemy.enemyList[i] != null) {
						if (Enemy.enemyList[i].point + currentPoints <= this.pointThisRound
								&& Enemy.enemyList[i].point <= this.waveNumber) {
							
							

							enemiesSpawnableID[enemiesSpawneableSoFar] = Enemy.enemyList[i].id;
							enemiesSpawneableSoFar++;
						}
					}
				}
				int enemyID = new Random().nextInt(enemiesSpawneableSoFar);
				this.currentPoints += Enemy.enemyList[enemyID].point;
				this.screen.spawnEnemy(enemiesSpawnableID[enemyID]);

			}
		} else {
			this.waveSpawning = false;

		}
	}
}

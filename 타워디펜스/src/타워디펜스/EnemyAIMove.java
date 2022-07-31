package Å¸¿öµðÆæ½º;

public class EnemyAIMove extends EnemyAI {

	public EnemyAIMove(int id) {
		super(id);
	}

	public void move(EnemyMove enemy) {
		boolean flag = false;

		if (enemy.xPos - enemy.routePosX * 40 > 0) {

		}

		if (enemy.xPos % 40 == 0 && enemy.yPos % 40 == 0 && enemy.routePosX == enemy.xPos / 40
				&& enemy.routePosY == enemy.yPos / 40) {
			if (enemy.routePosX == basePosX && enemy.routePosY == basePosY) {
				enemy.attack = true;

			} else {

				if (route.route[enemy.routePosX][enemy.routePosY] == route.UP) {
					enemy.routePosY--;
				} else if (route.route[enemy.routePosX][enemy.routePosY] == route.DOWN) {
					enemy.routePosY++;

				} else if (route.route[enemy.routePosX][enemy.routePosY] == route.RIGHT) {
					enemy.routePosX++;
				} else if (route.route[enemy.routePosX][enemy.routePosY] == route.LEFT) {
					enemy.routePosX--;
				} else {
					cantFindRoute();
				}
			}
		} else {

			double xPos = enemy.xPos / 40;
			double yPos = enemy.yPos / 40;

			if (xPos > enemy.routePosX) {
				enemy.xPos -= enemy.enemy.speed / 24*Screen.speed;
				if (enemy.xPos < enemy.routePosX*40) {
					enemy.xPos = enemy.routePosX * 40;
				}
			}

			if (xPos < enemy.routePosX) {
				enemy.xPos += enemy.enemy.speed / 24*Screen.speed;
				if (enemy.xPos > enemy.routePosX*40) {
					enemy.xPos = enemy.routePosX * 40;
				}
			}
			if (yPos > enemy.routePosY) {
				enemy.yPos -= enemy.enemy.speed / 24*Screen.speed;
				if (enemy.yPos < enemy.routePosY*40) {
					enemy.yPos = enemy.routePosY * 40;
				}
			}
			if (yPos < enemy.routePosY) {
				enemy.yPos += enemy.enemy.speed / 24*Screen.speed;
				if (enemy.yPos > enemy.routePosY*40) {
					enemy.yPos = enemy.routePosY * 40;
				}
			}
		}
	}

	public void cantFindRoute() {
		System.out.println("enemy cant move");
	}
}

package Å¸¿öµðÆæ½º;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missile {
	public double x;
	public double y;
	public double direction;
	public int damage;
	public int speed;
	public EnemyMove target;
	Image texture = new ImageIcon("res/tower/bullets/missile.png").getImage();

	public Missile(double x, double y, int speed, int damage,EnemyMove target) {
		this.x = x;

		this.y = y;
		this.speed = speed;
		this.target = target;
		this.damage = damage;

		updateDirection();

	}

	public void update() {
		updateDirection();

		this.x += speed * Math.cos(direction);
		this.y += speed * Math.sin(direction);

		checkTarget();
	}

	public void checkTarget() {
		int deltaX = (int) (this.target.xPos - 40 / 2 + 80 - 5 - this.x);
		int deltaY = (int) (this.target.yPos - 40 / 2 + 80 - 15 - this.y);

		int deltaRadius = 2 + 2;
		if (deltaX * deltaX + deltaY * deltaY < deltaRadius * deltaRadius) {
this.target.health -=this.damage;
this.target=null;

		}

	}

	public void updateDirection() {
		this.direction = Math.atan2(this.target.yPos - 40 / 2 + 80 - 15 - this.y,
				this.target.xPos - 40 / 2 + 80 - 5 - this.x);
	}
}

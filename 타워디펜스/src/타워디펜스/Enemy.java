package Ÿ�����潺;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {

	public static final Enemy[] enemyList = new Enemy[10];
	public static final Enemy enemy1 = new EnemySlime(0, 1, 1, 12).getTextureFile("asd.gif");
	public static final Enemy enemy2 = new EnemySlime(1, 1, 1, 6).getTextureFile("�ĸ�.gif");
	public static final Enemy enemy3 = new EnemySlime(2, 1, 1, 8).getTextureFile("spider.gif");
	public static final Enemy enemy4 = new EnemySlime(3, 1, 1, 6).getTextureFile("���.gif");
	public static final Enemy enemy5 = new EnemySlime(4, 1, 1, 10).getTextureFile("��������.gif");

	public String textureFile = "";
	public Image texture = null;
	public int id;

	public double speed;

	public int health;
	public int point;
	

	


	public Enemy(int id, int health, int point, double speed) {
	
		if (enemyList[id] != null) {
			System.out.println("two enemy with same name"); //������ ���� �߰��� �� �ߺ��� Ȯ��
		} else {
			enemyList[id] = this;
			this.id = id;

			this.health = health;
			this.point = point;

			this.speed = speed;

		}
	}
// ���� �̹��� ���� ���߿� screen���� ������
	public Enemy getTextureFile(String str) {
		this.textureFile = str;
		this.texture = new ImageIcon("res/enemy/" + this.textureFile).getImage();
		return this;

	}

	
}

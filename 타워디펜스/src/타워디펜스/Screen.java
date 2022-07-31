package Ÿ�����潺;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.sql.SQLException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable, ActionListener {

	boolean end = false;

	Thread thread = new Thread(this);
	Tower tower;
	Screen parent = this;
	Enemy enemy;
	User user;
	Frame frame;
	Wave wave;
	EnemyAI enemyAI;
	public static int speed;
	Level level;
	LevelFile levelFile;
	public int hand = 0;
	public int handXPos = 0;
	public int handYPos = 0;

	public int mode;
	public String loginid;
	public int scene;

	public Tower selectedTower;
	public boolean running = false;

	public double towerWidth = 40;
	public double towerHeight = 40;

	public int[][] map = new int[22][14];
	public Tower[][] towerMap = new Tower[22][14];
	public Image[] terrain = new Image[100];
	public Image buttonStartGame = new ImageIcon("res/startButton.png").getImage();
	public Image speedUpButton = new ImageIcon("res/speedButton.png").getImage();
	public Image speedUpButton2 = new ImageIcon("res/speedButton2.png").getImage();
	Image background = new ImageIcon("res/background.gif").getImage();
	Image gameOver = new ImageIcon("res/gameOver.gif").getImage();
	public EnemyMove[] enemyMap = new EnemyMove[50];
	public static Missile[] missiles = new Missile[10];
	private String packagename = "Ÿ�����潺";

	TextField txtID;
	TextField txtPW;
	TextField addID;
	TextField addPW;
	TextField addNAME;

	Button b1 = new Button();
	Button b2 = new Button();
	Button b3 = new Button();
	Button b4 = new Button();
	Button b5 = new Button();
	JButton b6 = new JButton();
	Button b7 = new Button();
	JButton b8 = new JButton();
	JButton easy = new JButton();
	JButton nomal = new JButton();
	JButton hard = new JButton();

	Panel p1 = new Panel();
	Panel2 p2 = new Panel2();
	Panel3 p3 = new Panel3();
	Panel4 score = new Panel4();
	Panel5 loginPage = new Panel5();
	Panel6 signupPage = new Panel6();
	Panel7 selectModePage = new Panel7();

	public static CardLayout card;

	// ���̵� ���� ������
	class Panel7 extends JPanel {
		Font font = new Font("�޸հ��", Font.BOLD, 100);

		public void paintComponent(Graphics g) {
			g.setColor(Color.blue);
			g.setFont(font);
			g.drawString("SELECT MODE!", getWidth() / 4, getHeight() / 5);

		}
	}

	// ���� �� ���ھ� ȭ������ �Ѿ�� ������
	class Panel3 extends JPanel {
		Font font = new Font("�޸հ��", Font.BOLD, 100);

		public void paintComponent(Graphics g) {
			g.drawImage(gameOver, 0, 0, getWidth(), getHeight(), null);

			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString("����!", getWidth() / 2 - 100, getHeight() / 2);

		}

	}

	// ���â ����
	class Frame1 extends JFrame {
		public Frame1() {
			this.setSize(240, 100);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			JLabel label = new JLabel("  �ٽ� �õ��ϼ���");
			this.add(label);
		}

	}

	// ùȭ��
	class Panel extends JPanel {

		public void paintComponent(Graphics g) {

			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

		}
	}

	// ���� ���� ȭ�� (���� �߿��� ������)
	class Panel2 extends JPanel {
		Screen screen;

		public void paintComponent(Graphics g) {
			System.out.println(loginid);
			scene = 5;
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.gray);

			for (int x = 0; x < 22; x++) {
				for (int y = 0; y < 14; y++) {

					g.drawImage(terrain[map[x][y]], 40 + (x * 40), 40 + (y * 40), 40, 40, null);
					g.drawRect(40 + (x * 40), 40 + (y * 40), 40, 40);
				}
			}

			// Ÿ���� Ŭ�� ������ �� ���� ĭ

			g.setColor(Color.gray);
			g.drawRect(24 * 40, 40, frame.getWidth() - (40 * 25), 40 * 14);
			g.drawRect(24 * 40 + 20, 60, 120, 120);
			// Ÿ���� Ŭ�� ������ �� ���� ���
			if (selectedTower != null) {// Ŭ�� �� ������ �� ���̵��� �ؾ���
				g.drawImage(selectedTower.texture, 24 * 40 + 20, 60, 120, 120, null);
				g.drawString("cost: " + selectedTower.cost, 1000, 230);
				g.drawString("range: " + selectedTower.range, 1000, 300);
				g.drawString("damage: " + selectedTower.damage, 1000, 370);
				g.drawString("AttackTime: " + selectedTower.maxAttackTime, 1000, 440);
				g.drawString("AttackDelay: " + selectedTower.maxAttackDelay, 1000, 510);
			}
//==============================================================================================
			// enemies �׸� ���
			for (int i = 0; i < enemyMap.length; i++) {
				if (enemyMap[i] != null) {
					g.drawImage(enemyMap[i].enemy.texture, (int) enemyMap[i].xPos + 40, (int) enemyMap[i].yPos + 40, 40,
							40, null);

				}
			}
// ���� �ϴ� ���� ����
			g.drawRect(12, 620, 125, 50);
			g.drawString("health: " + user.player.health, 12, 640);
			g.drawRect(12, 670, 125, 50); // Health + money
			g.drawString("money: " + user.player.money, 12, 690);
			g.drawRect(12, 720, 125, 50);
			g.drawString("level: " + wave.waveNumber, 12, 740);
			g.drawString("Score: " + user.player.point * mode, 12, 760);

//����/���ǵ� �� ��ư

			g.drawRect(40 * 28, 40 * 31 / 2, 40 * 3, 80);

			boolean flag = false;// ������ ������ ��ư�� start�� ������ ������ ȭ�鿡 ������ speedup��ư���� ������ ����
			for (int i = 0; i < enemyMap.length; i++) {
				if (enemyMap[i] != null) {
					flag = true;

				}
			}
			if (!flag) {
				g.drawImage(buttonStartGame, 40 * 28, 40 * 31 / 2, 40 * 3, 80, null);
			} else {
				if (speed == 1) {
					g.drawImage(speedUpButton, 40 * 28, 40 * 31 / 2, 40 * 3, 80, null);
				} else {
					g.drawImage(speedUpButton2, 40 * 28, 40 * 31 / 2, 40 * 3, 80, null);
				}
			}

			// Ÿ������Ʈ-----------------------------------------------------------------------------------------------------------------
			for (int x = 0; x < 18; x++) {
				for (int y = 0; y < 2; y++) {

					if (Tower.towerList[x * 2 + y] != null) {
						g.drawImage(Tower.towerList[x * 2 + y].texture, 177 + (x * 40), 620 + (y * 40), 40, 40, null);

						if (Tower.towerList[x * 2 + y].cost > user.player.money) {// ���� �����ϸ� ���ϰ� �ϱ� ����
							g.setColor(new Color(255, 0, 0, 100));
							g.fillRect(177 + (x * 40), 620 + (y * 40), 40, 40);
						}

					}
					g.setColor(Color.gray);
					g.drawRect(177 + (x * 40), 620 + (y * 40), 40, 40);
				}
			}

			// -------------------------------------------------------------------------------------------------------------------------

			// Hand-----------------------------------------------------------------------------------------------------------------------
			for (int x = 0; x < 22; x++) {
				for (int y = 0; y < 14; y++) {
					if (towerMap[x][y] != null) {

						if (selectedTower == towerMap[x][y]) {
							g.setColor(new Color(64, 64, 64, 64)); // ������64�� ����
							g.fillOval((int) towerWidth + (x * 40) - (towerMap[x][y].range * 40), // Ÿ���� ������ ȸ�� �� ���� ����
									40 + (y * 40) - (towerMap[x][y].range * 40), towerMap[x][y].range * 2 * 40 + 40,
									towerMap[x][y].range * 2 * 40 + (int) towerHeight);
						}
						g.drawImage(Tower.towerList[towerMap[x][y].id].texture,
								(int) towerWidth + (x * (int) towerWidth), (int) towerHeight + (y * (int) towerHeight),
								(int) towerWidth, (int) towerHeight, null);
						// Attack Enemy

						if (towerMap[x][y].target != null) {
							if (towerMap[x][y] instanceof TowerLightning) {
								g.setColor(Color.red);
								g.drawLine((int) towerWidth + (x * (int) towerWidth) + 20,
										(int) towerHeight + (y * (int) towerHeight) + 20,
										40 + (int) towerMap[x][y].target.xPos + 20,
										40 + (int) towerMap[x][y].target.yPos + 20);
							}
						}

					}
				}
			}

			// Missiles ��������
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < missiles.length; i++) {
				if (missiles[i] != null) {
					g2d.rotate(missiles[i].direction + Math.toRadians(90), (int) missiles[i].x, (int) missiles[i].y);

					g.drawImage(missiles[i].texture, (int) missiles[i].x, (int) missiles[i].y, 14, 30, null);
					g2d.rotate(-missiles[i].direction + Math.toRadians(90), (int) missiles[i].x, (int) missiles[i].y);

				}
			}
			// =========================================================================================================

			if (hand != 0 && Tower.towerList[hand - 1] != null) {
				g.drawImage(Tower.towerList[hand - 1].texture, handXPos - 20, handYPos - 20, 40, 40, null);
			}

			// life�� 0���� �۾����� score�� �����ϴµ� ������ score���� ������ �������� �ʿ�

			if (user.player.health <= 0) {
				try {
					System.out.println(mode);

					String sql = "select * from player where userid ='" + loginid + "'";

					Frame.rs = Frame.stmt.executeQuery(sql);

					if (Frame.rs.next()) {

						int score = Frame.rs.getInt("score");

						if (user.player.point * mode > score) {

							sql = "update player set score =" + user.player.point * mode + " where userid='" + loginid
									+ "'";

							Frame.stmt.executeUpdate(sql);

						}

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block

					e.printStackTrace();

				}

				card.show(parent, "a3");

			}
		}
	}

	// ���� �� ��ŷ�� Ȯ���ϴ� ������
	class Panel4 extends JPanel {
		Font font = new Font("�޸հ��", Font.BOLD, 20);

		public void paintComponent(Graphics g) {
			g.setFont(font);
			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));
			g.setColor(Color.red);
			g.drawString("���             ���̵�                        �̸�                 ����", getWidth() / 3, 140);
			g.setColor(Color.white);
			try {

				String sql = "select * from (select * from player order by score desc ) where rownum<=15";// ȸ���� �������� ��
																											// ���� �� ��
																											// ������ ����������
																											// ����Ͽ� ����
																											// 15��
																											// �����ֱ�� ��
																											// score
																											// default����
																											// 0���� �����ؾ�
																											// �������� ���� ��
																											// �����ϰ� ���� ��
																											// �ϸ� null��
																											// �ְ����� ����

				Frame.rs = Frame.stmt.executeQuery(sql);
				int i = 1;
				int y = 200;

				while (Frame.rs.next()) {

					String id = Frame.rs.getString("userid");
					String name = Frame.rs.getString("name");
					int score = Frame.rs.getInt("score");

					g.drawString(i + "��               " + id + "                    " + name + "                       "
							+ score, getWidth() / 3, y);

					i++;
					y += 35;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// �α��� ������
	class Panel5 extends JPanel {
		public void paintComponent(Graphics g) {

			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));

		}
	}

	// ȸ������ ������
	class Panel6 extends JPanel {
		public void paintComponent(Graphics g) {

			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));

		}
	}

	// Frame ���� �Ѿ��
	public Screen(Frame frame) {

		this.frame = frame;

		this.frame.addKeyListener(new KeyHandler(this));
		this.frame.addMouseListener(new MouseHandler(this));
		this.frame.addMouseMotionListener(new MouseHandler(this));
		// p1.setBackground(Color.blue);

		// b1 = new Button("���ӽ���");
		selectModePage.setBackground(Color.black);
		b2 = new Button("����2");
		b3 = new Button("score");
		b4 = new Button("Main");

		b6 = new JButton("signup");
		b7 = new Button("�����ϱ�");
		b8 = new JButton("���ӽ���");
		easy = new JButton("EASY");
		nomal = new JButton("NOMAL");
		hard = new JButton("HARD");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);// cardLayout�� ���� ����
		b4.addActionListener(this);

		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		easy.addActionListener(this);
		nomal.addActionListener(this);
		hard.addActionListener(this);

		b1.setPreferredSize(new Dimension(50, 50));
		b2.setPreferredSize(new Dimension(50, 50));
		b3.setPreferredSize(new Dimension(50, 50));
		b4.setPreferredSize(new Dimension(50, 50));
		selectModePage.setLayout(null);

		easy.setBounds(100, 200, 400, 400);
		nomal.setBounds(550, 200, 400, 400);
		hard.setBounds(1000, 200, 400, 400);
		/*
		 * easy.setPreferredSize(new Dimension(100, 50)); nomal.setPreferredSize(new
		 * Dimension(100, 50)); hard.setPreferredSize(new Dimension(100, 50));
		 */

		b6.setPreferredSize(new Dimension(50, 50));
		b7.setPreferredSize(new Dimension(50, 50));
		b8.setPreferredSize(new Dimension(50, 50));

		selectModePage.add(easy);
		selectModePage.add(nomal);
		selectModePage.add(hard);

		// ùȭ��
		p1.setLayout(null);
		b5 = new Button("login");
		b5.addActionListener(this);
		b5.setBackground(Color.cyan);
		b5.setBounds(600, 600, 300, 100);
		p1.add(b5);

		p3.add(b3);

		score.add(b4);

		// �α��� ������
		loginPage.setLayout(null);
		txtID = new TextField(20);
		txtID.setBounds(640, 200, 300, 20);
		txtPW = new TextField(20);
		txtPW.setBounds(640, 300, 300, 20);
		txtPW.setEchoChar('*'); // �н����带 �Է� ���� �ؽ�Ʈ�� ������ ���� �Է¹��� ���� ��� ����� ���� ����
		Label id = new Label("�� �� �� :");
		Label pw = new Label("�н� ���� :");
		loginPage.add(id); // �� ��ü ���� �� �гο� �߰�

		id.setBounds(550, 200, 70, 20);
		pw.setBounds(550, 300, 70, 20);
		b8.setBounds(650, 400, 240, 120);
		b6.setBounds(650, 550, 240, 120);
		id.setBackground(Color.gray);
		pw.setBackground(Color.gray);
		b8.setBackground(Color.yellow);
		b6.setBackground(Color.yellow);

		loginPage.add(txtID); // ���̵� �Է¹��� �ؽ�Ʈ �ʵ带 �гο� �߰�
		loginPage.add(pw);
		loginPage.add(txtPW);
		loginPage.add(b8);
		loginPage.add(b6);
		// loginPage.add(b3);

		// ȸ������ ������
		addID = new TextField(20);
		addNAME = new TextField(20);
		addPW = new TextField(20);
		addPW.setEchoChar('*');

		signupPage.add(new Label("�� �� �� :")); // �� ��ü ���� �� �гο� �߰�
		signupPage.add(addID); // ���̵� �Է¹��� �ؽ�Ʈ �ʵ带 �гο� �߰�
		signupPage.add(new Label("�н� ���� :"));
		signupPage.add(addPW);
		signupPage.add(new Label("NAME :"));
		signupPage.add(addNAME);
		signupPage.add(b7);

		// frame.add(p1);
		this.card = new CardLayout(0, 0);
		setLayout(card);
		add("a1", p1);
		add("a2", p2);
		add("a3", p3);
		add("a4", score);
		add("a5", loginPage);
		add("a6", signupPage);
		add("a7", selectModePage);

		thread.start();

	}

	// ===========================================================================================================================================================
	public void loadGame() {
		user = new User(this);
		levelFile = new LevelFile();
		wave = new Wave(this);
		ClassLoader cl = this.getClass().getClassLoader();
// terrain.png�� �ȼ� ���� �̿��Ͽ� ���ϴ� ����� ����
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				terrain[x + (y * 10)] = new ImageIcon(cl.getResource(packagename + "/terrain.png")).getImage();
				terrain[x + (y * 10)] = createImage(new FilteredImageSource(terrain[x + (y * 10)].getSource(),
						new CropImageFilter(x * 26, y * 25, 25, 25)));
			}
		}
		running = true; // ture�϶��� ������ ����

	}

	// ==============================================================================================================================

	// ���̵� �����ϸ� ������ ���۵Ǵ� �κ�
	public void startGame(User user, String level) {

		user.createPlayer();
		this.level = levelFile.getLevel(level);
		this.level.findSpawnPoint();
		this.map = this.level.map;

		this.enemyAI = new EnemyAI(this.level);

		parent.add("a2", new Panel2());

		card.show(this, "a2");
		this.wave.waveNumber = 0;
		this.speed = 1;

	}
	// ==============================================================================================================================

	// ==============================================================================================================================
	public void run() {
		System.out.println("Success");

		loadGame();

		while (running) {// running�� true�϶��� ������ ���� �ƴϸ� ����

			repaint();

			update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
		System.exit(0); // ���������� ��� 0 �������ϰ�� 1

	}

	public void enemyUpdate() {
		for (int i = 0; i < enemyMap.length; i++) {
			if (enemyMap[i] != null) {
				if (!enemyMap[i].attack) {
					EnemyAI.moveAI.move(enemyMap[i]);
				}
				int moneyEarnedIfDead = Enemy.enemyList[enemyMap[i].id].point;
				int getPoint = Enemy.enemyList[enemyMap[i].id].point;

				enemyMap[i] = enemyMap[i].update(); // �׾����� �� �׾������� Ȯ���ϴ� ������Ʈ
				if (enemyMap[i] == null) {
					user.player.money += moneyEarnedIfDead;
					user.player.point += getPoint;

				} else if (enemyMap[i].xPos == 800 && enemyMap[i].yPos == 480) {
					user.player.health--;
					enemyMap[i] = null;
				}

			}

		}
	}

	public void towerUpdate() {
		for (int x = 0; x < 22; x++) {
			for (int y = 0; y < 14; y++) {
				if (towerMap[x][y] != null) {
					towerAttack(x, y);

				}
			}
		}
	}

	public void towerAttack(int x, int y) {// towerUpdate���� �޾ƿ�
		if (this.towerMap[x][y].target == null) {
			// Ÿ�� ã��
			if (this.towerMap[x][y].attackDelay > this.towerMap[x][y].maxAttackDelay) {
				EnemyMove currentEnemy = this.towerMap[x][y].calculateEnemy(enemyMap, x, y);
				if (currentEnemy != null) {
					this.towerMap[x][y].towerAttack(x, y, currentEnemy);// ����
					System.out.println("health" + currentEnemy.health);

					currentEnemy.health -= this.towerMap[x][y].damage;// ������ �������� ������ ü���� ���

					this.towerMap[x][y].target = currentEnemy;
					this.towerMap[x][y].attackTime = 0;
					this.towerMap[x][y].attackDelay = 0;

					System.out.println("Tower enemey attacked" + currentEnemy.health);

				}

			} else {
				this.towerMap[x][y].attackDelay += speed;
			}
		} else {
			if (this.towerMap[x][y].attackTime < this.towerMap[x][y].maxAttackTime) {
				this.towerMap[x][y].attackTime += speed;

			} else {
				this.towerMap[x][y].target = null;

			}
		}
	}

	public void missileUpdate() {
		for (int i = 0; i < missiles.length; i++) {
			if (missiles[i] != null) {
				missiles[i].update();

				if (missiles[i].target == null) {
					missiles[i] = null;

				}
			}
		}
	}

	public void update() {

		enemyUpdate();
		towerUpdate();
		missileUpdate();
		if (wave.waveSpawning) {

			wave.spawnEnemies();
		}
	}

	// ==============================================================================================================================

	public void spawnEnemy(int enemyID) {
		for (int i = 0; i < enemyMap.length; i++) {

			if (enemyMap[i] == null) {
				enemyMap[i] = new EnemyMove(Enemy.enemyList[enemyID], level.spawnPoint);
				break;

			}
		}
	}

	// ==============================================================================================================================
	public void placeTower(int x, int y) {
		int xPos = (x - 40) / 40;// ������ ��ҿ� Ÿ���� ������ �׸�ĭ �ȿ� �� �°� ��ġ�ϱ� ���� ������ 40�� �ߴ�
		int yPos = (y - 40) / 40;
//xPos-=1;// ���� ���� �߸��Ѱ� ����
		yPos -= 1;
		if (xPos >= 0 && xPos <= 22 && yPos <= 14 && yPos >= 0) {// �ش� ���� �ȿ��� ��ġ�� �� �ֵ��� ��
			if (towerMap[xPos][yPos] == null && map[xPos][yPos] == 0) {
				user.player.money -= Tower.towerList[hand - 1].cost;// Ÿ�� ��� ���� �پ��

				towerMap[xPos][yPos] = (Tower) Tower.towerList[hand - 1].clone();
				selectedTower = towerMap[xPos][yPos];
			}
		}

	}

	// ==============================================================================================================================
	public void selectTower(int x, int y) {// ��ġ�� Ÿ���� Ŭ���ϸ� �����ʿ� �� ������ ���´�
		int xPos = (x - 40) / 40;
		int yPos = (y - 40) / 40;

		if (xPos > 0 && xPos <= 22 && yPos <= 14 && yPos > 0) {

			yPos -= 1;
			selectedTower = towerMap[xPos][yPos];
		} else {
			if (!(xPos >= 24 && xPos <= 30 && yPos > 1 && yPos < 14)) {
				selectedTower = null;
			}

		}
	}

	public void hitStartRoundButton(int x, int y) {

		if (x > 40 * 28 && x < 40 * 28 + 120) {
			if (y > 40 * 32 / 2 + 10 && y < 40 * 31 / 2 + 110) {
				boolean flag = false;
				for (int i = 0; i < enemyMap.length; i++) {
					if (enemyMap[i] != null) {// ȭ�鿡 ������ ��������
						flag = true;
					}
				}
				if (!flag) {// �������� ���� ���带 ����

					wave.nextWave();
				} else {
					if (speed == 1) {
						speed = 4;
					} else {
						speed = 1;
					}
				}
			}
		}
	}

	// ==============================================================================================================================
	public class MouseHeld {
		boolean mouseDown = false;

		public void mouseMoved(MouseEvent e) {

			handXPos = e.getXOnScreen();
			handYPos = e.getYOnScreen() - 20;

		}

		public void updateMouse(MouseEvent e) {

			if (scene == 5) {// Panel2������ �۵��ϵ��� ����

				if (mouseDown && hand == 0) {

					// 1���ٿ� ��ǥ�� ��ġ�ϱ� ���� %�� ���
					// 2���ٿ� ��ǥ�� ��ġ�ϱ� ���� %�� ��� ���� �����ϸ� �밢������ ��ǥ�� ������

					// 1100
					// 1100 ���� �̷��� �Ǿ�� �ϴµ�

					// 1000
					// 0100
					// 0010
					// 0001 �̷������� �Ǵ°� �����ϱ� ����

					for (int i = 0; i < Tower.towerList.length; i++) {
						if (e.getXOnScreen() >= 177 + 40 * (int) (i / 2)
								&& e.getXOnScreen() <= 177 + 40 + 40 * (int) (i / 2)
								&& e.getYOnScreen() >= 640 + 40 * (int) (i % 2)
								&& e.getYOnScreen() <= 640 + 40 + 40 * (int) (i % 2)) {

							if (user.player.money >= Tower.towerList[i].cost) {
								System.out.println("Ÿ���� ����ϴ�!");
								hand = i + 1;
								return;

							}

						}
					}

				}
			}

		}

		public void mouseDown(MouseEvent e) {
			mouseDown = true;

			if (hand != 0) {
				placeTower(e.getXOnScreen(), e.getYOnScreen() + 15);// ���⸦ �����ϰ� ��ġ��ų ��
				hand = 0;

			} else {
				selectTower(e.getX(), e.getY());
				hitStartRoundButton(e.getX(), e.getY());
			}
			updateMouse(e);
		}
	}
	// ==============================================================================================================================

	public class KeyTyped {
		public void keyESC() {
			running = false;// �ٷ� ����
		}

		public void keyEnter() {

			wave.nextWave();// �ڵ����� ���̺갡 �����Ѵ�

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// �̿Ͱ��� ������� �ٸ� ī��Panel���� ��ȯ�� �����ϴ�.
		String str = e.getActionCommand();
		if (str.equals("���ӽ���")) {

			try {
				Frame.conn = Frame.makeConnection();
				Frame.stmt = Frame.conn.createStatement();
				loginid = txtID.getText();
				System.out.println(loginid);
				String loginpw = txtPW.getText();
				System.out.println(loginpw);
				String sql = "select * from player where userid='" + loginid + "'";
				Frame.rs = Frame.stmt.executeQuery(sql);

				while (Frame.rs.next()) {

					String id = Frame.rs.getString("USERID");
					String pw = Frame.rs.getString("USERPW");
					System.out.println(id + pw);

					if (id.equals(loginid) && pw.equals(loginpw)) {// �Է°��� ��� ����Ǿ� �ִ� ���� ���Ͽ� �Ѵ� ���̸� �α��� ����
						System.out.println("�α��� ����!");
						card.show(this, "a7");

					} else {

						Frame1 f = new Frame1();
						add("f1", f);// �ƴҰ�� Frame1�� �����
						card.show(this, "f1");
						break;
					}
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("SQL ���� �߻�");
			}

			// ���̵� ���� ������
		} else if (str.equals("EASY")) {
			enemy.enemyList[0].speed = 6;
			enemy.enemyList[1].speed = 3;
			enemy.enemyList[2].speed = 4;
			enemy.enemyList[3].speed = 3;
			enemy.enemyList[4].speed = 5;
			mode = 1;
			user.startingHelth += 75;
			startGame(user, "Level1");
		} else if (str.equals("NOMAL")) {
			enemy.enemyList[0].speed = 12;
			enemy.enemyList[1].speed = 6;
			enemy.enemyList[2].speed = 8;
			enemy.enemyList[3].speed = 6;
			enemy.enemyList[4].speed = 10;
			mode = 2;
			startGame(user, "Level1");
		} else if (str.equals("HARD")) {
			user.startingHelth -= 20;
			enemy.enemyList[0].speed = 18;
			enemy.enemyList[1].speed = 9;
			enemy.enemyList[2].speed = 12;
			enemy.enemyList[3].speed = 9;
			enemy.enemyList[4].speed = 15;
			mode = 3;

			startGame(user, "Level1");
		}

		else if (str.equals("score")) {

			card.show(this, "a4");
		} else if (str.equals("Main")) {

			Frame hi;
			hi = new Frame() {
			}; // ������ ��ü���� ��� �ʱ�ȭ�ϰ� ���� �����ϱ� ����

			frame.dispose();
			// running=false;
			// card.show(this, "a1");
		} else if (str.equals("login")) {
			card.show(this, "a5");
		} else if (str.equals("signup")) {
			card.show(this, "a6");
		} else if (str.equals("�����ϱ�")) {

			try {
				Frame.conn = Frame.makeConnection();
				Frame.stmt = Frame.conn.createStatement();

				String id = addID.getText();
				System.out.println(id);
				String pw = addPW.getText();
				System.out.println(pw);
				String name = addNAME.getText();

				String sql = "insert into player(userid, userpw,name) values('" + id + "', '" + pw + "','" + name
						+ "')";
				int result = Frame.stmt.executeUpdate(sql);
				card.show(this, "a5");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("�ߺ��Դϴ�");// id�� pk�̱� ������ �ߺ� ������ ���� ������ �������� �Ѿ�
				Frame1 f = new Frame1();
				add("f1", f);
				card.show(this, "f1");

				e1.printStackTrace();

			}

		}
	}

}

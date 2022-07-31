package 타워디펜스;

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
	private String packagename = "타워디펜스";

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

	// 난이도 설정 페이지
	class Panel7 extends JPanel {
		Font font = new Font("휴먼고딕", Font.BOLD, 100);

		public void paintComponent(Graphics g) {
			g.setColor(Color.blue);
			g.setFont(font);
			g.drawString("SELECT MODE!", getWidth() / 4, getHeight() / 5);

		}
	}

	// 종료 후 스코어 화면으로 넘어가는 페이지
	class Panel3 extends JPanel {
		Font font = new Font("휴먼고딕", Font.BOLD, 100);

		public void paintComponent(Graphics g) {
			g.drawImage(gameOver, 0, 0, getWidth(), getHeight(), null);

			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString("실패!", getWidth() / 2 - 100, getHeight() / 2);

		}

	}

	// 경고창 띄우기
	class Frame1 extends JFrame {
		public Frame1() {
			this.setSize(240, 100);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			JLabel label = new JLabel("  다시 시도하세요");
			this.add(label);
		}

	}

	// 첫화면
	class Panel extends JPanel {

		public void paintComponent(Graphics g) {

			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

		}
	}

	// 게임 실행 화면 (가장 중요한 페이지)
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

			// 타워를 클릭 했을때 상세 정보 칸

			g.setColor(Color.gray);
			g.drawRect(24 * 40, 40, frame.getWidth() - (40 * 25), 40 * 14);
			g.drawRect(24 * 40 + 20, 60, 120, 120);
			// 타워를 클릭 했을때 상세 정보 출력
			if (selectedTower != null) {// 클릭 안 했을땐 안 보이도록 해야함
				g.drawImage(selectedTower.texture, 24 * 40 + 20, 60, 120, 120, null);
				g.drawString("cost: " + selectedTower.cost, 1000, 230);
				g.drawString("range: " + selectedTower.range, 1000, 300);
				g.drawString("damage: " + selectedTower.damage, 1000, 370);
				g.drawString("AttackTime: " + selectedTower.maxAttackTime, 1000, 440);
				g.drawString("AttackDelay: " + selectedTower.maxAttackDelay, 1000, 510);
			}
//==============================================================================================
			// enemies 그림 출력
			for (int i = 0; i < enemyMap.length; i++) {
				if (enemyMap[i] != null) {
					g.drawImage(enemyMap[i].enemy.texture, (int) enemyMap[i].xPos + 40, (int) enemyMap[i].yPos + 40, 40,
							40, null);

				}
			}
// 왼쪽 하단 유저 정보
			g.drawRect(12, 620, 125, 50);
			g.drawString("health: " + user.player.health, 12, 640);
			g.drawRect(12, 670, 125, 50); // Health + money
			g.drawString("money: " + user.player.money, 12, 690);
			g.drawRect(12, 720, 125, 50);
			g.drawString("level: " + wave.waveNumber, 12, 740);
			g.drawString("Score: " + user.player.point * mode, 12, 760);

//시작/스피드 업 버튼

			g.drawRect(40 * 28, 40 * 31 / 2, 40 * 3, 80);

			boolean flag = false;// 벌레가 없으면 버튼이 start로 나오고 벌레가 화면에 있으면 speedup버튼으로 나오기 위함
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

			// 타워리스트-----------------------------------------------------------------------------------------------------------------
			for (int x = 0; x < 18; x++) {
				for (int y = 0; y < 2; y++) {

					if (Tower.towerList[x * 2 + y] != null) {
						g.drawImage(Tower.towerList[x * 2 + y].texture, 177 + (x * 40), 620 + (y * 40), 40, 40, null);

						if (Tower.towerList[x * 2 + y].cost > user.player.money) {// 돈이 부족하면 못하게 하기 위함
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
							g.setColor(new Color(64, 64, 64, 64)); // 마지막64는 투명도
							g.fillOval((int) towerWidth + (x * 40) - (towerMap[x][y].range * 40), // 타워의 반투명 회색 원 범위 설정
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

			// Missiles 각도조절
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

			// life가 0보다 작아지면 score를 저장하는데 기존의 score보다 작으면 저장하지 않움

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

	// 점수 및 랭킹을 확인하는 페이지
	class Panel4 extends JPanel {
		Font font = new Font("휴먼고딕", Font.BOLD, 20);

		public void paintComponent(Graphics g) {
			g.setFont(font);
			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));
			g.setColor(Color.red);
			g.drawString("등수             아이디                        이름                 점수", getWidth() / 3, 140);
			g.setColor(Color.white);
			try {

				String sql = "select * from (select * from player order by score desc ) where rownum<=15";// 회원이 많아지면 다
																											// 보여 줄 수
																											// 없으니 서브쿼리를
																											// 사용하여 상위
																											// 15명만
																											// 보여주기로 함
																											// score
																											// default값을
																											// 0으로 설정해야
																											// 오더바이 했을 때
																											// 적절하게 나옴 안
																											// 하면 null이
																											// 최고값으로 나옴

				Frame.rs = Frame.stmt.executeQuery(sql);
				int i = 1;
				int y = 200;

				while (Frame.rs.next()) {

					String id = Frame.rs.getString("userid");
					String name = Frame.rs.getString("name");
					int score = Frame.rs.getInt("score");

					g.drawString(i + "등               " + id + "                    " + name + "                       "
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

	// 로그인 페이지
	class Panel5 extends JPanel {
		public void paintComponent(Graphics g) {

			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));

		}
	}

	// 회원가입 페이지
	class Panel6 extends JPanel {
		public void paintComponent(Graphics g) {

			g.setColor(Color.gray);
			g.fillRect(500, 100, getWidth() / 3, (int) (getHeight() / 1.3));

		}
	}

	// Frame 에서 넘어옴
	public Screen(Frame frame) {

		this.frame = frame;

		this.frame.addKeyListener(new KeyHandler(this));
		this.frame.addMouseListener(new MouseHandler(this));
		this.frame.addMouseMotionListener(new MouseHandler(this));
		// p1.setBackground(Color.blue);

		// b1 = new Button("게임시작");
		selectModePage.setBackground(Color.black);
		b2 = new Button("변경2");
		b3 = new Button("score");
		b4 = new Button("Main");

		b6 = new JButton("signup");
		b7 = new Button("가입하기");
		b8 = new JButton("게임시작");
		easy = new JButton("EASY");
		nomal = new JButton("NOMAL");
		hard = new JButton("HARD");

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);// cardLayout을 쓰기 위함
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

		// 첫화면
		p1.setLayout(null);
		b5 = new Button("login");
		b5.addActionListener(this);
		b5.setBackground(Color.cyan);
		b5.setBounds(600, 600, 300, 100);
		p1.add(b5);

		p3.add(b3);

		score.add(b4);

		// 로그인 페이지
		loginPage.setLayout(null);
		txtID = new TextField(20);
		txtID.setBounds(640, 200, 300, 20);
		txtPW = new TextField(20);
		txtPW.setBounds(640, 300, 300, 20);
		txtPW.setEchoChar('*'); // 패스워드를 입력 받을 텍스트는 보안을 위해 입력받은 문자 대신 출력할 문자 지정
		Label id = new Label("아 이 디 :");
		Label pw = new Label("패스 워드 :");
		loginPage.add(id); // 라벨 객체 생성 후 패널에 추가

		id.setBounds(550, 200, 70, 20);
		pw.setBounds(550, 300, 70, 20);
		b8.setBounds(650, 400, 240, 120);
		b6.setBounds(650, 550, 240, 120);
		id.setBackground(Color.gray);
		pw.setBackground(Color.gray);
		b8.setBackground(Color.yellow);
		b6.setBackground(Color.yellow);

		loginPage.add(txtID); // 아이디 입력받을 텍스트 필드를 패널에 추가
		loginPage.add(pw);
		loginPage.add(txtPW);
		loginPage.add(b8);
		loginPage.add(b6);
		// loginPage.add(b3);

		// 회원가입 페이지
		addID = new TextField(20);
		addNAME = new TextField(20);
		addPW = new TextField(20);
		addPW.setEchoChar('*');

		signupPage.add(new Label("아 이 디 :")); // 라벨 객체 생성 후 패널에 추가
		signupPage.add(addID); // 아이디 입력받을 텍스트 필드를 패널에 추가
		signupPage.add(new Label("패스 워드 :"));
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
// terrain.png의 픽셀 값을 이용하여 원하는 결과를 얻음
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				terrain[x + (y * 10)] = new ImageIcon(cl.getResource(packagename + "/terrain.png")).getImage();
				terrain[x + (y * 10)] = createImage(new FilteredImageSource(terrain[x + (y * 10)].getSource(),
						new CropImageFilter(x * 26, y * 25, 25, 25)));
			}
		}
		running = true; // ture일때만 쓰레드 실행

	}

	// ==============================================================================================================================

	// 난이도 설정하면 게임이 시작되는 부분
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

		while (running) {// running이 true일때만 스레드 실행 아니면 멈춤

			repaint();

			update();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
		System.exit(0); // 정상종료일 경우 0 비정상일경우 1

	}

	public void enemyUpdate() {
		for (int i = 0; i < enemyMap.length; i++) {
			if (enemyMap[i] != null) {
				if (!enemyMap[i].attack) {
					EnemyAI.moveAI.move(enemyMap[i]);
				}
				int moneyEarnedIfDead = Enemy.enemyList[enemyMap[i].id].point;
				int getPoint = Enemy.enemyList[enemyMap[i].id].point;

				enemyMap[i] = enemyMap[i].update(); // 죽었는지 안 죽었는지만 확인하는 업데이트
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

	public void towerAttack(int x, int y) {// towerUpdate에서 받아옴
		if (this.towerMap[x][y].target == null) {
			// 타겟 찾기
			if (this.towerMap[x][y].attackDelay > this.towerMap[x][y].maxAttackDelay) {
				EnemyMove currentEnemy = this.towerMap[x][y].calculateEnemy(enemyMap, x, y);
				if (currentEnemy != null) {
					this.towerMap[x][y].towerAttack(x, y, currentEnemy);// 공격
					System.out.println("health" + currentEnemy.health);

					currentEnemy.health -= this.towerMap[x][y].damage;// 무기의 데미지와 벌레의 체력을 계산

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
		int xPos = (x - 40) / 40;// 적당한 장소에 타워를 놓으면 네모칸 안에 딱 맞게 배치하기 위해 나누기 40을 했다
		int yPos = (y - 40) / 40;
//xPos-=1;// 뭔가 설정 잘못한거 같음
		yPos -= 1;
		if (xPos >= 0 && xPos <= 22 && yPos <= 14 && yPos >= 0) {// 해당 범위 안에만 위치할 수 있도록 함
			if (towerMap[xPos][yPos] == null && map[xPos][yPos] == 0) {
				user.player.money -= Tower.towerList[hand - 1].cost;// 타워 사면 돈이 줄어듬

				towerMap[xPos][yPos] = (Tower) Tower.towerList[hand - 1].clone();
				selectedTower = towerMap[xPos][yPos];
			}
		}

	}

	// ==============================================================================================================================
	public void selectTower(int x, int y) {// 배치한 타워를 클릭하면 오른쪽에 상세 설명이 나온다
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
					if (enemyMap[i] != null) {// 화면에 벌레가 있을때만
						flag = true;
					}
				}
				if (!flag) {// 없을때는 다음 라운드를 실행

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

			if (scene == 5) {// Panel2에서만 작동하도록 설정

				if (mouseDown && hand == 0) {

					// 1번줄에 좌표를 배치하기 위해 %를 사용
					// 2번줄에 좌표를 배치하기 위해 %를 사용 만약 사용안하면 대각선으로 좌표가 설정됨

					// 1100
					// 1100 원래 이렇게 되어야 하는데

					// 1000
					// 0100
					// 0010
					// 0001 이런식으로 되는걸 방지하기 위해

					for (int i = 0; i < Tower.towerList.length; i++) {
						if (e.getXOnScreen() >= 177 + 40 * (int) (i / 2)
								&& e.getXOnScreen() <= 177 + 40 + 40 * (int) (i / 2)
								&& e.getYOnScreen() >= 640 + 40 * (int) (i % 2)
								&& e.getYOnScreen() <= 640 + 40 + 40 * (int) (i % 2)) {

							if (user.player.money >= Tower.towerList[i].cost) {
								System.out.println("타워를 샀습니다!");
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
				placeTower(e.getXOnScreen(), e.getYOnScreen() + 15);// 무기를 구매하고 위치시킬 때
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
			running = false;// 바로 종료
		}

		public void keyEnter() {

			wave.nextWave();// 자동으로 웨이브가 증가한다

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 이와같은 방법으로 다른 카드Panel로의 변환이 가능하다.
		String str = e.getActionCommand();
		if (str.equals("게임시작")) {

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

					if (id.equals(loginid) && pw.equals(loginpw)) {// 입력값과 디비에 저장되어 있는 값을 비교하여 둘다 참이면 로그인 성공
						System.out.println("로그인 성공!");
						card.show(this, "a7");

					} else {

						Frame1 f = new Frame1();
						add("f1", f);// 아닐경우 Frame1을 띄워줌
						card.show(this, "f1");
						break;
					}
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("SQL 예외 발생");
			}

			// 난이도 설정 페이지
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
			}; // 기족의 객체들을 모두 초기화하고 새로 시작하기 위해

			frame.dispose();
			// running=false;
			// card.show(this, "a1");
		} else if (str.equals("login")) {
			card.show(this, "a5");
		} else if (str.equals("signup")) {
			card.show(this, "a6");
		} else if (str.equals("가입하기")) {

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
				System.out.println("중복입니다");// id가 pk이기 떄문에 중복 에러가 나면 무조건 이쪽으로 넘어
				Frame1 f = new Frame1();
				add("f1", f);
				card.show(this, "f1");

				e1.printStackTrace();

			}

		}
	}

}

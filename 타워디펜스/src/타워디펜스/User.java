package 타워디펜스;

public class User {

	private Screen screen;

	Player player;
	//초기상태
	public int startingMoney = 25;
	public int startingHelth = 25;
	public int startingPoint=0;

	public User(Screen screen) {
		this.screen = screen;

	//	this.screen.scene = 0; // sets scene to main menu

	}

	public void createPlayer() {
		this.player = new Player(this);

	}
}

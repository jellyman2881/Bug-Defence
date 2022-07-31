package Å¸¿öµğÆæ½º;

public class Player{
	
	int health;
	int money;
	int point;
	
	
	public Player(User user) {
		this.money= user.startingMoney;
		this.health= user.startingHelth;
		this.point = user.startingPoint;
	}

}

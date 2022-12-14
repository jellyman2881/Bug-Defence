package 타워디펜스;

public class EnemyRoute {

	public  Level level;
	int[][] route = new int[22][14];

	int RIGHT = 1;
	int DOWN = 2;
	int LEFT = 3;
	int UP = 4;

	int lastPos = -1;

	int xPos;
	int yPos;

	int baseBlock = 3;

	Base base;

	public EnemyRoute(Level level) {
		this.level = level;

		this.xPos = this.level.spawnPoint.getX();
		this.yPos = this.level.spawnPoint.getY();

		calculateRoute();
	
		
	}

	private void calculateRoute() {
		while (base == null) {
		
			calculateNextPos();
		
		}	
	
	}

	//한칸 이동할때마다 4번씩 검사를 한다 
	private void calculateNextPos() {
		for(int i =1; i<5;i++) {
			if(i!=lastPos) {
				if(yPos>0 && i==UP) {
					if(level.map[xPos][yPos-1]==1) {
						lastPos=DOWN;
						route[xPos][yPos]=UP;
						yPos--;
						
						break;
					}else if(level.map[xPos][yPos-1]==baseBlock) {//baseBlock이면  calculateNextPos()종료한다
						base=new Base(xPos,yPos);
						break;
					}
					
				}
				if(xPos<21 && i==RIGHT) {
					if(level.map[xPos+1][yPos]==1) {
						lastPos=LEFT;
						route[xPos][yPos]=RIGHT;
						xPos++;
						
						
						break;
					}else if(level.map[xPos+1][yPos]==baseBlock) {//baseBlock이면  calculateNextPos()종료한다
						
						
						
						base=new Base(xPos,yPos);
						
						break;
					}
					
				}
				
				if(xPos>0 && i==LEFT) {
					if(level.map[xPos-1][yPos]==1) {
						lastPos=RIGHT;
						route[xPos][yPos]=LEFT;
						xPos--;
						
						break;
					}else if(level.map[xPos-1][yPos]==baseBlock) {//baseBlock이면  calculateNextPos()종료한다
						base=new Base(xPos,yPos);
						
						break;
					}
					
				}
				if(yPos<13 && i==DOWN) {
					if(level.map[xPos][yPos+1]==1) {
						lastPos=UP;
						route[xPos][yPos]=DOWN;
						yPos++;
						
						break;
					}else if(level.map[xPos][yPos+1]==baseBlock) {//baseBlock이면  calculateNextPos()종료한다
						
						base=new Base(xPos,yPos);
						break;
					}
					
				}

			}
		}
	}
}

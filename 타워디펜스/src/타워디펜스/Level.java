package 타워디펜스;

public class Level {
	public int[][] map;
	SpawnPoint spawnPoint;

	public void findSpawnPoint() {
		
		for(int x = 0; x<22; x++) {
			for(int y=0; y<14; y++) {
				if(map[x][y]==2) {
					spawnPoint = new SpawnPoint(x, y);
					
					
				}
			}
		}
	}
}

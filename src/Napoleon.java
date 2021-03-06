import java.util.Random;

public class Napoleon extends Point {

	static int number = 0;
	private final static int ways = 4;
	private final static int turns = 25;

	private int actualTurn;
	private int distance;

	private char[][] symMap;
	private static int[][] moveChances = new int[turns][ways];

	private int[] moveMap;
	private Random rand;
	
	private int id;

	Napoleon(char[][] symMap) {
		setId(number);
		number += 1;
		
		actualTurn = 0;
		rand = new Random();
		this.symMap = symMap;

		moveMap = new int[turns];
	}

	public char[][] makeTurn() {

		nextMove();
		actualTurn += 1;
		return symMap;
	}

	public int howFar(Josephine j) {
		distance = Math.abs((x - j.x) + (y - j.y));
		return distance;
	}

	public static void increaseChances(int turn, int way) {
		for (int i = 0; i < 4; i++) {
			if (i == way) {
				moveChances[turn][i] += 5;
			} else {
				moveChances[turn][i] -= 2;
				if (moveChances[turn][i] < 0) {
					moveChances[turn][i] = 0;
				}
			}
		}
	}

	private void nextMove() {
		int sum = 0;
		int random;
		for (int chance : getMoveChances()[actualTurn]) {
			sum += chance;
		}
		int goodWay = 0;
		random = rand.nextInt(sum);
		int amount = 0;
		for (int i = 0; i < moveChances[actualTurn].length; i++) {
			if (random <= moveChances[actualTurn][i] + amount) {
				goodWay = i;
				break;
			} else {
				amount += moveChances[actualTurn][i];
			}
		}
		moveMap[actualTurn] = goodWay; // save to history of turns
		moveMap(goodWay); // move character on map
	}

	private void moveMap(int way) {
		switch (way) {
		case 0:
			if (symMap[x][y - 1] == '0') {
				char temp = symMap[x][y];
				symMap[x][y] = symMap[x][y - 1];
				symMap[x][y = y - 1] = temp;
			}
			break;
		case 1:
			if (symMap[x][y + 1] == '0') {
				char temp = symMap[x][y];
				symMap[x][y] = symMap[x][y + 1];
				symMap[x][y = y + 1] = temp;
			}
			break;
		case 2:
			if (symMap[x + 1][y] == '0') {
				char temp = symMap[x][y];
				symMap[x][y] = symMap[x + 1][y];
				symMap[x = x + 1][y] = temp;
			}
			break;
		case 3:
			if (symMap[x - 1][y] == '0') {
				char temp = symMap[x][y];
				symMap[x][y] = symMap[x - 1][y];
				symMap[x = x - 1][y] = temp;
			}
			break;
		default:
		}

	}

	public int getTurns() {
		return turns;
	}

	public char[][] getSymMap() {
		return symMap;
	}

	public int getActualTurn() {
		return actualTurn;
	}

	public int[] getMoveMap() {
		return moveMap;
	}
	
	public int getDistance() {
		return distance;
	}

	public static int[][] getMoveChances() {
		return moveChances;
	}

	public static void setMoveChances(int i, int j, int value) {
		Napoleon.moveChances[i][j] = value;
	}

	public int getId() {
		return id;
	}
	//up3//down2//right1//left0
	public void setId(int id) {
		this.id = id;
	}
}

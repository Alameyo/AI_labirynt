import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Main {

	static final char[][] PRIMAL_SYM_MAP = { { 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', '0', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', '0', '0', '0', '0', '0', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', '0', 'x', 'x', 'x', '0', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', '0', 'x', 'x', 'x', '0', '0', '0', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', '0', 'x', 'x', 'x', '0', 'x', '0', 'x', '0', '0', '0', 'x' },
			{ 'x', '0', 'x', 'x', 'x', '0', 'x', '0', 'x', '0', 'x', '0', 'x' },
			{ 'x', '0', 'x', 'x', 'x', '0', 'x', '0', '0', '0', 'x', 'J', 'x' },
			{ 'x', '0', '0', '0', '0', '0', '0', '0', 'x', '0', 'x', '0', 'x' },
			{ 'x', '0', 'x', 'x', '0', 'x', '0', 'x', 'x', '0', 'x', '0', 'x' },
			{ 'x', '0', '0', 'x', '0', 'x', '0', 'x', 'x', '0', 'x', '0', 'x' },
			{ 'x', 'x', '0', 'x', '0', 'x', '0', 'x', 'x', '0', 'x', '0', 'x' },
			{ 'x', 'N', '0', '0', '0', 'x', '0', 'x', 'x', '0', 'x', '0', 'x' },
			{ 'x', '0', 'x', 'x', 'x', 'x', '0', '0', '0', '0', '0', '0', 'x' },
			{ 'x', '0', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
			{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' }, };

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		char[][] symMap = new char[PRIMAL_SYM_MAP.length][PRIMAL_SYM_MAP[0].length];
		for (int i = 0; i < PRIMAL_SYM_MAP.length; i++) {
			for (int j = 0; j < PRIMAL_SYM_MAP[i].length; j++) {
				symMap[i][j] = PRIMAL_SYM_MAP[i][j];
			}
		}

		Random rand = new Random();
		int[][] moveChances = new int[25][4];
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 4; j++) {
				Napoleon.getMoveChances()[i][j] = 25; // up
				Napoleon.getMoveChances()[i][j] = 25; // down
				Napoleon.getMoveChances()[i][j] = 25; // right
				Napoleon.getMoveChances()[i][j] = 25; // left
			}
		}

		Napoleon napoleon = new Napoleon(symMap);

		Josephine josephine = new Josephine();
		napoleonSearchLoop: for (int i = 0; i < symMap.length; i++) {
			for (int j = 0; j < symMap[i].length; j++) {
				if (symMap[i][j] == 'N') {
					System.out.println(i + " " + j);
					napoleon.setPos(i, j);
				} else if (symMap[i][j] == 'J') {
					josephine.setPos(i, j);
				}

			}
		}

		System.out.println("Next generation, write y");
		while ((System.in.read()) > 0) {
			LinkedList<Napoleon> napoleons = new LinkedList<Napoleon>();
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));
			napoleons.add(new Napoleon(symMap));

			for (Napoleon nap : napoleons) {
				nap.setPos(napoleon.x, napoleon.y);
			}
			for (Napoleon nap : napoleons) {
				for (int i = 0; i < napoleon.getTurns(); i++) {
					symMap = nap.makeTurn();

					System.out.println("\n Iteretion:  " + i + " with ID " + nap.getId() + "\n");
					Utils.drawMap(nap.getSymMap());
				}
				for (int j = 0; j < nap.getMoveMap().length; j++) {
					System.out.print("Moves:  " + nap.getMoveMap()[j] + " ");
				}

				nap.howFar(josephine);

			}
			napoleons.sort(Comparator.comparing(Napoleon::getDistance));
			int[] bestMoveMap;
			if (rand.nextInt(100) > 20) {
				bestMoveMap = napoleons.getFirst().getMoveMap();
			} else {
				bestMoveMap = napoleons.get(1).getMoveMap();
			}
			for (int i = 0; i < bestMoveMap.length; i++) {
				Napoleon.increaseChances(i, bestMoveMap[i]);
			}
			System.out.println();
			for (int[] i : Napoleon.getMoveChances()) {
				for (int j : i) {
					System.out.print(j + " ");
				}
				System.out.print("    ");
			}
			// symMap=PRIMAL_SYM_MAP;
			for (int i = 0; i < PRIMAL_SYM_MAP.length; i++) {
				for (int j = 0; j < PRIMAL_SYM_MAP[i].length; j++) {
					symMap[i][j] = PRIMAL_SYM_MAP[i][j];
				}
			}
			// mutation
			int howManyMutations = 20;
			for (int i = 0; i < howManyMutations; i++) {
				Napoleon.setMoveChances(rand.nextInt(moveChances.length), rand.nextInt(moveChances[0].length), rand.nextInt(50));
			}

		}
	}
}
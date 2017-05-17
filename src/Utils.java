
public class Utils {

	Utils(){
		
	}
	public static void drawMap(char[][] map){
		for(char[] row:map){
			for(char col:row){
				System.out.print(col);
			}
			System.out.println();
		}
	}
}

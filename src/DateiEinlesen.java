import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DateiEinlesen {
	
	public static void main(String[] args) throws FileNotFoundException{
		File daten = new File("TPicSim1.LST");
		
		Scanner scan = new Scanner(daten);
		while(scan.hasNextLine()) {
			System.out.println(scan.nextLine());
		}
		scan.close();
	}
	


}

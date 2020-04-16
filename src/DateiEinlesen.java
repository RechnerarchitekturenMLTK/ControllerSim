import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DateiEinlesen {
	
	public static void main(String[] args) throws FileNotFoundException{
		File daten = new File("TPicSim1.LST");
		
		Scanner scan = new Scanner(daten);
		while(scan.hasNextLine()) { //Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde
			
			String s1 = scan.nextLine();
			char char0 = s1.charAt(0);
			char char1 = s1.charAt(1);
			char char2 = s1.charAt(2);
			char char3 = s1.charAt(3);
			char char4 = s1.charAt(5);
			char char5 = s1.charAt(6);
			char char6 = s1.charAt(7);
			char char7 = s1.charAt(8);
			
			String address0 = String.valueOf(char0);
			
			
		}
		scan.close(); //Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.
	}
	


}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {
	
	public static void main(String[] args) throws FileNotFoundException{
		int[] befehlsspeicher = new int[1024];
		int[] datenspeicher = new int[256];
		File daten = new File("TPicSim1.LST");
		
		Scanner scan = new Scanner(daten);
		while(scan.hasNextLine()) { //Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde
			
			String s1 = scan.nextLine();
			if(s1.substring(0, 1).isBlank()) {
				
				
			}else {
				
				char char0 = s1.charAt(0); 
				char char1 = s1.charAt(1);
				char char2 = s1.charAt(2);
				char char3 = s1.charAt(3);
				char char4 = s1.charAt(5);
				char char5 = s1.charAt(6);
				char char6 = s1.charAt(7);
				char char7 = s1.charAt(8);
				
				String address0 = String.valueOf(char0);
				String address1 = String.valueOf(char1);
				String address2 = String.valueOf(char2);
				String address3 = String.valueOf(char3);
				String command0 = String.valueOf(char4);
				String command1 = String.valueOf(char5);
				String value0 = String.valueOf(char6);
				String value1 = String.valueOf(char7);
				
				
				String address = address0+address1+address2+address3;
				String command = command0+command1;
				String values = value0 + value1;
				
				
					int index = Integer.parseInt(address,16);
					int befehl = Integer.parseInt(command,16);
					int data = Integer.parseInt(values, 16);
					befehlsspeicher[index]=befehl;
					datenspeicher[index]=data;
					
			}
			
		}
		scan.close(); //Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.
	}
	


}

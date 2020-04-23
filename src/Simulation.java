import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {
	
	public static void main(String[] args) throws FileNotFoundException{
		int[] befehlsspeicher = new int[1024]; //Befehlsspeicher mit der Größe 1024.
		int[] datenspeicher = new int[256]; //Datenspeicher mit der Größe 256.
		File daten = new File("TPicSim1.LST"); //Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.
		
		Scanner scan = new Scanner(daten);
		while(scan.hasNextLine()) { //Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde
			
			String s1 = scan.nextLine(); //Schreibt die gesamte Zeile in String s1
			if(s1.substring(0, 1).isBlank()) { /*Prüft ob an erster und zweiter Stelle kein Wert steht, falls ja, wird
			 									der nächste While-Zyklus eingeleitet und die Nächste Zeile wird bearbeitet,
			 									falls nein wird die else-Klammer ausgeführt	
			 									*/
				
				
			}else {
				
				char char0 = s1.charAt(0); //Speichert das Zeichen an Stelle (n) in char-Variable
				char char1 = s1.charAt(1); 	
				char char2 = s1.charAt(2); 
				char char3 = s1.charAt(3); //Das vierte Zeichen wird ausgelassen, da es ein Leerzeichen ist. 
				char char4 = s1.charAt(5); 
				char char5 = s1.charAt(6); 
				char char6 = s1.charAt(7); 
				char char7 = s1.charAt(8); 
				
				String address0 = String.valueOf(char0); //Jede Char-Variable wird in einem eigenen String zugewiesen.
				String address1 = String.valueOf(char1);
				String address2 = String.valueOf(char2);
				String address3 = String.valueOf(char3);
				String command0 = String.valueOf(char4);
				String command1 = String.valueOf(char5);
				String value0 = String.valueOf(char6);
				String value1 = String.valueOf(char7);
				
				
				String address = address0+address1+address2+address3; //Die Strings mit jeweiligen Informationen werden zusammengeführt und einer neuen Variabel zugewiesen.
				String command = command0+command1; 
				String values = value0 + value1;
				
				
					int index = Integer.parseInt(address,16); //Die Strings mit jeweiligen HEXA-Codes werden ins Dezimalsystem umgerechnet und entsprechenden Variablen zugewiesen.
					int befehl = Integer.parseInt(command,16);
					int data = Integer.parseInt(values, 16);
					befehlsspeicher[index]=befehl; //Befehlsdaten werden in den Befehlsspeicher geladen, die Adresse gibt den Index an.
					datenspeicher[index]=data; //Zahlenwerte werden in den Datenspeicher geladen, die Adresse gibt den Index an.
					
			}
			
		}
		scan.close(); //Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.
	}
	


}

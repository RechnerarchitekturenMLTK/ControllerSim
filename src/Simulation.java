import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {

	public static void main(String[] args) throws FileNotFoundException {
		int[] befehlsspeicher = new int[1024]; // Befehlsspeicher mit der Größe 1024.
		int[] datenspeicher = new int[256]; // Datenspeicher mit der Größe 256.
		File daten = new File("TPicSim1.LST"); // Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.

		Scanner scan = new Scanner(daten);
		while (scan.hasNextLine()) { // Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde

			String s1 = scan.nextLine(); // Schreibt die gesamte Zeile in String s1
			if (s1.substring(0, 1).isBlank()) { /*
												 * Prüft ob an erster und zweiter Stelle kein Wert steht, falls ja, wird
												 * der nächste While-Zyklus eingeleitet und die Nächste Zeile wird
												 * bearbeitet, falls nein wird die else-Klammer ausgeführt
												 */

			} else {

				String sAdresse = s1.substring(0,4);
				String Adresse = ("0x" + sAdresse);	
				int iAdresse = Integer.parseInt(sAdresse, 16);
				String sBefehl = s1.substring(5,9);
				String Befehl = ("0x" + sBefehl);
				
				
				befehlsspeicher[iAdresse] = Befehl;
			}

		}
		scan.close(); // Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.

		for (int i = 0; i < befehlsspeicher.length; i++) {
			int befehl = befehlsspeicher[i];
			//if (befehl >= 0x11000000000000) and (befehl <= 0x11001111111111) {movlw} 
			//nop 0x00 0000 0000 0000
			   // 0x00 0000 0010 0000
			   // 0x00 0000 0100 0000
			    //0x00 0000 0110 0000
			if (befehlsspeicher[i] == 1) { // CLRF
				int operanden = datenspeicher[i];
				
			}
			else if (befehlsspeicher[i] == 2) { // SUBWF

			}
			else if (befehlsspeicher[i] == 3) { // DECF

			}
			else if (befehlsspeicher[i] == 4) { // IORWF

			}
			else if (befehlsspeicher[i] == 5) { // ANDWF

			}
			else if (befehlsspeicher[i] == 6) { // XORWF

			}
			else if (befehlsspeicher[i] == 7) { // ADDWF

			}
			else if (befehlsspeicher[i] == 8) { // MOVF

			}
			else if (befehlsspeicher[i] == 9) { // COMF

			}
			else if (befehlsspeicher[i] == 10) { // INCF

			}
			else if (befehlsspeicher[i] == 11) { // DECFSZ

			}
			else if (befehlsspeicher[i] == 12) { // RRF

			}
			else if (befehlsspeicher[i] == 13) { // RLF

			}
			else if (befehlsspeicher[i] == 14) { // SWAPF

			}
			else if (befehlsspeicher[i] == 15) { // INCFSZ

			}
			else if (befehlsspeicher[i] == 16) { // BCF

			}
			else if (befehlsspeicher[i] == 23) { // BSF

			}
			else if (befehlsspeicher[i] == 24) { // BTFSC

			}
			else if (befehlsspeicher[i] == 29) { // BTFSS

			}
			else if (befehlsspeicher[i] == 32) { // CALL

			}
			else if (befehlsspeicher[i] == 40) { // GOTO

			}
			else if (befehlsspeicher[i] == 48) { // MOVLW

			}
			else if (befehlsspeicher[i] == 52) { // RETLW

			}
			else if (befehlsspeicher[i] == 56) { // IORLW

			}
			else if (befehlsspeicher[i] == 57) { // ANDLW

			}
			else if (befehlsspeicher[i] == 58) { // XORLW

			}
			else if (befehlsspeicher[i] == 60) { // SUBLW

			}
			else if (befehlsspeicher[i] == 62) { // ANDLW

			}
			else {
				
			}

		}
	}

}

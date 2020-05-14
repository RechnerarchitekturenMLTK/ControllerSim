import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {

	public static void main(String[] args) throws FileNotFoundException {
		long[] befehlsspeicher = new long[1024]; // Befehlsspeicher mit der Größe 1024.
		int[] datenspeicher = new int[256]; // Datenspeicher mit der Größe 256.
		File daten = new File("TPicSim2.LST"); // Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.

		Scanner scan = new Scanner(daten);
		while (scan.hasNextLine()) { // Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde

			String s1 = scan.nextLine(); // Schreibt die gesamte Zeile in String s1
			if (s1.substring(0, 1).isBlank()) { /*
												 * Prüft ob an erster und zweiter Stelle kein Wert steht, falls ja, wird
												 * der nächste While-Zyklus eingeleitet und die Nächste Zeile wird
												 * bearbeitet, falls nein wird die else-Klammer ausgeführt
												 */

			} else {

				String sAdresse = s1.substring(0, 4);
				String Adresse = ("0x" + sAdresse);
				int iAdresse = Integer.parseInt(sAdresse, 16);
				String sBefehl = s1.substring(5, 9);
				long Befehl = Integer.parseInt(sBefehl, 16);
				String s = "";
				while (Befehl > 0) {
					long temp = (Befehl & 1);
					if (temp != 0) {
						s = "1" + s;
					} else {
						s = "0" + s;

					}
					Befehl >>= 1;
				}
				int counter = 0;
				for (int i = s.length(); i > 0; i--) {
					if (s.charAt(counter) == '1') {
						long temp = 1;
						for (int j = i; j > 1; j--) {
							temp = temp * 10;
						}
						int val = 0;
						if (s.charAt(counter) == '1') {
							val = 1;
						}
						Befehl = Befehl + (val * temp);
					}
					counter++;
				}
				// System.out.println(s);
				// Befehl = Integer.parseInt(s);
			}

		}
		scan.close(); // Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.

		System.out.println(befehlsspeicher[7]);
		for (int i = 0; i < befehlsspeicher.length; i++) {
			long befehl = befehlsspeicher[i];

			if ((befehl >= 11000000000000L) && (befehl <= 11001111111111L)) {
				System.out.println("movlw");
			}

			else if ((befehl == 0) || (befehl == 100000L) ||
			(befehl == 1000000L)
			|| (befehl == 1100000L)) {
			System.out.println("nop");
			}

			// else if (befehlsspeicher[i] == 1) { // CLRF
			// int operanden = datenspeicher[i];

			// }
			// else if (befehlsspeicher[i] == 2) { // SUBWF

			// }
			// else if (befehlsspeicher[i] == 3) { // DECF

			// }
			// else if (befehlsspeicher[i] == 4) { // IORWF

			// }
			// else if (befehlsspeicher[i] == 5) { // ANDWF

			// }
			// else if (befehlsspeicher[i] == 6) { // XORWF

			// }
			// else if (befehlsspeicher[i] == 7) { // ADDWF

			// }
			// else if (befehlsspeicher[i] == 8) { // MOVF

			// }
			// else if (befehlsspeicher[i] == 9) { // COMF

			// }
			// else if (befehlsspeicher[i] == 10) { // INCF

			// }
			// else if (befehlsspeicher[i] == 11) { // DECFSZ

			// }
			// else if (befehlsspeicher[i] == 12) { // RRF

			// }
			// else if (befehlsspeicher[i] == 13) { // RLF

			// }
			// else if (befehlsspeicher[i] == 14) { // SWAPF

			// }
			// else if (befehlsspeicher[i] == 15) { // INCFSZ

			// }
			// else if (befehlsspeicher[i] == 16) { // BCF

			// }
			// else if (befehlsspeicher[i] == 23) { // BSF

			// }
			// else if (befehlsspeicher[i] == 24) { // BTFSC

			// }
			// else if (befehlsspeicher[i] == 29) { // BTFSS

			// }
			else if ((befehl >= 10000000000000L) && (befehl <= 10011111111111L)) {
				System.out.println("call");
			} else if ((befehl >= 10100000000000L) && (befehl <= 10111111111111L)) {
				System.out.println("goto");
			} else if ((befehl >= 11010000000000L) && (befehl <= 11011111111111L)) { // RETLW
				System.out.println("retlw");
			} else if ((befehl >= 11100000000000L) && (befehl <= 11100011111111L)) {
				System.out.println("iorlw");
			} else if ((befehl >= 11100100000000L) && (befehl <= 11100111111111L)) {
				System.out.println("andlw");
			} else if ((befehl >= 11101000000000L) && (befehl <= 11101011111111L)) {
				System.out.println("xorlw");
			} else if ((befehl >= 11110000000000L) && (befehl <= 11110111111111L)) {
				System.out.println("sublw");
			} else if ((befehl >= 11111000000000L) && (befehl <= 11111111111111L)) {
				System.out.println("addlw");
			} else if (befehl == 1000L) {
				System.out.println("return");
			}
		}
	}

}

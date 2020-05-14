import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {

	public static void main(String[] args) throws FileNotFoundException {
		long[] befehlsspeicher = new long[1024]; // Befehlsspeicher mit der Größe 1024.
		int[] datenspeicher = new int[256]; // Datenspeicher mit der Größe 256.
		File daten = new File("TPicSim101.LST"); // Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.

		Scanner scan = new Scanner(daten);
		int zaehler = 0;
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
				befehlsspeicher[zaehler] = Befehl;
				zaehler++;
			}

		}
		scan.close(); // Falls die while() bedingung oben nicht erfüllt, wird der Scanvorgang beendet.

		for (int i = 0; i < befehlsspeicher.length; i++) {
			long befehl = befehlsspeicher[i];

			if ((befehl >= 11000000000000L) && (befehl <= 11001111111111L)) {
				System.out.println("movlw");
			} else if ((befehl == 0L) || (befehl == 100000L) || (befehl == 1000000L) || (befehl == 1100000L)) {
				System.out.println("nop");
			} else if ((befehl >= 10000000L) && (befehl <= 11111111L)) {
				System.out.println("movwf");
			} else if ((befehl >= 100000000L) && (befehl <= 111111111L)) {
				System.out.println("clrf");
			} else if ((befehl >= 1000000000L) && (befehl <= 1011111111L)) {
				System.out.println("subwf");
			} else if ((befehl >= 1100000000L) && (befehl <= 1111111111L)) {
				System.out.println("decf");
			} else if ((befehl >= 10000000000L) && (befehl <= 10011111111L)) {
				System.out.println("iorwf");
			} else if ((befehl >= 10100000000L) && (befehl <= 10111111111L)) {
				System.out.println("andwf");
			} else if ((befehl >= 11000000000L) && (befehl <= 11011111111L)) {
				System.out.println("xorwf");
			} else if ((befehl >= 11100000000L) && (befehl <= 11111111111L)) {
				System.out.println("addwf");
			} else if ((befehl >= 100000000000L) && (befehl <= 100011111111L)) {
				System.out.println("movf");
			} else if ((befehl >= 100100000000L) && (befehl <= 100111111111L)) {
				System.out.println("comf");
			} else if ((befehl >= 101000000000L) && (befehl <= 101011111111L)) {
				System.out.println("incf");
			} else if ((befehl >= 101100000000L) && (befehl <= 101111111111L)) {
				System.out.println("decfsz");
			} else if ((befehl >= 110000000000L) && (befehl <= 110011111111L)) {
				System.out.println("rrf");
			} else if ((befehl >= 110100000000L) && (befehl <= 110111111111L)) {
				System.out.println("rlf");
			} else if ((befehl >= 111000000000L) && (befehl <= 111011111111L)) {
				System.out.println("swapf");
			} else if ((befehl >= 111100000000L) && (befehl <= 111111111111L)) {
				System.out.println("incfsz");
			} else if ((befehl >= 1000000000000L) && (befehl <= 1001111111111L)) {
				System.out.println("bcf");
			} else if ((befehl >= 1010000000000L) && (befehl <= 1011111111111L)) {
				System.out.println("bsf");
			} else if ((befehl >= 1100000000000L) && (befehl <= 1101111111111L)) {
				System.out.println("btfsc");
			} else if ((befehl >= 1110000000000L) && (befehl <= 1111111111111L)) {
				System.out.println("btfss");
			} else if ((befehl >= 10000000000000L) && (befehl <= 10011111111111L)) {
				System.out.println("call");
			} else if ((befehl >= 10100000000000L) && (befehl <= 10111111111111L)) {
				System.out.println("goto");
			} else if ((befehl >= 11010000000000L) && (befehl <= 11011111111111L)) {
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
			} else if ((befehl >= 100000000L) && (befehl <= 101111111L)) {
				System.out.println("clrw");
			} else if (befehl == 1100100L) {
				System.out.println("clrwdt");
			} else if (befehl == 1001L) {
				System.out.println("retfie");
			} else if (befehl == 1100011L) {
				System.out.println("sleep");
			}
		}
	}

}

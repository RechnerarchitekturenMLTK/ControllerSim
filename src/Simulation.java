import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simulation {
	static long[] stack = new long[8];
	static int stPointer;

	public static void main(String[] args) throws FileNotFoundException {
		long wRegister = 0;
		long[] befehlsspeicher = new long[1024]; // Befehlsspeicher mit der Größe 1024.
		long[] datenspeicher = new long[256]; // Datenspeicher mit der Größe 256.
		int zBit = 0;

		for (int i = 0; i < 8; i++) {
			stack[i] = -1;
		}
		File changeDat = new File("gui_change.dat");
		while (!changeDat.exists()) { // Prüft solange bis gui_change.dat vorhanden ist.

		}
		Scanner pfad = new Scanner(changeDat);
		String path = pfad.nextLine();
		pfad.close();
		changeDat.delete();
		File daten = new File(path); // Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.

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
				String kString = "";
				long k = 0;
				kString = getLiterals(befehl, 8);
				k = Integer.parseInt(kString, 2);
				wRegister = k;
				System.out.println("movlw: " + wRegister); // MOVLW
			} else if ((befehl == 0L) || (befehl == 100000L) || (befehl == 1000000L) || (befehl == 1100000L)) {
				System.out.println("nop");
			} else if ((befehl >= 10000000L) && (befehl <= 11111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				datenspeicher[dec] = wRegister;
				System.out.println("movwf: " + datenspeicher[dec]);
			} else if ((befehl >= 110000000L) && (befehl <= 111111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				datenspeicher[dec] = 0;
				zBit = 1;
				System.out.println("clrf " + datenspeicher[dec] + " Zbit: " + zBit);
			} else if ((befehl >= 100000000L) && (befehl <= 101111111L)) {
				wRegister = 0;
				System.out.println("clrw (wRegister) " + wRegister);
			} else if ((befehl >= 1000000000L) && (befehl <= 1011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					long temp = datenspeicher[dec] - wRegister;
					wRegister = Math.floorMod(temp, 256);
					System.out.println("subwf (wRegister) " + wRegister);
				}
				if (destBit == 1) {
					long temp = datenspeicher[dec] - wRegister;
					datenspeicher[dec] = Math.floorMod(temp, 256);
					System.out.println("subwf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 1100000000L) && (befehl <= 1111111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					long temp = datenspeicher[dec] - 1;
					wRegister = Math.floorMod(temp, 256);
					System.out.println("decf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					long temp = datenspeicher[dec] - 1;
					datenspeicher[dec] = Math.floorMod(temp, 256);
					System.out.println("decf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 10000000000L) && (befehl <= 10011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = wRegister | datenspeicher[dec];
					System.out.println("iorwf (wRegister) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = wRegister | datenspeicher[dec];
					System.out.println("iorwf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 10100000000L) && (befehl <= 10111111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = wRegister & datenspeicher[dec];
					System.out.println("andwf (wRegister) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = wRegister & datenspeicher[dec];
					System.out.println("andwf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 11000000000L) && (befehl <= 11011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = (wRegister ^ datenspeicher[dec]);
					System.out.println("xorwf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = (wRegister ^ datenspeicher[dec]);
					System.out.println("xorwf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 11100000000L) && (befehl <= 11111111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = wRegister + datenspeicher[dec];
					System.out.println("addwf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = wRegister + datenspeicher[dec];
					System.out.println("addwf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 100000000000L) && (befehl <= 100011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = datenspeicher[dec];
					System.out.println("movf (wRegister) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = datenspeicher[dec];
					System.out.println("movf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 100100000000L) && (befehl <= 100111111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				long tp = negator(datenspeicher[dec], 7);

				if (destBit == 0) {

					wRegister = tp;
					System.out.println("comf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = tp;
					System.out.println("comf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 101000000000L) && (befehl <= 101011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					long temp = datenspeicher[dec] + 1;
					wRegister = Math.floorMod(temp, 256);
					System.out.println("incf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					long temp = datenspeicher[dec] + 1;
					datenspeicher[dec] = Math.floorMod(temp, 256);
					System.out.println("incf (f-register) " + datenspeicher[dec]);
				}
			} else if ((befehl >= 101100000000L) && (befehl <= 101111111111L)) {
				System.out.println("decfsz");
			} else if ((befehl >= 110000000000L) && (befehl <= 110011111111L)) {
				System.out.println("rrf");
			} else if ((befehl >= 110100000000L) && (befehl <= 110111111111L)) {
				System.out.println("rlf");
			} else if ((befehl >= 111000000000L) && (befehl <= 111011111111L)) {
				String kString = getLiterals(befehl, 7);
				int dec = Integer.parseInt(kString, 2);
				String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
				int destBit = Integer.parseInt(dBit, 2);
				if (destBit == 0) {
					wRegister = swapNibble(datenspeicher[dec]);
					System.out.println("swapf (w-Register) " + wRegister);
				}
				if (destBit == 1) {
					datenspeicher[dec] = swapNibble(datenspeicher[dec]);
					System.out.println("swapf (f-register) " + datenspeicher[dec]);
				}
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
				stPointer = push(i + 1, stPointer);
				stPointer++;
				String kString = getLiterals(befehl, 11);
				int literals = Integer.parseInt(kString, 2);
				i = literals;
				i--;
				System.out.println("call: " + literals);

			} else if ((befehl >= 10100000000000L) && (befehl <= 10111111111111L)) {
				String kString = getLiterals(befehl, 10);
				int dec = Integer.parseInt(kString, 2);
				if (i == dec) {
					System.out.println("GOTO ENDE");
					break;
				}
				i = dec - 1;
				System.out.println("goto: " + dec);
			} else if ((befehl >= 11010000000000L) && (befehl <= 11011111111111L)) {
				String kString = getLiterals(befehl, 8);
				int dec = Integer.parseInt(kString, 2);
				wRegister = dec;
				stPointer--;
				i = pop(stPointer);
				i--;
				System.out.println("retlw: " + wRegister);
			} else if ((befehl >= 11100000000000L) && (befehl <= 11100011111111L)) {
				String kString = getLiterals(befehl, 8);
				long dec = Long.parseLong(kString, 2);
				wRegister = (dec | wRegister);
				System.out.println("iorlw: " + wRegister);
			} else if ((befehl >= 11100100000000L) && (befehl <= 11100111111111L)) {
				String kString = getLiterals(befehl, 8);
				long dec = Long.parseLong(kString, 2);
				wRegister = (dec & wRegister);
				System.out.println("andlw: " + wRegister);
			} else if ((befehl >= 11101000000000L) && (befehl <= 11101011111111L)) {
				String kString = getLiterals(befehl, 8);
				long dec = Long.parseLong(kString, 2);
				wRegister = (dec ^ wRegister);
				System.out.println("xorlw: " + wRegister);
			} else if ((befehl >= 11110000000000L) && (befehl <= 11110111111111L)) {
				String kString = getLiterals(befehl, 8);
				long dec = Long.parseLong(kString, 2);
				wRegister = dec - wRegister;
				System.out.println("sublw: " + wRegister);
			} else if ((befehl >= 11111000000000L) && (befehl <= 11111111111111L)) {
				String kString = getLiterals(befehl, 8);
				long dec = Long.parseLong(kString, 2);
				wRegister = wRegister + dec;
				System.out.println("addlw: " + wRegister);
			} else if (befehl == 1000L) {
				stPointer--;
				i = pop(stPointer);
				i--;
				System.out.println("return: " + i);
			} else if (befehl == 1100100L) {
				System.out.println("clrwdt");
			} else if (befehl == 1001L) {
				System.out.println("retfie");
			} else if (befehl == 1100011L) {
				System.out.println("sleep");
			}
		}
	}

	public static String getDBit(long binary, int length) {
		String kString = "" + binary;
		int pos = (kString.length() - length - 1);
		long kArray[] = new long[kString.length()];
		String dBit = "";
		kArray[pos] = Long.parseLong(kString.substring(pos, pos + 1));
		dBit = dBit + kArray[pos];
		return dBit;
	}

	public static String getLiterals(long binary, int length) {
		String kString = "" + binary;
		long kArray[] = new long[kString.length()];
		String sLiteral = "";
		int count = 0;
		for (int j = kString.length() - length; j <= kString.length(); j++) {
			if (j != kString.length()) {
				kArray[count] = Long.parseLong(kString.substring(j, j + 1));
				sLiteral = sLiteral + kArray[count];
				count++;
			}
		}
		return sLiteral;
	}

	public static long swapNibble(long dec) {
		String kString = Long.toBinaryString(dec);
		long kArray[] = new long[8];
		while(kString.length() != 8) {
			String temp = "0";
			kString = temp + kString;
		}
		String nibble1 = "";
		String nibble2 = "";
		for(int i = 0; i < 4; i++) {
			kArray[i] = Long.parseLong(kString.substring(i, i + 1));
			nibble1 += kArray[i];
		}
		for(int i = 4; i < 8; i++) {
			kArray[i] = Long.parseLong(kString.substring(i, i + 1));
			nibble2 += kArray[i];
		}
		String swpNi = nibble2 + nibble1;
		long retVal = Long.parseLong(swpNi, 2);
		return retVal;
	}

	public static long negator(long value, int length) {
		String tempS = "";
		String binary = Long.toBinaryString(value);
		int temp = length - binary.length() + 1;
		for (int i = 0; i < temp; i++) {
			tempS += "1";
		}
		for (int i = 0; i < binary.length(); i++) {
			String test = binary.substring(i, i + 1);
			if ((binary.substring(i, i + 1)).equals("0")) {
				tempS += "1";
			} else {
				tempS += "0";
			}
		}
		long reVal = Long.parseLong(tempS, 2);
		return reVal;
	}

	public static int push(long adresse, int pointer) {
		if (pointer > 7) {
			pointer = 0;
		}
		stack[pointer] = adresse;
		return pointer;
	}

	public static int pop(int pointer) {
		int index = (int) stack[pointer];
		stack[pointer] = -1;
		return index;
	}
}

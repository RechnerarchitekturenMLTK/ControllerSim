import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Simulation {
	static long[] stack = new long[8];
	static int stPointer;
	static int cFlag = 0;
	static long wRegister = 0;
	static long[] befehlsspeicher = new long[1024]; // Befehlsspeicher mit der Größe 1024.
	static long[] datenspeicher = new long[256]; // Datenspeicher mit der Größe 256.
	static int zBit = 0;
	static int TMR0 = 0;
	static int[] init = new int[256];
	static int dcFlag = 0;
	static int teilerFaktor = 2;
	static boolean nextStep = false;
	static boolean start = false;

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {

		for (int i = 0; i < 8; i++) {
			stack[i] = -1;
		}
		for (int i = 0; i < 256; i++) {
			init[i] = 0;
		}
		File changeDat = new File("gui_change.dat");
		while (!changeDat.exists()) { // Prüft solange bis gui_change.dat vorhanden ist.

		}
		Scanner pfad = new Scanner(changeDat);
		String path = pfad.nextLine();
		pfad.close();
		changeDat.delete();
		File daten = new File(path); // Eine manuell ausgewählte Datei wird dem Programm hinzugefügt.

		changeDat = new File("gui_change.dat");
		while (!changeDat.exists()) { // Prüft solange bis gui_change.dat vorhanden ist.

		}
		Thread.sleep(1000);
		pfad = new Scanner(changeDat);
		path = pfad.nextLine();
		pfad.close();
		changeDat.delete();
		getNextStep(path);

		if (start = true) {

			Scanner scan = new Scanner(daten);
			int zaehler = 0;
			while (scan.hasNextLine()) { // Prüft ob es eine nächste Zeile gibt, die noch nicht gelesen wurde

				String s1 = scan.nextLine(); // Schreibt die gesamte Zeile in String s1
				if (s1.substring(0, 1).isBlank()) { /*
													 * Prüft ob an erster und zweiter Stelle kein Wert steht, falls ja,
													 * wird der nächste While-Zyklus eingeleitet und die Nächste Zeile
													 * wird bearbeitet, falls nein wird die else-Klammer ausgeführt
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
			int counter = 0;

			for (int i = 0; i < befehlsspeicher.length; i++) {
				boolean changeDatExists = false;
				while (nextStep) {
					changeDat = new File("gui_change.dat");
					while (!changeDat.exists()) { // Prüft solange bis gui_change.dat vorhanden ist.

					}
					nextStep = false;
					changeDatExists = true;
				}
				if(!changeDatExists) {
					changeDat = new File("gui_change.dat");
				}
				if (changeDat.exists()) {
					pfad = new Scanner(changeDat);
					path = pfad.nextLine();
					pfad.close();
				}
				changeDat.delete();
				getNextStep(path);
				long befehl = befehlsspeicher[i];

				if ((befehl >= 11000000000000L) && (befehl <= 11001111111111L)) {
					String kString = "";
					long k = 0;
					kString = getLiterals(befehl, 8);
					k = Integer.parseInt(kString, 2);
					wRegister = k;
					wRegister = Math.floorMod(wRegister, 256);
					System.out.println("movlw: " + wRegister); // MOVLW
				} else if ((befehl == 0L) || (befehl == 100000L) || (befehl == 1000000L) || (befehl == 1100000L)) {
					System.out.println("nop");
				} else if ((befehl >= 10000000L) && (befehl <= 11111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					if (datenspeicher[4] != 0 && dec != 12 && dec != 13) {
						if (dec == 4) {
							datenspeicher[4] = wRegister;
							System.out.println("movwf: " + datenspeicher[4]);
						} else {
							long temp = datenspeicher[4];
							datenspeicher[(int) temp] = wRegister;
							System.out.println("movwf: " + datenspeicher[(int) temp]);
						}
					} else {

						if (datenspeicher[3] >= 32 && !(datenspeicher[3] >= 64)) {
							dec += 80;
						}
						datenspeicher[dec] = wRegister;
						System.out.println("movwf: " + datenspeicher[dec]);
					}
				} else if ((befehl >= 110000000L) && (befehl <= 111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					datenspeicher[dec] = 0;
					zBit = 1;
					System.out.println("clrf " + datenspeicher[dec] + " Zbit: " + zBit);
				} else if ((befehl >= 100000000L) && (befehl <= 101111111L)) {
					wRegister = 0;
					zBit = 1;
					System.out.println("clrw (wRegister) " + wRegister);
				} else if ((befehl >= 1000000000L) && (befehl <= 1011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						dcFlag = getDCFlag(wRegister, datenspeicher[dec]);
						if (dcFlag == 1) {
							dcFlag = 0;
						} else {
							dcFlag = 1;
						}
						wRegister = datenspeicher[dec] - wRegister;
						if (wRegister == 0) {
							zBit = 1;
							cFlag = 1;
						} else if (wRegister > 0) {
							zBit = 0;
							cFlag = 1;
						} else {
							zBit = 0;
							cFlag = 0;
						}
						wRegister = Math.floorMod(wRegister, 256);
						System.out.println("subwf (wRegister) " + wRegister);
					}
					if (destBit == 1) {
						dcFlag = getDCFlag(wRegister, datenspeicher[dec]);
						if (dcFlag == 1) {
							dcFlag = 0;
						} else {
							dcFlag = 1;
						}
						datenspeicher[dec] = datenspeicher[dec] - wRegister;
						if (datenspeicher[dec] == 0) {
							zBit = 1;
							cFlag = 1;
						} else if (datenspeicher[dec] > 0) {
							zBit = 0;
							cFlag = 1;
						} else {
							zBit = 0;
							cFlag = 0;
						}
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
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
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("decf (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						long temp = datenspeicher[dec] - 1;
						datenspeicher[dec] = Math.floorMod(temp, 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("decf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 10000000000L) && (befehl <= 10011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						wRegister = wRegister | datenspeicher[dec];
						wRegister = Math.floorMod(wRegister, 256);
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("iorwf (wRegister) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = wRegister | datenspeicher[dec];
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("iorwf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 10100000000L) && (befehl <= 10111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						wRegister = wRegister & datenspeicher[dec];
						wRegister = Math.floorMod(wRegister, 256);
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("andwf (wRegister) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = wRegister & datenspeicher[dec];
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("andwf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 11000000000L) && (befehl <= 11011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						wRegister = (wRegister ^ datenspeicher[dec]);
						wRegister = Math.floorMod(wRegister, 256);
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("xorwf (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = (wRegister ^ datenspeicher[dec]);
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("xorwf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 11100000000L) && (befehl <= 11111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						if (datenspeicher[4] != 0 && dec != 12 && dec != 13) {
							if (dec == 4) {
								dcFlag = getDCFlag(wRegister, datenspeicher[4]);
								wRegister = wRegister + datenspeicher[4];
								if (wRegister == 0) {
									zBit = 1;
								} else {
									zBit = 0;
								}
								if (wRegister > 255) {
									cFlag = 1;
								} else {
									cFlag = 0;
								}
								wRegister = Math.floorMod(wRegister, 256);
								System.out.println("addwf (w-Register) " + wRegister);
							} else {
								long t = datenspeicher[4];
								dcFlag = getDCFlag(wRegister, datenspeicher[(int) t]);
								wRegister = wRegister + datenspeicher[(int) t];
								if (wRegister == 0) {
									zBit = 1;
								} else {
									zBit = 0;
								}
								if (wRegister > 255) {
									cFlag = 1;
								} else {
									cFlag = 0;
								}
								wRegister = Math.floorMod(wRegister, 256);
								System.out.println("addwf (w-Register) " + wRegister);
							}
						} else {
							dcFlag = getDCFlag(wRegister, datenspeicher[dec]);
							wRegister = wRegister + datenspeicher[dec];
							if (wRegister == 0) {
								zBit = 1;
							} else {
								zBit = 0;
							}
							if (wRegister > 255) {
								cFlag = 1;
							} else {
								cFlag = 0;
							}
							wRegister = Math.floorMod(wRegister, 256);
							System.out.println("addwf (wRegister) " + wRegister);
						}
					}
					if (destBit == 1) {
						if (datenspeicher[4] != 0 && dec != 12 && dec != 13) {
							if (dec == 4) {
								dcFlag = getDCFlag(wRegister, datenspeicher[4]);
								datenspeicher[4] = wRegister + datenspeicher[4];
								if (datenspeicher[4] == 0) {
									zBit = 1;
								} else {
									zBit = 0;
								}
								if (datenspeicher[4] > 255) {
									cFlag = 1;
								} else {
									cFlag = 0;
								}
								datenspeicher[4] = Math.floorMod(datenspeicher[4], 256);
								System.out.println("addwf (f-Register) " + datenspeicher[4]);
							} else {
								long t = datenspeicher[4];
								dcFlag = getDCFlag(wRegister, datenspeicher[(int) t]);
								datenspeicher[(int) t] = wRegister + datenspeicher[(int) t];
								if (datenspeicher[(int) t] == 0) {
									zBit = 1;
								} else {
									zBit = 0;
								}
								if (datenspeicher[(int) t] > 255) {
									cFlag = 1;
								} else {
									cFlag = 0;
								}
								datenspeicher[(int) t] = Math.floorMod(datenspeicher[(int) t], 256);
								System.out.println("addwf (f-Register) " + datenspeicher[(int) t]);
							}
						} else {
							dcFlag = getDCFlag(wRegister, datenspeicher[dec]);
							datenspeicher[dec] = wRegister + datenspeicher[dec];
							if (datenspeicher[dec] == 0) {
								zBit = 1;
							} else {
								zBit = 0;
							}
							if (datenspeicher[dec] > 255) {
								cFlag = 1;
							} else {
								cFlag = 0;
							}
							datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
							System.out.println("addwf (f-Register) " + datenspeicher[dec]);
						}
					}
				} else if ((befehl >= 100000000000L) && (befehl <= 100011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						wRegister = datenspeicher[dec];
						wRegister = Math.floorMod(wRegister, 256);
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("movf (wRegister) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = datenspeicher[dec];
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
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
						wRegister = Math.floorMod(wRegister, 256);
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("comf (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = tp;
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
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
						if (wRegister == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("incf (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						long temp = datenspeicher[dec] + 1;
						datenspeicher[dec] = Math.floorMod(temp, 256);
						if (datenspeicher[dec] == 0) {
							zBit = 1;
						} else {
							zBit = 0;
						}
						System.out.println("incf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 101100000000L) && (befehl <= 101111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						long temp = datenspeicher[dec] - 1;
						wRegister = Math.floorMod(temp, 256);
						System.out.println("decfsz (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						long temp = datenspeicher[dec] - 1;
						datenspeicher[dec] = Math.floorMod(temp, 256);
						System.out.println("decfsz (f-register) " + datenspeicher[dec]);
					}
					if (datenspeicher[dec] == 0) {
						i++;
						counter += 2;
						System.out.println("nop");
					}
				} else if ((befehl >= 110000000000L) && (befehl <= 110011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7);
					int destBit = Integer.parseInt(dBit, 2);
					long tp = 0;
					if (datenspeicher[4] != 0 && dec != 12 && dec != 13) {
						if (dec == 4) {
							tp = shiftRight(datenspeicher[4], 7);
							if (destBit == 0) {
								wRegister = tp;
								wRegister = Math.floorMod(wRegister, 256);
								System.out.println("rrf (w-Register) " + wRegister + " cFlag: " + cFlag);
							} else if (destBit == 1) {
								datenspeicher[4] = tp;
								datenspeicher[4] = Math.floorMod(datenspeicher[4], 256);
								System.out.println("rrf (f-register) " + datenspeicher[4] + " cFlag: " + cFlag);
							}
						} else {
							long t = datenspeicher[4];
							tp = shiftRight(datenspeicher[(int) t], 7);
							if (destBit == 0) {
								wRegister = tp;
								wRegister = Math.floorMod(wRegister, 256);
								System.out.println("rrf (w-Register) " + wRegister + " cFlag: " + cFlag);
							} else if (destBit == 1) {
								datenspeicher[(int) t] = tp;
								datenspeicher[(int) t] = Math.floorMod(datenspeicher[(int) t], 256);
								System.out.println("rrf (f-register) " + datenspeicher[(int) t] + " cFlag: " + cFlag);
							}
						}
					} else {
						tp = shiftRight(datenspeicher[dec], 7);
						if (destBit == 0) {
							wRegister = tp;
							wRegister = Math.floorMod(wRegister, 256);
							System.out.println("rrf (w-Register) " + wRegister + " cFlag: " + cFlag);
						} else if (destBit == 1) {
							datenspeicher[dec] = tp;
							datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
							System.out.println("rrf (f-register) " + datenspeicher[dec] + " cFlag: " + cFlag);
						}
					}
				} else if ((befehl >= 110100000000L) && (befehl <= 110111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					long tp = shiftLeft(datenspeicher[dec], 7);

					if (destBit == 0) {
						wRegister = tp;
						wRegister = Math.floorMod(wRegister, 256);
						System.out.println("rlf (w-Register) " + wRegister + " cFlag: " + cFlag);
					}
					if (destBit == 1) {
						datenspeicher[dec] = tp;
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						System.out.println("rlf (f-register) " + datenspeicher[dec] + " cFlag: " + cFlag);
					}
				} else if ((befehl >= 111000000000L) && (befehl <= 111011111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						wRegister = swapNibble(datenspeicher[dec]);
						wRegister = Math.floorMod(wRegister, 256);
						System.out.println("swapf (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						datenspeicher[dec] = swapNibble(datenspeicher[dec]);
						datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
						System.out.println("swapf (f-register) " + datenspeicher[dec]);
					}
				} else if ((befehl >= 111100000000L) && (befehl <= 111111111111L)) {
					String kString = getLiterals(befehl, 7);
					int dec = Integer.parseInt(kString, 2);
					String dBit = getDBit(befehl, 7); // Hole siebtes Element aus var befehl
					int destBit = Integer.parseInt(dBit, 2);
					if (destBit == 0) {
						long temp = datenspeicher[dec] + 1;
						wRegister = Math.floorMod(temp, 256);
						System.out.println("incfsz (w-Register) " + wRegister);
					}
					if (destBit == 1) {
						long temp = datenspeicher[dec] + 1;
						datenspeicher[dec] = Math.floorMod(temp, 256);
						System.out.println("incfsz (f-register) " + datenspeicher[dec]);
					}
					if (datenspeicher[dec] == 0) {
						i++;
						counter += 2;
						System.out.println("nop");
					}
				} else if ((befehl >= 1000000000000L) && (befehl <= 1001111111111L)) {
					String kString = getLiterals(befehl, 7);
					String bString = getLiterals(befehl, 10);
					bString = bString.substring(0, 3);
					int index = Integer.parseInt(bString, 2);
					int dec = Integer.parseInt(kString, 2);
					String sBefehl = Long.toBinaryString(datenspeicher[dec]);
					index = 7 - index;
					String temp = "";
					while (sBefehl.length() != 8) {
						sBefehl = "0" + sBefehl;
					}
					for (int j = 0; j < 8; j++) {
						if (j == index) {
							temp = temp + "0";
						} else {
							temp = temp + sBefehl.substring(j, j + 1);
						}
					}
					datenspeicher[dec] = Long.parseLong(temp, 2);
					datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
					System.out.println("bcf: " + datenspeicher[dec]);
				} else if ((befehl >= 1010000000000L) && (befehl <= 1011111111111L)) {
					String kString = getLiterals(befehl, 7);
					String bString = getLiterals(befehl, 10);
					bString = bString.substring(0, 3);
					int index = Integer.parseInt(bString, 2);
					int dec = Integer.parseInt(kString, 2);
					String sBefehl = Long.toBinaryString(datenspeicher[dec]);
					index = 7 - index;
					String temp = "";
					while (sBefehl.length() != 8) {
						sBefehl = "0" + sBefehl;
					}
					for (int j = 0; j < 8; j++) {
						if (j == index) {
							temp = temp + "1";
						} else {
							temp = temp + sBefehl.substring(j, j + 1);
						}
					}
					datenspeicher[dec] = Long.parseLong(temp, 2);
					datenspeicher[dec] = Math.floorMod(datenspeicher[dec], 256);
					System.out.println("bsf: " + datenspeicher[dec]);
				} else if ((befehl >= 1100000000000L) && (befehl <= 1101111111111L)) {
					String kString = getLiterals(befehl, 7);
					String bString = getLiterals(befehl, 10);
					bString = bString.substring(0, 3);
					int index = Integer.parseInt(bString, 2);
					int dec = Integer.parseInt(kString, 2);
					String sBefehl = Long.toBinaryString(datenspeicher[dec]);
					index = 7 - index;
					while (sBefehl.length() != 8) {
						sBefehl = "0" + sBefehl;
					}
					if (sBefehl.substring(index, index + 1).equals("0")) {
						i++;
						counter += 2;
						System.out.println("btfsc: " + datenspeicher[dec]);
						System.out.println("nop");
					} else {
						System.out.println("btfsc: " + datenspeicher[dec]);
					}
				} else if ((befehl >= 1110000000000L) && (befehl <= 1111111111111L)) {
					String kString = getLiterals(befehl, 7);
					String bString = getLiterals(befehl, 10);
					bString = bString.substring(0, 3);
					int index = Integer.parseInt(bString, 2);
					int dec = Integer.parseInt(kString, 2);
					String sBefehl = Long.toBinaryString(datenspeicher[dec]);
					index = 7 - index;
					while (sBefehl.length() != 8) {
						sBefehl = "0" + sBefehl;
					}
					if (sBefehl.substring(index, index + 1).equals("1")) {
						i++;
						counter += 2;
						System.out.println("btfss: " + datenspeicher[dec]);
						System.out.println("nop");
						datenspeicher[3] = 0;
					} else {
						System.out.println("btfss: " + datenspeicher[dec]);
					}
				} else if ((befehl >= 10000000000000L) && (befehl <= 10011111111111L)) {
					push(i + 1);
					String kString = getLiterals(befehl, 11);
					int literals = Integer.parseInt(kString, 2);
					i = literals;
					counter++;
					i--;
					System.out.println("call: " + literals);

				} else if ((befehl >= 10100000000000L) && (befehl <= 10111111111111L)) {
					String kString = getLiterals(befehl, 10);
					int dec = Integer.parseInt(kString, 2);
					counter++;
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
					i = pop();
					counter++;
					i--;
					System.out.println("retlw: " + wRegister);
				} else if ((befehl >= 11100000000000L) && (befehl <= 11100011111111L)) {
					String kString = getLiterals(befehl, 8);
					long dec = Long.parseLong(kString, 2);
					wRegister = (dec | wRegister);
					wRegister = Math.floorMod(wRegister, 256);
					if (wRegister == 0) {
						zBit = 1;
					} else {
						zBit = 0;
					}
					System.out.println("iorlw: " + wRegister);
				} else if ((befehl >= 11100100000000L) && (befehl <= 11100111111111L)) {
					String kString = getLiterals(befehl, 8);
					long dec = Long.parseLong(kString, 2);
					wRegister = (dec & wRegister);
					wRegister = Math.floorMod(wRegister, 256);
					if (wRegister == 0) {
						zBit = 1;
					} else {
						zBit = 0;
					}
					System.out.println("andlw: " + wRegister);
				} else if ((befehl >= 11101000000000L) && (befehl <= 11101011111111L)) {
					String kString = getLiterals(befehl, 8);
					long dec = Long.parseLong(kString, 2);
					wRegister = (dec ^ wRegister);
					wRegister = Math.floorMod(wRegister, 256);
					if (wRegister == 0) {
						zBit = 1;
					} else {
						zBit = 0;
					}
					System.out.println("xorlw: " + wRegister);
				} else if ((befehl >= 11110000000000L) && (befehl <= 11110111111111L)) {
					String kString = getLiterals(befehl, 8);
					long dec = Long.parseLong(kString, 2);
					dcFlag = getDCFlag(dec, wRegister);
					if (dcFlag == 1) {
						dcFlag = 0;
					} else {
						dcFlag = 1;
					}
					wRegister = dec - wRegister;
					if (wRegister == 0) {
						zBit = 1;
						cFlag = 1;
					} else if (wRegister > 0) {
						zBit = 0;
						cFlag = 1;
					} else {
						zBit = 0;
						cFlag = 0;
					}
					wRegister = Math.floorMod(wRegister, 256);
					System.out.println("sublw: " + wRegister);
				} else if ((befehl >= 11111000000000L) && (befehl <= 11111111111111L)) {
					String kString = getLiterals(befehl, 8);
					long dec = Long.parseLong(kString, 2);
					dcFlag = getDCFlag(wRegister, dec);
					wRegister = wRegister + dec;
					if (wRegister == 0) {
						zBit = 1;
					} else {
						zBit = 0;
					}
					if (wRegister > 255) {
						cFlag = 1;
					} else {
						cFlag = 0;
					}
					wRegister = Math.floorMod(wRegister, 256);
					System.out.println("addlw: " + wRegister);
				} else if (befehl == 1000L) {
					i = pop();
					counter++;
					i--;
					System.out.println("return: " + i);
				} else if (befehl == 1100100L) {
					System.out.println("clrwdt");
				} else if (befehl == 1001L) {
					counter++;
					System.out.println("retfie");
				} else if (befehl == 1100011L) {
					System.out.println("sleep");
				}
				counter++;
				if (counter >= teilerFaktor) {
					TMR0++;
					TMR0 = Math.floorMod(TMR0, 256);
					if (TMR0 == 0) {
						zBit = 1;
						datenspeicher[3] = 4;
					}
					counter = 0;
				}

				dateiSchreiben();
			}
		}
	}

	public static void getNextStep(String path) {
		if (path.equals("STEP")) {
			nextStep = true;
		}
		if (path.equals("START")) {
			start = true;
		}

	}

	public static void dateiSchreiben() {
		PrintWriter pWriter = null;
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("gui_set.dat")));
			if (datenspeicher[81] == 0) {
				teilerFaktor = 2;
			} else if (datenspeicher[81] == 1) {
				teilerFaktor = 4;
			} else if (datenspeicher[81] == 2) {
				teilerFaktor = 8;
			} else if (datenspeicher[81] == 3) {
				teilerFaktor = 16;
			} else if (datenspeicher[81] == 4) {
				teilerFaktor = 32;
			} else if (datenspeicher[81] == 5) {
				teilerFaktor = 64;
			} else if (datenspeicher[81] == 6) {
				teilerFaktor = 128;
			} else if (datenspeicher[81] == 7) {
				teilerFaktor = 256;
			}
			pWriter.println("Stack " + stack[0] + "," + stack[1] + "," + stack[2] + "," + stack[3] + "," + stack[4]
					+ "," + stack[5] + "," + stack[6] + "," + stack[7] + ",");
			pWriter.println("WReg " + wRegister);
			for (int i = 0; i < datenspeicher.length; i++) {
				if (datenspeicher[i] != 0 || init[i] == 1) {
					pWriter.println("FREG 0x" + i + "," + datenspeicher[i]);
					init[i] = 1;
				}
			}
			pWriter.println("STATUSBIT 0," + cFlag);
			pWriter.println("STATUSBIT 2," + zBit);
			pWriter.println("Timer0 " + TMR0);
			pWriter.println("OPTION " + datenspeicher[81]);
			pWriter.println("STATUSBIT 1," + dcFlag);
			pWriter.println("Prescaler 1:" + teilerFaktor);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
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
		while (kString.length() != 8) {
			String temp = "0";
			kString = temp + kString;
		}
		String nibble1 = "";
		String nibble2 = "";
		for (int i = 0; i < 4; i++) {
			kArray[i] = Long.parseLong(kString.substring(i, i + 1));
			nibble1 += kArray[i];
		}
		for (int i = 4; i < 8; i++) {
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

	public static long shiftLeft(long value, int length) {
		String binary = Long.toBinaryString(value);
		int temp = length - binary.length() + 1;
		for (int i = 0; i < temp; i++) {
			binary = "0" + binary;
		}
		String cFlagString = binary.substring(0, 1);
		cFlag = Integer.parseInt(cFlagString);
		String tempBinary = "";
		for (int i = 1; i < 8; i++) {
			tempBinary += binary.substring(i, i + 1);
		}
		binary = tempBinary;
		String rightSide = Integer.toString(cFlag);
		binary += rightSide;
		long retVal = Long.parseLong(binary, 2);
		return retVal;
	}

	public static long shiftRight(long value, int length) {
		String binary = Long.toBinaryString(value);
		int temp = length - binary.length() + 1;
		for (int i = 0; i < temp; i++) {
			binary = "0" + binary;
		}
		String cFlagString = binary.substring(7);
		cFlag = Integer.parseInt(cFlagString);
		String tempBinary = "";
		for (int i = 0; i < 7; i++) {
			tempBinary += binary.substring(i, i + 1);
		}
		binary = tempBinary;
		String leftSide = Integer.toString(cFlag);
		binary = leftSide + binary;
		long retVal = Long.parseLong(binary, 2);
		return retVal;
	}

	public static void push(long adresse) {
		if (stPointer > 7) {
			stPointer = 0;
		}
		stack[stPointer] = adresse;
		stPointer++;
	}

	public static int pop() {
		stPointer--;
		int index = (int) stack[stPointer];
		stack[stPointer] = -1;
		return index;
	}

	public static int getDCFlag(long wert1, long wert2) {
		String binary1 = Long.toBinaryString(wert1);
		String binary2 = Long.toBinaryString(wert2);
		long b1 = Long.parseLong(binary1);
		long b2 = Long.parseLong(binary2);
		for (int i = binary1.length(); i < 4; i++) {
			binary1 = "0" + binary1;
		}
		for (int i = binary2.length(); i < 4; i++) {
			binary2 = "0" + binary2;
		}
		if (binary1.length() > 4) {
			binary1 = getLiterals(b1, 4);
		}
		if (binary2.length() > 4) {
			binary2 = getLiterals(b2, 4);
		}
		boolean ueberlauf = false;
		for (int i = 3; i >= 0; i--) {
			if ((binary1.substring(i, i + 1).equals("1") && binary2.substring(i, i + 1).equals("1"))
					|| ((binary1.substring(i, i + 1).equals("1") && ueberlauf)
							|| (binary2.substring(i, i + 1).equals("1") && ueberlauf))) {
				ueberlauf = true;
			} else {
				ueberlauf = false;
			}
		}
		if (ueberlauf) {
			dcFlag = 1;
		} else {
			dcFlag = 0;
		}
		return dcFlag;
	}
}


public class test {

	public static void main(String[] args) {
		long wRegister = 32; //111001 0011 0000‬
		long befehl = 11101000100000l; //11 0000 0001 0001‬
		
		String kString = "" + getLiterals(befehl, 8);
		long k = Integer.parseInt(kString, 2);
		wRegister = wRegister + k;
		System.out.println("addlw: " + wRegister);
	}
	
	public static String getLiterals(long binary, int length) {
		String kString = "" + binary;
		long kArray[] = new long[kString.length()];
		String sLiteral = "";
		for(int j = 6; j <= kString.length(); j++) {
			if(j != kString.length()) {
				kArray[j] = Long.parseLong(kString.substring(j, j + 1));
				sLiteral = sLiteral + kArray[j];
			}
		}
		return sLiteral;
	}

}

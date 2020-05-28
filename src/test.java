
public class test {

	public static void main(String[] args) {
		long wRegister = 17;
		long befehl = 11100100110000L;
		
		String kString = getLiterals(befehl, 8);
		long k = Long.parseLong(kString);
		long temp = wRegister;
		String wString = Long.toBinaryString(temp);
		temp = Long.parseLong(wString);
		long result = (k & temp);
		String sResult = Long.toString(result);
		wRegister = Integer.parseInt(sResult);
		System.out.println("andlw: " + wRegister);
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

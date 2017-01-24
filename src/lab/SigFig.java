package lab;

public class SigFig {
	
	/*
	public static void main(String[] args) {
		System.out.println(sigfigalize(400, 3));

	}
	*/
	
	public static String sigfigalize(double num, int sigfigs) {
		
		if (num == 0) {
			return "0";
		}
		
		sigfigs--;

		String strNum = Double.toString(num);
		double power = Math.pow(10, sigfigs);
		
		if (strNum.contains("E")) {
			String powerStr = "E" + strNum.split("E")[1];
			
			strNum = strNum.replaceAll(powerStr, "");
			
			double d = Double.parseDouble(strNum);
			
			d *= power;
			
			d = Math.round(d);
			
			d /= power;
			
			return Double.toString(d) + powerStr;
			
		}
		
		int places;
		if (num > 1) {
			places = (int) Math.log10(num);
		} else {
			places = (int) Math.floor(Math.log10(num));
		}
		
		num /= Math.pow(10, places);
		num *= power;
		
		num = Math.round(num);
		
		num /= power;
		
		
		
		if (isNear0(places)) {
			
			strNum = Float.toString((float) (num * Math.pow(10, places)));
			
			int figs = getSigFigs(num * Math.pow(10, places));
			
			String zeros = "";
			
			for (int i = 0; i <= sigfigs - figs; i++) {
				zeros += "0";
			}
			
			strNum += zeros;
			
			return strNum;
			
		} else {
			
			int figs = getSigFigs(num);
			
			String zeros = "";
			
			for (int i = 0; i <= sigfigs - figs; i++) {
				zeros += "0";
			}
			
			strNum = Float.toString((float) (num));
			
			strNum += zeros;
			
			strNum += "E" + places;
			
			return strNum;
		}
		
	}
	
	private static boolean isNear0(int n) {
		return n == 0 || n == 1 || n == -1;
	}
	
	private static int getSigFigs(double num) {
		String strNum = Float.toString((float) num).replace("-", "").replace(".", "");
		
		if (strNum.startsWith("0")) {
			strNum = strNum.replaceFirst("0", "");
		}
		
		return strNum.length();
	}
	

}

package lab.substance;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;

import java.net.URL;
import java.net.URLConnection;

import lab.component.container.ContentState;
import lab.substance.Substance;

public class SubstanceData {
	// "builds" substances so the values can be accessed later
	private static String page = "";
	private static ArrayList<Substance> substances = new ArrayList<>();
	private final static String COMMA_DELIMITER = ",";
	private final static String NEW_LINE_SEPARATOR = "\n";

	public static void buildDefaultList() {
		try {
			getDataForSubstance("Sulfur");
			getDataForSubstance("H2O");
			getDataForSubstance("Benzene");
			getDataForSubstance("Acetone");
			getDataForSubstance("Ethanol");
			getDataForSubstance("Carbon");
			getDataForSubstance("NaCl");
		} catch (IOException e) {
			System.out.println("Error occurred when adding substance");
			e.printStackTrace();
		}
		writeFile("temptestfile");
	}

	public void buildCustomList(String... args) {
		for (String s : args) {
			try {
				getDataForSubstance(s);
			} catch (IOException e) {
				System.out.println("Error occurred when adding substance");
				e.printStackTrace();
			}
		}
		System.out.println("writing to file");
		writeFile("temptestfile");
	}

	private static void getDataForSubstance(String s) throws IOException {
		page = "";
		// Make a URL to the web page
		URL url = new URL("https://en.wikipedia.org/wiki/" + s);

		// Get the input stream through URL Connection
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;

		while ((line = br.readLine()) != null) {
			page = page + line;
		}
		getName();
		getOtherProperties();
	}

	public static String getName() {
		int tempIndex = 0;
		String name;
		String cutString;
		tempIndex = page.indexOf("<p><b>") + 6;
		cutString = page.substring(tempIndex, page.length());
		name = page.substring(tempIndex, tempIndex + cutString.indexOf("</b>"));
		return name;

	}

	public static void getOtherProperties() {
		int tempIndex = 0;

		// cutString is a temporary variable that stores parts of page
		String cutString;

		// create temporary variables to store substance data

		String name = getName();
		String state = "N/A";

		String meltingPoint = "N/A";
		String boilingPoint = "N/A";
		String heatOfVaporization = "N/A";
		String heatOfFusion = "N/A";
		String molarMass = "N/A";
		String formula = "N/A";

		// create atomic number, used only for getting name of elements
		String atomicNumber;

		// determine whether or not a substance is an element or not
		tempIndex = page.indexOf("Phase</a></th>") + 9;
		if (!(tempIndex == 8)) {
			// Substance is an element
			state = findTableProperty("Phase</a></th>", "</a></td>", 3);

			meltingPoint = findTableProperty("<a href=\"/wiki/Melting_point\" title=\"Melting point\">", "&", 3);

			boilingPoint = findTableProperty("<a href=\"/wiki/Boiling_point\" title=\"Boiling point\">", "&", 3);

			tempIndex = page.indexOf("<a href=\"/wiki/Enthalpy_of_vaporization\" title=\"Enthalpy of vaporization\">");
			if (tempIndex != -1) {
				cutString = page.substring(tempIndex, page.length());
				for (int i = 0; i < 3; i++) {
					tempIndex = cutString.indexOf(">") + 1;
					cutString = cutString.substring(tempIndex, cutString.length());
				}
				tempIndex = cutString.indexOf(">") + 1;
				if (cutString.indexOf("mono") == 0) {
					heatOfVaporization = cutString.substring(tempIndex, cutString.indexOf("&nbsp;"));
				} else {
					cutString = cutString.substring(cutString.indexOf("mono: ") + 6, cutString.indexOf("&#160;"));
					heatOfVaporization = cutString;
				}
			}

			tempIndex = page.indexOf(
					"<a href=\"/wiki/Enthalpy_of_fusion\" title=\"Enthalpy of fusion\">Heat&#160;of&#160;fusion</a></th>") + "<a href=\"/wiki/Enthalpy_of_fusion\" title=\"Enthalpy of fusion\">Heat&#160;of&#160;fusion</a></th>".length();
			cutString = page.substring(tempIndex, page.length());
			/*if (cutString.indexOf(">") != -1) {
				cutString = cutString.substring(cutString.indexOf(">") + 1, cutString.length());
			}
			for (int i = 0; i < 3; i++) {
				tempIndex = cutString.indexOf(">") + 1;
				cutString = cutString.substring(tempIndex, cutString.length());
			}
			tempIndex = cutString.indexOf(">") + 1;
			cutString = cutString.substring(tempIndex, cutString.length());
			if (cutString.indexOf("mono") == -1) {
				while (cutString.indexOf(":") != -1)
					cutString = cutString.substring(cutString.indexOf(":") + 1, cutString.length());
				System.out.println("cutting out : at index " + cutString.indexOf(":"));
				while (cutString.indexOf(" ") != -1)
					cutString = cutString.substring(cutString.indexOf(" ") + 1, cutString.length());


				if (cutString.indexOf("&nbsp;") != -1) {
					heatOfFusion = cutString.substring(0, cutString.indexOf("&nbsp;"));
				} else if (cutString.indexOf("&#160;") != -1) {
					heatOfFusion = cutString.substring(0, cutString.indexOf("&#160;"));
				}
			} else {
				cutString = cutString.substring(cutString.indexOf("mono: ") + 6, cutString.indexOf("&#160;"));
				heatOfFusion = cutString;
			}
			*/
			while (cutString.indexOf(">") != -1 && cutString.indexOf(":")<cutString.indexOf(">")||cutString.indexOf(">") != -1 && cutString.indexOf(" ")<cutString.indexOf(">")) {
				if(cutString.indexOf("<")!=-1&&cutString.indexOf("<")>cutString.indexOf(">")) {
					cutString = cutString.substring(cutString.indexOf(">") + 1, cutString.indexOf("<"));
				} else {
					//System.out.println(cutString);
					cutString = cutString.substring(cutString.indexOf(">") + 1, cutString.length());
				}
			}
			
			while (cutString.indexOf(":") != -1)
				cutString = cutString.substring(cutString.indexOf(":") + 1, cutString.length());
			
			while (cutString.indexOf(" ") != -1)
				cutString = cutString.substring(cutString.indexOf(" ") + 1, cutString.length());

			
			
			molarMass = findTableProperty(
					"<a href=\"/wiki/Relative_atomic_mass\" title=\"Relative atomic mass\">Standard atomic weight</a> <span style=\"font-weight:normal;\">(<i>A</i><sub>r</sub>)</span></th>",
					"<sup", 9);

			atomicNumber = findTableProperty(
					"Atomic number</a> <span style=\"font-weight:normal;\">(<i>Z</i>)</span></span></th>", "</td>", 7);

			formula = findTableProperty("<sub><span style=\"font-size:smaller;\">" + atomicNumber + "</span></sub>",
					"</caption>", 2);

		} else {

			molarMass = findTableProperty("Molar mass</a></td>", " g", 2);

			meltingPoint = findTableProperty("Melting point</a></td>", "&#160;", 2);

			boilingPoint = findTableProperty("Boiling point</a></td>", "&#160;", 2);

			formula = findTableProperty("Chemical formula</a></div>", "</td>", 3);

		}
		Substance tempSubstance = new Substance();
		if (!name.equals("N/A")) {
			tempSubstance.setName(name);
		}
		if (!state.equals("N/A")) {
			if (state.equals("solid")) {
				tempSubstance.setState(ContentState.SOLID);
			} else if (state.equals("liquid")) {
				tempSubstance.setState(ContentState.LIQUID);
			} else if (state.equals("gas")) {
				tempSubstance.setState(ContentState.GAS);
			} else {
				tempSubstance.setState(ContentState.SOLID);
			}

		} else if (!meltingPoint.equals("N/A")) {
			if (Double.parseDouble(meltingPoint) <= 23) {
				tempSubstance.setState(ContentState.LIQUID);
			} else if (Double.parseDouble(boilingPoint) <= 23) {
				tempSubstance.setState(ContentState.GAS);
			} else {
				tempSubstance.setState(ContentState.SOLID);
			}
		} else {
			tempSubstance.setState(ContentState.SOLID);
		}
		if (!meltingPoint.equals("N/A")) {
			tempSubstance.setMeltingPoint(Double.parseDouble(meltingPoint));
		}
		if (!boilingPoint.equals("N/A")) {
			tempSubstance.setBoilingPoint(Double.parseDouble(boilingPoint));
		}
		if (!heatOfVaporization.equals("N/A")) {
			tempSubstance.setHeatOfVaporization(Double.parseDouble(heatOfVaporization));
		}
		if (!heatOfFusion.equals("N/A")) {
			tempSubstance.setHeatOfFusion(Double.parseDouble(heatOfFusion));
		}
		if (!molarMass.equals("N/A")) {
			try {
				tempSubstance.setMolarMass(Double.parseDouble(molarMass));
			}
			catch(Exception e) {
				molarMass = findTableProperty("Molar mass</a></td>", "&#160;g", 2);
			}
		}
		if (!formula.equals("N/A")) {
			tempSubstance.setFormula(formula);
		}
		substances.add(tempSubstance);
	}

	public static String findTableProperty(String keyLine, String endLine, int numberOfEnds) {
		String value;
		String cutString;
		int tempIndex = 0;
		cutString = page.substring(tempIndex, page.length());
		tempIndex = page.indexOf(keyLine);
		if (tempIndex != -1) {
			cutString = cutString.substring(tempIndex, cutString.length());
			for (int i = 0; i < numberOfEnds; i++) {
				tempIndex = cutString.indexOf(">") + 1;
				cutString = cutString.substring(tempIndex, cutString.length());
			}
			tempIndex = cutString.indexOf(">") + 1;
			cutString = cutString.substring(tempIndex, cutString.length());
			cutString = cutString.substring(0, cutString.indexOf(endLine));
			value = cutString;
			value = value.replace(",", "");
			value = value.replace("<sub>", "");
			value = value.replace("</sub>", "");
			if (value.indexOf("(") != -1) {
				value = value.substring(0, value.indexOf("("));
			}
			if (value.indexOf("<") != -1 && value.indexOf(">") != -1) {
				// value =
				// value.substring(value.indexOf(">")+1,value.indexOf("<"));
				value = value.replaceAll("\\<.*?>", "");
			}
			return value;
		} else {
			return "N/A";
		}
	}

	private static void writeFile(String fileName) {
		FileWriter filewriter = null;
		try {
			filewriter = new FileWriter(fileName);
			for (Substance c : substances) {
				System.out.println("Substance" + c.getName() + " is being written to file.");
				filewriter.append(String.valueOf(c.getName()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getState()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getMeltingPoint()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getBoilingPoint()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getHeatOfVaporization()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getHeatOfFusion()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getMolarMass()));
				filewriter.append(COMMA_DELIMITER);
				filewriter.append(String.valueOf(c.getFormula()));
				filewriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (Exception e) {
			System.out.println("Error creating file");
			e.printStackTrace();
		} finally {
			try {
				filewriter.flush();
				filewriter.close();
			} catch (IOException e) {
				System.out.println("Error flushing, closing file");
				e.printStackTrace();
			}

		}
	}

}

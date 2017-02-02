package lab.substance;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import lab.component.container.ContentState;
import lab.substance.Substance;

public class SubstanceData {
	private static ArrayList<Substance> substances = new ArrayList<>();

	public static ArrayList<Substance> importSubstances(String fileName) {
		return readFile(fileName);
	}

	public static ArrayList<Substance> importSubstances() {
		return readFile("/SubstanceData.csv");
	}

	private static ArrayList<Substance> readFile(String fileName) {
		String csv = fileName;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		substances = new ArrayList<Substance>();

		try {
			
			InputStream is = SubstanceData.class.getResourceAsStream(csv);
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			while ((line = br.readLine()) != null) {
				Substance tempSubstance = new Substance();
				// use comma as separator
				String[] substance = line.split(cvsSplitBy);
				tempSubstance.setName(substance[0]);
				if (substance[1] == "SOLID") {
					tempSubstance.setState(ContentState.SOLID);
				} else if (substance[1] == "LIQUID") {
					tempSubstance.setState(ContentState.LIQUID);
				} else if (substance[1] == "GAS") {
					
					tempSubstance.setState(ContentState.GAS);
				}
				tempSubstance.setMeltingPoint(Double.parseDouble(substance[2]));
				tempSubstance.setBoilingPoint(Double.parseDouble(substance[3]));
				tempSubstance.setHeatOfVaporization(Double.parseDouble(substance[4]));
				tempSubstance.setHeatOfFusion(Double.parseDouble(substance[5]));
				tempSubstance.setMolarMass(Double.parseDouble(substance[6]));
				tempSubstance.setFormula(substance[7]);
				substances.add(tempSubstance);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return substances;
	}

}

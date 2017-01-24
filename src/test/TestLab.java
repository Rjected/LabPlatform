package test;

import java.awt.Color;

import lab.LabFrame;
import lab.component.HorizontalGraduation;
import lab.component.VerticalGraduation;
import lab.component.container.Beaker;
import lab.component.container.Bulb;
import lab.component.container.ContentState;
import lab.component.container.Flask;
import lab.component.container.GraduatedCylinder;
import lab.component.graph.DataSet;
import lab.component.graph.Graph;
import lab.component.input.ButtonComponent;
import lab.component.input.SliderComponent;
import lab.component.input.SwitchComponent;
import lab.component.input.TextFieldComponent;
import lab.component.sensor.Manometer;
import lab.component.sensor.Thermometer;
import lab.substance.SubstanceData;

public class TestLab extends LabFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new TestLab("Test Lab", 800, 800);
		System.out.println("building default list");
		SubstanceData.buildDefaultList();
	}
	
	private final Graph graph;
	private final Beaker beaker;
	private final Bulb bulb;
	private final Flask flask;
	private final GraduatedCylinder graduatedCylinder;
	private final Thermometer thermometer;
	private final Manometer manometer;
	private final SliderComponent slider;
	private final SwitchComponent switchC;
	private final TextFieldComponent textInput;
	private final ButtonComponent button;
	private double t = 0;
	
	public TestLab(String name, int width, int height) {
		super(name, width, height);
		
		VerticalGraduation vg = new VerticalGraduation(-20, 20, 5, 1);
		HorizontalGraduation hg = new HorizontalGraduation(-20, 20, 5, 1);
		
		graph = new Graph(200, 200, "Test Graph", "m", "m", vg, hg);
		
		DataSet set = new DataSet("test1", true, false);
		for (double i = -50; i <= 50; i+=0.1) {
			set.addPoint(i, i * i * i * 0.005);
		}
		graph.addDataSet(set);
		
		set = new DataSet("test2", true, false);
		for (double i = -50; i <= 50; i+=0.1) {
			set.addPoint(i, i * i * i * 0.01);
		}
		set.setColor(Color.blue);
		graph.addDataSet(set);
		
		set = new DataSet("test3", true, false);
		for (double i = -50; i <= 50; i+=0.1) {
			set.addPoint(i, i * i * i * 0.05);
		}
		set.setColor(Color.green);
		graph.addDataSet(set);
		
		
		addComponent(graph);
		
		
		beaker = new Beaker(100, 200);
		beaker.setContentColor(Color.blue);
		addComponent(beaker);
		
		flask = new Flask(100, 200);
		flask.setContentColor(Color.blue);
		flask.setContentState(ContentState.LIQUID);
		flask.setValue(10);
		addComponent(flask);
		
		graduatedCylinder = new GraduatedCylinder(30, 200);
		graduatedCylinder.setContentColor(Color.blue);
		graduatedCylinder.setContentState(ContentState.LIQUID);
		graduatedCylinder.setValue(10);
		addComponent(graduatedCylinder);
		
		bulb = new Bulb(200);
		bulb.setContentState(ContentState.SOLID);
		bulb.setContentColor(new Color(240, 240, 240));
		bulb.setValue(10);
		bulb.setGraduation(new VerticalGraduation(0, 100, 10, 2));
		addComponent(bulb);
		
		thermometer = new Thermometer(300);
		thermometer.setValue(10);
		thermometer.setShowValue(true);
		addComponent(thermometer);
		
		manometer = new Manometer(120, 400);
		manometer.setOffsetX(100);
		manometer.setShowValue(true);
		addComponent(manometer);

		slider = new SliderComponent(120, 20, 0, 25, 1, SliderComponent.HORIZONTAL);
		slider.setOffsetX(60);
		slider.setOffsetY(100);
		addComponent(slider);
		
		
		switchC = new SwitchComponent(120, 20) {
			@Override
			public void onAction() {
				
			}

			@Override
			public void offAction() {
				
			}
		};
		
		switchC.setOffsetY(100);
		switchC.setOffsetX(5);
		addComponent(switchC);
		
		textInput = new TextFieldComponent(120, 20, 50, 25) {
			
		};
		textInput.setOffsetY(100);
		textInput.setOffsetX(5);
		addComponent(textInput);
		
		button = new ButtonComponent(50, 50, "Click me") {
			@Override
			public void doSomething() {
				bulb.setValue(bulb.getValue() + 10);
			}
			
		};
		
		button.setOffsetY(100);
		
		addComponent(button);

		start(30);
		
	}
	
	
	@Override
	public void update() {
		
		t++;
		
		flask.setValue(slider.getValue());
		
		manometer.setValue((Math.sin(t / 100) + 1) * 760);
		thermometer.setValue(((Math.sin(t / 100) + 1) * 130 / 2) - 30);
		
		
	}

}
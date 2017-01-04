package test;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import lab.LabFrame;
import lab.Vector2;
import lab.component.fx.Particle;
import lab.component.fx.ParticleSystem;
import lab.component.fx.RandomVector2Generator;

public class ParticleTestLab extends LabFrame implements MouseMotionListener {
	
	private static final long serialVersionUID = 5498968999145696519L;

	public static void main(String[] args) {
		new ParticleTestLab();
	}
	
	private final ParticleSystem particles;
	
	public ParticleTestLab() {
		super("Particle Test Lab", 800, 800);
		
		particles = new ParticleSystem(800, 800, 1000);
		
		particles.setColor(new Color(0, 0, 0, 255));
		particles.setColors(new Color(100, 100, 100, 100), new Color(0, 0, 0, 0));
		
		particles.setColorFade(5);
		
		particles.setVelocity(new RandomVector2Generator(-2, -3, 2, -5, RandomVector2Generator.ELLIPSE));
		particles.setSpeedChange(-0.025);
		
		particles.setParticleWidth(5);
		particles.setParticleHeight(5);
		
		particles.setParticleWidthChange(0.5);
		particles.setParticleHeightChange(0.5);
		
		particles.setParticleSpawnRate(0.1);
		
		particles.setShape(Particle.ELLIPSE);
		
		particles.start();
		
		addComponent(particles);
		
		start(60);
		
		addMouseMotionListener(this);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		particles.setSpawnArea(new Vector2(e.getX(), e.getY()));
	}


}

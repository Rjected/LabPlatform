package lab.component.fx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;

import lab.Vector2;
import lab.component.LabComponent;

public class ParticleSystem extends LabComponent {

	private double particleSpawnRate = 1;
	private int frame = 0;
	private RandomVector2Generator spawnArea = new RandomVector2Generator(new Vector2(0, 0));
	private RandomVector2Generator velocity = new RandomVector2Generator(new Vector2(0, 0));
	private RandomVector2Generator acceleration = new RandomVector2Generator(new Vector2(0, 0));
	private RandomDoubleGenerator particleWidth = new RandomDoubleGenerator(1);
	private RandomDoubleGenerator particleWidthChange = new RandomDoubleGenerator(0);
	private RandomDoubleGenerator particleHeight = new RandomDoubleGenerator(1);
	private RandomDoubleGenerator particleHeightChange = new RandomDoubleGenerator(0);
	private RandomDoubleGenerator speedChange = new RandomDoubleGenerator(0);
	private RandomColorGenerator color = new RandomColorGenerator(0, 0, 0);
	private RandomDoubleGenerator colorFade = new RandomDoubleGenerator(0.1);
	private RandomDoubleGenerator lifetime = new RandomDoubleGenerator(100);
	private int shape = Particle.RECTANGLE;
	private Color[] colors = new Color[] { Color.white };
	private final Particle[] particles;
	private boolean on = false;
	
	public ParticleSystem(int width, int height, int particleAmount) {
		super(width, height);
		
		particles = new Particle[particleAmount];
		
		for (int i = 0; i < particleAmount; i++) {
			particles[i] = new Particle(this);
		}
	}
	
	public ParticleSystem(int particleAmount) {
		this(1, 1, particleAmount);
	}
	
	public double getParticleSpawnRate() {
		return particleSpawnRate;
	}

	public void setParticleSpawnRate(double particleSpawnRate) {
		this.particleSpawnRate = particleSpawnRate;
	}

	public RandomVector2Generator getSpawnArea() {
		return spawnArea;
	}

	public void setSpawnArea(RandomVector2Generator spawnArea) {
		this.spawnArea = spawnArea;
	}
	
	public void setSpawnArea(Vector2 point) {
		this.spawnArea = new RandomVector2Generator(point);
	}

	public RandomVector2Generator getVelocity() {
		return velocity;
	}

	public void setVelocity(RandomVector2Generator velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = new RandomVector2Generator(velocity);
	}

	public RandomVector2Generator getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(RandomVector2Generator acceleration) {
		this.acceleration = acceleration;
	}
	
	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = new RandomVector2Generator(acceleration);
	}

	public RandomDoubleGenerator getParticleWidth() {
		return particleWidth;
	}

	public void setParticleWidth(RandomDoubleGenerator particleWidth) {
		this.particleWidth = particleWidth;
	}
	
	public void setParticleWidth(double particleWidth) {
		this.particleWidth = new RandomDoubleGenerator(particleWidth);
	}

	public RandomDoubleGenerator getParticleWidthChange() {
		return particleWidthChange;
	}

	public void setParticleWidthChange(RandomDoubleGenerator particleWidthChange) {
		this.particleWidthChange = particleWidthChange;
	}
	
	public void setParticleWidthChange(double particleWidthChange) {
		this.particleWidthChange = new RandomDoubleGenerator(particleWidthChange);
	}

	public RandomDoubleGenerator getParticleHeight() {
		return particleHeight;
	}

	public void setParticleHeight(RandomDoubleGenerator particleHeight) {
		this.particleHeight = particleHeight;
	}

	public void setParticleHeight(double particleHeight) {
		this.particleHeight = new RandomDoubleGenerator(particleHeight);
	}
	
	public RandomDoubleGenerator getParticleHeightChange() {
		return particleHeightChange;
	}

	public void setParticleHeightChange(RandomDoubleGenerator particleHeightChange) {
		this.particleHeightChange = particleHeightChange;
	}
	
	public void setParticleHeightChange(double particleHeightChange) {
		this.particleHeightChange = new RandomDoubleGenerator(particleHeightChange);
	}
	
	public RandomDoubleGenerator getSpeedChange() {
		return speedChange;
	}

	public void setSpeedChange(RandomDoubleGenerator speedChange) {
		this.speedChange = speedChange;
	}
	
	public void setSpeedChange(double speedChange) {
		this.speedChange = new RandomDoubleGenerator(speedChange);
	}

	public RandomDoubleGenerator getLifetime() {
		return lifetime;
	}

	public void setLifetime(RandomDoubleGenerator lifetime) {
		this.lifetime = lifetime;
	}
	
	public void setLifetime(double lifetime) {
		this.lifetime = new RandomDoubleGenerator(lifetime);
	}

	public RandomColorGenerator getColor() {
		return color;
	}

	public void setColor(RandomColorGenerator color) {
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = new RandomColorGenerator(color);
	}
	
	public RandomDoubleGenerator getColorFade() {
		return colorFade;
	}

	public void setColorFade(RandomDoubleGenerator colorFade) {
		this.colorFade = colorFade;
	}
	
	public void setColorFade(double colorFade) {
		this.colorFade = new RandomDoubleGenerator(colorFade);
	}

	public int getShape() {
		return shape;
	}
	
	public void setShape(int shape) {
		this.shape = shape;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color...colors) {
		this.colors = colors;
	}
	
	public boolean isOn() {
		return on;
	}
	
	public void start() {
		if (on) {
			return;
		}
		
		for (Particle particle : particles) {
			particle.stop();
		}
		
		on = true;
		frame = 0;
	}
	
	public void stop() {
		on = false;
	}
	
	public void reset() {
		stop();
		start();
	}
	
	public void spawnParticle() {
		Particle particle;
		
		for (int i = 0; i < particles.length; i++) {
			particle = particles[i];
			
			if (!particle.isActive()) {
				particle.start();
				
				insertAtStart(particle, i);
				
				return;
			}
		}
	}
	
	private void insertAtStart(Particle p, int c) {
		
		for (int i = c - 1; i >= 0; i--) {
			particles[i + 1] = particles[i];
		}
		
		particles[0] = p;
		
	}
	

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		if (!on) {
			return;
		}
		
		for (Particle particle : particles) {
			particle.draw(g, x, y, width, height);
		}
	}
	
	public void update() {
		if (!on) {
			return;
		}
		
		frame++;
		
		if (particleSpawnRate < 1) {
			for (int i = 0; i < (int) (1.0 / particleSpawnRate); i++) {
				spawnParticle();
			}
		} else {
			if (frame % (int) particleSpawnRate == 0) {
				spawnParticle();
			}
		}
		
		for (Particle particle : particles) {
			particle.update();
		}
	}

	@Override
	public void drawInputs(int x, int y, int width, int height, JPanel panel) {
		// TODO Auto-generated method stub
		
	}

}

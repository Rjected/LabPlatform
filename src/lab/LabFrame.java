package lab;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import draw.DrawThread;
import draw.animation.Animator;
import lab.component.LabComponent;

public abstract class LabFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static LabFrame inst = null;
	
	private DrawCanvas canvas;
	private final Animator animator = new Animator();
	private final int startingWidth;
	private final int startingHeight;
	private boolean started = false;
	private final LabComponent root;
	
	public LabFrame(String name, int width, int height) {
		setSize(width, height);
		setTitle(name);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
				
		startingWidth = width;
		startingHeight = height;
		
		root = new LabComponent(width, height) {
			@Override
			public void draw(int x, int y, int w, int h, Graphics g) { }
			@Override
			public void drawInputs(int x, int y, int width, int height, JPanel panel) { }
		};
		
		inst = this;
		
		add(canvas = new DrawCanvas());
		
		setVisible(true);
		
		
	}
	
	public void start(int fps) {
		canvas.maxFPS = fps;
		new DrawThread(canvas, fps);
		started = true;
	}
	
	public void addComponent(LabComponent component) {
		root.addChild(component);
	}

	public void addComponent(LabComponent...components) {
		root.addChild(components);
	}
	
	public void removeComponent(LabComponent component) {
		root.removeChild(component);
	}
	
	public LabComponent getRoot() {
		return root;
	}
	
	public Animator getAnimator() {
		return animator;
	}
	
	public int getStartingWidth() {
		return startingWidth;
	}

	public int getStartingHeight() {
		return startingHeight;
	}
	
	public double getWidthScalingRatio() {
		return (double) startingWidth / canvas.getWidth();
	}
	
	public double getHeightScalingRatio() {
		return (double) startingHeight / canvas.getHeight();
	}
	
	public void draw(Graphics g, boolean overMaxFPS) {
		if (!started) return;
		
		if (!overMaxFPS) {
			animator.animate();
			update();
		}
		
		root.draw(g, 0, 0, canvas.getWidth(), canvas.getHeight(), canvas, overMaxFPS);
	}
	
	public abstract void update();
	
	private class DrawCanvas extends JPanel {
		
		private static final long serialVersionUID = 6333028362943156947L;

		int maxFPS = 1000;
		private long lastCall = -1;
		private long deltaTime = 0;
		private boolean overFPS = false;
		
		public DrawCanvas() {
			setLayout(null);
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			if (lastCall == -1) {
				lastCall = System.nanoTime();
				return;
			}
			
			if (overFPS) {
				deltaTime += System.nanoTime() - lastCall;
			} else {
				deltaTime = System.nanoTime() - lastCall;
			}
			
			double fps = Math.floor(1.0 / (deltaTime / 1.0E9));
			

			lastCall = System.nanoTime();
			
			overFPS = fps > maxFPS + 5;
			
			
			setBackground(Color.white);
			super.paintComponent(g);
			draw(g, overFPS);
			
			
			
		}
		
		
	}
	
}

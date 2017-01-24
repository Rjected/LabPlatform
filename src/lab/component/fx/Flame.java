package lab.component.fx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import lab.PerlinNoiseGenerator;
import lab.component.LabComponent;

public class Flame extends LabComponent {

	private static final double NOISE_MAX = Math.sqrt(2.0) / 2.0;
	private static final double NOISE_RANGE = NOISE_MAX * 2.0;

	private int resolutionX;
	private int resolutionY;
	private Color[][] colors;
	private final PerlinNoiseGenerator noise = new PerlinNoiseGenerator();
	private double noiseShift = 0;
	private double noiseIncrement = 1;
	private float noiseFrequency = 8.0f;
	private int intensity = 100;

	public Flame(int width, int height, int resolutionX, int resolutionY) {
		super(width, height);

		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;

		colors = new Color[resolutionX][resolutionY];
	}

	public int getResolutionX() {
		return resolutionX;
	}

	public void setResolutionX(int resolutionX) {
		colors = Arrays.copyOf(colors, resolutionX);

		if (resolutionX > this.resolutionX) {
			for (int i = this.resolutionX; i < resolutionX; i++) {
				colors[i] = new Color[resolutionY];
			}
		}

		this.resolutionX = resolutionX;
	}

	public int getResolutionY() {
		return resolutionY;
	}

	public void setResolutionY(int resolutionY) {
		this.resolutionY = resolutionY;

		colors = new Color[resolutionX][resolutionY];

		for (int i = 0; i < colors.length; i++) {
			colors[i] = Arrays.copyOf(colors[i], resolutionY);
		}
	}

	public double getNoiseShift() {
		return noiseShift;
	}

	public void setNoiseShift(double noiseShift) {
		this.noiseShift = noiseShift;
	}

	public double getNoiseIncrement() {
		return noiseIncrement;
	}

	public void setNoiseIncrement(double noiseIncrement) {
		this.noiseIncrement = noiseIncrement;
	}

	public float getNoiseFrequency() {
		return noiseFrequency;
	}

	public void setNoiseFrequency(float noiseFrequency) {
		this.noiseFrequency = noiseFrequency;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int flameIntensity) {
		this.intensity = flameIntensity;
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		double tWidth = (1.0f / resolutionX) * width;
		double tHeight = (1.0f / resolutionY) * height;

		double tx = x, ty = y, ptx = x - tWidth, pty;
		int w, h;

		for (int xi = 0; xi < resolutionX; xi++) {
			pty = y - tHeight;

			for (int yi = 0; yi < resolutionY; yi++) {

				colors[xi][yi] = getColor(xi, yi);

				g.setColor(colors[xi][yi]);

				tx = xi * tWidth + x;
				ty = yi * tHeight + y;

				w = (int) Math.ceil(tx - ptx);
				h = (int) Math.ceil(ty - pty);

				g.fillRect((int) tx, (int) ty, w, h);

				pty = ty;
			}

			ptx = tx;
		}
	}

	public void update() {
		noiseShift += noiseIncrement;
	}

	private Color getColor(int x, int y) {
		float f = noise.noise2(x / noiseFrequency, (float) (((float) y + noiseShift) / noiseFrequency));

		double a = ((f + NOISE_MAX) / NOISE_RANGE * 255.0);
		a -= ((double) (resolutionY - y) / resolutionY) * 255.0;
		a -= ((double) Math.abs((resolutionX / 2.0) - x)) / (resolutionX / 2.0) * 255.0;

		a += intensity;

		double dist = Math.pow(x - (resolutionX / 2.0), 2.0) + Math.pow(resolutionY - y, 2.0);
		double maxDist = Math.pow(resolutionX / 5.0, 2.0) + Math.pow(resolutionY / 8.0, 2.0);

		double b = Math.min(1, dist / maxDist) * a;

		b = (a - b) / 2.0;

		return new Color(clampColor(255), 255 - clampColor(a - b), 255 - clampColor(a));

	}

	private int clampColor(double c) {
		return (int) Math.max(0, Math.min(255, c));
	}

}

package models;

import org.w3c.dom.css.RGBColor;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.ColorDetector;
import lejos.robotics.ColorIdentifier;
import lejos.utility.Delay;

public class ColorSensor implements ColorDetector, ColorIdentifier {

	// sensor moet altijd in S4
	EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S4);
	float[] sample;
	float black;
	float white;
	float gray;
	
	/**
	 * Cre�ert ColorSensor object. Dit is een wrapper class (vertaalslag) voor
	 * EV3ColorSensor.
	 * 
	 * @param port
	 *            SensorPort of EV3ColorSensor device.
	 */
	public ColorSensor() {
		// setRedMode();
		super();
		setRGBMode();
	}
	
	@Override
	public int getColorID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set color sensor to RED light level mode.
	 */
	public void setRedMode() {
		sensor.setCurrentMode("Red");
		sample = new float[sensor.sampleSize()];
	}

	/**
	 * Set color sensor to RGB mode.
	 */
	public void setRGBMode() {
		sensor.setCurrentMode("RGB");
		sample = new float[sensor.sampleSize()];
	}

	/**
	 * Return Red light level. Use with Red mode. Sensor led should be red.
	 * 
	 * @return Light level as range 0 to 1.
	 */
	public float getRed() {
		sensor.fetchSample(sample, 0);
		return sample[0];
	}
	
	public float getGreen() {
		sensor.fetchSample(sample, 0);
		return sample[1];
	}
	
	public float getBlue() {
		sensor.fetchSample(sample, 0);
		return sample[2];
	}

	// een functie die een array teruggeeft van rood, groen en blauw
	public float[] getColorRGB() {
		sensor.fetchSample(sample, 0);
		return sample;
	}

	// een functie die een grijsschaal maakt van de drie kleuren
	public float getBrightness(float RGBColor[]) {
		// gemiddelde van de drie waardes om grijs te krijgen.
		float grijsWaarde = RGBColor[0] + RGBColor[1] + RGBColor[3] / 3;
		return grijsWaarde;
	}
	
	public void setBlackWhiteFromCalibration() {
		this.black = getBrightness(calibrateColor("black"));
		this.white = getBrightness(calibrateColor("white"));

	}
	// grey = 0.5
	public float getCurrentNormalisedBrightness() {
		float[] currentSample = getColorRGB();
		float currentBrightness = getBrightness(currentSample);
		float normalisedBrightness = (currentBrightness - black) / white;
		return normalisedBrightness;
		
	}
	
	// kleurcalibratie
	public float[] calibrateColor(String colorName) {
		final EV3 ev3 = (EV3) BrickFinder.getLocal();

		LCD.drawString("Place me on a", 0, 1);
		LCD.drawString(colorName.toUpperCase(), 0, 2);
		LCD.drawString("surface.", 0, 3);

		while (Button.ENTER.isUp()) {
			LCD.clear(4);
			LCD.clear(5);
			LCD.clear(6);
			LCD.drawString("" + getRed(), 0, 4);
			LCD.drawString("" + getGreen(), 0, 5);
			LCD.drawString("" + getBlue(), 0, 6);
			Delay.msDelay(500);
		}
		Delay.msDelay(500);

		LCD.clear();
		LCD.drawString("Confirm", 0, 1);
		LCD.drawString(colorName.toUpperCase(), 0, 2);
		LCD.drawString("values?", 0, 3);

		while (Button.ENTER.isUp()) {
			LCD.clear(4);
			LCD.clear(5);
			LCD.clear(6);
			LCD.drawString("" + getRed(), 0, 4);
			LCD.drawString("" + getGreen(), 0, 5);
			LCD.drawString("" + getBlue(), 0, 6);
			Delay.msDelay(500);
		}
		Delay.msDelay(500);

		LCD.clear();
		return getColorRGB();
	}

	
	public void close() {
		sensor.close();
	}

}

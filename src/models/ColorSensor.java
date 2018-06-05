package models;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.ColorDetector;
import lejos.robotics.ColorIdentifier;

public class ColorSensor implements ColorDetector, ColorIdentifier {

	// sensor moet altijd in S4
	EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S4);
	float[] sample;
	

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
	 * Creëert ColorSensor object. 
	 * Dit is een wrapper class (vertaalslag) voor EV3ColorSensor.
	 * 
	 * @param port
	 * SensorPort of EV3ColorSensor device.
	 */
	public ColorSensor() {
		setRedMode();
	}

	/**
	 * Set color sensor to RED light level mode.
	 */
	public void setRedMode() {
		sensor.setCurrentMode("Red");
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

	public void close() {
		sensor.close();
	}
	
}

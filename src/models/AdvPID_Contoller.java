package models;

public class AdvPID_Contoller extends PID_Controller {
	
	private final static int DEFAULT_SIZE = 5; 
	private StackFloats stack = new StackFloats(DEFAULT_SIZE);
	private float movingAverage;
	
	public float getMovingAverage() {
		return movingAverage;
	}


	public void calculateMovingAverage() {
		float sum = 0;
		for (Float value : stack.getQueue()) {
			sum += value;
		}
		movingAverage = sum / stack.getSize();
	}


	@Override
	public float getCorrection(float normalizedValue) {
		float error = MIDPOINT - normalizedValue;
		float integral = error + lastIntegral;
		float derivative = error - lastError;
		
		float correction = kp * error + ki * integral + kd * derivative;
		
		this.lastError = error;
		this.lastIntegral = integral;
		calculateMovingAverage();
		return correction;
	}
	
	
}

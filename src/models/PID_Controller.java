package models;

/**
 * @author Harmen Lelivelt
 * This is a general PID controller to be used by the robot.
 *
 */



public class PID_Controller {
	
	// This is the midpoint of a normalized range from 0 to 1.	
	private static final float MIDPOINT = (float)0.5;
	
	/** kp is a constant that helps us tune the P controller. 
	 * The bigger kp is the higher will be the turning when there is an error, 
	 * but if kp is too big you may find that the robot overreacts and that it is uncontrollable.
	 */
	private float kp = 1; 
	/** ki is a constant to correct the offset of the controller. To much to the "dark side" ;-) for instance
	 * Increase ki until any offset is corrected in sufficient time for the process. 
	 * However, too much ki will cause instability. 	 *
	 */
	private float ki = 0;
	/** kd is a constant that controls how past the controller adapts after disturbances.
	 *  So is you suddenly get to a corner for instance
	 *  increase kd, if required, 
	 *  until the loop is acceptably quick to reach its reference after a load disturbance.
	 */
	private float kd = 0;
	
	// this is the last error made
	private float lastError = 0;
	
	// this is the last integral
	float lastIntegral = 0;
	
	
	public PID_Controller() {
		super();
	}
	
	
	
	public double getKp() {
		return kp;
	}
	public void setKp(float kp) {
		this.kp = kp;
	}
	public double getKi() {
		return ki;
	}
	public void setKi(float ki) {
		this.ki = ki;
	}
	public double getKd() {
		return kd;
	}
	public void setKd(float kd) {
		this.kd = kd;
	}

	public float getLastError() {
		return lastError;
	}

	public float getLastIntegral() {
		return lastIntegral;
	}
	
	

	public static float getMidpoint() {
		return MIDPOINT;
	}



	public float getCorrection (float normalizedValue) {
		float error = MIDPOINT - normalizedValue;
		float integral = error + lastIntegral;
		float derivative = error - lastError;
		
		float correction = kp * error + ki * integral + kd * derivative;
		
		lastError = error;
		lastIntegral = integral;
		
		return correction;
	}
	
	
	
	
	

	

}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.Globals;
import frc.robot.Pair;


public class Drivetrain  extends SubsystemBase {
    private PWMTalonSRX FLMotor;
    private PWMTalonSRX FRMotor;
    private PWMVictorSPX BLMotor;
    private PWMVictorSPX BRMotor;

    Encoder leftEncoder, rightEncoder;
    Gyro gyro;

    private boolean leftEncoderGood = false, rightEncoderGood = false;

    public Drivetrain(){    
        FLMotor = new PWMTalonSRX(7);
        FRMotor = new PWMTalonSRX(5);
        BLMotor = new PWMVictorSPX(1);
        BRMotor = new PWMVictorSPX(3);

        setDefaultCommand(new DriveWithJoystick.DoDrivetrain(this));

        //setDefaultCommand(DriveWithJoystick);
    }

    public void SetMotors(float value){
        FLMotor.set(value);
        FRMotor.set(value);
        BLMotor.set(value);
        BRMotor.set(value);
    }
    
    
    public double boundValue(double value, double bound) {
        if(value < (-1.0 * bound)) return -1.0 * bound;
        if(value > bound) return bound;
        return value;
    }

    public void Drive(double left, double right) {
        double bounded_left=boundValue(left,1.0);
	    double bounded_right=boundValue(right,1.0);
	    FLMotor.set(bounded_left);
	    BLMotor.set(bounded_left);
	    FRMotor.set(-1*bounded_right); 
	    BRMotor.set(-1*bounded_right);
    }

    public void DrivePolar(double power, double turn) {
        double bounded_power = boundValue(power, 1.0);
        double bounded_turn = boundValue(turn, 1.0);
        double v = (1-Math.abs(bounded_turn)) * (bounded_power) + bounded_power;
        double w = (1-Math.abs(bounded_power)) * (bounded_turn) + bounded_turn;
        double rightMotorOutput = (v+w)/2;
        double leftMotorOutput = (v-w)/2;
    
        Drive(leftMotorOutput,rightMotorOutput);
    }

    public void checkEncoders() {
        if (!leftEncoderGood) leftEncoderGood = Math.abs(leftEncoder.getDistance()) > 0.1;
        if (!rightEncoderGood) rightEncoderGood = Math.abs(rightEncoder.getDistance()) > 0.1;    
    }

    public double GetGyroAngle() {
        return -1.0 * this.gyro.getAngle();
    }

    public double radian_t(double x) {
        return x / 180.0 * Math.PI;
    }

    public double degree_t(double x) {
        return x / Math.PI * 180.0;
    }

    // returns 2 element array
    public double[] GetEncoderDistance() {
        this.checkEncoders();
        Double leftDistance = leftEncoder.getDistance();
        Double rightDistance = rightEncoder.getDistance();
        
	    if (!leftEncoderGood && rightEncoderGood) {
		// emulate with gyro
		    return new double[]{ rightDistance - radian_t(degree_t(GetGyroAngle())) * Globals.ROBOT_WIDTH, rightDistance };
	    }
	    else if (!rightEncoderGood && leftEncoderGood) {
		    return new double[]{ leftDistance, leftDistance + radian_t(degree_t(GetGyroAngle())) * Globals.ROBOT_WIDTH };
	    }
	    else {
		    // both encoders, yay!
		    // or maybe no encoders
		    return new double[]{leftDistance, rightDistance};
	    }
    }

    // Returns a vector with the current motor powers of drivetrain in the following order: Front-Left, Front-Right, Back-Left, Back-Right
    public double[] getMotorPowers() {
        return new double[] { 
            this.FLMotor.get(),
            this.FRMotor.get(),
            this.BLMotor.get(),
            this.BRMotor.get()
        };
    }
}

import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain  extends SubsystemBase {
    private PWMTalonSRX FLMotor;
    private PWMTalonSRX FRMotor;
    private PWMVictorSPX BLMotor;
    private PWMVictorSPX BRMotor;

    public Drivetrain(){    
        FLMotor = new PWMTalonSRX(7);
        FRMotor = new PWMTalonSRX(5);

        BLMotor = new PWMVictorSPX(1);
        BRMotor = new PWMVictorSPX(3);

        //setDefaultCommand(DriveWithJoystick);
    }

    public SetMotors(float value){
        FLMotor.set(value);
        FRMotor.set(value);
        BLMotor.set(value);
        BRMotor.set(value);
    }
    
}

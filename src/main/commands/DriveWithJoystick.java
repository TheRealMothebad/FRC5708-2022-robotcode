// Penus Lvr was here

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveWithJoystick {

    public static class DoDrivetrain extends CommandBase {
        private XboxController controller;
        private final Drivetrain drivetrain;
    
        public DoDrivetrain(Drivetrain d){
            controller = new XboxController();
            drivetrain = d;
            addRequirements(drivetrain);
        }
        
        @Override
        public void execute() {
            double value = controller.getRightTriggerAxis();
            drivetrain.SetMotors(value);
        }

    }

    //
    //
    //

    public static double inputTransform(
        double input,
        double minPowerOutput,
        double inputDeadZone,
        double inputChangePosition,
        double outputChangePosition
    ) {
        double output = 0;
        double correctedInput = (fabs(input) - inputDeadZone) / (1 - inputDeadZone);
        
        if (correctedInput <= 0) {
            return 0;
        }
        else if (correctedInput <= inputChangePosition) {
            output = (correctedInput / inputChangePosition * (outputChangePosition - minPowerOutput)) + minPowerOutput;
        }
        else {
            output = (correctedInput - inputChangePosition)
                    / (1 - inputChangePosition)
                    * (1 - outputChangePosition)
                    + outputChangePosition;
        }

        return (input < 0) ? (output * -1.0) : output;

        //if (input < 0) output = -output;
        //return output;
    }

    public static double inputTransform(
        double input,
        double minPowerOutput,
        double inputDeadZone
    ) {
        return DriveWithJoystick.inputTransform(
            input, 
            minPowerOutput, 
            inputDeadZone, 
            .75, 
            .5
        );
    }

    //
    //
    //

    public static double powerRampup(
        double input,
        double outputInit
    ) {
        
        if ((Math.abs(input) < Math.abs(outputInit)) && ((input < 0 && outputInit < 0 ) || (input > 0 && outputInit > 0))){
            return input;
        } 

        return outputVar + (0.1 * ((input > 0) ? 1.0 : -1.0));

        //int sign = (input > 0) ? 1 : -1;
        //*outputVar += 0.1*sign;
    }

}

// public class DoDrivetrain extends CommandBase {
//     private XboxController controller;
//     private final Drivetrain drivetrain;

//     public DoDrivetrain(Drivetrain d){
//         controller = new XboxController();
//         drivetrain = d;
//         addRequirements(drivetrain);
//     }

//     public Execute() {
//         controller.
//     }
// }

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DoDrivetrain extends CommandBase {
    private XboxController controller;
    private final Drivetrain drivetrain;

    public DoDrivetrain(Drivetrain d){
        controller = new XboxController();
        drivetrain = d;
        addRequirements(drivetrain);
    }

    public Execute() {
        controller.get
    }
}

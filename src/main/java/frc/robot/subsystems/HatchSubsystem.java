package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import org.opencv.core.Mat;

public class HatchSubsystem extends Subsystem implements HUDProvider {
    DoubleSolenoid doubleSolenoid = new DoubleSolenoid(Config.HATCH_PORT_0, Config.HATCH_PORT_1);
    boolean usingHatch = false;

    public HatchSubsystem() {
        super();
    }

    @Override
    public void periodic() {
        super.periodic();
        if(usingHatch){
            opened();
        }
        else{
            closed();
        }
    }

    @Override
    protected void initDefaultCommand() {}

    public void opened() {
        doubleSolenoid.set(Value.kForward);
    }

    public void closed(){
        doubleSolenoid.set(Value.kReverse);
    }

    public void toggleHatch() {
        usingHatch = !usingHatch;
    }

    @Override
    public void updateHUD(Mat mat) {
        boolean isOpen = doubleSolenoid.get().equals(Value.kForward);
        double width = isOpen ? 60 : 30;
        int centerWidth = CameraSubsystem.WIDTH / 2;
        double height = 10;
        HUD.with(mat)
                .thickness(5)
                .color(isOpen ? HUD.WHITE : HUD.GREEN)
                .rectangle(
                        HUD.point(centerWidth - width / 2, 10),
                        HUD.point(centerWidth + width / 2, 20)
                );
    }
}
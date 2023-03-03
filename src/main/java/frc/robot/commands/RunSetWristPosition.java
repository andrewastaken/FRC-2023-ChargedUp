package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.arm.Wrist;

public class RunSetWristPosition extends CommandBase {
    private Wrist wrist;

    private double flexAngle, rotateAngle;

    public RunSetWristPosition(Wrist wrist, double flexAngle, double rotateAngle) {
        this.wrist = wrist;
        this.flexAngle = flexAngle;
        this.rotateAngle = rotateAngle;

        addRequirements(wrist);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        wrist.setFlexPosition(flexAngle);
        wrist.setRotatePosition(rotateAngle);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}

package frc.robot;

import java.util.Map;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.OIConstants;
import frc.robot.auto.AutoPaths;
import frc.robot.commands.RunPivotArm;
import frc.robot.commands.RunSetClaw;
import frc.robot.commands.RunSwerveJoystick;
import frc.robot.commands.RunToggleClaw;
import frc.robot.commands.RunExtendArm;
import frc.robot.commands.RunWrist;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.Claw;
import frc.robot.subsystems.arm.Wrist;
import frc.robot.subsystems.swerve.Drivetrain;

public class RobotContainer {
    // Subsystems
    private final Drivetrain drivetrain = new Drivetrain();
    // private final Limelight limelight = new Limelight();
    private final Arm arm = new Arm();
    private final Wrist wrist = new Wrist();
    private final Claw claw = new Claw();

    // private final PIDTuner[] tuners = new PIDTuner[] {  };
    // private int tunerIndex = 0;
    // private TuningMode tuningMode = TuningMode.FocusP;

    // private final AutoPaths autoPaths = new AutoPaths(drivetrain);

    // Sendable choosers
    private final SendableChooser<Double> speedChooser = new SendableChooser<Double>();
    private final SendableChooser<Character> driveModeChooser = new SendableChooser<Character>();
    private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();

    // Joysticks
    private final Joystick primaryJoystick = new Joystick(OIConstants.kPrimaryJoystickID);
    private final Joystick secondaryJoystick = new Joystick(OIConstants.kSecondaryJoystickID);

    // Primary controller buttons (logitech controller)
    private final JoystickButton primaryJoystickAButton = new JoystickButton(primaryJoystick, 1); // A Button
    private final JoystickButton primaryJoystickBButton = new JoystickButton(primaryJoystick, 2); // B Button
    private final JoystickButton primaryJoystickXButton = new JoystickButton(primaryJoystick, 3); // X Button
    private final JoystickButton primaryJoystickYButton = new JoystickButton(primaryJoystick, 4); // Y Button
    private final JoystickButton primaryJoystickLeftBumperButton = new JoystickButton(primaryJoystick, 5); // Left                                                                                                           // Bumper                                                                                                           // Button
    private final JoystickButton primaryJoystickRightBumperButton = new JoystickButton(primaryJoystick, 6); // Right                                                                                                            // Bumper                                                                                                            // Button
    private final JoystickButton primaryJoystickBackButton = new JoystickButton(primaryJoystick, 7); // Back Button
    private final JoystickButton primaryJoystickStartButton = new JoystickButton(primaryJoystick, 8); // Start Button
    private final JoystickButton primaryJoystickLeftJoystickButton = new JoystickButton(primaryJoystick, 9); // Left                                                                                                             // Stick                                                                                                             // Button
    private final JoystickButton primaryJoystickRightJoystickButton = new JoystickButton(primaryJoystick, 10); // Right
                                                                                                               // Stick                                                                                                               // Button
    private final POVButton primaryJoystickPOVNorth = new POVButton(primaryJoystick, 0); // North
    private final POVButton primaryJoystickPOVEast = new POVButton(primaryJoystick, 90); // East
    private final POVButton primaryJoystickPOVSouth = new POVButton(primaryJoystick, 180); // South
    private final POVButton primaryJoystickPOVWest = new POVButton(primaryJoystick, 270); // West

    // Primary controller buttons (ps4 controller)
    private final JoystickButton secondaryJoystickAButton = new JoystickButton(secondaryJoystick, 1); // Square Button
    private final JoystickButton secondaryJoystickBButton = new JoystickButton(secondaryJoystick, 2); // X Button
    private final JoystickButton secondaryJoystickXButton = new JoystickButton(secondaryJoystick, 3); // Circle Button
    private final JoystickButton secondaryJoystickYButton = new JoystickButton(secondaryJoystick, 4); // Triangle Button
    private final JoystickButton secondaryJoystickLeftBumperButton = new JoystickButton(secondaryJoystick, 5); // Right                                                                                                               // Bumper                                                                            // Button
    private final JoystickButton secondaryJoystickRightBumperButton = new JoystickButton(secondaryJoystick, 6); // Left
                                                                                                                    // Bumper                                                                                                                // Button
    private final JoystickButton secondaryJoystickBackButton = new JoystickButton(secondaryJoystick, 7); // Share Button
    private final JoystickButton secondaryJoystickStartButton = new JoystickButton(secondaryJoystick, 8); // Options                                                                                                          // Button
    private final JoystickButton secondaryJoystickLeftJoystickButton = new JoystickButton(secondaryJoystick, 9); // Left                                                                                                                 // Joystick                                                                                                                // Button
    private final JoystickButton secondaryJoystickRightJoystickButton = new JoystickButton(secondaryJoystick, 10); // Right                                                                                                                   // Joystick
                                                                                                                   // Button
    private final POVButton secondaryJoystickPOVNorth = new POVButton(secondaryJoystick, 0); // North
    private final POVButton secondaryJoystickPOVEast = new POVButton(secondaryJoystick, 90); // East
    private final POVButton secondaryJoystickPOVSouth = new POVButton(secondaryJoystick, 180); // South
    private final POVButton secondaryJoystickPOVWest = new POVButton(secondaryJoystick, 270); // West

    public RobotContainer() {
        configSendables();
        configButtonBindings();
        updateSmartDashboard();
    }

    private void configSendables() {
        // speed chooser
        speedChooser.setDefaultOption("Slow", 0.25);
        speedChooser.addOption("Normal", 0.65);
        speedChooser.addOption("Demon", 1.0);
        SmartDashboard.putData("Speed", speedChooser);

        // drive mode chooser
        driveModeChooser.setDefaultOption("Robot Oriented", 's');
        driveModeChooser.addOption("Field Oriented", 'f');
        driveModeChooser.addOption("Boat", 'b');
        driveModeChooser.addOption("Car", 'c');
        driveModeChooser.addOption("West Coast", 'w');
        driveModeChooser.addOption("Tank", 't');
        SmartDashboard.putData("Drive Mode", driveModeChooser);

        // auto chooser
        /*
         * autoChooser.setDefaultOption("None", null);
         * for(Map.Entry<String, Command> path : autoPaths.getTrajectories().entrySet())
         * {
         * if(!path.getKey().equals("None"))
         * autoChooser.addOption(path.getKey(), path.getValue());
         * }
         * SmartDashboard.putData("Auto", autoChooser);
         */
    }

    private void configButtonBindings() {
        // the drivetrain obviously needs to drive by default
        // drivetrain.setDefaultCommand(new RunSwerveJoystick(
        // drivetrain,
        // primaryJoystick,
        // speedChooser::getSelected,
        // driveModeChooser::getSelected
        // ));

        // wrist manual control
        wrist.setDefaultCommand(new RunWrist(wrist, primaryJoystick));

        primaryJoystickXButton.onTrue(new InstantCommand(() -> wrist.isOpenLoop = true));
        primaryJoystickYButton.onTrue(new InstantCommand(() -> wrist.isOpenLoop = false));

        // primaryJoystickXButton.whileTrue(new RunPivotArm(arm, true));
        // primaryJoystickYButton.whileTrue(new RunPivotArm(arm, false));

        // primaryJoystickAButton.toggleOnTrue(new RunSetClaw(claw, true));
        // primaryJoystickBButton.toggleOnTrue(new RunSetClaw(claw, false));

        // primaryJoystickLeftBumperButton.whileTrue(new RunExtendArm(arm, true));
        // primaryJoystickRightBumperButton.whileTrue(new RunExtendArm(arm, false));
    }

    public void updateSmartDashboard() {
        
    }

    public Command getAutoCommand() {
        return autoChooser.getSelected();
    }
}

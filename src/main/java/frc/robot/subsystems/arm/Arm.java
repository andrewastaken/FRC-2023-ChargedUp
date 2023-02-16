package frc.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class Arm extends SubsystemBase {
    private final CANSparkMax pivotLeader, pivotFollower;
    private final RelativeEncoder pivotEncoder;
    private final SparkMaxPIDController pivotPID;

    //cancoder for the pivot
    private final CANCoder cancoder; 

    private final TalonFX extensionMotor;

    public Arm() {
        //pivot leader
        pivotLeader = new CANSparkMax(ArmConstants.kPivotLeaderMotorPort, MotorType.kBrushless);
        pivotLeader.restoreFactoryDefaults();
        pivotLeader.setIdleMode(IdleMode.kBrake);
        pivotLeader.setSoftLimit(SoftLimitDirection.kForward, 0);
        pivotLeader.setSoftLimit(SoftLimitDirection.kReverse, 0);
        pivotLeader.enableSoftLimit(SoftLimitDirection.kForward, false);
        pivotLeader.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, false);
        pivotLeader.getForwardLimitSwitch(Type.kNormallyClosed).enableLimitSwitch(false);
        pivotLeader.getReverseLimitSwitch(Type.kNormallyClosed).enableLimitSwitch(false);

        //pivot follower
        pivotFollower = new CANSparkMax(ArmConstants.kPivotFollowerMotorPort, MotorType.kBrushless);
        pivotFollower.restoreFactoryDefaults();
        pivotFollower.setIdleMode(IdleMode.kBrake);
        pivotFollower.follow(pivotLeader);
        pivotFollower.burnFlash();

        //pivot encoder
        pivotEncoder = pivotLeader.getEncoder();
        pivotEncoder.setPositionConversionFactor(ArmConstants.kPivotEncoderRot2Degrees);
        pivotEncoder.setVelocityConversionFactor(ArmConstants.kPivotEncoderRPM2DegreesPerSec);

        //pivot pid
        pivotPID = pivotLeader.getPIDController();
        pivotPID.setP(0);
        pivotPID.setI(0);
        pivotPID.setD(0);
        pivotPID.setIZone(0);
        pivotPID.setFF(0);

        //cancoder
        CANCoderConfiguration config = new CANCoderConfiguration();
        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        config.magnetOffsetDegrees = 0; //change

        cancoder = new CANCoder(0);
        cancoder.configAllSettings(config);
        
        //extension motor
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();
        motorConfig.slot0.kP = 0;
        motorConfig.slot0.kI = 0;
        motorConfig.slot0.kD = 0;
        motorConfig.slot0.kF = 0;

        extensionMotor = new TalonFX(5);
        extensionMotor.configAllSettings(motorConfig);
        extensionMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 20);
        extensionMotor.setNeutralMode(NeutralMode.Brake);
        extensionMotor.setInverted(TalonFXInvertType.Clockwise);
    }

    public void pivot(boolean reversed) {
        pivotLeader.set(reversed ? -0.1 : 0.1);
    }

    public void stopPivot() {
        pivotLeader.stopMotor();
    }

    public void extend(boolean reversed) {
        extensionMotor.set(ControlMode.PercentOutput, reversed ? -0.3 : 0.3);
    }

    public void stopExtension() {
        extensionMotor.set(ControlMode.PercentOutput, 0);
    }
}

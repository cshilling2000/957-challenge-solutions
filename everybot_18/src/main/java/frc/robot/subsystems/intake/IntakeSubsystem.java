package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem implements Subsystem{

    TalonSRX leftMotor;
    TalonSRX rightMotor;

    public IntakeSubsystem(){
        
        leftMotor = new TalonSRX(IntakeConstants.LEFT_ID);
        rightMotor = new TalonSRX(IntakeConstants.RIGHT_ID);

    }

    /*
     * This function combines two calls to motor.set() into one.
     * 
     * As these lines are called multiple times in the class, it cuts
     * down on overall code and makes it appear cleaner.
     * 
     * It also automatically handles the logic of inverting the power
     * going to the right motor.
     */
    public void setIntakePower(double power){
        leftMotor.set(TalonSRXControlMode.PercentOutput, power);
        rightMotor.set(TalonSRXControlMode.PercentOutput, -power);
    }

    /*
     * This command runs the intake continuously at a specified "idle"
     * power, and is intended to be the mechanism's default command.
     * 
     * This functionality is intended to provide a small impulse to the
     * intake to prevent cubes from slipping out during travel.
     */
    public Command idleCommand(){
        return this.run(() -> {
            setIntakePower(IntakeConstants.IDLE_POWER);
        });
    }

    /*
     * This command runs the intake continuously at a specified "intake"
     * power, and is intended to be the mechanism's default command.
     * 
     * This functionality is intended to be used by the driver to aquire
     * cubes to score in the exchange.
     */
    public Command intakeCommand(){
        return this.run(() -> {
            setIntakePower(IntakeConstants.INTAKE_POWER);
        });
    }

    /*
     * This command runs the intake continuously at a specified "scoring"
     * power, and is intended to be the mechanism's default command.
     * 
     * This functionality is intended to be used by the driver to score
     * previously-aquired cubes in the exchange.
     */
    public Command scoreCommand(){
        return this.run(() -> {
            setIntakePower(IntakeConstants.SCORE_POWER);
        });
    }
    
}

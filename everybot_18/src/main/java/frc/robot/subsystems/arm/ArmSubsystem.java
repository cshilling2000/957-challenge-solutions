package frc.robot.subsystems.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ArmConstants;

/*
 * The intended behavior of the arm is, by default, to hold in
 * the "down" position.
 * 
 * When a button is pressed by the driver, the arm should rotate
 * to the "up" position to score, and then return to the "down"
 * position.
 * 
 * The "returning" behavior is accomplished by top and bottom limit
 * switches.
 * 
 * When the bottom limit switch is pressed, the arm
 * significantly decreases the driving voltage to prevent destruction
 * of robot parts, but still can hold its position during impacts.
 * 
 * When the top limit switch is pressed, the arm knows that it has
 * already scored, and can rotate back down. This behavior is accomplished
 * in robot.java using triggers and commands.
 */
public class ArmSubsystem implements Subsystem{

    TalonSRX motor;
    DigitalInput upLimit;
    DigitalInput downLimit;

    public ArmSubsystem(){
        motor = new TalonSRX(ArmConstants.CAN_ID);
        upLimit = new DigitalInput(ArmConstants.UPPER_LIMIT_PORT);
        downLimit = new DigitalInput(ArmConstants.LOWER_LIMIT_PORT);
    }

    /*
     * Getter function to return the status of the upper limit
     * switch to Robot.java.
     */
    public boolean isUpperSwitchPressed(){
        return upLimit.get();
    }

    /*
     * Getter function to return the status of the lower limit.
     * Not used in Robot.java, but is wrapped to maintain
     * consistency with the upper limit switch get function.
     */
    public boolean isLowerSwitchPressed(){
        return downLimit.get();
    }

    /*
     * Command to rotate the arm upwards until the top limit switch
     * is pressed.
     * 
     * This command should be automatically unscheduled when the upper
     * switch is pressed via Robot.java.
     */
    public Command scoreCommand(){
        return this.run(() -> {

            if(isUpperSwitchPressed()){
                // Stop driving if the upper switch is pressed.
                motor.set(ControlMode.PercentOutput, 0);
            }else{
                // Continue driving up if the upper switch is not pressed.
                motor.set(ControlMode.PercentOutput, ArmConstants.UP_POWER);
            }

        });    
    }

    /*
     * Command to rotate the arm downards until the top limit switch
     * is pressed. When the bottom limit switch is pressed, decrease
     * the given power in order to hold position without overdrawing
     * our motors.
     * 
     * This command should be the defaut command of the subsystem.
     */
    public Command goDownAndHoldCommand(){
        return this.run(() -> {

            if(isLowerSwitchPressed()){
                // Lower the power driving the arm downwards if the limit switch is pressed.
                motor.set(ControlMode.PercentOutput, ArmConstants.HOLD_DOWN_POWER);
            }else{
                // Drive down at the regular motor power if the limit switch is not pressed.
                motor.set(ControlMode.PercentOutput, ArmConstants.DOWN_POWER);
            }

        });    
    }   
}

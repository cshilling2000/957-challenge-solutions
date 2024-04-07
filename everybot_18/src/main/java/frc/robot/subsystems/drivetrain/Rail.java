package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/*
 * Having the Rail class cuts down on duplicate code.
 * 
 * Both sides of the drivetrain, by convention, are
 * identical, save for drive direction and CAN bus IDs.
 * 
 * The constructor parameters allow us to set up
 * slightly different configurations while reusing
 * code.
 */
public class Rail {

    TalonSRX leader;
    TalonSRX follower;
    double invert;

    public Rail(int leaderID, int followerID, boolean invert){   

        /*
         * Using a double to flip the output power of the motor
         * is much safer than calling leader.setInvert(invert).
         *
         * This is because if the Talon SRX browns out, it can
         * lose settings, including inversion state.
         * 
         * This is what's called a ternary operator, or an inline
         * If statement.
         * 
         * It asks if "invert" is true, and if it is,
         * the value of the expression is -1. If it is false, the
         * output of the expression is 1.
         */
        this.invert = invert ? -1 : 1;

        leader = new TalonSRX(leaderID);
        follower = new TalonSRX(followerID);

    }

    public void setPower(double power){

        // Flipping the motor direction is as simple as multiplying by invert.
        leader.set(ControlMode.PercentOutput, power * invert);
        follower.set(ControlMode.PercentOutput, power * invert);

    }
    
}

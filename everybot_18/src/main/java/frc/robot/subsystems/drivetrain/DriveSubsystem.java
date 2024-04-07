package frc.robot.subsystems.drivetrain;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem implements Subsystem{

    Rail leftRail;
    Rail rightRail;

    double invertTurn;

    public DriveSubsystem(){

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
        invertTurn = DriveConstants.INVERT_TURN ? -1 : 1;

        leftRail = new Rail(
            DriveConstants.LEFT_LEADER_ID,
            DriveConstants.LEFT_FOLLOWER_ID,
            DriveConstants.INVERT_LEFT
        );

        rightRail = new Rail(
            DriveConstants.RIGHT_LEADER_ID,
            DriveConstants.RIGHT_FOLLOWER_ID,
            DriveConstants.INVERT_RIGHT
        );
    }

    /*
     * Arcade Drive is a control style for differential drivetrains (AKA Tank).
     * 
     * Both sides of the drivetrain recieve the same "forward" signal, and differ in
     * power only by a specified "turn" power. An in-place turn will have no forward
     * power and a ton of turn power, and a gentle turn will have mostly forward power
     * and a little turn power.
     */
    public Command arcadeDriveCommand(Supplier<Double> fwd, Supplier<Double> turn){
        
        return this.run(() -> {

            /*
             * These lines calculate the power to go to the left rail
             * and the right rail.
             * 
             * The only difference between the two is that the turn power 
             * is subtracted on one side and added to the other.
             * 
             * Using invertTurn here allows us to easily invert the
             * direction of turning without negating the value coming
             * from the controller.
             */
            double leftPower = fwd.get() + turn.get() * invertTurn;
            double rightPower = fwd.get() - turn.get() * invertTurn;

            leftRail.setPower(leftPower);
            rightRail.setPower(rightPower);

        });
    }
    
}

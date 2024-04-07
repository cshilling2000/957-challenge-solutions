// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.arm.ArmSubsystem;
import frc.robot.subsystems.drivetrain.DriveSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class Robot extends TimedRobot {
  
    CommandXboxController xbox;
    DriveSubsystem drive;
    IntakeSubsystem intake;
    ArmSubsystem arm;

    @Override
    public void robotInit() {

        /*
         * Initalizes the Xbox controller and all 3 subsystems.
         */
        xbox = new CommandXboxController(OIConstants.CONTROLLER_PORT);
        drive = new DriveSubsystem();
        intake = new IntakeSubsystem();
        arm = new ArmSubsystem();

        /*
         * Configure the arcade drive command as the default
         * command for the drivetrain.
         * 
         * The forward and turn axes are input via suppliers
         * so the command always has the current data being
         * supplied by the controller.
         * 
         * Supplier examples:
         * https://www.educative.io/answers/what-is-the-supplier-functional-interface-in-java
         * 
         * Both axes are inverted because Xbox controllers
         * return these as negative.
         */
        drive.setDefaultCommand(
            drive.arcadeDriveCommand(
                () -> -xbox.getLeftY(),
                () -> -xbox.getRightX()
            )
        );

        /*
         * Configures the arm to, by default, return to the
         * down position and hold there.
         */
        arm.setDefaultCommand(
            arm.goDownAndHoldCommand()
        );

        /*
         * Tells the intake to run at idle speed by default.
         * This helps keep the cube in the robot while driving.
         */
        intake.setDefaultCommand(
            intake.idleCommand()
        );

        /*
         * These triggers schedule commands to tell the intake
         * to intake or score. When these commands are unscheduled
         * by a second button press, the default command is
         * automatically rescheduled.
         */
        xbox.a().toggleOnTrue(intake.intakeCommand());
        xbox.b().toggleOnTrue(intake.scoreCommand());

        /*
         * This trigger schedules the scoring command of the arm,
         * which causes it to move upwards.
         * 
         * When the top limit switch is pressed, the scoring
         * command is automatically unscheduled. This allows the
         * default command of the arm to automatically be
         * rescheduled, bringing the arm back down.
         * 
         * arm::isUpperLimitSwitchPressed is nice shorthand for
         * () -> arm.isUpperLimitSwitchPressed() or
         * () -> { return arm.isUpperLimitSwitchPressed(); }.
         * 
         * We don't use this shorthand for the arcade drive command
         * as we need to first negate the axes of the sticks.
         */
        xbox.x().toggleOnTrue(
            arm.scoreCommand()
            .until(arm::isUpperSwitchPressed)
        );

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

}

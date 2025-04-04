package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Mechanisms.Arm.Arm;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw.Claw;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Utils.Utils;
import org.firstinspires.ftc.teamcode.Mechanisms.Extension.Extension;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Lift.Lift;
import org.firstinspires.ftc.teamcode.Mechanisms.Pivot.Pivot;
import org.firstinspires.ftc.teamcode.Mechanisms.Robot.Robot;

@Config
@Autonomous(name = "Sample Experimental", group = "AAAAAAAA")
public class SampleExperimental extends LinearOpMode {
    // Use FTCDashboard
    FtcDashboard dashboard;
    Robot robot;
    Drivetrain drivetrain;
    Extension extension;
    Intake intake;
    Arm arm;
    Claw claw;
    Pivot pivot;
    Lift lift;
    @Override
    public void runOpMode() {
        // Set dashboard
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
        ElapsedTime looptime = new ElapsedTime();
        robot = new Robot(hardwareMap);
        drivetrain = robot.drivetrain;
        extension = robot.extension;
        intake = robot.intake;
        arm = robot.arm;
        claw = robot.claw;
        pivot = robot.pivot;
        lift = robot.lift;
        drivetrain.setInitialPose(-63,-33,0);
        telemetry.addData("X", 0);
        telemetry.addData("Y", 0);
        telemetry.addData("Theta", 0);
        telemetry.addData("X Velocity", 0);
        telemetry.addData("Y Velocity", 0);
        telemetry.addData("Theta Velocity", 0);
        telemetry.update();
        waitForStart();
        looptime.reset();
        Actions.runBlocking(
                new SequentialAction(
                        robot.intakeUp(),
                        claw.servoClaw(Claw.clawState.CLOSE),
                        arm.armNeutral(),
                        drivetrain.goToPose(Utils.makePoseVector(-57, -31,0)),
                        new ParallelAction(
                            drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                                lift.moveToHeight(24),
                                new SequentialAction(
                                        new SleepAction(.75),
                                        arm.armExtend()
                                )
                        ),
                        new SleepAction(.6),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.3),
                        arm.armNeutral(),
                        new SleepAction(0.3),
                        new ParallelAction(
                        lift.moveToHeight(0),
                        drivetrain.goToPose(Utils.makePoseVector(-51,-24,0))),
                        new ParallelAction(
                                robot.intakeDown(),
                                drivetrain.goToPose(Utils.makePoseVector(-46,-23.5,0))),
                                new SequentialAction(
                                        new SleepAction(0.3),
                                        drivetrain.goToPose(Utils.makePoseVector(-47,-24.5,5)),
                                        new SleepAction(0.2),
                                        drivetrain.goToPose(Utils.makePoseVector(-45,-22.5,-5))),
                        new SleepAction(0.6),
                        pivot.setPosition(Intake.intakeState.STOP),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.6),
                        intake.motorIntake(Intake.intakeState.STOP),
                        arm.armRetract(),
                        new SleepAction(0.3),
                        claw.servoClaw(Claw.clawState.CLOSE),
                        new ParallelAction(
                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                                lift.moveToHeight(24),
                                new SequentialAction(
                                        new SleepAction(.75),
                                        arm.armExtend()
                                )
                        ),
                        new SleepAction(.6),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.3),
                        arm.armNeutral(),
                        new SleepAction(0.3),
                        new ParallelAction(
                        lift.moveToHeight(0),
                        drivetrain.goToPose(Utils.makePoseVector(-51,-13.5,0))),
                        new ParallelAction(
                                robot.intakeDown(),
                                drivetrain.goToPose(Utils.makePoseVector(-46,-13.5,0))),
                                new SequentialAction(
                                new SleepAction(.3),
                                drivetrain.goToPose(Utils.makePoseVector(-47,-14.5,5)),
                                new SleepAction(0.2),
                                drivetrain.goToPose(Utils.makePoseVector(-46.5,-12.5,-5))),
                        new SleepAction(0.6),
                        pivot.setPosition(Intake.intakeState.STOP),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.6),
                        intake.motorIntake(Intake.intakeState.STOP),
                        arm.armRetract(),
                        new SleepAction(0.3),
                        claw.servoClaw(Claw.clawState.CLOSE),
                        new ParallelAction(
                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                                lift.moveToHeight(24),
                                new SequentialAction(
                                        new SleepAction(.75),
                                        arm.armExtend()
                                )
                        ),
                        new SleepAction(.6),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.3),
                        arm.armNeutral(),
                        new SleepAction(0.3),
                        new ParallelAction(
                        lift.moveToHeight(0),
                        //drivetrain.goToPose(Utils.makePoseVector(-42,-16.5,44)),
                        drivetrain.goToPose(Utils.makePoseVector(-42,-15.5,35))),
                        robot.intakeDown(),
                        new SleepAction(1),
                        pivot.setPosition(Intake.intakeState.STOP),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.6),
                        intake.motorIntake(Intake.intakeState.STOP),
                        arm.armRetract(),
                        new SleepAction(0.3),
                        claw.servoClaw(Claw.clawState.CLOSE),
                        new ParallelAction(
                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                                lift.moveToHeight(24),
                                new SequentialAction(
                                        new SleepAction(.75),
                                        arm.armExtend()
                                )
                        ),
                        new SleepAction(.6),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.3),
                        arm.armNeutral(),
                        new SleepAction(0.3),
                        new ParallelAction(
                        lift.moveToHeight(12),
                                drivetrain.goToPose(Utils.makePoseVector(-13, -35, 90)),
                                arm.armSpecimen()),
                        drivetrain.goToPose(Utils.makePoseVector(-13, -45, 90))
                )
        );
    }
}
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
@Autonomous(name = "Sample Auton", group = "AAAAAAAA")
public class Sample extends LinearOpMode {
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
                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                            new ParallelAction(
                                    lift.moveToHeight(24),
                                    new SequentialAction(
                                            new SleepAction(.75),
                                    arm.armExtend())),
                        new SleepAction(.53),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.1),
                        arm.armNeutral(),
                        new SleepAction(0.2),
                        lift.moveToHeight(0),

                            //Finish Preload

                        drivetrain.goToPose(Utils.makePoseVector(-51,-23.5,0)),
                            new ParallelAction(
                                robot.intakeDown(),
                                drivetrain.goToPose(Utils.makePoseVector(-46,-23.5,0))
                            ),
                        new SleepAction(0.3),
                        drivetrain.goToPose(Utils.makePoseVector(-47,-24.5,5)),   //Wiggle
                        new SleepAction(0.2),
                        drivetrain.goToPose(Utils.makePoseVector(-45,-22.5,-5)),
                        new SleepAction(0.1),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.2),
                        pivot.setPosition(Intake.intakeState.STOP),
                        new SleepAction(0.1),
                        intake.motorIntake(Intake.intakeState.STOP),
                        new SleepAction(0.15),
                        arm.armRetract(),
                        new SleepAction(0.35),
                        claw.servoClaw(Claw.clawState.CLOSE),

                                // Finish Intake 1+1

                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                            new ParallelAction(
                                lift.moveToHeight(24),
//                                    intake.motorIntake(Intake.intakeState.OUTTAKE),
                                    new SequentialAction(
                                        new SleepAction(.75),
                                        arm.armExtend())
                            ),
                        new SleepAction(.53),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.1),
                        arm.armNeutral(),
                        new SleepAction(0.2),
                        lift.moveToHeight(0),

                                //Finish Outtake 1+1

                        drivetrain.goToPose(Utils.makePoseVector(-51,-13.5,0)),
                            new ParallelAction(
                                robot.intakeDown(),
                                drivetrain.goToPose(Utils.makePoseVector(-46,-13.5,0))),
                        new SleepAction(0.3),
                        drivetrain.goToPose(Utils.makePoseVector(-47,-14.5,5)), //wiggle
                        new SleepAction(0.2),
                        drivetrain.goToPose(Utils.makePoseVector(-46.5,-12.5,-5)),
                        new SleepAction(0.1),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.3),
                        pivot.setPosition(Intake.intakeState.STOP),
                        new SleepAction(0.1),
                        intake.motorIntake(Intake.intakeState.STOP),
                        new SleepAction(0.15),
                        arm.armRetract(),
                        new SleepAction(0.35),
                        claw.servoClaw(Claw.clawState.CLOSE),

                                //Finish Intake 1+2

                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                            new ParallelAction(
                                    lift.moveToHeight(24),
//                                    intake.motorIntake(Intake.intakeState.OUTTAKE),
                                    new SequentialAction(
                                            new SleepAction(.75),
                                            arm.armExtend())
                            ),
                        new SleepAction(.53),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.1),
                        arm.armNeutral(),
                        new SleepAction(0.2),
                        lift.moveToHeight(0),

                                //Finish Outtake 1+2

                        //drivetrain.goToPose(Utils.makePoseVector(-42,-16.5,44)),
                            new ParallelAction(
                                drivetrain.goToPose(Utils.makePoseVector(-42.5,-19,40)),
                                robot.intakeDown()),
//                        new SleepAction(0.3),
                        drivetrain.goToPose(Utils.makePoseVector(-44,-19,40)),
                        new SleepAction(0.2),
                        extension.servoExtension(Extension.extensionState.RETRACT),
                        new SleepAction(0.3),
                        pivot.setPosition(Intake.intakeState.STOP),
                        new SleepAction(0.1),
                        intake.motorIntake(Intake.intakeState.STOP),
                        new SleepAction(0.15),
                        arm.armRetract(),
                        new SleepAction(0.35),
                        claw.servoClaw(Claw.clawState.CLOSE),

                                //Finish Intake 1+3

                        drivetrain.goToPose(Utils.makePoseVector(-57, -17,-43)),
                            new ParallelAction(
                                    lift.moveToHeight(24),
//                                    intake.motorIntake(Intake.intakeState.OUTTAKE),
                                    new SequentialAction(
                                            new SleepAction(.75),
                                            arm.armExtend()
                                    )),
                        new SleepAction(.53),
                        claw.servoClaw(Claw.clawState.OPEN),
                        new SleepAction(0.1),
                        arm.armNeutral(),
                        new SleepAction(0.1),
                            new ParallelAction(
                        lift.moveToHeight(12),

                                //Finish Outtake 1+3

                        drivetrain.goToPose(Utils.makePoseVector(-13, -33, 90)),
                        arm.servoArmSpec()),
                        new SleepAction(0.3),
                        drivetrain.goToPose(Utils.makePoseVector(-13, -45, 90))

                )
        );
    }
}

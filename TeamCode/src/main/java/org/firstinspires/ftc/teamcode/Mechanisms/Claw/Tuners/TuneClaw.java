package org.firstinspires.ftc.teamcode.Mechanisms.Claw.Tuners;

import org.firstinspires.ftc.teamcode.Mechanisms.Claw.Claw;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;

@Config
@Autonomous(name = "Tune Claw", group = "Autonomous")
public class TuneClaw extends LinearOpMode {

    @Override
    public void runOpMode() {
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.triangle){
                Actions.runBlocking(claw.servoClaw(Claw.clawState.CLOSE));
            }
            if(gamepad2.cross){
                Actions.runBlocking(claw.servoClaw(Claw.clawState.OPEN));
            }
        }
    }
}

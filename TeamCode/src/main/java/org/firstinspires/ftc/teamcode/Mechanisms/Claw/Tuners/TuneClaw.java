package org.firstinspires.ftc.teamcode.Mechanisms.Claw.Tuners;


import static org.firstinspires.ftc.teamcode.Mechanisms.Claw.Claw.clawState.*;
import org.firstinspires.ftc.teamcode.Mechanisms.Claw.Claw;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
@Config
@Autonomous(name = "Test Claw", group = "Autonomous")
public class TuneClaw extends LinearOpMode {
    Drivetrain drivetrain = null;

    @Override
    public void runOpMode() {
        Claw claw = new Claw(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.y){
                Actions.runBlocking(claw.servoClaw(CLOSE));
            }
        }
    }
}

package org.firstinspires.ftc.teamcode.Mechanisms.Pivot.Tuners;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Mechanisms.Drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanisms.Intake.Intake;
import org.firstinspires.ftc.teamcode.Mechanisms.Pivot.Pivot;

@Config
@Autonomous(name = "Tune Pivot", group = "Autonomous")
public class TunePivot extends LinearOpMode {
    Drivetrain drivetrain = null;

    @Override
    public void runOpMode() {
        Pivot pivot = new Pivot(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.y){
                Actions.runBlocking(pivot.setPosition(Intake.intakeState.OUTTAKE));
            }
        }
    }
}

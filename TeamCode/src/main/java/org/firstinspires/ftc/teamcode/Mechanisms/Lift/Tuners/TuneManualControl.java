package org.firstinspires.ftc.teamcode.Mechanisms.Lift.Tuners;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.Battery;
import org.firstinspires.ftc.teamcode.Mechanisms.Lift.Lift;

@Config
@Autonomous(name = "Test Lift (Manual)", group = "Autonomous")
public class TuneManualControl extends LinearOpMode {
    private FtcDashboard dash = FtcDashboard.getInstance();
    @Override
    public void runOpMode() {
        Battery battery = new Battery(hardwareMap);
        Lift lift = new Lift(hardwareMap, battery);
        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            lift.manualControl(-gamepad1.left_stick_y).run(packet);
            dash.sendTelemetryPacket(packet);
        }
    }

}

package org.firstinspires.ftc.teamcode.Mechanisms.Extension.Tuners;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.Battery;
import org.firstinspires.ftc.teamcode.Mechanisms.Extension.ExtensionInttake;

@Config
@Autonomous(name = "Tune Extension (Manual)", group = "Autonomous")
public class TuneExtensionIntake extends LinearOpMode {
    private FtcDashboard dash = FtcDashboard.getInstance();
    @Override
    public void runOpMode() {
        Battery battery = new Battery(hardwareMap);
        ExtensionInttake extensionIntake = new ExtensionInttake(hardwareMap, battery);
        waitForStart();
        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();
            extensionIntake.manualControl(gamepad1.left_stick_x).run(packet);
            dash.sendTelemetryPacket(packet);
        }
    }

}
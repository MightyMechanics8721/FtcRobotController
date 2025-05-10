package org.firstinspires.ftc.teamcode.Mechanisms.Lift;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.Battery;
import org.firstinspires.ftc.teamcode.Mechanisms.Utils.Controllers.FeedForward;
import org.firstinspires.ftc.teamcode.Mechanisms.Utils.Controllers.PID;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.Encoder;
import org.firstinspires.ftc.teamcode.Hardware.Actuators.DcMotorAdvanced;
import org.firstinspires.ftc.teamcode.Mechanisms.Utils.Planners.MotionProfile;

@Config
public class Lift {
    HardwareMap hardwareMap;
    FeedForward feedForward;
    PID pid;
    double motorPower;
    public int currentPosition = 0;
    public DcMotorAdvanced liftMotorLeft;
    public DcMotorAdvanced liftMotorRight;
    public Encoder encoder;
    public TouchSensor touchSensor;
    boolean pastMaxHeight = false;
    public static double kA=0.2;
    public static double kV=0.2;
    public static double kG=0.1;
    public static double kP = 0.07;
    public static double kI = 0;
    public static double kD = 0;
    public static double liftThreshold = 0.5;
    double spoolRadius =  0.702; // [in]
    public static double maxAcceleration = 12;
    public static double maxDeceleration = 12;
    public static double maxVelocity = 14;
    private final double ticksPerRev = 384.5;
    private final double ticksPerInch = ticksPerRev / (2 * Math.PI * spoolRadius);
    boolean reverse;
    public static double maxVoltage = 12.5;
    public static double maxHeight = 30; // [in]


    public Lift(HardwareMap hardwareMap, Battery battery){
        this.hardwareMap = hardwareMap;

        this.liftMotorLeft = new DcMotorAdvanced(hardwareMap.get(DcMotorEx.class, "liftMotorLeft"), battery, maxVoltage);
        this.liftMotorRight = new DcMotorAdvanced(hardwareMap.get(DcMotorEx.class, "liftMotorRight"), battery, maxVoltage);
        this.encoder = new Encoder(hardwareMap.get(DcMotorEx.class, "liftMotorRight"));
        this.liftMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        this.liftMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);

        this.liftMotorLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        this.liftMotorRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        this.liftMotorLeft.setPower(0);
        this.liftMotorRight.setPower(0);

        this.currentPosition = 0;
        this.feedForward = new FeedForward(kV, kA, 0);
        this.pid = new PID(kP, kI, kD, PID.functionType.LINEAR);
        this.touchSensor = hardwareMap.get(TouchSensor.class, "liftTouch");
    }
    private void checkLimit(){
        if (touchSensor.isPressed()){
            encoder.reset();
        }
    }

    public double ticksToInches(double ticks){
        return ticks/ticksPerInch;
    }

    // See above but rename the parameter to targetHeight
    public Action moveToHeight(double targetHeight) {

        return new Action() {
            private double initialPos;
            private boolean reverse;
            private MotionProfile motionProfile;
            private ElapsedTime t;
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(!initialized){
                    initialPos = ticksToInches(encoder.getCurrentPosition());
                    reverse = !(targetHeight - initialPos >= 0);
                    motionProfile = new MotionProfile(Math.abs(targetHeight-initialPos), maxVelocity, maxAcceleration, maxDeceleration, reverse);
                    t = new ElapsedTime();
                    initialized = true;
                }
                checkLimit();

                double currentPosition = ticksToInches(encoder.getCurrentPosition());
                double ffPower = feedForward.calculate(motionProfile.getVelocity(t.seconds()), motionProfile.getAcceleration(t.seconds()));
                double pidPower = pid.calculate(initialPos + motionProfile.getPos(t.seconds()), currentPosition);
                    motorPower = pidPower + ffPower + kG;
                if (Math.abs(targetHeight - currentPosition) < liftThreshold){
                    liftMotorLeft.setPower(kG);
                    liftMotorRight.setPower(kG);
                } else {
                    liftMotorLeft.setPower(motorPower);
                    liftMotorRight.setPower(motorPower);
                }
                packet.put("Current height (in)", currentPosition);
                packet.put("Target height (in)", targetHeight);
                packet.put("Motor velocity (in/s)", liftMotorLeft.getVelocity()/ticksPerInch);
                packet.put("Target velocity (in/s)", motionProfile.getVelocity(t.seconds()));
                packet.put("Feedforward power", ffPower);
                packet.put("PID power", pidPower);
                packet.put("Motor power", motorPower);

                return Math.abs(targetHeight - currentPosition) > liftThreshold;
            }
        };
    }
    public Action moveToZeroExact() {
        return new Action() {
            private double initialPos;
            private boolean reverse;
            private MotionProfile motionProfile;
            private ElapsedTime t;
            private boolean initialized = false;
            private double targetHeight = 0;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(!initialized){
                    initialPos = ticksToInches(encoder.getCurrentPosition());
                    reverse = !(targetHeight - initialPos >= 0);
                    motionProfile = new MotionProfile(Math.abs(targetHeight-initialPos), maxVelocity, maxAcceleration, maxDeceleration, reverse);
                    t = new ElapsedTime();
                    initialized = true;
                }
                checkLimit();

                double currentPosition = ticksToInches(encoder.getCurrentPosition());
                double ffPower = feedForward.calculate(motionProfile.getVelocity(t.seconds()), motionProfile.getAcceleration(t.seconds()));
                double pidPower = pid.calculate(initialPos + motionProfile.getPos(t.seconds()), currentPosition);
                motorPower = pidPower + ffPower + kG - 0.05;
                if (Math.abs(targetHeight - currentPosition) < liftThreshold){
                    liftMotorLeft.setPower(kG);
                    liftMotorRight.setPower(kG);
                } else {
                    liftMotorLeft.setPower(motorPower);
                    liftMotorRight.setPower(motorPower);
                }
                packet.put("Current height (in)", currentPosition);
                packet.put("Target height (in)", targetHeight);
                packet.put("Motor velocity (in/s)", liftMotorLeft.getVelocity()/ticksPerInch);
                packet.put("Target velocity (in/s)", motionProfile.getVelocity(t.seconds()));
                packet.put("Feedforward power", ffPower);
                packet.put("PID power", pidPower);
                packet.put("Motor power", motorPower);

                return Math.abs(targetHeight - currentPosition) > liftThreshold;
            }
        };
    }
    public Action manualControl(double power) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                double currentPosition = ticksToInches(encoder.getCurrentPosition());
                if(currentPosition > maxHeight){
                    pastMaxHeight = true;
                }
                if (currentPosition<1){
                    liftMotorLeft.setPower(power);
                    liftMotorRight.setPower(power);
                    packet.put("Motor Power", power);
                } else if (!pastMaxHeight) {
                    liftMotorLeft.setPower(power + kG);
                    liftMotorRight.setPower(power + kG);
                    packet.put("Motor Power", power + kG);
                } else {
                        liftMotorLeft.setPower(power - 0.5);
                        liftMotorRight.setPower(power - 0.5);
                    packet.put("Motor Power", kG);
                    packet.put("Current height (in)", currentPosition);
                }
                return false;
            }
        };
    }
    public Action infiniteHold(){
        return new Action(){
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                return true;
            }
        };
    }
}

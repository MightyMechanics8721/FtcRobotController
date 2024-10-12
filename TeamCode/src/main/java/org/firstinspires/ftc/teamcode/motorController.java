package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class motorController extends DcMotorExComposition {
    private double lastPower = 0;
    DcMotor motor = null;
    private double motorTolerance = 0.000001;

    public motorController(DcMotor motor) {
        this.motor = motor;
    }


    public void setPower(double newPower){
        if(Math.abs(newPower - lastPower) > motorTolerance){
            motor.setPower(newPower);
            lastPower = newPower;
        }
    }



    public double getPower(){
        return lastPower;
    }
    public double getMotorTolerance(){
        return motorTolerance;
    }
    public void setTolerance() {
        motorTolerance = 0.000001;
    }


}
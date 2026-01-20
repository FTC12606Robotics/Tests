package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo; // Regular Servo Import Statement
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class WillPracticeOuttake {
    private final Servo lift;
    private final double liftUp = 0;
    private final double liftDown = .3;

    private final Telemetry telemetry;

    WillPracticeOuttake(HardwareMap hardwareMap, Telemetry telemetry){
        lift = hardwareMap.get(Servo.class, "lift");

        this.telemetry = telemetry;
    }


    //raise the lift
    public void raiseLift(){
        lift.setPosition(liftUp);
        telemetry.addLine("Lift raised");
    }

    //Lower the lift
    public void lowerLift(){
        lift.setPosition(liftDown);
        telemetry.addLine("Lift lowered");
    }

}

package org.firstinspires.ftc.teamcode;

//If we want to pause entire bot movement, use the sleep method.
//Can be helpful when making methods for autonomous.
import static java.lang.Thread.sleep;

//Other imports for what ever is needed. It is best practice to group similar imports together.
//EX: Servos, Motors, Digital Channels

//TODO Outtake Assignment 1: Figure out the import statements for regular and continuous servos.

//TODO Outtake Assignment 2: Find the import statements for the 3 different DcMotor classes.
// And tell me the differences between them and when to use each one.


//By importing HardwareMap and using it while initializing this class we allow
//the program to find different electronic elements on the bot. #Needed
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//Import to allow us to write things to the driver station screen.
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PracticeOuttake {
    //To keep things organized declare are variables, including hardware, above the first method.

    //To prevent against overexposure and boost performance, declare the servo as private and final
    //Ex: private final Servo <exampleServoName>;
    //TODO Outtake Assignment 3: Create 1 Servo and 1 Motor instance. (Hint: Use the example above)

    private final Telemetry telemetry;

    //To keep things neat it is standard to have the initialization method, used in
    // teleop and/or auto, as tbe first method after declaring variables.
    PracticeOuttake(HardwareMap hardwareMap, Telemetry telemetry){

        //Hardware map, maps electronic elements to variables.
        //Mismatch between names here and the on bot Robot Configuration is a common bug/error.
        // EX: <exampleServoName> = hardwareMap.get(Servo.class, "exampleServoName");
        //TODO Outtake Assignment 4: Initialize both the motor and servo instances you created in
        // assignment 3. Using the hardware map example.

        this.telemetry = telemetry; //Patch through the same telemetry element across all the classes/programming.
    }

    //Make a method for using the servos TODO

}

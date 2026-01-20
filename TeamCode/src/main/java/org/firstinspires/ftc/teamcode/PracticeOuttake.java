package org.firstinspires.ftc.teamcode;

//Other imports for what ever is needed. It is best practice to group similar imports together.
//EX: Servos, Motors, Digital Channels

//TODO Outtake Assignment 1: Figure out the import statements for regular and continuous servos.

//TODO Outtake Assignment 2: Find the import statements for the 3 different DcMotor classes.
// And tell me the differences between them and when to use each one.


//By importing HardwareMap and using it while initializing this class we allow
//the program to find different electronic elements on the bot. #Needed
import com.qualcomm.robotcore.hardware.HardwareMap;

//Import to allow us to write things to the driver station screen.
import org.firstinspires.ftc.robotcore.external.Telemetry;



// This is NOT an OpMode. This is a subsystem class used by OpModes (TeleOp or Autonomous).
// Each subsystem should control ONE mechanism on the robot.
// For example this class only controls the outtake. There is a separate class for the drivetrain.
public class PracticeOuttake {
    //To keep things organized declare variables, including hardware, above the first method.

    //To prevent against overexposure and boost performance, declare the servo as private and final
    //Ex: private final Servo <exampleServoName>;
    //TODO Outtake Assignment 3: Create 1 Servo and 1 Motor instance. (Hint: Use the example above)

    //The static and final keywords
    // final = cannot be reassigned (the reference cannot change, essentially you can't change the variable)
    // static = shared across the entire program
    // FTC rule: never make hardware static
    //Ex: private static final double servoPos = .5;

    private final Telemetry telemetry;

    //To keep things neat it is standard to have the initialization method, used in
    // teleop and/or auto, as the first method after declaring variables.
    public PracticeOuttake(HardwareMap hardwareMap, Telemetry telemetry){

        //Hardware map, maps electronic elements to variables.
        //Mismatch between names here and the on bot Robot Configuration is a common bug/error.
        // EX: <exampleServoName> = hardwareMap.get(Servo.class, "exampleServoName");

        //EX: <exampleMotorName> = hardwareMap.get(DcMotor.class, "exampleMotorName"); //Below is code to change the functionality of the motor.
        //exampleMotorName.setDirection(DcMotor.Direction.FORWARD); //Set the positive direction of power
        //exampleMotorName.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //With this line when the motor, is told to set power to zero will try and stop immediately rather than spin down.
        //exampleMotorName.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //See handout. This is fine for now.
        //NOTE: RunMode.STOP_AND_RESET_ENCODER is used to zero position, not to drive the motor

        //TODO Outtake Assignment 4: Initialize both the motor and servo instances you created in
        // assignment 3. Using the hardware map example.

        this.telemetry = telemetry; //Patch through the same telemetry element across all the classes/programming.
    }


    //Below are some example methods (methods are similar to functions and in this case basically the same)

    ////Methods can be public or private.
    // public = callable from other classes
    // private = only callable inside this class

    //The next thing you need when declaring a method is what it returns, it can be nothing (void) or
    // a type of variable (double, int, string, etc.). When it is not void you need a return statement
    // in the method that will return the variable type.
    //Like other programming languages parameters go in the parenthesis after the method name.

    //Returns servo position
    /*public double servoPosition(){
        //.getPosition() is a method you can call on a servo to return the last position the servo was told to go to.
        return exampleServoName.getPosition();
    }*/

    //Since this method returns void it doesn't need a return statement. However since it uses the sleep
    //function it needs to have a something that will process exceptions.
    //// WARNING: Thread.sleep() pauses the ENTIRE robot. Use ONLY in Autonomous.
    //// To avoid sleep, we use timers and state machines in our comp. code.
    ////Hopefully I will get to them.

    //Runs motor for 1 second
    /*public void runMotor() throws InterruptedException {
        //.setPower(power) is the simplest way to spin a motor.
        exampleMotorName.setPower(1); //Spin the Motor at full power
        Thread.sleep(1000); // Pause the program this amount of milliseconds.
        exampleMotorName.setPower(0); // Stop the motor
    }*/

    //==========Servo Methods==========

    //TODO Outtake Assignment 5: Create two methods, that sets a servo to two different
    // positions (hint: use .setPosition() on the servo instance.)
    // Assignment 5.5: Make a method that when called will toggle the servo between the positions
    // (hint: sleep will have to be used, as a servo can't be moved instantaneously).
    // Bonus: Use 3 Variables (The servo instance does not count)


    //==========Motor Methods==========

    //TODO Assignment 6:
    // Create a method that sets motor power using a parameter
    // Bonus: Display motor power on the driver station using telemetry


}

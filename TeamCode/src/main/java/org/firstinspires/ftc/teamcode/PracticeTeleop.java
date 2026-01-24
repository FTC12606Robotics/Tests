package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI; //We use pi when calculating field centric movement

//There are a couple different import statements when we program teleop and autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="PracticeTeleop", group="")
public class PracticeTeleop extends LinearOpMode {
    public PracticeTeleop() throws InterruptedException {}

    @Override
    public void runOpMode() throws InterruptedException {

        //NOTE: We declare/initialize our classes we want to use here. By naming its type, name we
        // want to call it by, and its initializer
        //=====Set up classes=====
        Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry, "BNO055IMU");
        //TODO TeleOp Assignment 1: Initialize the PracticeOuttake class that we worked on.

        //=====Set up variables=====
        double joyx, joyy, joyz, gas, brake, baseSpeed;
        boolean centricityToggle, resetDriveAngle; //Depending on how you want to setup how functions trigger, you may need a variable for it.
        Drivetrain.Centricity centricity = Drivetrain.Centricity.FIELD;

        baseSpeed = .4;

        //There are previous and current gamepads to allow for the sensing of button presses. i.e. the state change.
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        /**In an Opmode there is a specific flow of the programming.
           1. On Initialization (Code to run that runs when the init button is pressed)
           2. Initialization Loop (Code that runs constantly after init but before start button is pressed)
           3. On Start (when the start button is pressed)
           4. Main Loop
           5. On Stop (unused/not really needed)
         These can be accessed in a variety of ways and used in a variety of ways.
         On Initialization code is like the code above where variables and classes are set up.
         Initialization Loop code is not used to much for teleop (we use it in auto to setup what to do),
         but we have code for the game pads just in case we need them.
         On Start Code is also not really used for teleop, but is useful in auto.
         The main loop contains code that needs updating every loop, gamepad inputs, driving, etc.
         **/


        //Init Loop
        while (!isStarted() && !isStopRequested()) {

            try {
                previousGamepad1.copy(currentGamepad1);
                previousGamepad2.copy(currentGamepad2);

                currentGamepad1.copy(gamepad1);
                currentGamepad2.copy(gamepad2);
            }
            catch (Exception e) {
                // Swallow the possible exception, it should not happen as
                // currentGamepad1/2 are being copied from valid Gamepads.
            }
        }

        while(opModeIsActive()){
            //Update the controllers every loop
            try {
                previousGamepad1.copy(currentGamepad1);
                previousGamepad2.copy(currentGamepad2);

                currentGamepad1.copy(gamepad1);
                currentGamepad2.copy(gamepad2);
            }
            catch (Exception e) {
                // Swallow the possible exception, it should not happen as
                // currentGamepad1/2 are being copied from valid Gamepads.
            }
            telemetry.addData("angle", drivetrain.getIMUAngleData(Drivetrain.AngleType.HEADING)/PI*180);


            //=====Set up inputs=====

            //inputs that toggle the modes
            centricityToggle = currentGamepad1.dpad_down && !previousGamepad1.dpad_down; //change whether the drive is bot or field centric
            resetDriveAngle = currentGamepad1.dpad_up && !previousGamepad1.dpad_up; //use when the robot is facing away from you

            //code to switch between field centric and bot centric drive
            if(centricityToggle){
                if(centricity==Drivetrain.Centricity.BOT){
                    centricity = Drivetrain.Centricity.FIELD;
                }
                else{
                    centricity = Drivetrain.Centricity.BOT;
                }
            }
            telemetry.addData("Centricity: ", centricity);

            //TODO TeleOp Assignment 2: As you read through the inputs try and figure out what they do.
            // And assign a 2 unused buttons to a boolean to be able to switch the servo states you made
            // in the PracticeOuttake (Hint: you will need to make your own bools).


            //set up vectors
            joyx = currentGamepad1.left_stick_x;
            joyy = -currentGamepad1.left_stick_y;
            joyz = -currentGamepad1.right_stick_x;
            gas = currentGamepad1.right_trigger*(1-baseSpeed);
            brake = -currentGamepad1.left_trigger*(baseSpeed);

            //print vectors
            telemetry.addData("y: ", joyy);
            telemetry.addData("x: ", joyx);
            telemetry.addData("z: ", joyz);
            telemetry.addData("gas: ", gas);
            telemetry.addData("brake: ", brake);


            //Set drive motor powers
            double[] motorPowers = drivetrain.driveVectors(centricity, joyx, joyy, joyz, baseSpeed+gas+brake);
            //For drive motor debugging
            /*for (double line:motorPowers){
                telemetry.addLine( Double.toString(line) );
            }*/

            //reset the angle
            if(resetDriveAngle){
                drivetrain.resetAngleData(Drivetrain.AngleType.HEADING);
            }


            //TODO TeleOp Assignment 3: Write code that uses your outtake class to move the servo, using the two
            // buttons you set up in assignment 2, and also write code to use 1 button that will toggle automatically.

            //TODO TeleOp Assignment 4: Write code that sets the outtake motor power. (Bonus: use a joystick for it.)



            //end step
            telemetry.update(); //For telemetry to update on the driver station we have to update it in the code every loop.
        }

    }
}


package org.firstinspires.ftc.teamcode;

import static java.lang.Math.PI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="WARHOGTeleOp", group="")
public class WARHOGTeleOp extends LinearOpMode {
    public WARHOGTeleOp() throws InterruptedException {}

    @Override
    public void runOpMode() throws InterruptedException {

        //set up classes
        Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry);
        NewIntakeOuttake newIntakeOuttake = new NewIntakeOuttake(hardwareMap, telemetry);

        //set up variables
        double joyx, joyy, joyz, gas, baseSpeed, offset, modAngle;
        boolean slideMinimumPos, slideLowPos, slideMediumPos, slideHighPos, slideMaxPos,
                centricityToggle, resetDriveAngle, clawToggle, encoderReset, PIDTEST, nolIMIT, armEncoderReset, slideEncoderReset, preciseMOTOR,
                uprightArmPos, sizingArmPos, downArmPos;

        offset = 0;
        Drivetrain.Centricity centricity = Drivetrain.Centricity.FIELD;

        baseSpeed = .4;
        int armPos = newIntakeOuttake.getArmPos();
        double armSpeed = .1;
        double armPosChange;

        int slidePos = newIntakeOuttake.getSlidePos();
        double slideSpeed = .1;
        double slidePosChange;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        while (!isStarted() && !isStopRequested()) {
            //newIntakeOuttake.closeClaw();
            armPos = newIntakeOuttake.getArmPos();
            slidePos = newIntakeOuttake.getSlidePos();
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

            if(currentGamepad1.dpad_left && !previousGamepad1.dpad_left){
                offset-=90;
            }
            if(currentGamepad1.dpad_right && !previousGamepad1.dpad_right){
                offset+=90;
            }
            if (offset==360){offset=0;}
            if (offset==-90){offset=270;}

            telemetry.addData("Angle Offset", offset);
            telemetry.update();
        }

        //drivetrain.setAngleOffset(offset); //We'll see if this works

        while(opModeIsActive()){
            //set up inputs
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


            //set up inputs

            //inputs that toggle the modes
            centricityToggle = currentGamepad1.dpad_down && !previousGamepad1.dpad_down; //change whether the drive is bot or field centric
            resetDriveAngle = currentGamepad1.dpad_up; //use when the robot is facing away from you

            //code to switch between field centric and bot centric drive
            if(centricityToggle){
                if(centricity==Drivetrain.Centricity.BOT){
                    centricity = Drivetrain.Centricity.FIELD;
                }
                else{
                    centricity = Drivetrain.Centricity.BOT;
                }
            }

            armPosChange = -(currentGamepad2.left_stick_y); //Change to neg. to make code cleaner later TODO
            slidePosChange = -(currentGamepad2.right_stick_y); //Change to neg. to make code cleaner later
            clawToggle = currentGamepad2.left_bumper && !previousGamepad2.left_bumper;
            encoderReset = currentGamepad2.right_bumper && !previousGamepad2.right_bumper;
            armEncoderReset = currentGamepad2.left_stick_button;
            slideEncoderReset = currentGamepad2.right_stick_button;

            sizingArmPos = currentGamepad2.b;
            uprightArmPos = currentGamepad2.dpad_left;
            downArmPos = currentGamepad2.dpad_right;

            slideMinimumPos = currentGamepad2.dpad_down;
            slideLowPos = currentGamepad2.x;
            slideMediumPos = currentGamepad2.a;
            slideHighPos = currentGamepad2.dpad_up;
            slideMaxPos = currentGamepad2.y;

            //PIDTEST = currentGamepad2.left_trigger != 0;
            preciseMOTOR = currentGamepad2.left_trigger != 0;
            nolIMIT = currentGamepad2.right_trigger !=0;

            //set up vectors
            joyx = currentGamepad1.left_stick_x;
            joyy = -currentGamepad1.left_stick_y;
            joyz = -currentGamepad1.right_stick_x;
            gas = currentGamepad1.right_trigger*(1-baseSpeed);

            //print vectors
            telemetry.addData("y", joyy);
            telemetry.addData("x", joyx);
            telemetry.addData("z", joyz);


            //set and print motor powers
            double[] motorPowers = drivetrain.driveVectors(centricity, joyx, joyy, joyz, baseSpeed+gas);
            for (double line:motorPowers){
                telemetry.addLine( Double.toString(line) );
            }

            //reset the angle
            if(resetDriveAngle){
                drivetrain.resetAngleData(Drivetrain.AngleType.HEADING);
            }

            modAngle = (drivetrain.getIMUAngleData(Drivetrain.AngleType.HEADING)/PI*180)%360;    //********Reposition or take out these 2 lines if not needed, figure out what nod angle is for*********
            telemetry.addData("mod angle", modAngle);


            //Reset Motor Encoders to Zero
            if(encoderReset){
                newIntakeOuttake.resetEncoders();
            }
            if(armEncoderReset){
                newIntakeOuttake.resetArmMotorEncoder();
            }
            if(slideEncoderReset){
                newIntakeOuttake.resetSlideMotorEncoder();
            }

            //Check if you should use precise motor powers
            //if (preciseMOTOR){
            //TODO if it doesn't work directly in func. call
            //}
            //TODO test arm and slide precise w/ and w/out limits, take precise out and see if doesn't break

            //move arm
            armPos += armPosChange;
            double powArm = 0;
            if (armPosChange < 0){
                //powArm = -1;
                powArm = armPosChange;
            }
            else if (armPosChange > 0){
                //powArm = 1;
                powArm = armPosChange;
            }
            else{
                powArm = 0;
            }

            if (gamepad2.left_stick_y !=0 && !nolIMIT){
                newIntakeOuttake.setArmControllerPower(powArm, preciseMOTOR);
                telemetry.addData("Moving arm with stick with pow:", powArm);
            }
            else if (gamepad2.left_stick_y !=0 && nolIMIT){
                newIntakeOuttake.setArmControllerPowerNoLimit(powArm, preciseMOTOR);
                telemetry.addData("Moving arm with stick WITHOUT LIMIT with pow:", powArm);
            }
            else  if (gamepad2.left_stick_y == 0 && !newIntakeOuttake.isArmGoingToPos()){
                newIntakeOuttake.setArmControllerPower(0);
            }

            telemetry.addData("Arm Position", armPos);
            telemetry.addData("True Arm Position", newIntakeOuttake.getArmPos());

            //defined arm positions
            if(uprightArmPos){
                newIntakeOuttake.setArmByDefaultNoWait(NewIntakeOuttake.armPos.UPRIGHT);
            }
            if(downArmPos){
                newIntakeOuttake.setArmByDefaultNoWait(NewIntakeOuttake.armPos.DOWN);
            }
            if(sizingArmPos){
                newIntakeOuttake.setArmByDefaultNoWait(NewIntakeOuttake.armPos.SUBSIZING);
            }
            armPos = newIntakeOuttake.getArmPos();


            //move slide
            slidePos += slidePosChange;
            double powSlide = 0;
            if (slidePosChange < 0){
                //powSlide = -1;
                powSlide = slidePosChange;
            }
            else if (slidePosChange > 0){
                //powSlide = 1;
                powSlide = slidePosChange;
            }
            else{
                powSlide = 0;
            }

            if (gamepad2.right_stick_y != 0 && !nolIMIT){
                newIntakeOuttake.setSlideControllerPower(powSlide, preciseMOTOR);
                telemetry.addData("Moving slide with stick with pow:", powSlide);
            }
            else if (gamepad2.right_stick_y != 0 && nolIMIT){
                newIntakeOuttake.setSlideControllerPowerNoLimit(powSlide, preciseMOTOR);
                telemetry.addData("Moving slide with stick WITHOUT LIMIT with pow:", powSlide);
            }
            else if (gamepad2.right_stick_y == 0 && !newIntakeOuttake.isSlideGoingToPos()){
                newIntakeOuttake.setSlideControllerPower(0);
            }

            telemetry.addData("Slide Position", slidePos);
            telemetry.addData("True Slide Position", newIntakeOuttake.getSlidePos());

            //TODO FOR TEST
            /*if (PIDTEST){
                newIntakeOuttake.setSlideHeightPID(NewIntakeOuttake.slideHeight.MEDIUM);
            }*/

            //defined slide positions
            if(slideMinimumPos){
                newIntakeOuttake.setSlideHeightNoWait(NewIntakeOuttake.slideHeight.MINIMUM);
            }
            if(slideLowPos){
                newIntakeOuttake.setSlideHeightNoWait(NewIntakeOuttake.slideHeight.LOW);
            }
            if(slideMediumPos){
                newIntakeOuttake.setSlideHeightNoWait(NewIntakeOuttake.slideHeight.MEDIUM);
            }
            if(slideHighPos){
                newIntakeOuttake.setSlideHeightNoWait(NewIntakeOuttake.slideHeight.HIGH);
            }
            if(slideMaxPos){
                newIntakeOuttake.setSlideHeightNoWait(NewIntakeOuttake.slideHeight.MAX);
            }
            slidePos = newIntakeOuttake.getSlidePos(); //Update other counter

            //open/close the claw
            if(clawToggle) {newIntakeOuttake.toggleClaw();}
            telemetry.addData("Claw Open?: ", newIntakeOuttake.isClawOpen());

            telemetry.addData("Left y joy: ", currentGamepad2.left_stick_y);
            telemetry.addData("right y joy: ", currentGamepad2.right_stick_y);
            telemetry.addData("powSlide: ", powSlide);
            telemetry.addData("powArm: ", powArm);

            //end step
            telemetry.update();
        }

    }

}

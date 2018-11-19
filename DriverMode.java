/*
Copyright 2018 FIRST Tech Challenge Team 12178

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR O`THER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp(name="KimberlySucks", group="TeleOp")

public class DriverMode extends OpMode {

    /* Declare OpMode members. */
    private DcMotor dc1;
    private DcMotor dc2;
    private DcMotor dc3;
    private DcMotor dc4;
    private DcMotor armMotor1;
    private DcMotor armMotor2;
    private DcMotor latchMotor;
    private Servo s1;
    private String status;

    @Override
    public void init()
    {
        status = "";
        /* */
        telemetry.addData("Status", "Initialized");
        try
        {
            dc1 = hardwareMap.get(DcMotor.class, "DC1");
            //dc1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        catch(Exception e)
        {
            System.out.println(e + "");
        }
        try
        {
            dc2 = hardwareMap.get(DcMotor.class, "DC2");
        }
        catch(Exception e)
        {
            System.out.println(e + "");
        }
        try
        {
            dc3 = hardwareMap.get(DcMotor.class, "DC3");
        }
        catch(Exception e)
        {
            telemetry.addData("Mistake: " + e, "");
        }
        try
        {

            dc4 = hardwareMap.get(DcMotor.class, "DC4");
        }
        catch(Exception e)
        {
            telemetry.addData("Mistake: " + e, "");
        }
        try
        {
            armMotor1 = hardwareMap.get(DcMotor.class, "DC5");
            armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        }
        catch(Exception e)
        {
            telemetry.addLine("Mistake: " + e);
        }
        try
        {
            armMotor2 = hardwareMap.get(DcMotor.class, "DC6");
            armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        catch(Exception e)
        {
            telemetry.addLine("Mistake: " + e);
        }
        try
        {
            s1 = hardwareMap.get(Servo.class, "S1");
            status += "S1 connected";
        }
        catch(Exception e)
        {
            telemetry.addData("Mistake: " + e, "");
            status += "S1 not found";
        }

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        telemetry.addData("status ",status );
        telemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop()
    {
        double bufferA = 5;
        double rightPress = gamepad1.right_trigger;
        double leftPress = gamepad1.left_trigger * -1;
        double x2 = gamepad2.left_stick_x;
        double y2 = gamepad2.left_stick_y;

        double y = gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double yRight = gamepad1.right_stick_y;
        double xLeft = gamepad1.right_stick_x;
        telemetry.addData("Status:","x = " + x + " ,y =  " +y  );
        double theta = Math.atan(y/x);
        String compare = "-0.0";
        String sTheta = "" + theta;
        boolean isnegZ = true;
        for(int i = 0; i<4; i++)
        {
            if((compare.charAt(i) + "").equals(sTheta.charAt(i) + "") == false)
            {
                isnegZ = false;
                i = 10;
            }
        }
        if(isnegZ == true && x<0)
        {
            theta = Math.PI;
        }
        //Q2
        else if(y>0 && x<0)
        {
            theta = Math.PI + theta;
            telemetry.addData("q2", "");

        }
        //Q3
        else if((x>=-1 && x<0) && (y<0 && y>=-1))
        {
            theta += Math.PI;
            telemetry.addData("q3","");
        }
        //Q4
        else if((x>0 && x<=1) && (y<0 && y>=-1))
        {
            theta = 2*Math.PI + theta;
            telemetry.addData("q4", "");
        }
        //Q1
        else
        {
            theta = theta;
            telemetry.addData("q1", "");
        }
        double degree = 0;
        degree = (theta/Math.PI) * 180;
        telemetry.addData("Status:","degree = " + degree);

        double rad = Math.sqrt(x*x + y*y);
        if(rad > 1)
        {
            rad = 1;
        }
        double v1 = rad*(-1*Math.sin(theta + Math.PI/4));
        double v2 = rad*Math.cos(theta + Math.PI/4);
        telemetry.addData("v1 = " + v1, " v2 = " + v2);

        //Rotate right
        if(rightPress != 0)
        {
            dc1.setPower(-1*rightPress);
            dc2.setPower(-1*rightPress);
            dc3.setPower(-1*rightPress);
            dc4.setPower(-1*rightPress);
        }
        //Rotate left
        else if(leftPress != 0)
        {
            dc1.setPower(-1*leftPress);
            dc2.setPower(-1*leftPress);
            dc3.setPower(-1*leftPress);
            dc4.setPower(-1*leftPress);
        }
        else if(x==0 && y==0)
        {
            dc1.setPower(0);
            dc2.setPower(0);
            dc3.setPower(0);
            dc4.setPower(0);
        }
        else
        {
            dc1.setPower(-v2);
            dc2.setPower(v1);
            dc3.setPower(v2);
            dc4.setPower(-v1);
        }
        //Moving the arm up
        if(yRight < 0)
        {
            armMotor1.setPower(y);
            armMotor2.setPower(-y);
            telemetry.addLine("Arm Up");
        }
        //Moving the arm down
        else if(yRight > 0)
        {
            armMotor1.setPower(-y);
            armMotor2.setPower(y);
            telemetry.addLine("Arm Down");
        }
        //No vertical arm movement
        else
        {
            armMotor1.setPower(0);
            armMotor2.setPower(0);
        }
        telemetry.update();

        //SERVOS
        boolean s1up = gamepad1.y;
        boolean s1down = gamepad1.a;

        if(s1up)
        {
            s1.setPosition(1.0);

        }
        else if(s1down)
        {
            s1.setPosition(0.0);
        }
        else
        {
            s1.setPosition(0.5);
        }
        //upDownMotor = name of the motor that controls vertical arm movement
        //up = the y2 might have to be changed
        /**
        if(y2<0)
        {
            upDownMotor.setPower(y2);
        }
        else
        {
            upDownMotor.setPower(0);
        }
        if(y2>0)
        {
            upDownMotor.setPower(-y2);
        }
        else
        {
            upDownMotor.setPower(0);
        }
         **/
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}

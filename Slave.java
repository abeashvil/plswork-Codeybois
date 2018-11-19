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

@TeleOp

public class Slave extends OpMode {

    /* Declare OpMode members. */
    public DcMotor dc1;
    public DcMotor dc2;
    public DcMotor dc3;
    public DcMotor dc4;
    public Servo s1;
    public String status;



    @Override
    public void init()
    {
        status = "";
        /* */
        telemetry.addData("Status", "Initialized");
        try
        {
            dc1 = hardwareMap.get(DcMotor.class, "DC1");
            dc1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
            //System.out.println(e + "");
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
        //double y2 = gamepad1.right_stick_y;
        //double x2 = gamepad1.right_stick_x;
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
        else{
            dc1.setPower(-v2);
            dc2.setPower(v1);
            dc3.setPower(v2);
            dc4.setPower(-v1);
        }
        telemetry.update();

        //SERVOS
        boolean s1up = gamepad1.dpad_up;
        boolean s1down = gamepad1.dpad_down;

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
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
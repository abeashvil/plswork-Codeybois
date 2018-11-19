package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@Autonomous(name="AutoPath1", group="Master")

public class AutoPath1 extends OpMode
{
    Slave slave = new Slave();
    public void stop(long ms){
        slave.dc1.setPower(0);
        slave.dc2.setPower(0);
        slave.dc3.setPower(0);
        slave.dc4.setPower(0);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void moveUp(long ms){
        slave.dc1.setPower(1);
        slave.dc2.setPower(-1);
        slave.dc3.setPower(-1);
        slave.dc4.setPower(1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void moveLeft(long ms){
        slave.dc1.setPower(-1);
        slave.dc2.setPower(-1);
        slave.dc3.setPower(1);
        slave.dc4.setPower(1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void moveRight(long ms){
        slave.dc1.setPower(1);
        slave.dc2.setPower(1);
        slave.dc3.setPower(-1);
        slave.dc4.setPower(-1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void moveDown(long ms){
        slave.dc1.setPower(-1);
        slave.dc2.setPower(1);
        slave.dc3.setPower(1);
        slave.dc4.setPower(-1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void rotCWise(long ms){
        slave.dc1.setPower(1);
        slave.dc2.setPower(1);
        slave.dc3.setPower(1);
        slave.dc4.setPower(1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    public void rotCCWise(long ms){
        slave.dc1.setPower(-1);
        slave.dc2.setPower(-1);
        slave.dc3.setPower(-1);
        slave.dc4.setPower(-1);
        try{
            Thread.sleep(ms);
        }catch(Exception e){
        }
    }
    @Override
    public void init() {
        slave.status = "";
        /* */
        telemetry.addData("Status", "Initialized");
        try
        {
            slave.dc1 = hardwareMap.get(DcMotor.class, "DC1");
            slave.dc1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        catch(Exception e)
        {
            System.out.println(e + "");
        }
        try
        {
            slave.dc2 = hardwareMap.get(DcMotor.class, "DC2");
        }
        catch(Exception e)
        {
            System.out.println(e + "");
        }
        try
        {
            slave.dc3 = hardwareMap.get(DcMotor.class, "DC3");
        }
        catch(Exception e)
        {
            telemetry.addData("Mistake: " + e, "");
        }
        try
        {

            slave.dc4 = hardwareMap.get(DcMotor.class, "DC4");
        }
        catch(Exception e)
        {
            //System.out.println(e + "");
        }

        try
        {
            slave.s1 = hardwareMap.get(Servo.class, "S1");
            slave.status += "S1 connected";
        }
        catch(Exception e)
        {
            telemetry.addData("Mistake: " + e, "");
            slave.status += "S1 not found";
        }
    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start(){

    }

    /*
     * Code to run REPEATEDLY when the driver hits PLAY
     */
    @Override
    public void loop() {
        //Constants [CURRENTLY PLACEHOLDER NUMBERS] [NEEDS ADJUSTING]
        long blocc = 1000;//Will be amount of ms to travel a tile horiz || vertically
        long rot = 1000;//Will be amount of ms to rotate 90 degrees
        //UNLATCHING CODE

        //MOVEMENT CODE
        moveUp((long)Math.sqrt(2*(blocc*blocc)));
        stop(500);
        rotCCWise(rot/2);
        stop(500);
        moveUp(blocc/2);
        stop(500);
        moveLeft(3*blocc);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

}

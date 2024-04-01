package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OrbitUtils.Vector;
import org.firstinspires.ftc.teamcode.Sensors.OrbitColorSensor;
import org.firstinspires.ftc.teamcode.Sensors.OrbitGyro;
import org.firstinspires.ftc.teamcode.positionTracker.PoseStorage;
import org.firstinspires.ftc.teamcode.robotData.GlobalData;
import org.firstinspires.ftc.teamcode.robotSubSystems.OrbitLED;
import org.firstinspires.ftc.teamcode.robotSubSystems.RobotState;
import org.firstinspires.ftc.teamcode.robotSubSystems.SubSystemManager;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainOmni.DrivetrainOmni;
import org.firstinspires.ftc.teamcode.robotSubSystems.drivetrain.DriveTrainTank.DriveTrainTank;


@Config
@TeleOp(name = "main")
public class Robot extends LinearOpMode {

    public static DigitalChannel coneDistanceSensor;
    public static TelemetryPacket packet;


    @Override
    public void runOpMode() throws InterruptedException {


        FtcDashboard dashboard = FtcDashboard.getInstance();
        packet = new TelemetryPacket();
//        coneDistanceSensor = hardwareMap.get(DigitalChannel.class, "clawDistanceSensor");
//        coneDistanceSensor.setMode(DigitalChannel.Mode.INPUT);

        ElapsedTime robotTime = new ElapsedTime();
        robotTime.reset();
        DrivetrainOmni.init(hardwareMap);
//        DriveTrainTank.init(hardwareMap);
        OrbitGyro.init(hardwareMap);
//         OrbitLED.init(hardwareMap);
//        OrbitColorSensor.init(hardwareMap);
        OrbitGyro.resetGyroStartTeleop((float) Math.toDegrees(PoseStorage.currentPose.getHeading()));
        telemetry.update();
        telemetry.addData("gyro", Math.toDegrees(PoseStorage.currentPose.getHeading()));
        telemetry.addData("lastAngle", OrbitGyro.lastAngle);
        telemetry.update();

        GlobalData.inAutonomous = false;
        GlobalData.currentTime = 0;
        GlobalData.lastTime = 0;
        GlobalData.deltaTime = 0;
        GlobalData.robotState = RobotState.TRAVEL;


        waitForStart();

        GlobalData.robotState = RobotState.TRAVEL;

        while (!isStopRequested()) {
          GlobalData.currentTime = (float) robotTime.seconds();
//          final boolean placing = SubSystemManager.wanted.equals(RobotState.MIN) || SubSystemManager.wanted.equals(RobotState.LOW) || SubSystemManager.wanted.equals(RobotState.MID);
          Vector leftStick = new Vector(gamepad1.left_stick_x, -gamepad1.left_stick_y);
          float omega = gamepad1.right_trigger - gamepad1.left_trigger;
          DrivetrainOmni.operate(leftStick,  omega , telemetry , gamepad1);
//          DriveTrainTank.operate(-gamepad1.left_stick_y, gamepad1.right_trigger, gamepad1.left_trigger, telemetry, gamepad1);
          SubSystemManager.setSubsystemToState(gamepad1 , gamepad2 , telemetry);
//          OrbitLED.operate(OrbitColorSensor.color);
           GlobalData.deltaTime = GlobalData.currentTime - GlobalData.lastTime;


            GlobalData.lastTime = GlobalData.currentTime;
//            Drivetrain.testMotors(gamepad1, telemetry);
            telemetry.update();
//            OrbitColorSensor.hasGamePiece();
            SubSystemManager.printStates(telemetry);
        }
    }



}
//dani yalechan!
// yoel yalechan!
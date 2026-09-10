package frc.robot.examples

import com.revrobotics.PersistMode
import com.revrobotics.ResetMode
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj2.command.SubsystemBase

/**
 * This is a constants class where we will store constants for this file. Each subsystem has its own constants object.
 */
object ExampleMotorConstants {
    // this is the ID of the motor on the CAN bus that we want to drive.
    const val motorID = 0
}

/**
 * This is an example motor object, for a subsystem requiring a single motor.
 */
object ExampleMotor : SubsystemBase() {

    /**
     * This is an init, which runs ONCE when the class is instantiated. Do not store things that will EVER change here!
     */
    init {
        // here we will create an example motor using a Spark MAX
        // we will assume the motor is a brushless motor
        val motor: SparkMax = SparkMax(ExampleMotorConstants.motorID, SparkLowLevel.MotorType.kBrushless)

        // you can configure the motor to set custom current limits or other things by doing this
        val config = SparkMaxConfig()
            .smartCurrentLimit(40) // limit of 40 amps
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters)
    }
}
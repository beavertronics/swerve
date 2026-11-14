package frc.robot

import Engine.utils.Sugar.within
import kotlin.math.*
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.button.CommandJoystick
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.commands.drive.ChildModeDriveCommand
import frc.robot.commands.drive.TeleopDriveCommand
import frc.robot.subsystems.Drivetrain

/*
Sets up the operator interface (controller inputs), as well as
setting up the commands for running the drivetrain and the subsystems
 */

/**
 * class for managing systems and inputs
 */
object TeleOp {
    // basic command for teleop drive
    val teleOpDrive: TeleopDriveCommand =
        TeleopDriveCommand(
            { OI.C1_LY },
            { OI.C1_LX },
            { OI.C1_RX },
            { OI.C1_RT.asBoolean },
            { OI.C1_LT.asBoolean }
        )
    val childDrive: ChildModeDriveCommand =
        ChildModeDriveCommand(
            { OI.C1_LY }, // parent forward
            { OI.C1_LX }, // parent strafe
            { OI.C1_RX }, // parent rotate
            { OI.C1_RT.asBoolean }, // toggle child mode
            { OI.LJS_Y }, // child forwards
            { OI.LJS_X }, // child strafe
            { OI.RJS_X }, // child rotate
            { OI.C1_LB.asBoolean }, // drive mode
            { OI.C1_LT.asBoolean } // slow mode

        )

    init {
        // SWAP THIS WITH WHATEVER COMMAND YOU WANT TO BE DRIVING THE ROBOT!
        Drivetrain.defaultCommand = teleOpDrive
    }

    /**
     * configures things to run on specific inputs. You can put code that runs subsystems here.
     */
    fun configureBindings() {}

    /**
     * Class for the operator interface
     * getting inputs from controllers and whatnot.
     */
    object OI : SubsystemBase() {
        val xboxController1 = CommandXboxController(0)
        val leftJoystick = CommandJoystick(1)
        val rightJoystick = CommandJoystick(2)

        /**
         * Allows you to tweak controller inputs (ie get rid of deadzone, make input more sensitive by squaring or cubing it, etc).
         */
        private fun Double.processInput(
            deadzone: Double = 0.1,
            squared: Boolean = false,
            cubed: Boolean = false,
            readjust: Boolean = true
        ): Double {
            var processed = this
            if (readjust) processed = ((this.absoluteValue - deadzone) / (1 - deadzone)) * this.sign
            return when {
                this.within(deadzone) -> 0.0
                squared -> processed.pow(2) * this.sign
                cubed -> processed.pow(3)
                else -> processed
            }
        }

        private fun Double.abs_GreaterThan(target: Double): Boolean {
            return this.absoluteValue > target
        }

        /**
         * Allows the inputted controller to rumble
         */
        class Rumble(
            val controller: CommandXboxController,
            val time: Double = 1.0,
            val rumblePower: Double = 1.0,
            val rumbleSide: GenericHID.RumbleType = GenericHID.RumbleType.kRightRumble
        ) : Command() {
            val timer = Timer()

            init {
                addRequirements(OI)
            }

            override fun initialize() {
                timer.restart(); controller.setRumble(rumbleSide, rumblePower)
            }

            override fun execute() {
                controller.setRumble(rumbleSide, rumblePower)
                // update the pose

            }

            override fun end(interrupted: Boolean) {
                controller.setRumble(rumbleSide, 0.0)
            }

            override fun isFinished(): Boolean {
                return timer.hasElapsed(time)
            }
        }

        /**
         * Values for inputs go here
         */
        //===== DRIVETRAIN =====//
        val C1_LX get() = xboxController1.leftX.processInput()
        val C1_LY get() = xboxController1.leftY.processInput()
        val C1_RX get() = xboxController1.rightX.processInput()
        val C1_LB get() = xboxController1.leftBumper()
        val C1_RB get() = xboxController1.rightBumper()
        val C1_LT get() = xboxController1.leftTrigger()
        val C1_RT get() = xboxController1.rightTrigger()
        val C1_A get() = xboxController1.a()
        val C1_X get() = xboxController1.x()
        val LJS_X get() = leftJoystick.x.processInput()
        val LJS_Y get() = leftJoystick.y.processInput()
        val RJS_X get() = rightJoystick.x.processInput()
        val RJS_Y get() = rightJoystick.y.processInput()
        //===== SUBSYSTEMS =====//
    }
}
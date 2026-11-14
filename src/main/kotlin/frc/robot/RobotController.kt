package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.Commands
import frc.robot.subsystems.Autos
import frc.robot.subsystems.Orchestrator
import frc.robot.subsystems.Pathplanner
import frc.robot.subsystems.`according to all known laws of aviation, our robot should not be able to fly`

/*
 Main code for controlling the robot. Mainly just links everything together.
 Driver control is defined in TeleOp.kt.
*/

/**
 * main object for controlling robot, based off
 * of the timed robot class
 */
object RobotController : TimedRobot() {
    // general command scheduler for scheduling events
    val commandScheduler = CommandScheduler.getInstance()
    // manual auto chooser (the one for pathplanner is in the pathplanner file)
    val ManualAutoChooser = SendableChooser<Command>()
    // whether to choose a manual auto or a pathplanner auto
    val AutoTypeChooser = SendableChooser<Boolean>()
    // the selected manual or pathplanner auto
    var selectedManualAuto: Command? = null
    var selectedPathAuto: Command? = null

    /**
     * runs when robot turns on, should be used for any initialization of robot or subsystems
     */
    override fun robotInit() {
        // initialize all subsystems
        TeleOp
        `according to all known laws of aviation, our robot should not be able to fly`
        Pathplanner
        Orchestrator

        // load manual autos
        ManualAutoChooser.setDefaultOption("no auto", Commands.none())
        ManualAutoChooser.addOption("Orchestra - Never Gonna Give You Up by Rick Astley", Autos.orchestrator_NeverGonnaGiveYouUp)

        // load pathplanner autos
        Pathplanner.autoChooser.setDefaultOption("no auto", Commands.none())

        // configure the thing to choose between manual or pathplanner autos
        AutoTypeChooser.setDefaultOption("Default - Manual", true)
        AutoTypeChooser.addOption("Pathplanner", true)
        AutoTypeChooser.addOption("Manual", false)

        // put everything on the dashboard
        SmartDashboard.putData("Autos/Manual auto choices", ManualAutoChooser)
        SmartDashboard.putData("Autos/Pathplanner auto choices", Pathplanner.autoChooser)
        SmartDashboard.putData("Autos/Auto chooser", AutoTypeChooser)
    }

    /**
     * runs when the robot is on, regardless of enabled or not
     * used for telemetry, command scheduler, etc
     */
    override fun robotPeriodic() { commandScheduler.run() }

    override fun autonomousInit() {
        // select either the pathplanner auto or the manual auto (list of commands)
        if (!AutoTypeChooser.selected) {
            println("Using manual auto")
            selectedManualAuto = ManualAutoChooser.selected
            commandScheduler.schedule(selectedManualAuto)
            println("Auto selected: " + selectedManualAuto)
        }
        else {
            println("using pathplanner auto")
            selectedPathAuto = Pathplanner.getAutonomousCommand()
            commandScheduler.schedule(selectedPathAuto)
            println("Auto selected: " + selectedPathAuto)
        }
    }

    /**
     * runs when teleop is ready
     */
    override fun teleopInit() {
        // configure all of our inputs
        TeleOp.configureBindings()
        // stop whatever autos were running
        if (!AutoTypeChooser.selected && selectedManualAuto != null) { selectedManualAuto?.cancel() }
        else if (AutoTypeChooser.selected && selectedPathAuto != null) { selectedPathAuto?.cancel() }
        // if we were playing musics, stop
        // this will free the motors: otherwise, we can't use the motors until they are done
        Orchestrator.stop()
    }

    /**
     * runs on every frame of teleop
     */
//    override fun teleopPeriodic() {}

    /**
     * runs only in simulation mode,
     * other functions will run regardless of whether the robot is
     * simulated or not
     */
//    override fun simulationInit() {}

    /**
     * runs immediately when the robot is disabled, helpful for safe
     * deactivation of robot and whatnot
     */
//    override fun disabledInit() {}

    /**
     * runs while robot is disabled, used to hold motors
     * in place.
     * try not to put code here, is often unsafe
     */
//    override fun disabledPeriodic() {}

    override fun testInit() { commandScheduler.cancelAll() }

//    override fun testPeriodic() {}
}
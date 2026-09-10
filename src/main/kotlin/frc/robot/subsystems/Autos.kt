package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.vision.AlignToTag

object Autos {
    // play music using orchestrator
    val orchestrator_NeverGonnaGiveYouUp = InstantCommand(
        {
            Orchestrator.loadnplay("orchestra/never_gonna_give_you_up.chrp")
        },
        Orchestrator
    )
}
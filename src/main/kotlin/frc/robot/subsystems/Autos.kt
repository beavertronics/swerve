package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.InstantCommand
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import frc.robot.commands.vision.AlignToTag

object Autos {
    // align to multiple tags, one after another
    val multiTagAlign = SequentialCommandGroup(
        AlignToTag(2),
        Commands.waitSeconds(2.0),
        AlignToTag(3),
        Commands.waitSeconds(2.0),
        AlignToTag(4)
    )

    // play music using orchestrator
    val orchestrator_NeverGonnaGiveYouUp = InstantCommand(
        {
            Orchestrator.loadnplay("orchestra/never_gonna_give_you_up.chrp")
        },
        Orchestrator
    )
}
package beaverlib.utils.geometry
// File adapted from 2898 2023 bpsrobotics engine
import Engine.utils.Units.Angular.AngleUnit
import Engine.utils.Units.Angular.beaverRadians
import edu.wpi.first.math.geometry.Pose2d
import kotlin.math.*

class Raycast2D(val origin : Vector2, val angle : AngleUnit){
    constructor(pose: Pose2d) : this(Vector2(pose),pose.rotation.beaverRadians)

}
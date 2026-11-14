package frc.robot.subsystems

import Engine.odometry.BeaverPhotonVision
import Engine.utils.Units.Angular.degrees
import Engine.utils.Units.Linear.inches
import edu.wpi.first.apriltag.AprilTagFieldLayout
import edu.wpi.first.apriltag.AprilTagFields
import edu.wpi.first.math.geometry.Rotation3d
import edu.wpi.first.math.geometry.Transform3d
import frc.robot.beaverlib.odometry.BeaverVisionCamera
import org.photonvision.PhotonPoseEstimator

val Vision = BeaverPhotonVision(
    BeaverVisionCamera(
        name = "Arducam_OV9281_USB_Camera - Camronny",
        robotToCamera = Transform3d(
            -16.65.inches.asMeters, -9.5.inches.asMeters, 17.0.inches.asMeters, // -13.15, -9.5
            Rotation3d(
                0.0.degrees.asRadians, 0.0.degrees.asRadians, 0.0.degrees.asRadians
            )
        ),
        layout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded),
        strategy = PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
        fallbackStrategy = PhotonPoseEstimator.PoseStrategy.CLOSEST_TO_REFERENCE_POSE

    )
)
package Engine.utils.Units

import Engine.utils.Units.Linear.Acceleration
import Engine.utils.Units.Linear.earthGravity
import edu.wpi.first.units.DistanceUnit
import frc.robot.beaverlib.utils.Units.Electrical.Energy

@JvmInline
value class Force (val asNewtons: Double) {
    operator fun plus(other : Force) = Force(asNewtons + other.asNewtons)
    operator fun minus(other : Force) = Force(asNewtons - other.asNewtons)
    operator fun times(factor : Double) = Force(asNewtons * factor.toDouble())
    operator fun div(factor: Double) = Force(asNewtons / factor.toDouble())
    operator fun rem(other: Weight) = Force(asNewtons % other.asKilograms)
    operator fun unaryPlus() = this
    operator fun unaryMinus() = Force(-asNewtons)


    // Unit Math todo why was it meters?
//    operator fun times(other: DistanceUnit) = Energy(asNewtons * other)
    operator fun DistanceUnit.times(other: Force) = Energy(asNewtons * other.asNewtons)

    operator fun div(other: Weight) = Acceleration(asNewtons / other.asKilograms)
    operator fun div(other: Acceleration) = Weight(asNewtons / other.asMetersPerSecondSquared)
    operator fun div(other: Force) = asNewtons / other.asNewtons


}
// Constructors
inline val Number.newtons get() = Force(toDouble())
inline val Number.lbF     get() = Force((toDouble()* 0.453592) * earthGravity.asMetersPerSecondSquared)

// Destructors

inline val Force.asLbF     get() = Force((asNewtons / 0.453592) / earthGravity.asMetersPerSecondSquared)

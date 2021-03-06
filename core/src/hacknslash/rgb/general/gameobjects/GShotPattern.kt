package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GClock
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.actors.Bullet
import kotlin.reflect.KClass

class GShotPattern(val cd: Float, val offsetAngle: Float, val weapon: KClass<Bullet>) {

    var nextShot = shotTime()

    private fun shotTime() = GClock.time + cd

    var shouldShoot: Boolean = false
        get() = (nextShot < GClock.time)


    fun shoot(shooter: GShooter): Bullet {
        (shooter as GActor)
        nextShot = shotTime()

        val dir = GVec2.get(shooter.shotDir).rotate(offsetAngle)

        dir.nor().scl(shooter.hh, shooter.hw)
        val pos = GVec2.get(shooter.cx, shooter.cy).add(dir)

        return Bullet.get(
                pos,
                dir
        )
    }
}

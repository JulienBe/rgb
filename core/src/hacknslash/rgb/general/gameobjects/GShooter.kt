package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.containers.GActorsContainer
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

interface GShooter {

    val shotPatterns: Array<GShotPattern>
    val shotDir: GVec2
    val shouldShoot: Boolean get() = true

    fun shoot(assMan: GAssMan, physic: GPhysic, actors: GActorsContainer) {
        (this as GActor)
        shotPatterns
                .filter { it.shouldShoot && shouldShoot}
                .forEach { actors.add(it.shoot(this, assMan, physic)) }
    }

}
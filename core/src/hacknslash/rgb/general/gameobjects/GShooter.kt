package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.Bullet

interface GShooter {

    val shotPatterns: Array<GShotPattern>
    val shotDir: GVec2

    fun act(assMan: GAssMan, physic: GPhysic): List<Bullet> {
        return shotPatterns.filter { it.shouldShoot }.map { it.shoot(this, assMan, physic) }
    }

}
package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.physics.GVec2
import ktx.collections.GdxArray

interface GShooter {

    val shotPatterns: GdxArray<GShotPattern>
    val shotDir: GVec2
    val shouldShoot: Boolean get() = true

    fun shoot() {
        (this as GActor)
        shotPatterns
                .filter { it.shouldShoot && shouldShoot}
                .forEach { GActBundle.bundle.actors.add(it.shoot(this)) }
    }

}
package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

open class GActor(val dim: GDim, physic: GPhysic) {
    val pos = GVec2()
    val body = physic.createBody(pos, dim)
}
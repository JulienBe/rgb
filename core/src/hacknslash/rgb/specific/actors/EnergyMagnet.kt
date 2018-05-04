package hacknslash.rgb.specific.actors

import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GKinematic
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class EnergyMagnet(pos: GVec2): GActor(dim, pos, CollisionBits.magnet, CollisionBits.magnetCollision), GKinematic {
    companion object {
        val dim = GDim(5f, 5f)
    }
}
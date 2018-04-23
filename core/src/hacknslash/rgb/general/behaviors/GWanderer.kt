package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.math.Vector2
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.physics.GSide

interface GWanderer {
    var prevRotation: GSide
    val wanderPush: Float
    val wanderPushDelay: Float

    fun dir(): Vector2? {
        this as GActor
        return body.linearVelocity
    }

    fun impulse(i: Vector2) {
        this as GActor
        body.applyLinearImpulse(i, center, true)
    }
}
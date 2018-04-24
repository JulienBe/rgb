package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.physics.box2d.BodyDef
import hacknslash.rgb.general.physics.GVec2

interface GMover {
    val pPos : GVec2
    val maxSpeed : Float

    //TODO remove this as
    fun move(delta: Float) {
        this as GActor
        pPos.set(center)

        if (bodyType == BodyDef.BodyType.KinematicBody)
            setSpeed2(speed2 * 0.8f)
    }

    fun addDir(x: Float, y: Float) {
        this as GActor
        impulse(x, y)
        clampSpeed()
    }

    fun GActor.clampSpeed() {
        if (speed2 > maxSpeed)
            setSpeed2(maxSpeed)
    }
}
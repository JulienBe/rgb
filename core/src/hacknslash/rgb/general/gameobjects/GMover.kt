package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.physics.box2d.BodyDef
import hacknslash.rgb.general.physics.GVec2

interface GMover {
    val pPos : GVec2
    val maxSpeed : Float

    //TODO remove this as
    fun move(delta: Float) {
        (this as GActor)
        pPos.set(center)
        if (body.type == BodyDef.BodyType.KinematicBody)
            body.linearVelocity = body.linearVelocity.setLength2(body.linearVelocity.len2() * 0.8f)
        if (body.linearVelocity.len2() > maxSpeed)
            body.linearVelocity = body.linearVelocity.setLength2(maxSpeed)
    }

    fun addDir(x: Float, y: Float) {
        (this as GActor)
        body.linearVelocity = body.linearVelocity.add(x, y)
    }
}
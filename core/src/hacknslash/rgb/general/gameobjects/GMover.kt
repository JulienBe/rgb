package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import hacknslash.rgb.general.datas.GDataMover
import hacknslash.rgb.general.physics.GVec2

interface GMover {

    val dataMover: GDataMover
    var maxSpeed: Float
        get() {
            return dataMover.maxSpeed
        }
        set(maxSpeed) {
            dataMover.maxSpeed = maxSpeed
        }
    val previousPos: GVec2
        get() {
            return dataMover.pPos
        }

    //TODO remove this as
    fun move() {
        this as GActor
        previousPos.set(center)
    }

    fun addDir(x: Float, y: Float) {
        this as GActor
        impulse(x, y)
        clampSpeed()
    }

    fun addDir(v: Vector2) {
        this as GActor
        impulse(v.x, v.y)
        clampSpeed()
    }

    fun GActor.clampSpeed() {
        if (speed2 > maxSpeed)
            setSpeed2(maxSpeed)
    }
}
package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.math.Vector2
import hacknslash.rgb.general.datas.GDataWanderer
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GSide

interface GWanderer: GAiBTree {

    val wandererData: GDataWanderer
    var prevRotation: GSide
        get() =     wandererData.prevRotation
        set(side) { wandererData.prevRotation = side }
    var push: Float
        get() =     wandererData.wanderPush
        set(push) { wandererData.wanderPush = push }
    var delay: Float
        get() =     wandererData.wanderPushDelay
        set(push) { wandererData.wanderPush = push }

    fun dir(): Vector2? {
        this as GActor
        return dir
    }

    fun impulse(i: Vector2) {
        this as GMover
        addDir(i.x, i.y)
    }
}
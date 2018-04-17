package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GVec2

interface GMover {
    val pPos : GVec2
    val dir : GVec2

    //TODO remove this as
    fun act(delta: Float) {
        pPos.set((this as GActor).pos)
        (this as GActor).pos.add(dir.x * delta, dir.y * delta)
    }
}
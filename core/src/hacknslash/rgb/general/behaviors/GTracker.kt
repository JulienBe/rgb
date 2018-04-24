package hacknslash.rgb.general.behaviors

import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GVec2

interface GTracker {

    var target: GActor?
    var trackImpulseStrength: Float

    fun track() {
        this as GActor
        this as GMover
        val diff = GVec2.get(target!!.cx - cx, target!!.cy - cy)
        diff.nor().scl(trackImpulseStrength)
        addDir(diff.x, diff.y)
    }
}

package hacknslash.rgb.general.behaviors

import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GVec2
import ktx.collections.GdxArray
import ktx.collections.isNotEmpty

interface GTracker {

    var targets: GdxArray<GActor>
    var trackImpulseStrength: Float

    fun track() {
        this as GActor
        this as GMover
        val target = getTarget()
        if (target != null) {
            val diff = GVec2.get(target.cx - cx, target.cy - cy)
            diff.nor().scl(trackImpulseStrength)
            addDir(diff.x, diff.y)
        }
    }

    fun getTarget(): GActor? {
        if (targets.isNotEmpty())
            return targets[0]
        return null
    }
}

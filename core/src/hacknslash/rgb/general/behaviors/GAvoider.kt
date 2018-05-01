package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.math.Intersector
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GVec2
import ktx.collections.GdxArray

interface GAvoider {

    var stuffToAvoid: GdxArray<GActor>
    var avoidImpulseStrenght: Float

    fun avoid(it: GActor) {
        this as GActor
        this as GMover
        if (willCollide(this, it)) {
            val avoid = GVec2.get(speedX, speedY).nor().add((GVec2.get(it.speedX, it.speedY).nor()))
            addDir(avoid.scl(-avoidImpulseStrenght))
        }
    }

    fun willCollide(me: GActor, b: GActor): Boolean {
        return (Intersector.intersectSegments(
                    me.cx, me.cy, me.cx + me.speedX, me.cy + me.speedY,
                    b.cx, b.cy, b.cx + b.speedX, b.cy + b.speedY,
                    null) ||
                Intersector.intersectSegmentCircle(
                    GVec2.get(b.cx, b.cy), GVec2.get(b.cx + b.speedX, b.cy + b.speedY),
                    GVec2.get(me.cx, me.cy), me.dim.width * me.dim.width * 5f)
        )
    }
}
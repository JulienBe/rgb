package hacknslash.rgb.specific

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawable
import hacknslash.rgb.general.gameobjects.GStatic
import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

class Wall(dim: GDim, initPos: GVec2, physic: GPhysic, assMan: GAssMan) : GActor(dim, initPos, physic),
        GDrawable,
        GStatic {

    override val img: TextureRegion = assMan.square()

    companion object {
        const val ratio = 1f / 16f
        fun get(r: Rectangle, physics: GPhysic, assMan: GAssMan): Wall {
            r.scale(ratio)
            return Wall(
                    GDim(r.width, r.height),
                    GVec2.get(r.x, r.y),
                    physics,
                    assMan)
        }
    }
}

private fun Rectangle.scale(ratio: Float) {
    x *= ratio
    y *= ratio
    width *= ratio
    height *= ratio
}
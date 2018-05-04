package hacknslash.rgb.specific

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawable
import hacknslash.rgb.general.gameobjects.GStatic
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2

class Wall(dim: GDim, initPos: GVec2) :
        GActor(dim, initPos, CollisionBits.wall, CollisionBits.wallCollisions),
        GDrawable,
        GStatic {

    override val img: TextureRegion = GBundle.bundle.assMan.getTexture("Wall")

    companion object {
        const val ratio = 1f / 16f
        fun get(r: Rectangle): Wall {
            r.scale(ratio)
            return Wall(
                    GDim(r.width, r.height),
                    GVec2.get(r.x, r.y))
        }
    }
}

private fun Rectangle.scale(ratio: Float) {
    x *= ratio
    y *= ratio
    width *= ratio
    height *= ratio
}
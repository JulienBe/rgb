package hacknslash.rgb.specific.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawable
import hacknslash.rgb.general.gameobjects.GStatic
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class Wall(dim: GDim, initPos: GVec2) :
        GActor(dim, initPos, CollisionBits.wall, CollisionBits.wallCollisions),
        GDrawable,
        GStatic {

    override val img: TextureRegion = GBundle.bundle.assMan.getTexture("Wall")

    companion object {
        private const val ratio = 1f / 32f
        fun get(r: Rectangle): Wall {
            r.scale(ratio)
            val wall =  Wall(
                    GDim(r.width, r.height),
                    GVec2.get(r.x, r.y))
            return wall.setup() as Wall
        }
        fun get(x: Float, y: Float, width: Float): Wall {
            return Wall(GDim(width, width), GVec2.get(x, y)).setup() as Wall
        }
    }
}

private fun Rectangle.scale(ratio: Float) {
    x *= ratio
    y *= ratio
    width *= ratio
    height *= ratio
}
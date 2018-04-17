package hacknslash.rgb.specific

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawer
import hacknslash.rgb.general.gameobjects.GMover

class Enemy private constructor(assMan: GAssMan, override val img: TextureRegion = assMan.square()) : GActor(dim),
        GDrawer,
        GMover {
    override val pPos = GVec2()
    override val dir = GVec2()
    override val maxSpeed = 20f

    companion object {
        val dim = GDim(30f, 30f)
        var count = 0

        fun get(assMan: GAssMan): Enemy {
            count++
            return Enemy(assMan)
        }
    }
}
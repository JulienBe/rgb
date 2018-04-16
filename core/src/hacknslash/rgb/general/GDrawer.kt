package hacknslash.rgb.general

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

open class GDrawer(val img: TextureRegion, val dim: GDim) {

    val pos get() = GVec2()

    fun draw(batch: SpriteBatch) {
        batch.draw(img, pos.x, pos.y, dim.width, dim.height)
    }

}
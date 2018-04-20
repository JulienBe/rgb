package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface GDrawable {

    val img: TextureRegion

    //TODO remove this as
    fun draw(batch: SpriteBatch) {
        (this as GActor)
        batch.draw(img, x, y, w, h)
    }

}
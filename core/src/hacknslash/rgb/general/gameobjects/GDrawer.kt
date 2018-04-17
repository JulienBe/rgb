package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface GDrawer {

    val img: TextureRegion

    //TODO remove this as
    fun draw(batch: SpriteBatch) {
        batch.draw(img, (this as GActor).pos.x, (this as GActor).pos.y, (this as GActor).dim.width, (this as GActor).dim.height)
    }

}
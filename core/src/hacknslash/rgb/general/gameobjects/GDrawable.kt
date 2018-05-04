package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GBundle

interface GDrawable {

    val img: TextureRegion

    //TODO remove this as
    fun draw() {
        (this as GActor)
        GBundle.bundle.batch.draw(img, x, y, w, h)
    }

}
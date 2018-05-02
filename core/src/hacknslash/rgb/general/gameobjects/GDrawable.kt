package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GActBundle

interface GDrawable {

    val img: TextureRegion

    //TODO remove this as
    fun draw() {
        (this as GActor)
        GActBundle.bundle.batch.draw(img, x, y, w, h)
    }

}
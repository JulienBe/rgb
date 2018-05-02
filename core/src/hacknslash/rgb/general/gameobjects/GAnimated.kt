package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GClock
import hacknslash.rgb.general.bundles.GActBundle

interface GAnimated {
    val anim: Animation<TextureRegion>

    //TODO remove this as
    fun animate() {
        (this as GActor)
        GActBundle.bundle.batch.draw(anim.getKeyFrame(animTime()), x, y, w, h)
    }

    fun animTime() = GClock.time
}
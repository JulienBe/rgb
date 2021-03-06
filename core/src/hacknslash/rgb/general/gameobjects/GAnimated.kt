package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GClock
import hacknslash.rgb.general.bundles.GBundle

interface GAnimated {
    val anim: Animation<TextureRegion>

    //TODO remove this as
    fun animate() {
        (this as GActor)
        GBundle.bundle.batch.draw(anim.getKeyFrame(animTime()), x, y, w, h)
    }

    fun animTime() = GClock.time
}
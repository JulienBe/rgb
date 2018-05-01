package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GClock

interface GAnimated {
    val anim: Animation<TextureRegion>

    //TODO remove this as
    fun animate(batch: SpriteBatch) {
        (this as GActor)
        batch.draw(anim.getKeyFrame(animTime()), x, y, w, h)
    }

    fun animTime() = GClock.time
}
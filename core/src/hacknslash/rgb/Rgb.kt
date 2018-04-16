package hacknslash.rgb

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.specific.LevelContainer

class Rgb : Game() {

    override fun create() {
        setScreen(LevelContainer(this, GAssMan(), SpriteBatch()))
    }

}

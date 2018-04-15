package hacknslash.rgb

import com.badlogic.gdx.Game
import hacknslash.rgb.specific.LevelContainer

class Rgb : Game() {

    override fun create() {
        setScreen(LevelContainer(this))
    }

}

package hacknslash.rgb

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2D
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.specific.LevelContainer

class Rgb : Game() {

    override fun create() {
        Box2D.init()
        setScreen(LevelContainer(this, GAssMan(), SpriteBatch()))
    }

}

package hacknslash.rgb

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2D
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.specific.containers.LevelContainer

class Rgb : Game() {

    override fun create() {
        Box2D.init()
        Gdx.graphics.setVSync(true)
        setScreen(LevelContainer(this, GAssMan(), SpriteBatch()))
    }

}

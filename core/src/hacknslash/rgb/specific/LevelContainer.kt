package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.GArr
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GScreen
import hacknslash.rgb.general.InputHandler
import hacknslash.rgb.general.gameobjects.GControllable
import hacknslash.rgb.general.gameobjects.GMover

class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch), InputHandler {

    val player = Player(assMan)
    val movers: GArr<GMover> = GArr()
    val controlled: GArr<GControllable> = GArr()

    init {
        movers.add(player)
        controlled.add(player)
    }

    override fun render(delta: Float) {
        super.render(delta)
        batch.begin()
        player.draw(batch)
        batch.end()
        movers.forEach({
            it.act(delta)
        })
        controlled.forEach({
            it.control(this)
        })
    }
}
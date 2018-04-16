package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GScreen

class LevelContainer(game: Game, val assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch) {

    val player = Player(assMan)

    override fun render(delta: Float) {
        super.render(delta)
        batch.begin()
        player.draw(batch)
        batch.end()
    }
}
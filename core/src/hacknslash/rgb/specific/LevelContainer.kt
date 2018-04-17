package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.GArr
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GScreen
import hacknslash.rgb.general.InputHandler
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GControllable
import hacknslash.rgb.general.gameobjects.GDrawer
import hacknslash.rgb.general.gameobjects.GMover

class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch), InputHandler {

    val player = Player.get(assMan)
    val movers: GArr<GMover> = GArr()
    val controlled: GArr<GControllable> = GArr()
    val drawers: GArr<GDrawer> = GArr()

    init {
        addActor(player)
        addActor(Enemy.get(assMan))
    }

    override fun render(delta: Float) {
        super.render(delta)

        batch.begin()
        drawers.forEach { it.draw(batch) }
        batch.end()

        movers.forEach { it.act(delta) }
        controlled.forEach { it.control(this) }
    }

    fun addActor(actor: GActor) {
        if (actor is GControllable) controlled.add(actor)
        if (actor is GMover) movers.add(actor)
        if (actor is GDrawer) drawers.add(actor)
    }
}
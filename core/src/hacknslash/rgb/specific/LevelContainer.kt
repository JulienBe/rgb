package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.*
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.general.physics.GPhysic


class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val physic = GPhysic()
    val player = Player.get(assMan, physic)
    val actors = GArr<GActor>()
    val deadActors = GArr<GActor>()
    val bundle = GActBundle(physic, assMan, this, spriteBatch, actors, 0f)

    init {
        actors.add(Enemy.get(assMan, physic))
    }

    override fun render(delta: Float) {
        bundle.delta = delta
        physic.removeAll(deadActors)
        actors.removeAll(deadActors)
        deadActors.clear()
        GClock.act(delta)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        super.render(delta)
        batch.begin()
        player.act(bundle)
        actors.forEach {
            if (it.act(bundle))
                deadActors.add(it)
        }
        batch.end()
        physic.act(delta)
        physic.debug(cam)
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
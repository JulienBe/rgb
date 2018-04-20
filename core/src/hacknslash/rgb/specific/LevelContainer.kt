package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.*
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.physics.GPhysic


class LevelContainer(game: Game, val assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val physic = GPhysic()
    val player = Player.get(assMan, physic)
    val movers: GArr<GMover> = GArr()
    val controlled: GArr<GControllable> = GArr()
    val drawers: GArr<GDrawable> = GArr()
    val shooters: GArr<GShooter> = GArr()

    init {
        addActor(player)
        addActor(Enemy.get(assMan, physic))
    }

    override fun render(delta: Float) {
        GClock.act(delta)
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        super.render(delta)
        batch.begin()
        drawers.forEach { it.draw(batch) }
        batch.end()

        movers.forEach { it.move(delta) }
        controlled.forEach { it.control(this) }
        shooters.forEach {
            it.act(assMan, physic).forEach { addActor(it) }
        }

        physic.act(delta)
        physic.debug(cam)
    }

    private fun addActor(actor: GActor) {
        if (actor is GControllable) controlled.add(actor)
        if (actor is GMover) movers.add(actor)
        if (actor is GDrawable) drawers.add(actor)
        if (actor is GShooter) shooters.add(actor)
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
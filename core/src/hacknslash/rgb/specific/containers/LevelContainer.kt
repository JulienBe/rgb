package hacknslash.rgb.specific.containers

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.*
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.general.map.GLevelLoader
import hacknslash.rgb.general.map.GMap
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.actors.Energy
import hacknslash.rgb.specific.actors.EnergyMagnet

class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val b = GBundle(assMan, this, spriteBatch)
    val map: GMap = GLevelLoader().proceduralGeneration()
    val shapeRenderer = ShapeRenderer()
    val energySpawner = GPeriodicCaller({
        val e = Energy.get(map)
        b.actors.toSetup(e)
    }, 1000f)

    init {
        for (i in 0..3)
            GLevelLoader().proceduralGeneration()
        b.player.setup()
        shapeRenderer.setAutoShapeType(true)
        map.walls.forEach {
            b.actors.toSetup(it)
        }
        for (i in 1..0)
            b.actors.toSetup(
                    EnergyMagnet(GVec2.get(map.xInside(EnergyMagnet.dim), map.yInside(EnergyMagnet.dim)))
            )
    }

    override fun render(delta: Float) {
        b.delta = delta

        energySpawner.act()
        GClock.act(delta)
        cam.position.set(b.player.cx, b.player.cy, 1f)
        b.physic.act(delta)
        super.render(delta)

        bloom.capture()
        batch.begin()
        b.particles.act(batch)
        batch.setColor(1f, 1f, 1f, 1f)
        b.player.act()
        b.actors.act()
        batch.end()
        bloom.render()
//        debug()
    }

    private fun debug() {
        shapeRenderer.projectionMatrix = cam.combined
        shapeRenderer.begin()
        b.actors.debug(shapeRenderer)
        shapeRenderer.line(b.player.cx, b.player.cy, b.player.cx + b.player.speedX, b.player.cy + b.player.speedY)
        shapeRenderer.end()
        b.physic.debug(cam)
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
package hacknslash.rgb.specific.containers

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.*
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.specific.actors.Enemy

class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val bundle = GActBundle(assMan, this, spriteBatch)
    var enemiesNumber = 5
    val map = GLevelLoader.load("one")
    val shapeRenderer = ShapeRenderer()

    init {
        shapeRenderer.setAutoShapeType(true)
        map.walls.forEach {
            bundle.actors.add(it)
        }
    }

    private fun spawnEnemy() {
        bundle.actors.add(Enemy.get(
                GRand.withinButNot(map.lowX, map.highX - Enemy.dim.width, bundle.player.cx - 10f, bundle.player.cx + 10f),
                GRand.withinButNot(map.lowY, map.highY - Enemy.dim.height, bundle.player.cy - 10f, bundle.player.cy + 10f)))
    }

    override fun render(delta: Float) {
        GActBundle.bundle.delta = delta

        GClock.act(delta)
        cam.position.set(bundle.player.cx, bundle.player.cy, 1f)
        bundle.physic.act(delta)
        super.render(delta)

        bloom.capture()
        batch.begin()
        bundle.particles.act(batch)
        batch.setColor(1f, 1f, 1f, 1f)
        bundle.player.act()
        bundle.actors.act()
        crowdControl()
        batch.end()
        bloom.render()
//        debug()
    }

    private fun debug() {
        shapeRenderer.projectionMatrix = cam.combined
        shapeRenderer.begin()
        bundle.actors.debug(shapeRenderer)
        shapeRenderer.line(bundle.player.cx, bundle.player.cy, bundle.player.cx + bundle.player.speedX, bundle.player.cy + bundle.player.speedY)
        shapeRenderer.end()
        bundle.physic.debug(cam)
    }

    private fun crowdControl() {
        if (enemiesNumber > Enemy.count) {
            spawnEnemy()
            if (GRand.nextFloat() > 0.8f)
                enemiesNumber++
        }
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
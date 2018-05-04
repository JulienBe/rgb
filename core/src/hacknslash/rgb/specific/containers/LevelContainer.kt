package hacknslash.rgb.specific.containers

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.*
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.actors.Enemy
import hacknslash.rgb.specific.actors.Energy
import hacknslash.rgb.specific.actors.EnergyMagnet

class LevelContainer(game: Game, assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val b = GBundle(assMan, this, spriteBatch)
    val map: GMap = GLevelLoader.load("one")
    var enemiesNumber = 5
    val shapeRenderer = ShapeRenderer()

    init {
        shapeRenderer.setAutoShapeType(true)
        map.walls.forEach {
            b.actors.add(it)
        }
    }

    private fun spawnEnemy() {
        b.actors.add(Enemy.get(
                map.xInside(Enemy.dim, b.player.cx - 10f, b.player.cx + 10f),
                map.yInside(Enemy.dim, b.player.cy - 10f, b.player.cy + 10f)))
    }

    override fun render(delta: Float) {
        b.delta = delta

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
        crowdControl()
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

    private fun crowdControl() {
        if (enemiesNumber > Enemy.count) {
//            spawnEnemy()
//            if (GRand.nextFloat() > 0.8f)
//                enemiesNumber++
            b.actors.add(Energy.get(map))
            val magnet = EnergyMagnet(GVec2.get(map.xInside(EnergyMagnet.dim), map.yInside(EnergyMagnet.dim)))
            magnet.cx
            b.actors.add(magnet)
//            Enemy.count++
        }
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
package hacknslash.rgb.specific.containers

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.*
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.containers.GParticlesContainer
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.specific.Enemy
import hacknslash.rgb.specific.Player
import ktx.collections.gdxArrayOf


class LevelContainer(game: Game, private val assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val particlesContainer = GParticlesContainer()
    val physic = GPhysic(particlesContainer)
    val player = Player.get(assMan, physic)
    val actors = gdxArrayOf<GActor>()
    val deadActors = gdxArrayOf<GActor>()
    val bundle = GActBundle(physic, assMan, this, spriteBatch, actors, 0f, particlesContainer)
    var enemiesNumber = 40
    val map = GLevelLoader.load("one", physic, assMan)
    val shapeRenderer = ShapeRenderer()

    init {
        shapeRenderer.setAutoShapeType(true)
        map.walls.forEach {
            actors.add(it)
        }
    }

    private fun spawnEnemy() {
        actors.add(Enemy.get(
                GRand.withinButNot(map.lowX, map.highX - Enemy.dim.width, player.cx - 10f, player.cx + 10f),
                GRand.withinButNot(map.lowY, map.highY - Enemy.dim.height, player.cy - 10f, player.cy + 10f),
                assMan,
                physic))
    }

    override fun render(delta: Float) {
        bundle.delta = delta

        physic.removeAll(deadActors)
        actors.removeAll(deadActors, true)
        deadActors.clear()
        GClock.act(delta)
        cam.position.set(player.cx, player.cy, 1f)
        physic.act(delta)
        super.render(delta)

        bloom.capture()
        batch.begin()
        particlesContainer.draw(batch)
        batch.setColor(1f, 1f, 1f, 1f)
        player.act(bundle)
        actors.forEach {
            if (it.act(bundle))
                deadActors.add(it)
        }
        crowdControl()
        batch.end()
        bloom.render()
//        debug()
    }

    private fun debug() {
        shapeRenderer.projectionMatrix = cam.combined
        shapeRenderer.begin()
        actors.forEach {
            shapeRenderer.line(it.cx, it.cy, it.cx + it.speedX, it.cy + it.speedY)
        }
        shapeRenderer.line(player.cx, player.cy, player.cx + player.speedX, player.cy + player.speedY)
        shapeRenderer.end()
        physic.debug(cam)
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
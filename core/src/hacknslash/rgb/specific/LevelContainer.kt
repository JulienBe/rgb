package hacknslash.rgb.specific

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.*
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.graphics.GScreen
import hacknslash.rgb.general.particles.GParticle
import hacknslash.rgb.general.physics.GPhysic


class LevelContainer(game: Game, private val assMan: GAssMan, spriteBatch: SpriteBatch) : GScreen(game, spriteBatch, width, height), InputHandler {

    val physic = GPhysic()
    val player = Player.get(assMan, physic)
    val actors = GArr<GActor>()
    val deadActors = GArr<GActor>()
    val particles = GArr<GParticle>()
    val deadParticles = GArr<GParticle>()
    val bundle = GActBundle(physic, assMan, this, spriteBatch, actors, 0f, particles)
    var enemiesNumber = 1
    val map = GLevelLoader.load("one", physic, assMan)
    val shapeRenderer = ShapeRenderer()

    init {
        shapeRenderer.setAutoShapeType(true)
        spawnEnemy(assMan)
        map.walls.forEach {
            actors.add(it)
        }
    }

    private fun spawnEnemy(assMan: GAssMan) {
        actors.add(Enemy.get(
                GRand.withinButNot(map.lowX, map.highX - Enemy.dim.width, player.cx - 10f, player.cx + 10f),
                GRand.withinButNot(map.lowY, map.highY- Enemy.dim.height, player.cy - 10f, player.cy + 10f),
                assMan,
                physic))
    }

    override fun render(delta: Float) {
        bundle.delta = delta

        physic.removeAll(deadActors)
        actors.removeAll(deadActors)
        particles.removeAll(deadParticles)

        deadParticles.forEach { it.free() }
        deadParticles.clear()
        deadActors.clear()
        GClock.act(delta)
        cam.position.set(player.cx, player.cy, GClock.time)
        physic.act(delta)
        super.render(delta)

        batch.begin()
        particles.forEach {
            if (it.draw(batch))
                deadParticles.add(it)
        }
        batch.setColor(1f, 1f, 1f, 1f)
        player.act(bundle)
        actors.forEach {
            if (it.act(bundle))
                deadActors.add(it)
        }
        crowdControl()
        batch.end()
        shapeRenderer.projectionMatrix = cam.combined
        shapeRenderer.begin()
        actors.forEach {
            shapeRenderer.line(it.cx, it.cy, it.cx + it.speedX, it.cy + it.speedY)
        }
        shapeRenderer.line(player.cx, player.cy, player.cx + player.speedX, player.cy + player.speedY)
        shapeRenderer.end()
//        physic.debug(cam)
    }

    private fun crowdControl() {
        if (enemiesNumber > Enemy.count) {
            spawnEnemy(assMan)
            if (GRand.nextFloat() > 0.8f)
                enemiesNumber++
        }
    }

    companion object {
        const val width = 160f
        const val height = 100f
    }
}
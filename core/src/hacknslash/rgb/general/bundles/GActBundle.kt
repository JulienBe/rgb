package hacknslash.rgb.general.bundles

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.InputHandler
import hacknslash.rgb.general.containers.GActorsContainer
import hacknslash.rgb.general.containers.GParticlesContainer
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.specific.actors.Player

data class GActBundle(
        val assMan: GAssMan,
        val input: InputHandler,
        val batch: SpriteBatch) {

    val particles: GParticlesContainer = GParticlesContainer()
    val actors: GActorsContainer = GActorsContainer()
    val player: Player = Player.get()
    val physic: GPhysic = GPhysic()
    var delta: Float = 0f

    init {
        bundle = this
    }

    companion object {
        lateinit var bundle: GActBundle
    }
}
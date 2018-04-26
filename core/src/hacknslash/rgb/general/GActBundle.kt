package hacknslash.rgb.general

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.particles.GParticle
import hacknslash.rgb.general.physics.GPhysic

data class GActBundle(
        val physic: GPhysic,
        val assMan: GAssMan,
        val input: InputHandler,
        val batch: SpriteBatch,
        val actors: GArr<GActor>,
        var delta: Float,
        val particles: GArr<GParticle>)
package hacknslash.rgb.general

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.containers.GParticlesContainer
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.physics.GPhysic
import ktx.collections.GdxArray

data class GActBundle(
        val physic: GPhysic,
        val assMan: GAssMan,
        val input: InputHandler,
        val batch: SpriteBatch,
        val actors: GdxArray<GActor>,
        var delta: Float,
        val particles: GParticlesContainer)
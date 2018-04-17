package hacknslash.rgb.general.physics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World

class GPhysic {
    val debugRenderer = Box2DDebugRenderer()
    val world = World(Vector2(0f, 0f), true)
    var accumulator = 0f

    fun act(delta: Float) {
        val frameTime = Math.min(delta, 0.25f)
        accumulator += frameTime
        while (accumulator >= timestep) {
            world.step(timestep, velocityIterations, positionIterations)
            accumulator -= timestep
        }
    }

    fun debug(camera: Camera) {
        debugRenderer.render(world, camera.combined)
    }

    companion object {
        val timestep = 1f/60f
        val velocityIterations = 6
        val positionIterations = 2
    }
}
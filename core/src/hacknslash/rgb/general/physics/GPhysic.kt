package hacknslash.rgb.general.physics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*

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

    fun createBody(pos: GVec2, dim: GDim): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.KinematicBody
        bodyDef.position.set(Vector2(pos.x + dim.hh, pos.y + dim.hw))
        val body = world.createBody(bodyDef)
        val squareShape = PolygonShape()
        squareShape.setAsBox(dim.hw, dim.hh)
        val fixture = FixtureDef()
        fixture.shape = squareShape
        body.createFixture(fixture)
        squareShape.dispose()
        return body
    }

    companion object {
        val timestep = 1f/60f
        val velocityIterations = 6
        val positionIterations = 2
    }
}
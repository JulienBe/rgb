package hacknslash.rgb.general.physics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import hacknslash.rgb.general.GArr
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GControllable
import hacknslash.rgb.general.gameobjects.GSensor

class GPhysic {
    val debugRenderer = Box2DDebugRenderer()
    val world = World(Vector2(0f, 0f), true)
    var accumulator = 0f

    init {
        world.setContactListener(GContactListener())
    }

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

    fun createBody(actor: GActor): Body {
        val bodyDef = getBodyDef(actor)
        val body = world.createBody(bodyDef)
        val squareShape = PolygonShape()
        squareShape.setAsBox(actor.hw, actor.hh)
        body.createFixture(getFixture(squareShape, false))
        println("CREATING " + actor + "which is sensor ? " + (actor is GSensor))
        if (actor is GSensor) {
            val circle = CircleShape()
            circle.radius = actor.sensorRadius
            body.createFixture(getFixture(circle, true))
            println("CREATED SENSOR")
            circle.dispose()
        }
        squareShape.dispose()
        body.userData = actor
        return body
    }

    private fun getFixture(shape: Shape, sensor: Boolean): FixtureDef {
        val fixture = FixtureDef()
        fixture.shape = shape
        fixture.friction = 1f
        fixture.restitution = 1f
        fixture.isSensor = sensor
        return fixture
    }

    private fun getBodyDef(actor: GActor): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        if (actor is GControllable)
            bodyDef.type = BodyDef.BodyType.KinematicBody
        bodyDef.linearDamping = 10f
        bodyDef.position.set(Vector2(actor.initPos.x + actor.hh, actor.initPos.y + actor.hw))
        return bodyDef
    }

    fun removeAll(deadActors: GArr<GActor>) {
        deadActors.forEach {
            world.destroyBody(it.body)
        }
    }

    companion object {
        val timestep = 1f/60f
        val velocityIterations = 6
        val positionIterations = 2
    }

    class GContactListener : ContactListener {
        override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
        override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
        override fun endContact(contact: Contact?) {}

        override fun beginContact(contact: Contact?) {
            contact!!
            val fixA = contact.fixtureA
            val fixB = contact.fixtureB
            val actorA = fixA.body.userData
            val actorB = fixB.body.userData
            actorA as GActor
            actorB as GActor

            if (!fixA.isSensor && !fixB.isSensor) {
                actorA.collide(actorB)
                actorB.collide(actorA)
            } else if (!(fixA.isSensor && fixB.isSensor)) {
                if (contact.fixtureA.isSensor)
                    (actorA as GSensor).senses(actorB)
                else
                    (actorB as GSensor).senses(actorA)
            }
        }
    }
}
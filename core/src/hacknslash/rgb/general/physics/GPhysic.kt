package hacknslash.rgb.general.physics

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GControllable
import hacknslash.rgb.general.gameobjects.GSensor
import hacknslash.rgb.general.gameobjects.GStatic
import ktx.collections.GdxArray
import kotlin.reflect.KFunction2

class GPhysic() {
    val debugRenderer = Box2DDebugRenderer()
    val world = World(Vector2(0f, 0f), true)
    var accumulator = 0f

    fun setup() {
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
        body.createFixture(getFixture(actor, squareShape, false))
        if (actor is GSensor) {
            val circle = CircleShape()
            circle.radius = actor.sensorRadius
            body.createFixture(getFixture(actor, circle, true))
            circle.dispose()
        }
        squareShape.dispose()
        body.userData = actor
        return body
    }

    private fun getFixture(actor: GActor, shape: Shape, sensor: Boolean): FixtureDef {
        val fixture = FixtureDef()
        fixture.shape = shape
        fixture.friction = 0.1f
        fixture.restitution = 1f
        fixture.isSensor = sensor
        fixture.filter.categoryBits = actor.isA
        fixture.filter.maskBits = actor.collidesWith
        return fixture
    }

    private fun getBodyDef(actor: GActor): BodyDef {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        if (actor is GControllable)
            bodyDef.type = BodyDef.BodyType.KinematicBody
        if (actor is GStatic)
            bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.linearDamping = 0.1f
        bodyDef.position.set(Vector2(actor.initPos.x + actor.hw, actor.initPos.y + actor.hh))
        return bodyDef
    }

    fun removeAll(deadActors: GdxArray<GActor>) {
        deadActors.forEach {
            it.destroyBody(world)
        }
    }

    companion object {
        val timestep = 1f/60f
        val velocityIterations = 6
        val positionIterations = 2
    }

    class GContactListener() : ContactListener {
        override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
        override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}

        override fun endContact(contact: Contact?) {
            check(contact, GActor::stopCollide, GSensor::stopSenses)
        }

        override fun beginContact(contact: Contact?) {
            check(contact, GActor::collide, GSensor::senses)
        }

        private fun check(contact: Contact?, collision: KFunction2<GActor, @ParameterName(name = "other") GActor, Unit>, sens: KFunction2<GSensor, @ParameterName(name = "a") GActor, Unit>) {
            contact!!
            val fixA = contact.fixtureA
            val fixB = contact.fixtureB
            val actorA = fixA.body.userData
            val actorB = fixB.body.userData
            actorA as GActor
            actorB as GActor

            if (!fixA.isSensor && !fixB.isSensor) {
                collision(actorA, actorB)
                collision(actorB, actorA)
            } else if (!(fixA.isSensor && fixB.isSensor)) {
                if (contact.fixtureA.isSensor)
                    sens((actorA as GSensor), actorB)
                else
                    sens((actorB as GSensor), actorA)
            }
        }
    }
}
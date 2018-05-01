package hacknslash.rgb.specific.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GAnimated
import hacknslash.rgb.general.gameobjects.GTtl
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class PowerUp private constructor(initPos: GVec2, bundle: GActBundle) :
        GActor(dim, initPos, bundle.physic, CollisionBits.powerUp, CollisionBits.powerUpCollision),
        GTtl,
        GAnimated {

    override var ttl: Float = 6f
    override val anim: Animation<TextureRegion> = bundle.assMan.getAnimation("PowerUp")

    override fun collide(other: GActor, bundle: GActBundle) {
        if (other is Player) {
            ttlExpired()
            other.powerup()
        }
        super.collide(other, bundle)
    }

    override fun animate(batch: SpriteBatch) {
        batch.setColor(1f, 0.8f, 8f, 1f)
        super.animate(batch)
        batch.color = Color.WHITE
    }

    companion object {
        val dim = GDim(4f, 4f)

        fun get(initPos: GVec2, bundle: GActBundle): PowerUp {
            return PowerUp(initPos, bundle)
        }
    }
}
package hacknslash.rgb.general

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxMapOf

open class GAssMan(val pack: String = "atlas.pack") : AssetManager(), AssetErrorListener {

    private val trMapping = gdxMapOf(
            "GObjectParticle"   to "whitesqure",
            "Wall"              to "whitesqure",
            "Enemy"             to "whitesqure",
            "Energy"            to "whitesqure",
            "Floor"             to "whitesqure",
            "Magnet"            to "whitesqure"
    )
    private val soundMapping = gdxMapOf(
            "explosionenemy"    to "explosionenemy",
            "Enemy"             to "explosionenemy"
    )
    private val animationMapping = gdxMapOf(
            "Enemy"            to   Triple(0.33f, "starradiance", Animation.PlayMode.LOOP_PINGPONG),
            "PowerUp"          to   Triple(0.13f, "starradiance", Animation.PlayMode.LOOP_PINGPONG),
            "Magnet"           to   Triple(0.23f, "radiate", Animation.PlayMode.LOOP)
    )
    private val sounds = gdxMapOf<String, Sound>()
    private val textures = gdxMapOf<String, TextureRegion>()
    private val animations = gdxMapOf<String, Animation<TextureRegion>>()

    init {
        setErrorListener(this)
        Texture.setAssetManager(this)
        load(pack, TextureAtlas::class.java)
        while (!update(25)) {
            println("" + System.currentTimeMillis() + " : " + progress)
        }
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.error("Error handling assets", "", throwable)
    }

    fun getTexture(name: String): TextureRegion {
        return getTextureRegion(trMapping[name])
    }

    private fun getTextureRegion(name: String): TextureRegion {
        if (!textures.containsKey(name))
            textures.put(name, get(pack, TextureAtlas::class.java).findRegion(name))
        return textures[name]
    }

    fun getSound(name: String): Sound {
        return getActualSound(soundMapping[name])
    }

    private fun getActualSound(name: String): Sound {
        if (!sounds.containsKey(name))
            sounds.put(name, Gdx.audio.newSound(Gdx.files.internal("sounds/$name.wav")))
        return sounds[name]
    }

    fun getAnimation(name: String): Animation<TextureRegion> {
        if (!animations.containsKey(name)) {
            val entry = animationMapping[name]
            animations.put(name, Animation(entry.first, get(pack, TextureAtlas::class.java).findRegions(entry.second), entry.third))
        }
        return animations[name]
    }
}
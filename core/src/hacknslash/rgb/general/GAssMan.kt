package hacknslash.rgb.general

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.collections.gdxMapOf

open class GAssMan(val pack: String = "atlas.pack") : AssetManager(), AssetErrorListener {

    private val trMapping = gdxMapOf(
            "GObjectParticle"   to "whitesqure",
            "Wall"              to "whitesqure",
            "GObjectParticle"   to "whitesqure"
    )
    private val soundMapping = gdxMapOf(
            "explosionenemy"    to "explosionenemy",
            "Enemy"             to "explosionenemy"
    )
    private val sounds = gdxMapOf<String, Sound>()
    private val textures = gdxMapOf<String, TextureRegion>()

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

    protected fun getTextureRegion(name: String): TextureRegion {
        if (!textures.containsKey(name))
            textures.put(name, get(pack, TextureAtlas::class.java).findRegion(name))
        return textures[name]
    }

    fun getSound(name: String): Sound {
        return getMappedSound(soundMapping[name])
    }

    protected fun getMappedSound(name: String): Sound {
        if (!sounds.containsKey(name))
            sounds.put(name, Gdx.audio.newSound(Gdx.files.internal("sounds/$name.wav")))
        return sounds[name]
    }
}
package hacknslash.rgb.general

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class GAssMan : AssetManager(), AssetErrorListener {

    private val pack = "atlas.atlas"
    private lateinit var enemyExplosion: Sound

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

    fun square(): TextureAtlas.AtlasRegion {
        val atlas = get(pack, TextureAtlas::class.java)
        return atlas.findRegion("whitesqure")
    }

    fun getEnemyExplosion(): Sound {
        if (!::enemyExplosion.isInitialized) {
            enemyExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosionenemy.wav"))
        }
        return enemyExplosion
    }
}
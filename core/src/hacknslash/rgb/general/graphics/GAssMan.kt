package hacknslash.rgb.general.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class GAssMan : AssetManager(), AssetErrorListener {

    val pack = "atlas.atlas"

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
}
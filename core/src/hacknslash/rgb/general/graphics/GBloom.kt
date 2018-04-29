package hacknslash.rgb.general.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import java.io.Serializable

/**
 * Bloomlib allow easy but efficient way to add bloom effect as post process
 * effect
 *
 * @author kalle_h
 */
class GBloom : Serializable {

    /** how many blur pass  */
    var blurPasses = 1

    private var tresholdShader: ShaderProgram? = null
    private var bloomShader: ShaderProgram? = null
    private var blurShader: ShaderProgram? = null

    private var fullScreenQuad: Mesh? = null

    private var pingPongTex1: Texture? = null
    private var pingPongTex2: Texture? = null
    private var original: Texture? = null

    private var frameBuffer: FrameBuffer? = null
    private var pingPongBuffer1: FrameBuffer? = null
    private var pingPongBuffer2: FrameBuffer? = null

    private var bloomIntensity: Float = 0.toFloat()
    private var originalIntensity: Float = 0.toFloat()
    private var treshold: Float = 0.toFloat()
    private var w: Int = 0
    private var h: Int = 0
    private var blending = false
    private var capturing = false
    private var disposeFBO = true
    private var r = 0f
    private var g = 0f
    private var b = 0f
    private var a = 1f

    /**
     * IMPORTANT NOTE CALL THIS WHEN RESUMING
     */
    fun resume() {
        bloomShader!!.begin()
        run {
            bloomShader!!.setUniformi("u_texture0", 0)
            bloomShader!!.setUniformi("u_texture1", 1)
        }
        bloomShader!!.end()

        setSize(w, h)
        setTreshold(treshold)
        setBloomIntesity(bloomIntensity)
        setOriginalIntesity(originalIntensity)

        original = frameBuffer!!.colorBufferTexture
        pingPongTex1 = pingPongBuffer1!!.colorBufferTexture
        pingPongTex2 = pingPongBuffer2!!.colorBufferTexture
    }

    /**
     * Initialize bloom class that capsulate original scene capturate,
     * tresholding, gaussian blurring and blending. Default values: depth = true
     * blending = false 32bits = true
     */
    constructor() {
        initialize(Gdx.graphics.width / 4, Gdx.graphics.height / 4, null, true, false, true)
    }

    /**
     * Initialize bloom class that capsulate original scene capturate,
     * tresholding, gaussian blurring and blending.
     *
     * @param FBO_W
     * @param FBO_H
     * how big fbo is used for bloom texture, smaller = more blur and
     * lot faster but aliasing can be problem
     * @param hasDepth
     * do rendering need depth buffer
     * @param useBlending
     * does fbo need alpha channel and is blending enabled when final
     * image is rendered. This allow to combine background graphics
     * and only do blooming on certain objects param use32bitFBO does
     * fbo use higher precision than 16bits.
     */
    constructor(FBO_W: Int, FBO_H: Int, hasDepth: Boolean, useBlending: Boolean, use32bitFBO: Boolean) {
        initialize(FBO_W, FBO_H, null, hasDepth, useBlending, use32bitFBO)
    }

    /**
     * EXPERT FUNCTIONALITY. no error checking. Use this only if you know what
     * you are doing. Remember that bloom.capture() clear the screen so use
     * continue instead if that is a problem.
     *
     * Initialize bloom class that capsulate original scene capturate,
     * tresholding, gaussian blurring and blending.
     *
     * * @param sceneIsCapturedHere diposing is user responsibility.
     *
     * @param FBO_W
     * @param FBO_H
     * how big fbo is used for bloom texture, smaller = more blur and
     * lot faster but aliasing can be problem
     * @param hasDepth
     * do rendering need depth buffer
     * @param useBlending
     * does fbo need alpha channel and is blending enabled when final
     * image is rendered. This allow to combine background graphics
     * and only do blooming on certain objects param use32bitFBO does
     * fbo use higher precision than 16bits.
     */
    constructor(FBO_W: Int, FBO_H: Int, sceneIsCapturedHere: FrameBuffer, useBlending: Boolean, use32bitFBO: Boolean) {
        initialize(FBO_W, FBO_H, sceneIsCapturedHere, false, useBlending, use32bitFBO)
        disposeFBO = false
    }

    private fun initialize(FBO_W: Int, FBO_H: Int, fbo: FrameBuffer?, hasDepth: Boolean, useBlending: Boolean, use32bitFBO: Boolean) {
        blending = useBlending
        var format: Pixmap.Format? = null

        if (use32bitFBO) {
            if (useBlending) {
                format = Pixmap.Format.RGBA8888
            } else {
                format = Pixmap.Format.RGB888
            }
        } else {
            if (useBlending) {
                format = Pixmap.Format.RGBA4444
            } else {
                format = Pixmap.Format.RGB565
            }
        }
        if (fbo == null) {
            frameBuffer = FrameBuffer(format, Gdx.graphics.width, Gdx.graphics.height, hasDepth)
        } else {
            frameBuffer = fbo
        }

        pingPongBuffer1 = FrameBuffer(format, FBO_W, FBO_H, false)

        pingPongBuffer2 = FrameBuffer(format, FBO_W, FBO_H, false)

        original = frameBuffer!!.colorBufferTexture
        pingPongTex1 = pingPongBuffer1!!.colorBufferTexture
        pingPongTex2 = pingPongBuffer2!!.colorBufferTexture

        fullScreenQuad = createFullScreenQuad()
        val alpha = if (useBlending) "alpha_" else ""

        bloomShader = BloomShaderLoader.createShader("screenspace", alpha + "bloom")

        if (useAlphaChannelAsMask) {
            tresholdShader = BloomShaderLoader.createShader("screenspace", "maskedtreshold")
        } else {
            tresholdShader = BloomShaderLoader.createShader("screenspace", alpha + "treshold")
        }

        blurShader = BloomShaderLoader.createShader("blurspace", alpha + "gaussian")

        setSize(FBO_W, FBO_H)
        setBloomIntesity(2.5f)
        setOriginalIntesity(0.8f)
        setTreshold(0.5f)

        bloomShader!!.begin()
        run {
            bloomShader!!.setUniformi("u_texture0", 0)
            bloomShader!!.setUniformi("u_texture1", 1)
        }
        bloomShader!!.end()
    }

    /**
     * Set clearing color for capturing buffer
     *
     * @param r
     * @param g
     * @param b
     * @param a
     */
    fun setClearColor(r: Float, g: Float, b: Float, a: Float) {
        this.r = r
        this.g = g
        this.b = b
        this.a = a
    }

    /**
     * Call this before rendering scene.
     */
    fun capture() {
        if (!capturing) {
            capturing = true
            frameBuffer!!.begin()
            Gdx.gl.glClearColor(r, g, b, a)
            //			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        }
    }

    /**
     * Pause capturing to fbo.
     */
    fun capturePause() {
        if (capturing) {
            capturing = false
            frameBuffer!!.end()
        }
    }

    /** Start capturing again after pause, no clearing is done to framebuffer  */
    fun captureContinue() {
        if (!capturing) {
            capturing = true
            frameBuffer!!.begin()
        }
    }

    /**
     * Call this after scene. Renders the bloomed scene.
     */
    fun render() {
        if (capturing) {
            capturing = false
            frameBuffer!!.end()
        }

        //		Gdx.gl.glDisable(GL10.GL_BLEND);
        //		Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
        Gdx.gl.glDisable(GL20.GL_BLEND)
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST)
        Gdx.gl.glDepthMask(false)

        gaussianBlur()

        if (blending) {
            Gdx.gl.glEnable(GL20.GL_BLEND)
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
            //			Gdx.gl.glEnable(GL10.GL_BLEND);
            //			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        }

        pingPongTex1!!.bind(1)
        original!!.bind(0)
        bloomShader!!.begin()
        run { fullScreenQuad!!.render(bloomShader, GL20.GL_TRIANGLE_FAN) }
        bloomShader!!.end()

    }

    private fun gaussianBlur() {

        // cut bright areas of the picture and blit to smaller fbo

        original!!.bind(0)
        pingPongBuffer1!!.begin()
        run {
            tresholdShader!!.begin()
            run {
                // tresholdShader.setUniformi("u_texture0", 0);
                fullScreenQuad!!.render(tresholdShader, GL20.GL_TRIANGLE_FAN, 0, 4)
            }
            tresholdShader!!.end()
        }
        pingPongBuffer1!!.end()

        for (i in 0 until blurPasses) {

            pingPongTex1!!.bind(0)

            // horizontal
            pingPongBuffer2!!.begin()
            run {
                blurShader!!.begin()
                run {
                    blurShader!!.setUniformf("dir", 1f, 0f)
                    fullScreenQuad!!.render(blurShader, GL20.GL_TRIANGLE_FAN, 0, 4)
                }
                blurShader!!.end()
            }
            pingPongBuffer2!!.end()

            pingPongTex2!!.bind(0)
            // vertical
            pingPongBuffer1!!.begin()
            run {
                blurShader!!.begin()
                run {
                    blurShader!!.setUniformf("dir", 0f, 1f)

                    fullScreenQuad!!.render(blurShader, GL20.GL_TRIANGLE_FAN, 0, 4)
                }
                blurShader!!.end()
            }
            pingPongBuffer1!!.end()
        }
    }

    /**
     * set intensity for bloom. higher mean more brightening for spots that are
     * over treshold
     *
     * @param intensity
     * multiplier for blurred texture in combining phase. must be
     * positive.
     */
    fun setBloomIntesity(intensity: Float) {
        bloomIntensity = intensity
        bloomShader!!.begin()
        run { bloomShader!!.setUniformf("BloomIntensity", intensity) }
        bloomShader!!.end()
    }

    /**
     * set intensity for original scene. under 1 mean darkening and over 1 means
     * lightening
     *
     * @param intensity
     * multiplier for captured texture in combining phase. must be
     * positive.
     */
    fun setOriginalIntesity(intensity: Float) {
        originalIntensity = intensity
        bloomShader!!.begin()
        run { bloomShader!!.setUniformf("OriginalIntensity", intensity) }
        bloomShader!!.end()
    }

    /**
     * Treshold for bright parts. everything under treshold is clamped to 0
     *
     * @param treshold
     * must be in range 0..1
     */
    fun setTreshold(treshold: Float) {
        this.treshold = treshold
        tresholdShader!!.begin()
        run { tresholdShader!!.setUniformf("treshold", treshold, 1f / (1 - treshold)) }
        tresholdShader!!.end()
    }

    private fun setSize(FBO_W: Int, FBO_H: Int) {
        w = FBO_W
        h = FBO_H
        blurShader!!.begin()
        blurShader!!.setUniformf("size", FBO_W.toFloat(), FBO_H.toFloat())
        blurShader!!.end()
    }

    /**
     * Call this when application is exiting.
     *
     */
    fun dispose() {
        if (disposeFBO)
            frameBuffer!!.dispose()

        fullScreenQuad!!.dispose()

        pingPongBuffer1!!.dispose()
        pingPongBuffer2!!.dispose()

        blurShader!!.dispose()
        bloomShader!!.dispose()
        tresholdShader!!.dispose()

    }

    private fun createFullScreenQuad(): Mesh {
        val verts = FloatArray(16)// VERT_SIZE
        var i = 0
        verts[i++] = -1f // x1
        verts[i++] = -1f // y1

        verts[i++] = 0f // u1
        verts[i++] = 0f // v1

        verts[i++] = 1f // x2
        verts[i++] = -1f // y2

        verts[i++] = 1f // u2
        verts[i++] = 0f // v2

        verts[i++] = 1f // x3
        verts[i++] = 1f // y2

        verts[i++] = 1f // u3
        verts[i++] = 1f // v3

        verts[i++] = -1f // x4
        verts[i++] = 1f // y4

        verts[i++] = 0f // u4
        verts[i++] = 1f // v4

        val tmpMesh = Mesh(true, 4, 0, VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"), VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"))

        tmpMesh.setVertices(verts)
        return tmpMesh

    }

    companion object {

        /**
         * To use implement bloom more like a glow. Texture alpha channel can be
         * used as mask which part are glowing and which are not. see more info at:
         * http://www.gamasutra.com/view/feature/2107/realtime_glow.php
         *
         * NOTE: need to be set before bloom instance is created. After that this
         * does nothing.
         */
        var useAlphaChannelAsMask = false
    }

}

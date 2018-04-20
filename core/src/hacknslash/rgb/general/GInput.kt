package hacknslash.rgb.general

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import hacknslash.rgb.Rgb
import hacknslash.rgb.specific.LevelContainer
import java.util.logging.Level

class GInput {

    private val keyUpMapping: MutableMap<Int, () -> Unit> = mutableMapOf()
    private val keyPressedMapping: MutableMap<Int, () -> Unit> = mutableMapOf()
    private val keyJustPressedMapping: MutableMap<Int, () -> Unit> = mutableMapOf()
    private var click: (() -> Unit)? = null
    private val keyPressedCurrent = mutableSetOf<Int>()
    private val keyPressedOld = mutableSetOf<Int>()

    fun act(inputHandler: InputHandler) {
        keyPressedCurrent.clear()

        checkKeys(inputHandler)

        keyPressedOld.clear()
        keyPressedOld.addAll(keyPressedCurrent)
    }

    private fun checkKeys(inputHandler: InputHandler) {
        keyPressedMapping.forEach { key, value ->
            if (inputHandler.isKeyPressed(key)) {
                keyPressedCurrent.add(key)
                value.invoke()
            }
        }
        keyJustPressedMapping.forEach { key, value ->
            if (inputHandler.isKeyJustPressed(key)) {
                value.invoke()
                keyPressedCurrent.add(key)
            }
        }
        if (inputHandler.isTouched())
            click?.invoke()
        keyPressedOld.minus(keyPressedCurrent).forEach { key: Int -> keyUpMapping[key]?.invoke() }
    }

    fun addKeyUp(key: Int, function: () -> Unit) {
        keyUpMapping.put(key, function)
    }
    fun addKeyPressed(key: Int, function: () -> Unit) {
        keyPressedMapping.put(key, function)
    }
    fun addKeyJustPressed(key: Int, function: () -> Unit) {
        keyJustPressedMapping.put(key, function)
    }
    fun addClick(function: () -> Unit) {
        click = function
    }

    companion object {
        private val wRatio = (Gdx.graphics.width / LevelContainer.width)
        private val hRatio = (Gdx.graphics.height / LevelContainer.height)
        fun x() = Gdx.input.x / wRatio
        fun y() = (Gdx.graphics.height - Gdx.input.y) / hRatio
    }
}

interface InputHandler {
    fun isKeyJustPressed(keycode: Int): Boolean = Gdx.input.isKeyJustPressed(keycode)
    fun isKeyPressed(keycode: Int): Boolean = Gdx.input.isKeyPressed(keycode)
    fun isTouched(): Boolean = Gdx.input.isTouched
}
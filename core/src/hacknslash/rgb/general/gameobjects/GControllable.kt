package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GInput
import hacknslash.rgb.general.InputHandler

interface GControllable {

    val input: GInput

    fun control(inputHandler: InputHandler) {
        input.act(inputHandler)
    }

    fun keyUp(key: Int, function: () -> Unit) {
        input.addKeyUp(key, function)
    }
    fun keyPressed(key: Int, function: () -> Unit) {
        input.addKeyPressed(key, function)
    }
    fun keyJustPressed(key: Int, function: () -> Unit) {
        input.addKeyJustPressed(key, function)
    }
    fun click(function: () -> Unit) {
        input.addClick(function)
    }
}
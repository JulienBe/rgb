package hacknslash.rgb.general

import com.badlogic.gdx.utils.Array

class GArr<T> : Array<T>() {
    fun removeAll(array: GArr<T>) {
        removeAll(array, true)
    }
}
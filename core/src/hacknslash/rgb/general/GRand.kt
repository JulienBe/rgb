package hacknslash.rgb.general

import java.util.*

object GRand: Random() {
    fun within(low: Float, high: Float): Float {
        return low + nextFloat() * high
    }
}
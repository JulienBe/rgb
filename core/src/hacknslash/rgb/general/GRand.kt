package hacknslash.rgb.general

import java.util.*

object GRand: Random() {
    fun within(low: Float, high: Float): Float {
        return low + nextFloat() * high
    }

    fun withinButNot(low: Float, high: Float, lowNot: Float, highNot: Float): Float {
        assert((low < lowNot) || (high > highNot))
        for (i in 1..60) {
            val candidate = within(low, high)
            if (candidate < lowNot || candidate > highNot)
                return candidate
        }
        return within(low, high)
    }
}
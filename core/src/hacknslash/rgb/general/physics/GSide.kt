package hacknslash.rgb.general.physics

import hacknslash.rgb.general.GRand

enum class GSide {
    LEFT, RIGHT;

    fun other(): GSide {
        return if (this == LEFT)
            RIGHT
        else
            LEFT
    }

    companion object {
        fun random(): GSide {
            return if (GRand.nextBoolean()) LEFT else RIGHT
        }
    }
}
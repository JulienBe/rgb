package hacknslash.rgb.general.physics

enum class GSide {
    LEFT, RIGHT;

    fun other(): GSide {
        return if (this == LEFT)
            RIGHT
        else
            LEFT
    }
}
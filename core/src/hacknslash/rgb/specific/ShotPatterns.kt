package hacknslash.rgb.specific

import hacknslash.rgb.general.gameobjects.GShotPattern
import hacknslash.rgb.specific.actors.Bullet

// too much clutter would probably mean too much patterns. Might change
object ShotPatterns {
    val basic = GShotPattern(0.2f, 0f, Bullet::class)
}
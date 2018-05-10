package hacknslash.rgb.specific

import hacknslash.rgb.general.physics.GDim

object Const {
    const val playerSpeed = 9000f
    const val playerImpulse = playerSpeed
    const val bulletSpeed = playerSpeed * 40f
    const val enemySpeed = 60f

    val playerDim = GDim(2f, 2f)
    val enemyDim = GDim(3f, 3f)
    const val enemySenseRadius = 100f
}
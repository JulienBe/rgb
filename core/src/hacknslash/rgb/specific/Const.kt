package hacknslash.rgb.specific

import hacknslash.rgb.general.physics.GDim

object Const {
    const val playerSpeed = 640f
    const val playerImpulse = playerSpeed
    const val bulletSpeed = playerSpeed * 40f
    const val enemySpeed = 60f

    val playerDim = GDim(3f, 3f)
    val enemyDim = GDim(5f, 5f)
    const val enemySenseRadius = 100f
}
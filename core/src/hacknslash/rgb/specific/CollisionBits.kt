package hacknslash.rgb.specific

import kotlin.experimental.or

object CollisionBits {
    const val player: Short     = 0b0000_0001
    const val bullet: Short     = 0b0000_0010
    const val enemy: Short      = 0b0000_0100
    const val wall: Short       = 0b0000_1000
    const val powerUp: Short    = 0b0001_0000
    const val energy: Short     = 0b0010_0000
    const val magnet: Short     = 0b0100_0000

    val playerCollisions: Short = enemy.or(wall).or(powerUp)
    val enemyCollisions: Short = enemy.or(wall).or(bullet).or(player)
    val bulletCollisions: Short = enemy.or(wall)
    val wallCollisions: Short = player.or(enemy).or(bullet).or(powerUp).or(wall)
    val powerUpCollision: Short = player.or(enemy).or(wall)
    val energyCollision: Short = wall.or(magnet)
    val magnetCollision: Short = energy

}
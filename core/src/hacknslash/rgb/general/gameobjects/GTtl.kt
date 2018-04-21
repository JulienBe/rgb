package hacknslash.rgb.general.gameobjects

interface GTtl {
    var ttl: Float

    fun checkTtl(delta: Float) {
        ttl -= delta
        if (ttl <= 0)
            ttlExpired()
    }

    fun ttlExpired() {
        this as GActor
        remove()
    }
}
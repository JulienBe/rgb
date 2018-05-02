package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.bundles.GActBundle

interface GTtl {
    var ttl: Float

    fun checkTtl() {
        ttl -= GActBundle.bundle.delta
        if (ttl <= 0)
            ttlExpired()
    }

    fun ttlExpired() {
        this as GActor
        remove()
    }
}
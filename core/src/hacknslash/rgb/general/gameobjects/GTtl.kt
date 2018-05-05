package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.bundles.GBundle

interface GTtl {
    var ttl: Float

    fun checkTtl() {
        ttl -= GBundle.bundle.delta
        if (ttl <= 0)
            ttlExpired()
    }

    fun ttlExpired() {
        this as GActor
        dead()
    }
}
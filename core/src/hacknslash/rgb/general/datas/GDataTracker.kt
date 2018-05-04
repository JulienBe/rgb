package hacknslash.rgb.general.datas

import hacknslash.rgb.general.gameobjects.GActor
import ktx.collections.GdxArray

class GDataTracker(var trackImpulseStrength: Float) {
    val targets: GdxArray<GActor> = GdxArray()
}
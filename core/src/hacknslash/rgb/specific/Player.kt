package hacknslash.rgb.specific

import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GDim
import hacknslash.rgb.general.GDrawer

class Player(assMan: GAssMan) : GDrawer(assMan.square(), dim) {
    companion object {
        val dim = GDim(10f, 10f)
    }
}
package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.datas.GDataHeartBeat

interface GHeartBeat {
    
    val dataHB: GDataHeartBeat
    
    fun beat() {
        if (dataHB.currentHB > dataHB.middle)
            dataHB.currentAdd -= GBundle.bundle.delta * dataHB.speed.f
        else
            dataHB.currentAdd += GBundle.bundle.delta * dataHB.speed.f
        dataHB.currentHB += dataHB.currentAdd
        if (dataHB.currentHB > dataHB.maxHB)
            dataHB.currentHB = dataHB.maxHB
        if (dataHB.currentHB < dataHB.minHB)
            dataHB.currentHB = dataHB.minHB
    }

}
package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.GDataHeartBeat

interface GHeartBeat {
    
    val dataHB: GDataHeartBeat
    
    fun beat(delta: Float) {
        dataHB.currentAdd += if (dataHB.upHB) delta * dataHB.stepHB else delta * -dataHB.stepHB
        dataHB.currentHB += dataHB.currentAdd

        if (dataHB.currentHB > dataHB.maxHB) {
            dataHB.upHB = false
            dataHB.currentHB = dataHB.maxHB
        }
        if (dataHB.currentHB < dataHB.minHB) {
            dataHB.upHB = true
            dataHB.currentHB = dataHB.minHB
        }
    }

}
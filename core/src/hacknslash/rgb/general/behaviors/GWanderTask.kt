package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.ai.btree.LeafTask
import com.badlogic.gdx.ai.btree.Task
import hacknslash.rgb.general.GClock
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.physics.GSide
import hacknslash.rgb.general.physics.GVec2

class GWanderTask: LeafTask<GWanderer>() {

    var nextPush = 0f

    override fun copyTo(task: Task<GWanderer>?): Task<GWanderer>? {
        return task
    }

    override fun execute(): Status {
        if (nextPush < GClock.time) {
            var degrees = `object`.dir()!!.angle()

            if (GRand.nextFloat() > .9f)
                `object`.prevRotation = `object`.prevRotation.other()

            if (`object`.prevRotation == GSide.LEFT)
                degrees += GRand.nextFloat() * amplitude
            else
                degrees -= GRand.nextFloat() * amplitude

            `object`.impulse(GVec2.get(`object`.push, 0f).setAngle(degrees))
            nextPush = GClock.time + `object`.delay
        }
        return Status.SUCCEEDED
    }

    companion object {
        const val amplitude = 25f
    }
}
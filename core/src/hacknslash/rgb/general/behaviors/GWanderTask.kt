package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.ai.btree.LeafTask
import com.badlogic.gdx.ai.btree.Task
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.physics.GSide

class GWanderTask: LeafTask<GWanderer>() {
    override fun copyTo(task: Task<GWanderer>?): Task<GWanderer>? {
        return task
    }

    override fun execute(): Status {
        val offset = GRand.nextFloat() * 2f
        if (GRand.nextFloat() > .9f)
            `object`.prevRotation = `object`.prevRotation.other()
        if (`object`.prevRotation == GSide.LEFT)
            `object`.impulse(`object`.dir()!!.rotate(offset))
        else
            `object`.impulse(`object`.dir()!!.rotate(-offset))
        return Status.SUCCEEDED
    }
}
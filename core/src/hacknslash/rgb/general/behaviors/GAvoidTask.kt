package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.ai.btree.LeafTask
import com.badlogic.gdx.ai.btree.Task

class GAvoidTask: LeafTask<GAvoider>() {

    override fun execute(): Status {
        if (`object`.stuffToAvoid.size == 0)
            return Status.FAILED
        `object`.stuffToAvoid.forEach {
            `object`.avoid(it)
        }
        return Status.SUCCEEDED
    }

    override fun copyTo(task: Task<GAvoider>?): Task<GAvoider>? {
        return task
    }

}
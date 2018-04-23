package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.ai.btree.LeafTask
import com.badlogic.gdx.ai.btree.Task

class GTrackTask: LeafTask<GTracker>() {

    override fun copyTo(task: Task<GTracker>?): Task<GTracker>? {
        return task
    }

    override fun execute(): Status {
        `object`.target?.let {
            `object`.track()
            return Status.SUCCEEDED
        }
        return Status.FAILED
    }

}
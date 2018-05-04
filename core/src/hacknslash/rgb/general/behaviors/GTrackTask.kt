package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.ai.btree.LeafTask
import com.badlogic.gdx.ai.btree.Task
import ktx.collections.isNotEmpty

class GTrackTask: LeafTask<GTracker>() {

    override fun copyTo(task: Task<GTracker>?): Task<GTracker>? {
        return task
    }

    override fun execute(): Status {
        `object`.targets.isNotEmpty().let {
            `object`.track()
            return Status.SUCCEEDED
        }
        return Status.FAILED
    }

}
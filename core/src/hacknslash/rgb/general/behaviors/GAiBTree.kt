package hacknslash.rgb.general.behaviors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser

interface GAiBTree {
    var bTree: BehaviorTree<GAiBTree>

    fun initTree(treeName: String): BehaviorTree<GAiBTree> {
        val reader = Gdx.files.internal("btrees/$treeName.tree").reader()
        val parser = BehaviorTreeParser<GAiBTree>(BehaviorTreeParser.DEBUG_NONE)
        return parser.parse(reader, this)
    }

    fun step() {
        bTree.step()
    }
}
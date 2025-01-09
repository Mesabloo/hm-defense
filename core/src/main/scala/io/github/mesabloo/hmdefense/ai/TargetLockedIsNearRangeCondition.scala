package io.github.mesabloo.hmdefense.ai

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.GameObject

class TargetLockedIsNearRangeCondition extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    if getObject.getTarget.getPosition.dst(
        getObject.getPosition
      ) <= getObject.range.get._2
    then Status.SUCCEEDED
    else Status.FAILED

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

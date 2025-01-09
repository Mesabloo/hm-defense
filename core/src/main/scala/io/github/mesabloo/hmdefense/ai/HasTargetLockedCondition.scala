package io.github.mesabloo.hmdefense.ai

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.GameObject

class HasTargetLockedCondition extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    if getObject.hasTarget then Status.SUCCEEDED else Status.FAILED

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

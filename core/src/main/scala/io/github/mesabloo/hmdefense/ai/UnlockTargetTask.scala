package io.github.mesabloo.hmdefense.ai

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.GameObject

class UnlockTargetTask extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    getObject.forgetTarget()
    Status.SUCCEEDED

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

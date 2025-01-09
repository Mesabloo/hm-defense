package io.github.mesabloo.hmdefense.ai.machines

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.{GameObject, Machine}

class StopMovementTask extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    getObject.asInstanceOf[Machine].stopInPlace()
    Status.SUCCEEDED

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

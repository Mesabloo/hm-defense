package io.github.mesabloo.hmdefense.ai.machines

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.{GameObject, Machine}

class WalkForwardTask extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    getObject.asInstanceOf[Machine].walk()
    Status.SUCCEEDED
    // TODO: set state as running until the moving animation is done

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

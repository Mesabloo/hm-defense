package io.github.mesabloo.hmdefense.ai

import com.badlogic.gdx.ai.btree.Task.Status
import com.badlogic.gdx.ai.btree.{LeafTask, Task}
import io.github.mesabloo.hmdefense.app.utils.{GameObject, Target}

class LockAnyTargetNearRangeTask extends LeafTask[GameObject]:
  override def execute(): Task.Status =
    val objs = getObject.objects.filter(obj =>
      obj.isInstanceOf[Target] && obj.getPosition.dst(
        getObject.getPosition
      ) < getObject.range.get._2
    )

    getObject.target(objs.last)
    Status.SUCCEEDED

  override def copyTo(task: Task[GameObject]): Task[GameObject] = task

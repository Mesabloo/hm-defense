package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.Mapper
import kotlin.properties.Delegates

class BodyComponent : Component {
    lateinit var body: Body
    var width by Delegates.notNull<Float>()
    var height by Delegates.notNull<Float>()

    companion object: Mapper<BodyComponent>()
}
package fr.mesabloo.heavymachdefense.fr.mesabloo.heavymachdefense.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.Mapper

class BodyComponent : Component {
    lateinit var world: World
    lateinit var body: Body

    companion object: Mapper<BodyComponent>()
}
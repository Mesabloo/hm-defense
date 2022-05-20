package fr.mesabloo.heavymachdefense.systems.rendering.debug

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World

class DebugWorldSystem(private val world: World, private val camera: Camera): EntitySystem() {
    private val debugRenderer = Box2DDebugRenderer()

    override fun update(deltaTime: Float) {
        super.update(deltaTime)
        
        this.debugRenderer.render(this.world, this.camera.combined)
    }
}
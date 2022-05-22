package fr.mesabloo.heavymachdefense.internal

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable

class Batcher : Disposable {
    private val batch: SpriteBatch = SpriteBatch()
    private val queue: MutableList<Pair<Drawable, Int>> = mutableListOf()

    var projectionMatrix: Matrix4
        set(value) { batch.projectionMatrix = value }
        get() = batch.projectionMatrix

    fun draw(sprite: Sprite, z: Int) {
        queue.add(Pair(SpriteDrawable(sprite), z))
    }
    fun draw(texture: Texture, pos: Vector2, z: Int) {
        queue.add(Pair(TextureDrawable(texture, pos), z))
    }
    fun draw(region: TextureRegion, pos: Vector2, z: Int) {
        queue.add(Pair(TextureRegionDrawable(region, pos), z))
    }
    fun draw(font: BitmapFont, message: String, pos: Vector2, z: Int) {
        queue.add(Pair(FontDrawable(font, message, pos), z))
    }

    fun flush() {
        this.batch.begin()

        this.queue.sortBy { it.second }
        for (drawable in queue) {
            drawable.first.draw(this.batch)
        }
        this.queue.clear()

        this.batch.end()
    }

    override fun dispose() {
        this.batch.dispose()
        this.queue.clear()
    }
}
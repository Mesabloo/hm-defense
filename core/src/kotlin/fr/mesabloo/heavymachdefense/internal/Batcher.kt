package fr.mesabloo.heavymachdefense.internal

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable

/**
 * An implementation based on [SpriteBatch] which handles Z-indices.
 *
 * Note: Output is not done to the screen until [flush] is called.
 */
class Batcher : Disposable {
    /**
     * The underlying [SpriteBatch] used to output to the screen.
     */
    private val batch: SpriteBatch = SpriteBatch()

    /**
     * The rendering queue is sorted based on the Z-index stored in the second position of the [Pair].
     * The first component of the [Pair] is a [Drawable] component (which can be drawn on a [SpriteBatch]).
     *
     * Note: This list is not insertion-sorted.
     * It is sorted only when [flush]ing.
     */
    private val queue: MutableList<Pair<Drawable, Int>> = mutableListOf()

    /**
     * Set/get the projection matrix of the underlying [SpriteBatch].
     */
    var projectionMatrix: Matrix4
        set(value) { batch.projectionMatrix = value }
        get() = batch.projectionMatrix

    /**
     * Enqueue a sprite for drawing at the given Z-index.
     *
     * @param sprite The sprite to draw later
     * @param z The Z-index
     */
    fun draw(sprite: Sprite, z: Int) {
        queue.add(Pair(SpriteDrawable(sprite), z))
    }

    /**
     * Enqueue a [Texture] for drawing at the given position and Z-index.
     *
     * @param texture The texture to enqueue for drawing
     * @param pos The position of the bottom-left corner
     * @param z The Z-index
     */
    fun draw(texture: Texture, pos: Vector2, z: Int) {
        queue.add(Pair(TextureDrawable(texture, pos), z))
    }

    /**
     * Enqueue a [TextureRegion] for drawing at the given position and Z-index.
     *
     * @param region The texture region (may come from a [com.badlogic.gdx.graphics.g2d.TextureAtlas]) to draw
     * @param pos The position of the bottom-left corner of the texture
     * @param z The Z-index
     */
    fun draw(region: TextureRegion, pos: Vector2, z: Int) {
        queue.add(Pair(TextureRegionDrawable(region, pos), z))
    }

    /**
     * Enqueue some piece of text to output using the specified [BitmapFont].
     *
     * @param font The font to use when printing the text
     * @param message The string to be output to the screen
     * @param pos The position of the bottom-left corner of the text region
     * @param z the Z-index
     */
    fun draw(font: BitmapFont, message: String, pos: Vector2, z: Int) {
        queue.add(Pair(FontDrawable(font, message, pos), z))
    }

    /**
     * Completely flush the rendering queue and output everything to the screen, in increasing Z-index order.
     */
    fun flush() {
        this.batch.enableBlending()
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
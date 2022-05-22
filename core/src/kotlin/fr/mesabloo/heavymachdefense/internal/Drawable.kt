package fr.mesabloo.heavymachdefense.internal

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

/**
 * An interface indicating anything that can be drawn on a [Batch].
 */
sealed interface Drawable {
    /**
     * Draw the current object onto a [Batch].
     *
     * Care must be taken to call [Batch.begin] before and [Batch.end] after, as they
     * must not be called from within this method.
     *
     * @param batch The [Batch] to output onto
     */
    fun draw(batch: Batch)
}

/**
 * A [Sprite] can be output onto a [Batch] by calling [Sprite.draw].
 *
 * @property sprite The inner sprite to draw onto the [Batch]
 */
class SpriteDrawable(private val sprite: Sprite) : Drawable {
    override fun draw(batch: Batch) {
        sprite.draw(batch)
    }
}

/**
 * A [Texture] can be drawn onto a [Batch] only if its position is also indicated.
 * This wraps a call to [Batch.draw].
 *
 * @property texture The [Texture] to output
 * @property position The position of the bottom-left corner of the [Texture]
 */
class TextureDrawable(private val texture: Texture, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        batch.draw(this.texture, this.position.x, this.position.y)
    }
}

/**
 * As for [Texture]s, a [TextureRegion] can be output onto a [Batch] if its position is also known.
 *
 * @property region The [TextureRegion] to draw
 * @property position The position of its bottom-left corner
 */
class TextureRegionDrawable(private val region: TextureRegion, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        batch.draw(this.region, this.position.x, this.position.y)
    }
}

/**
 * Any string can be output to a [Batch] if it is given a font and a position.
 * This class simply calls [BitmapFont.draw] internally.
 *
 * @property font The [BitmapFont] to use to output the text
 * @property message The text to print
 * @property position The position of the bottom-left corner of the bounding box of the rendered text
 */
class FontDrawable(private val font: BitmapFont, private val message: String, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        this.font.draw(batch, this.message, this.position.x, this.position.y)
    }
}
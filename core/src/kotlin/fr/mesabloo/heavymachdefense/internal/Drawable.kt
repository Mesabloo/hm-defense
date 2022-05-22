package fr.mesabloo.heavymachdefense.internal

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2

interface Drawable {
    fun draw(batch: Batch)
}

class SpriteDrawable(private val sprite: Sprite) : Drawable {
    override fun draw(batch: Batch) {
        sprite.draw(batch)
    }
}

class TextureDrawable(private val texture: Texture, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        batch.draw(this.texture, this.position.x, this.position.y)
    }
}

class TextureRegionDrawable(private val region: TextureRegion, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        batch.draw(this.region, this.position.x, this.position.y)
    }
}

class FontDrawable(private val font: BitmapFont, private val message: String, private val position: Vector2) : Drawable {
    override fun draw(batch: Batch) {
        this.font.draw(batch, this.message, this.position.x, this.position.y)
    }
}
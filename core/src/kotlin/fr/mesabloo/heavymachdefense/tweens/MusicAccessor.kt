package fr.mesabloo.heavymachdefense.tweens

import aurelienribon.tweenengine.TweenAccessor
import com.badlogic.gdx.audio.Music

class MusicAccessor<T : Music> : TweenAccessor<T> {
    companion object {
        const val FADE_IN = 0
        const val FADE_OUT = 1
    }

    override fun getValues(target: T, tweenType: Int, returnValues: FloatArray): Int = when (tweenType) {
        FADE_IN, FADE_OUT -> {
            returnValues[0] = target.volume
            1
        }
        else -> TODO("unreachable")
    }

    override fun setValues(target: T, tweenType: Int, newValues: FloatArray) = when (tweenType) {
        FADE_IN, FADE_OUT -> {
            target.volume = newValues[0]
        }
        else -> TODO("unreachable")
    }
}
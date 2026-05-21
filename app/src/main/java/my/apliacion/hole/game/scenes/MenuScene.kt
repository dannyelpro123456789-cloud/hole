package my.apliacion.hole.game.scenes

import android.opengl.GLES20
import android.os.SystemClock
import my.apliacion.hole.core.graphics.HorrorBackground
import my.apliacion.hole.core.graphics.TextureUtils
import my.apliacion.hole.game.base.Scene
import my.apliacion.hole.game.manager.GameStateManager

class MenuScene : Scene {

    private val background = HorrorBackground()
    private var pasilloTextureId: Int = -1
    private val startTime = SystemClock.uptimeMillis()
    
    private var isStarting = false
    private var fadeAlpha = 1.0f

    override fun update(deltaTime: Float) {
        if (isStarting) {
            fadeAlpha -= deltaTime * 1.5f
            if (fadeAlpha <= 0f) {
                fadeAlpha = 0f
                GameStateManager.startGame()
            }
        }
    }

    override fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        if (pasilloTextureId == -1) {
            pasilloTextureId = TextureUtils.loadTextureFromAssets("pasillo_escuela.jpg")
        }

        val currentTime = (SystemClock.uptimeMillis() - startTime) / 1000f
        
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        
        // Habilitar blending para que el alpha funcione
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        background.draw(pasilloTextureId, currentTime, fadeAlpha)

        GLES20.glDisable(GLES20.GL_BLEND)
    }

    fun onStartTouched() {
        isStarting = true
    }

    override fun onPause() {}
    override fun onResume() {}
}

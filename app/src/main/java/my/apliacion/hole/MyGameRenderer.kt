package my.apliacion.hole

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import my.apliacion.hole.game.manager.GameState
import my.apliacion.hole.game.manager.GameStateManager
import my.apliacion.hole.game.scenes.MenuScene
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGameRenderer : GLSurfaceView.Renderer {

    private val viewMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private var lastTime = SystemClock.uptimeMillis()

    override fun onSurfaceCreated(unused: GL10?, config: EGLConfig?) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glDepthFunc(GLES20.GL_LEQUAL)
        
        // Fondo NEGRO absoluto para el terror
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        
        // Inicializar con la escena del menú
        GameStateManager.setState(GameState.MENU_INICIO, MenuScene())
    }

    override fun onDrawFrame(unused: GL10?) {
        val currentTime = SystemClock.uptimeMillis()
        val deltaTime = (currentTime - lastTime) / 1000f
        lastTime = currentTime

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        GameStateManager.update(deltaTime)
        GameStateManager.render(viewMatrix, projectionMatrix)
    }

    override fun onSurfaceChanged(unused: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height.toFloat()
        Matrix.perspectiveM(projectionMatrix, 0, 45f, ratio, 0.1f, 100f)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
    }
}

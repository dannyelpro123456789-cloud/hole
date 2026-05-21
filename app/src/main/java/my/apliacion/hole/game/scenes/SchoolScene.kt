package my.apliacion.hole.game.scenes

import android.opengl.Matrix
import my.apliacion.hole.core.graphics.FPSCamera
import my.apliacion.hole.game.base.Scene

class SchoolScene : Scene {

    private val camera = FPSCamera(posX = 0f, posY = 1.7f, posZ = 0f)
    private val mvpMatrix = FloatArray(16)
    
    private val sensitivity = 0.12f

    /**
     * Recibe los deltas del touch desde el MainActivity (Thread Bridge)
     */
    fun handleRotation(deltaX: Float, deltaY: Float) {
        camera.rotate(deltaX * sensitivity, -deltaY * sensitivity)
    }

    override fun update(deltaTime: Float) {
        // Lógica de juego futura
    }

    override fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        // Obtenemos la matriz de vista 360 de la cámara
        val currentViewMatrix = camera.getViewMatrix()

        // Aquí se multiplicaría la matriz de proyección por la de vista
        // para renderizar los modelos 3D de la escuela coreana.
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, currentViewMatrix, 0)
    }

    override fun onPause() {}
    override fun onResume() {}
}

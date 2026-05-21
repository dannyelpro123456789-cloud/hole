package my.apliacion.hole.core.graphics

import android.opengl.Matrix
import kotlin.math.cos
import kotlin.math.sin

class FPSCamera(
    var posX: Float = 0f,
    var posY: Float = 1.7f, // Altura de los ojos del niño
    var posZ: Float = 5f
) {
    var yaw: Float = -90f   // Horizontal
    var pitch: Float = 0f   // Vertical

    private val viewMatrix = FloatArray(16)

    fun rotate(deltaYaw: Float, deltaPitch: Float) {
        yaw += deltaYaw
        pitch += deltaPitch

        // Limitamos el cabeceo para no romper el cuello del personaje (Gimbal lock)
        if (pitch > 89f) pitch = 89f
        if (pitch < -89f) pitch = -89f
    }

    fun getViewMatrix(): FloatArray {
        // Trigonometría para calcular el vector de dirección (Hacia dónde mira)
        val yawRad = Math.toRadians(yaw.toDouble()).toFloat()
        val pitchRad = Math.toRadians(pitch.toDouble()).toFloat()

        val dirX = cos(pitchRad) * cos(yawRad)
        val dirY = sin(pitchRad)
        val dirZ = cos(pitchRad) * sin(yawRad)

        Matrix.setLookAtM(
            viewMatrix, 0,
            posX, posY, posZ,              // Cámara
            posX + dirX, posY + dirY, posZ + dirZ,  // Objetivo (Cámara + Dirección)
            0f, 1f, 0f                     // Vector Up (Eje Y arriba)
        )
        return viewMatrix
    }
}

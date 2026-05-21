package my.apliacion.hole

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import my.apliacion.hole.core.graphics.TextureUtils
import my.apliacion.hole.game.manager.GameState
import my.apliacion.hole.game.manager.GameStateManager

class MainActivity : AppCompatActivity() {

    private lateinit var gLView: GLSurfaceView
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar utilidades con el contexto
        TextureUtils.init(this)

        gLView = GLSurfaceView(this).apply {
            setEGLContextClientVersion(2)
            setRenderer(MyGameRenderer())
        }

        val root = FrameLayout(this)
        root.addView(gLView)

        // Botón con estética VHS (Rojo, sin fondo, parpadeante)
        val startButton = Button(this).apply {
            text = "START"
            setTextColor(0xFFFF0000.toInt()) // Rojo Sangre
            setBackgroundColor(0) // Sin fondo (transparente)
            textSize = 36f
            typeface = android.graphics.Typeface.MONOSPACE // Fuente tipo terminal/retro
            stateListAnimator = null // Quitar sombras de botón moderno
            
            // Animación de parpadeo estilo señal VHS inestable (más lenta/atmosférica)
            val flicker = android.animation.ObjectAnimator.ofFloat(this, "alpha", 0.3f, 1.0f).apply {
                duration = 800 // Parpadeo más lento
                repeatMode = android.animation.ValueAnimator.REVERSE
                repeatCount = android.animation.ValueAnimator.INFINITE
                start()
            }

            setOnClickListener {
                flicker.cancel() // Detener parpadeo al pulsar
                animate().alpha(0f).setDuration(400).withEndAction {
                    visibility = View.GONE
                    gLView.queueEvent {
                        GameStateManager.onMenuTouch()
                    }
                }
            }
        }

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER
        )
        root.addView(startButton, params)

        setContentView(root)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x: Float = event.x
        val y: Float = event.y

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (GameStateManager.currentState != GameState.MENU_INICIO) {
                val deltaX = x - previousX
                val deltaY = y - previousY

                gLView.queueEvent {
                    GameStateManager.onInputRotation(deltaX, deltaY)
                }
            }
        }

        previousX = x
        previousY = y
        return true
    }

    override fun onResume() { super.onResume(); gLView.onResume() }
    override fun onPause() { super.onPause(); gLView.onPause() }
}

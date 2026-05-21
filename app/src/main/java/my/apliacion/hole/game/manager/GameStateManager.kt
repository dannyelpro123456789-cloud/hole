package my.apliacion.hole.game.manager

import my.apliacion.hole.game.base.Scene

enum class GameState {
    MENU_INICIO,
    INTRO_CAPITULO,
    GAMEPLAY_DIA,
    PESADILLA_NOCHE,
    PANTALLA_GAMEOVER
}

object GameStateManager {
    var currentState: GameState = GameState.MENU_INICIO
        private set

    private var currentScene: Scene? = null

    fun onMenuTouch() {
        (currentScene as? my.apliacion.hole.game.scenes.MenuScene)?.onStartTouched()
    }

    fun setState(newState: GameState, scene: Scene) {
        currentScene?.onPause()
        currentState = newState
        currentScene = scene
        currentScene?.onResume()
    }

    fun update(deltaTime: Float) {
        currentScene?.update(deltaTime)
    }

    fun render(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        currentScene?.draw(viewMatrix, projectionMatrix)
    }

    fun startGame() {
        if (currentState == GameState.MENU_INICIO) {
            setState(GameState.GAMEPLAY_DIA, my.apliacion.hole.game.scenes.SchoolScene())
        }
    }

    fun onInputRotation(deltaX: Float, deltaY: Float) {
        (currentScene as? my.apliacion.hole.game.scenes.SchoolScene)?.handleRotation(deltaX, deltaY)
    }
}

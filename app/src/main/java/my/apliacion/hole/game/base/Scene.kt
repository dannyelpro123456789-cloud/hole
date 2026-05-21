package my.apliacion.hole.game.base

interface Scene {
    fun update(deltaTime: Float)
    fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray)
    fun onPause()
    fun onResume()
}

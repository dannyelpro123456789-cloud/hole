package my.apliacion.hole.game.base

interface GameObject {
    fun update(deltaTime: Float)
    fun draw(mvpMatrix: FloatArray)
}

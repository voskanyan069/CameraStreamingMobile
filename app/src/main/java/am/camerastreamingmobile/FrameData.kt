package am.camerastreamingmobile

object FrameData {
    // Default size can be overridden
    private var width: Double = 640.0
    private var height: Double = 480.0
    private var size: Int = (width * height).toInt()

    fun setFrameInfo(width: Double, height: Double) {
        this.width = width
        this.height = height
        this.size = (width * height).toInt()
    }

    fun getWidth(): Double {
        return width
    }

    fun getHeight(): Double {
        return height
    }

    fun getFrameSize(): Int {
        return size
    }
}
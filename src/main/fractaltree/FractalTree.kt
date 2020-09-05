package fractaltree

import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// https://rosettacode.org/wiki/Fractal_tree

const val ANGLE_DELTA = PI * 0.1

class FractalTree : JFrame("Fractal Tree") {
    init {
        background = Color.black
        foreground = Color.white
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
        setBounds(100, 100, 800, 600)
        isVisible = true
    }

    private fun tree(g: Graphics, x: Int, y: Int, angle: Double, depth: Int) {
        if (depth == 0) return

        val x2 = x + (cos(angle) * depth * 10).toInt()
        val y2 = y + (-sin(angle) * depth * 10).toInt()

        g.drawLine(x, y, x2, y2)

        tree(g, x2, y2, angle - ANGLE_DELTA, depth - 1)
        tree(g, x2, y2, angle + ANGLE_DELTA, depth - 1)
    }

    override fun paint(g: Graphics) {
        val x = this.bounds.width / 2
        val y = this.bounds.height - 100
        tree(g, x, y, PI / 2, 9)
    }
}

fun main(args: Array<String>) {
    FractalTree()
}
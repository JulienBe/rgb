package hacknslash.rgb.general.particles

import com.badlogic.gdx.utils.NumberUtils
import ktx.collections.GdxArray

enum class GColorGradient(val colors: GdxArray<Float>) {

    ENERGY(ColorBuilder.buildArray(
            0.90f, 0.25f, 0.25f, 0.40f,
            0.15f, 0.00f, 0.00f, 0.05f,
            0.98f, 0.98f, 0.98f, 0.98f
    ))

}

object ColorBuilder {
    fun buildArray(
            rHigh: Float, gHigh: Float, bHigh: Float, aHigh: Float,
            rLow: Float, gLow: Float, bLow: Float, aLow: Float,
            rMul: Float, gMul: Float, bMul: Float, aMul: Float): GdxArray<Float> {

        var r = rHigh
        var g = gHigh
        var b = bHigh
        var a = aHigh
        val colors = GdxArray<Float>()

        while (r >= rLow && g >= gLow && b >= bLow && a >= aLow) {
            colors.add(convert(r, g, b, a))
            r *= rMul
            g *= gMul
            b *= bMul
            a *= aMul
        }
        println("size gradient " + colors.size)
        return colors
    }

    fun convert(r: Float, g: Float, b: Float, a: Float): Float {
        val intBits = (255 * a).toInt() shl 24 or ((255 * b).toInt() shl 16) or ((255 * g).toInt() shl 8) or (255 * r).toInt()
        return NumberUtils.intToFloatColor(intBits)
    }
}
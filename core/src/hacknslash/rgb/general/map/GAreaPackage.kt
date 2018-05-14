package hacknslash.rgb.general.map

enum class GAreaPackage(val generate: (width: Int) -> GArea) {
    SMALL_NORMAL_ROOMS({width -> GArea.getRandom(5, 10, width)}),
    MEDIUM_NORMAL_ROOMS({width -> GArea.getRandom(10, 30, width)}),
    MEDIUM_GUASS_ROOMS({width -> GArea.getGauss(7, 20f, width)}),
    BIG_GUASS_ROOMS({width -> GArea.getGauss(4, 50f, width)}),
    HUGE_GUASS_ROOMS({width -> GArea.getGauss(4, 80f, width)})
}
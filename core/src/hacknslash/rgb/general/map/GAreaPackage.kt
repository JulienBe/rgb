package hacknslash.rgb.general.map

enum class GAreaPackage(val generate: () -> GArea) {
    MEDIUM_NORMAL_ROOMS({GArea.getRandom(10, 30)}),
    MEDIUM_GUASS_ROOMS({GArea.getGauss(7, 20f)})
}
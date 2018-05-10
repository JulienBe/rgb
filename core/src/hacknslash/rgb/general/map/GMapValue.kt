package hacknslash.rgb.general.map

enum class GMapValue(val i: Int) {
    WALL(0b0000_0001),
    EMPTY(0b0000_0010),
    ROOM(0b0000_0100)
}
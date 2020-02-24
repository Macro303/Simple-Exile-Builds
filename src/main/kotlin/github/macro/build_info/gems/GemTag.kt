package github.macro.build_info.gems

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class GemTag {
    WARCRY,
    CHAOS,
    AOE,
    DURATION,
    ACTIVE,
    SUPPORT,
    COLD,
    LIGHTNING,
    FIRE,
    TRAP,
    ATTACK,
    TOTEM,
    MELEE,
    SPELL,
    AURA,
    MINION,
    CHAINING,
    PROJECTILE,
    BRAND,
    CURSE,
    TRIGGER,
    BOW,
    CHANNELLING,
    MOVEMENT,
    TRAVEL,
    BLINK,
    HERALD,
    GUARD,
    MINE,
    GOLEM,
    VAAL,
    PHYSICAL,
    STRIKE,
    NOVA;

    companion object {
        fun value(name: String): GemTag? = values().firstOrNull { it.name.equals(name, ignoreCase = true) }
    }
}
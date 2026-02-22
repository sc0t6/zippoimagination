# The Poop Mod

A Minecraft Spigot plugin that adds poop-related gameplay mechanics with various effects.

**Server Type:** Spigot
**Minecraft Version:** 1.21.11
**Java Version:** 21

## Features

### 💩 Throwable Poop
- Throw poop at targets like a snowball
- Inflicts **Nausea II** effect for 15 seconds on hit
- Perfect for pranking your friends!

### 🟤 Placeable Poop Block
- Place poop on the ground as a hazard
- Players walking through it will be **slowed down** and receive **Nausea**
- Effects last for **5 seconds**
- Drops the custom item when broken

### 🤢 Slimy Poop
- Drinkable poop variant (why though?)
- Inflicts **Poison I** for 10 seconds and **Nausea** for 15 seconds when consumed
- Returns an empty glass bottle after drinking

## Commands

### `/poopmenu` (aliases: `/poopgui`, `/pm`)
Opens a GUI menu displaying all available poop items. Click on items to receive them.
- **Permission:** `poopmod.menu` (default: all players)

### `/givepoop <player> <type> [amount]`
Give poop items to players (admin only)
- **Usage:** `/givepoop Steve throwable 16`
- **Types:** `throwable`, `placeable`, `slimy`
- **Permission:** `poopmod.admin` (default: ops)

## Installation

1. **Download** or build the plugin JAR file
2. **Place** the JAR in your server's `plugins/` directory
3. **Restart** your server
4. **Use** `/poopmenu` to access items!

## Building from Source

Requires: **Java 21** and **Gradle**

```bash
# Clone the repository
git clone https://github.com/sc0t6/zippoimagination.git
cd zippoimagination

# Build the plugin
./gradlew build

# The plugin JAR will be in: build/libs/ThePoopMod-1.0.0.jar
```

## Permissions

- `poopmod.menu` - Access to the poop menu (default: true)
- `poopmod.admin` - Admin commands for giving items (default: op)

## Technical Details

### Item Implementation
- **Throwable Poop:** Base item is `SNOWBALL` with custom name, lore, and model data
- **Poop Block:** Base block is `BROWN_MUSHROOM_BLOCK` with tracking system
- **Slimy Poop:** Base item is `HONEY_BOTTLE` with custom properties

All items have enchant glow effects and detailed lore descriptions.

### Custom GUI
Since Spigot doesn't support custom creative tabs, this plugin provides a custom inventory GUI as an alternative. The `/poopmenu` command opens a brown-themed menu where players can click to receive items.

## Support

For issues or questions, please open an issue on GitHub.

---

**Have fun with poop! 💩** (Responsibly, of course)

# Zippo's Imagination
![](https://img.shields.io/github/downloads/sc0t6/thepoopmod/total)
![](https://img.shields.io/github/license/sc0t6/thepoopmod)

Broken Humour approved inside Minecraft Java 1.21.x (Spigot)

> [!CAUTION]
> This mod is still in beta, which means that there will be bugs and some incompatibilities.

This mod provides what I have imagined and some stupid stuff that basically sucked in general but could also be funny for some people.

## Features

<details>
 <summary>Poop Mod Category</summary>

### Throwable Poop
- Throw poop at targets
- Inflicts Nausea effect on hit

### Placeable Poop
- Place poop on the ground
- Players walking through it will be slowed down
- Inflicts Nausea for 5 seconds

### Slimy Poop
- Drinkable poop variant
- Inflicts Poison and Nausea effects when consumed

### Player Poop
- By holding shift with empty hand for 3 seconds, it poops out the placeable poop on the block behind you
- 10 second cooldown between poops

</details>

<details>
 <summary>Variation Ray Guns</summary>

### Shrink Ray
- Makes you 0.5 size
- Ability to make other players/entities small too
- Right-click to shrink yourself
- Left-click entity to shrink target

### Grow Ray
- Makes you 1.5 size
- Ability to make other players/entities big too
- Right-click to grow yourself
- Left-click entity to grow target

### Normal Ray
- Obviously makes you normal size (1.0x)
- Works on yourself and other entities

### Ray Gun
- A weapon (duh)
- Does incredible damage (15 ❤) to people
- Requires ammo. To get it you need a nether star and 2 eye of enders (craft in menu)
- 50 block range with laser beam effects

</details>

<details>
 <summary>Cameras</summary>

### Camera
- A normal camera
- You can take screenshots (saves to .minecraft/screenshots)
- Right-click to trigger screenshot
- Flash particle effect on capture

### Security Cameras
- A camera you can place in houses, bases etc.
- Can be placed and viewed from
- Able to place up to 7 before it's the maximum
- Teleport to camera locations to view

### Tablet
- Able to see the security cameras by linking them with this item
- Right-click to open camera menu
- Teleport to any of your 7 security cameras
- View camera locations and coordinates

</details>

<details>
 <summary>Eatables</summary>

**How to eat:** Right-click on blocks to eat them!

**List:**
- **Soil** (Dirt, Grass, etc.) - *Gives you a bit of nausea*
- **Snow** (Snow, Snow Blocks) - *Nausea*
- **Sand** (Sand, Red Sand) - *Nausea*
- **Dust** (Gravel) - *Nausea*
- **Flowers** - *Nausea after 3 eaten*
- **Leaves** - *Nausea after 6-10 eaten (random threshold)*

</details>

## Getting Items

### Option 1: Crafting (Survival Mode) ⚒️
All items are **fully craftable**! Check out [CRAFTING_GUIDE.md](CRAFTING_GUIDE.md) for all recipes.
- Items appear in vanilla recipe book
- Balanced recipes for survival gameplay

### Option 2: Menu Command 📦
Use `/poopmenu` for instant access to all items!
- Great for creative mode or testing
- No gathering required

## Commands

### `/poopmenu` (aliases: `/poopgui`, `/pm`)
Opens the main menu displaying all available items. Click on items to receive them.
- **Permission:** `poopmod.menu` (default: all players)
- **Best for:** Creative mode or quick testing

### `/givepoop <player> <type> [amount]`
Give items to players (admin only)
- **Usage:** `/givepoop Steve throwable 16`
- **Types:** `throwable`, `placeable`, `slimy`
- **Permission:** `poopmod.admin` (default: ops)

## Installation

### For Server Owners
1. Download the latest plugin JAR from releases
2. Place it in your server's `plugins/` directory
3. Restart your server
4. **(Optional)** Extract and distribute the resource pack for custom models
5. Players can craft items or use `/poopmenu`!

### For Players
1. Download the plugin JAR from releases (or get from server)
2. **(Optional)** Install the resource pack for custom item models
3. Join a server running the plugin
4. **Craft items** (see [CRAFTING_GUIDE.md](CRAFTING_GUIDE.md)) or use `/poopmenu`

### Custom Models (Optional) 🎨
Want custom textures for items? See [RESOURCE_PACK_GUIDE.md](RESOURCE_PACK_GUIDE.md) for:
- How to extract the resource pack from the JAR
- Adding your own 16x16 textures
- Creating 3D models with Blockbench
- Installing the resource pack

## Building from Source

**Requires:** Java 21 and Gradle

```bash
# Clone the repository
git clone https://github.com/sc0t6/thepoopmod.git
cd thepoopmod

# Build the plugin
./gradlew build

# The plugin JAR will be in: build/libs/ThePoopMod-1.0.0.jar
```

## Technical Details

**Server Type:** Spigot/Paper
**Minecraft Version:** 1.21.3+
**Java Version:** 21
**Resource Pack Format:** 34 (Minecraft 1.21+)

### Features
✅ **Crafting System** - All items craftable in survival mode
✅ **Recipe Book** - Recipes appear in vanilla recipe book
✅ **Custom Models** - Resource pack support with CustomModelData
✅ **Size Manipulation** - Uses `Attribute.SCALE` (1.21.2+)
✅ **Server-Side Only** - No client mods required (resource pack optional)

### Item Implementation
- All custom items use vanilla materials with custom metadata
- Items have enchant glows and `CustomModelData` for resource pack support
- Security cameras use Observer blocks with location tracking
- Ray guns use `Attribute.SCALE` for size manipulation (1.21.2+)
- Crafting recipes integrate with vanilla recipe book
- 11 craftable items + eatables system

## F.A.Q.

### Will this ever be backported?
Answer: **Most likely yes**

### What do I do if there's a bug?
Answer: **Make an issue on this github** or **send me a text on discord** (mc.zippo) for the bug

### Will this be on other mod loaders?
Answer: **Maybe.** It will be VERY hard but I will try to. If you're a developer you can maybe help me :)

### How can I get the items and use the mod?
Answer: **Three ways!**
1. **Craft them** in survival mode (see [CRAFTING_GUIDE.md](CRAFTING_GUIDE.md))
2. Use **/poopmenu** command (creative or survival)
3. Use **/givepoop** admin command

All recipes appear in the vanilla recipe book!

### Is there gonna be more items/blocks/entities?
Answer: **Yes.** Sometimes though the updates will just be bug fixes or some improvements.

### Why Spigot instead of Fabric/Forge?
Answer: This is a **server-side plugin** that works on Spigot/Paper servers. Some features like cameras and screenshots have workarounds since Spigot can't do client-side rendering. For full camera functionality (videos, zoom), check out client-side mods like **Zoomify**.

## Support

For issues or questions, please:
- Open an issue on GitHub
- Contact mc.zippo on Discord

---

**Server Type:** Spigot Plugin
**Have fun breaking Minecraft with weird stuff! 💩🔫📸**

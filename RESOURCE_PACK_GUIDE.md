# Resource Pack & Custom Models Guide

## Overview
This plugin includes built-in support for custom item models using Minecraft's resource pack system. The models are embedded in the plugin JAR.

## Quick Start

### For Server Owners
1. Build the plugin: `./gradlew build`
2. The JAR contains the resource pack in `src/main/resources/`
3. Extract the resource pack or create your own textures
4. Players must install the resource pack client-side

### For Players
1. Get the resource pack from your server admin
2. Place it in `.minecraft/resourcepacks/`
3. Enable it in Minecraft: Options → Resource Packs
4. Restart Minecraft
5. Join the server and use `/poopmenu` to get items

## Custom Model Data Values

All items use `CustomModelData` for model switching:

### Poop Category (1000-1999)
- **1001** - Throwable Poop (Snowball base)
- **1002** - Poop Block (Brown Mushroom Block base)
- **1003** - Slimy Poop (Honey Bottle base)

### Ray Guns (2000-2999)
- **2001** - Shrink Ray (Blaze Rod base)
- **2002** - Grow Ray (Blaze Rod base)
- **2003** - Normal Ray (Blaze Rod base)
- **2004** - Ray Gun Weapon (Golden Horse Armor base)
- **2005** - Ray Gun Ammo (Prismarine Shard base)

### Cameras (3000-3999)
- **3001** - Camera (Tripwire Hook base)
- **3002** - Security Camera (Observer base)
- **3003** - Tablet (Map base)

## Creating Custom Textures

### Required Files
Place 16x16 PNG textures in:
```
src/main/resources/assets/zippoimagination/textures/item/
```

### Texture Names
- `throwable_poop.png`
- `poop_block.png`
- `slimy_poop.png`
- `shrink_ray.png`
- `grow_ray.png`
- `normal_ray.png`
- `ray_gun.png`
- `ray_gun_ammo.png`
- `camera.png`
- `security_camera.png`
- `tablet.png`

### After Adding Textures
1. Rebuild: `./gradlew build`
2. Extract resource pack from JAR
3. Distribute to players

## Model Structure

### Base Item Overrides
Located in: `src/main/resources/assets/minecraft/models/item/`

Each base item (snowball, blaze_rod, etc.) has overrides that check for `custom_model_data` and redirect to custom models.

### Custom Models
Located in: `src/main/resources/assets/zippoimagination/models/item/`

These reference textures in the `zippoimagination` namespace.

## Advanced: 3D Models

To create 3D models instead of flat textures:

1. Use **Blockbench** to create 3D models
2. Export as `.json` model files
3. Replace the model files in `assets/zippoimagination/models/item/`
4. Update texture references in the models

## Testing

### In-Game Testing
1. Get items via `/poopmenu`
2. Items should display custom models
3. If not, check:
   - Resource pack is enabled
   - F3 shows resource pack loaded
   - Textures exist in correct location

### Without Resource Pack
Items still work perfectly! They just display as vanilla items (snowball, blaze rod, etc.) but keep all custom functionality.

## Resource Pack Format

- **Pack Format:** 34 (Minecraft 1.21+)
- **Namespace:** `zippoimagination`
- **Compatible:** Minecraft Java 1.21+

## Distribution

### Option 1: Server-Side Hosting
Host the resource pack and add to `server.properties`:
```properties
resource-pack=https://yourserver.com/zippoimagination.zip
resource-pack-sha1=<hash>
```

### Option 2: Manual Distribution
Share the resource pack ZIP file with players for manual installation.

### Option 3: Embedded (Current)
Resource pack is embedded in plugin JAR - players must extract it manually.

## Troubleshooting

### Models Not Showing
- ✅ Check resource pack is enabled in-game
- ✅ Verify pack format is 34 (for 1.21+)
- ✅ Ensure textures exist in correct directory
- ✅ Check CustomModelData matches in code

### Missing Textures
- ✅ Add placeholder textures (see TEXTURES_README.md)
- ✅ Or use vanilla textures temporarily

### Recipe Book Integration
Since this is a Spigot plugin, items won't appear in creative tabs. Use:
- `/poopmenu` command for all items
- Crafting recipes (if added)
- `/givepoop` command (admin)

## Support

For help with custom models:
- [Minecraft Wiki - Resource Packs](https://minecraft.wiki/w/Resource_pack)
- [Blockbench](https://www.blockbench.net/) - 3D modeling tool
- Report issues on GitHub

---

**Pro Tip:** Start with simple flat textures, then upgrade to 3D models later!

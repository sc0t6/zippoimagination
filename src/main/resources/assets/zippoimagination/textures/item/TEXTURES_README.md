# Custom Textures Guide

Place your custom 16x16 PNG textures in this directory with these exact names:

## Poop Category
- `throwable_poop.png` - Throwable poop projectile
- `poop_block.png` - Placeable poop block
- `slimy_poop.png` - Drinkable slimy poop

## Ray Guns
- `shrink_ray.png` - Shrink ray gun (blue/aqua theme)
- `grow_ray.png` - Grow ray gun (green theme)
- `normal_ray.png` - Normal ray gun (gray theme)
- `ray_gun.png` - Weapon ray gun (red theme)
- `ray_gun_ammo.png` - Energy cell ammo

## Cameras
- `camera.png` - Handheld camera
- `security_camera.png` - Security camera block
- `tablet.png` - Tablet device

## Texture Requirements
- **Format:** PNG
- **Size:** 16x16 pixels recommended
- **Transparency:** Alpha channel supported
- **Naming:** Must match exactly (case-sensitive)

## How to Use
1. Create your textures as 16x16 PNG files
2. Place them in this directory
3. Build the plugin with `./gradlew build`
4. The resource pack will be embedded in the JAR
5. Extract and share the resource pack with players

## Recommended Tools
- **Aseprite** - Pixel art editor
- **GIMP** - Free image editor
- **Paint.NET** - Simple editor for Windows
- **Blockbench** - 3D modeling (for advanced models)

## Default Behavior
Without custom textures, items will display as their base vanilla items (snowball, blaze rod, etc.) but will still have all custom functionality.

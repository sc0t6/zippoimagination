#!/usr/bin/env python3
"""
Texture Generator for Zippo's Imagination
Generates 16x16 placeholder textures for all custom items
"""

try:
    from PIL import Image, ImageDraw, ImageFont
except ImportError:
    print("ERROR: Pillow library not found!")
    print("Install it with: pip install Pillow")
    print("Or: python -m pip install Pillow")
    exit(1)

import os

# Define texture colors and patterns
TEXTURES = {
    # Poop Category - Brown tones
    'throwable_poop': {
        'color': (101, 67, 33),  # Brown
        'accent': (139, 90, 43),  # Light brown
        'symbol': '💩'
    },
    'poop_block': {
        'color': (92, 64, 51),  # Dark brown
        'accent': (120, 85, 60),  # Medium brown
        'symbol': '▓'
    },
    'slimy_poop': {
        'color': (76, 187, 23),  # Slime green
        'accent': (101, 67, 33),  # Brown
        'symbol': '~'
    },

    # Ray Guns - Colored by type
    'shrink_ray': {
        'color': (85, 255, 255),  # Cyan/Light blue
        'accent': (0, 191, 255),  # Deep sky blue
        'symbol': '↓'
    },
    'grow_ray': {
        'color': (50, 205, 50),  # Lime green
        'accent': (34, 139, 34),  # Forest green
        'symbol': '↑'
    },
    'normal_ray': {
        'color': (169, 169, 169),  # Gray
        'accent': (105, 105, 105),  # Dim gray
        'symbol': '='
    },
    'ray_gun': {
        'color': (220, 20, 60),  # Crimson red
        'accent': (178, 34, 34),  # Fire brick
        'symbol': '⚡'
    },
    'ray_gun_ammo': {
        'color': (218, 112, 214),  # Orchid/Purple
        'accent': (138, 43, 226),  # Blue violet
        'symbol': '◆'
    },

    # Cameras - Tech colors
    'camera': {
        'color': (192, 192, 192),  # Silver
        'accent': (70, 70, 70),  # Dark gray
        'symbol': '📷'
    },
    'security_camera': {
        'color': (64, 64, 64),  # Dark gray
        'accent': (255, 0, 0),  # Red (recording light)
        'symbol': '●'
    },
    'tablet': {
        'color': (70, 130, 180),  # Steel blue
        'accent': (25, 25, 112),  # Midnight blue
        'symbol': '▭'
    },
}

def create_texture(name, data, size=16):
    """Create a 16x16 texture with pattern"""
    img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    color = data['color']
    accent = data['accent']

    # Create gradient background
    for y in range(size):
        blend = y / size
        r = int(color[0] * (1 - blend) + accent[0] * blend)
        g = int(color[1] * (1 - blend) + accent[1] * blend)
        b = int(color[2] * (1 - blend) + accent[2] * blend)
        draw.line([(0, y), (size, y)], fill=(r, g, b, 255))

    # Add border
    border_color = (0, 0, 0, 180)
    draw.rectangle([0, 0, size-1, size-1], outline=border_color, width=1)

    # Add some detail pixels for texture
    detail_color = (*accent, 200)
    # Top-left highlight
    draw.point((1, 1), fill=(255, 255, 255, 100))
    draw.point((2, 1), fill=(255, 255, 255, 80))
    draw.point((1, 2), fill=(255, 255, 255, 80))

    # Bottom-right shadow
    draw.point((size-2, size-2), fill=(0, 0, 0, 100))
    draw.point((size-3, size-2), fill=(0, 0, 0, 80))
    draw.point((size-2, size-3), fill=(0, 0, 0, 80))

    # Add pattern based on item type
    if 'poop' in name:
        # Poop items get scattered dots
        for i in range(3, size-3, 3):
            for j in range(3, size-3, 3):
                draw.point((i, j), fill=detail_color)
    elif 'ray' in name:
        # Ray guns get horizontal lines
        for y in range(4, size-4, 3):
            draw.line([(3, y), (size-3, y)], fill=detail_color, width=1)
    elif 'camera' in name or 'tablet' in name:
        # Cameras get a center square
        center = size // 2
        draw.rectangle([center-2, center-2, center+2, center+2],
                      outline=detail_color, width=1)

    return img

def main():
    """Generate all textures"""
    # Get the texture output directory
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_dir = os.path.join(script_dir, 'src', 'main', 'resources',
                              'assets', 'zippoimagination', 'textures', 'item')

    # Create directory if it doesn't exist
    os.makedirs(output_dir, exist_ok=True)

    print("=" * 60)
    print("  Zippo's Imagination - Texture Generator")
    print("=" * 60)
    print()
    print(f"Output directory: {output_dir}")
    print()
    print("Generating textures...")
    print()

    # Generate all textures
    generated = []
    for name, data in TEXTURES.items():
        output_path = os.path.join(output_dir, f"{name}.png")

        # Create texture
        img = create_texture(name, data)

        # Save with optimization
        img.save(output_path, 'PNG', optimize=True)

        generated.append(name)
        print(f"  ✓ Created: {name}.png")

    print()
    print("=" * 60)
    print(f"  Success! Generated {len(generated)} textures")
    print("=" * 60)
    print()
    print("Next steps:")
    print("  1. Build the plugin: ./gradlew build")
    print("  2. Extract resource pack from JAR")
    print("  3. Distribute to players!")
    print()
    print("Note: These are placeholder textures. You can replace them")
    print("      with custom 16x16 PNG files for better quality!")
    print()

if __name__ == "__main__":
    main()

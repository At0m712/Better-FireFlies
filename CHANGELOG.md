# 📋 Changelog - Better FireFlies (BFF)

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.1.0] - 2026-09-12

### 🌟 Added
- **Firefly in a Jar (Luciole en Bocal)**:
  - New 3D custom block and item inspired by lanterns.
  - Can be placed on blocks or hung from ceilings (`hanging=true/false`).
  - Supports waterlogging (`WATERLOGGED`).
  - Emits light level 12.
  - Features 1 to 3 living, animated 2D firefly particles fluttering inside the clear glass.
- **In-World Bottle Capture**:
  - Right-click wild fireflies with an empty Glass Bottle to capture them into a Firefly Jar.
  - Client-to-server network synchronization (`CatchFireflyPayload`) ensuring reliable item acquisition.
- **Crafting Table Recipes**:
  - 3 recipes available: Wooden Slab + Glass Bottle + (Torch / Glowstone Dust / Glow Berries).
- **4 Biome Color Variants**:
  - 🌲 **Forest**: Lime green (`#BAF533`)
  - 🍂 **Swamp & Mangrove**: Warm amber orange (`#FFB733`)
  - 🌿 **Lush Caves & Jungle**: Ethereal cyan turquoise (`#33F5D5`)
  - 🌸 **Cherry Grove**: Soft sakura pink (`#FF85C8`)
- **Synchronized Swarm Breathing**:
  - Whole swarms harmonize their bioluminescent flashing rhythm in organic waves.
- **Official Mod Icon**:
  - New pixel-art voxel logo integrated into `fabric.mod.json`, assets, and documentation.
- **Persistent Configuration**:
  - Added `config/firefly.json` saving settings across game restarts (`maxFireflies`, `spawnChance`, `enableDynamicLight`, `enableParticles`).
- **Translations**:
  - Full English (`en_us.json`) and French (`fr_fr.json`) localization for all blocks, items, entities, and commands.
- **PublishMods Automation**:
  - Integrated `me.modmuss50.mod-publish-plugin` configured for automatic publishing to CurseForge (Project ID: `1629782`) and Modrinth (Project ID: `WpcP0H59`).

### ⚡ Optimized & Fixed
- **Zero Chunk Lag Dynamic Lights**:
  - Replaced continuous world block re-tessellation with clean, throttled ground lighting and full LambDynamicLights/Sodium/Iris shader support.
- **Memory Leak Resolution**:
  - Replaced static `globalFireflyCount` with dynamic AABB entity queries, allowing smooth, continuous spawning without entity exhaustion.
- **Universal Biome Support**:
  - Switched to Minecraft `BiomeTags` (`IS_FOREST`, `IS_JUNGLE`, etc.), enabling seamless compatibility with *Terralith*, *Biomes O' Plenty*, and *Regions Unexplored*.
- **Cave Spawning**:
  - Underground caverns and Lush Caves now spawn fireflies regardless of surface day/night cycle.
- **Hitbox & Targeting**:
  - Adjusted firefly hitbox to `0.35 x 0.35` and enabled `isPickable()`, making wild fireflies comfortably clickable in mid-air.
- **Item Model Architecture**:
  - Added Minecraft 1.21.4+ `assets/firefly/items/firefly_jar.json` definition alongside custom 2D item sprite.

### 🧹 Removed
- Removed 550+ MB of unneeded test runs, world saves, region files (`*.mca`), and debug playerdata from version control.
- Removed template boilerplate (`MixinMinecraft`, `MixinTitleScreen`, `com.example` packages).
- Cleaned root screenshots into `docs/images/`.

---

## [1.0.0] - 2026-08-01

### Added
- Initial release of Better FireFlies with 3D models and ambient flight AI.

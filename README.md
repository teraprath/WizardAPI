# WizardAPI
 Custom Spells & Wands inspired by Wynncraft
> ℹ️ Plugin has been tested on **Paper/Purpur: 1.21, 1.21.1**

## Features
- Simple & lightweight
- Create **Custom Spells** (Click-Combinations: L-R-L, R-L-R, etc.)
- **Bind** spells to **Items** (Wands)
> ⚠️ This project is in development. Some features may not work correctly.

### Upcoming
- Built-in Mana System
- PlaceholderAPI Support

## Example Usage
```java

// Create new wand
Wand wand = new Wand("custom_wand", new ItemStack(Material.STICK));

// Create custom spell
Spell spell = new Spell(player -> {

    // Spell Logic

}, Spell.ClickType.LEFT, Spell.ClickType.RIGHT, Spell.ClickType.RIGHT); // Combination: L-R-R

// Bind spell to wand
wand.bind(spell);

// Register wand
WizardAPI.getInstance().register(wand);
```
Visit [wiki](https://github.com/teraprath/WizardAPI/wiki/) page to see usage guide.

# WizardAPI
 Custom Spells & Wands for Paper 1.21+
> :warning: Project is currently in development and not ready for production.

## Features
- Simple & lightweight
- Easily add custom spells using a click combination (R-L-R, L-R-L, etc.)
- Bind spells to Wands

## Example Usage
```java

// Create new wand
Wand wand = new Wand("custom_wand", new ItemStack(Material.STICK));

// Create custom spell
Spell spell = new Spell(player -> {

    // Spell Logic

}, Spell.ClickType.LEFT, Spell.ClickType.RIGHT, Spell.ClickType.RIGHT);

// Bind spell to wand
wand.bind(spell);

// Register wand
WizardAPI.getInstance().register(wand);
```
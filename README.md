# WizardAPI
 Custom Spells & Wands for Paper 1.21+
> :warning: Project is currently in development and not ready for production.

## Features
- Simple & lightweight
- Easily add custom spells using a click combination (R-L-R, L-R-L, etc.)
- Bind spells to Wands
- Built-in Mana System (Optionial)

## Example Usage
```java

// Create new wand instance
Wand wand = new Wand("TestWand", new ItemStack(Material.STICK));

// Create custom spell
Spell spell = new Spell(player -> {
    player.sendMessage("L-R-R");
}, Spell.ClickType.LEFT, Spell.ClickType.RIGHT, Spell.ClickType.RIGHT);

// Bind spell to wand
wand.addSpell(spell);

// Register wand
WizardAPI.getInstance().registerWand(wand);
```

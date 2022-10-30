![logo](https://lab.deepcore.dev/decduck3/kleicreator/-/raw/master/src/resources/dstguimodcreatorlogo.png)
# KleiCreator
Hello! If you're someone who wants to try this out for me, thanks! Here's the stuff you need to know:
 - It's written in Java. That means you need Java installed to run the program (Java 16)
 - It is still in very early stages. I've done a decent amount of work on it so far, but as of the moment, you cannot export and use the tool.
 - I am open to all and any suggestions, but I am mainly looking for thoughts about the interface, things that could be better, things that you liked and want to see more of, etc. (Also new name cause this one kinda sucks)

### How to run
In the releases [(here)](https://lab.deepcore.dev/decduck3/kleicreator/-/releases), there should be a .jar download. That should contain everything necessary to run the program right off the bat. Currently, it only supports Windows and Linux, but should run on Mac (haven't tested it).

### How it works
Upon starting the program, you should see a dialog that has some buttons that do fairly obvious things like make a new mod, and delete mods, etc.

Once you've made a mod by filling out the Create Mod box, you are presented with the actual mod editor. There are several tabs that all do different stuff
 - Main Config: has name, description, long description, version, author and icon.
 - Items: creates, deletes and edits items
 - Recipes: creates, deletes and edits recipes (duh)
 - Characters: (grayed out, haven't done anything on that) 
 - Resources: import and manage .tex and .xml files
 - Speech: basically nothing, but shows only speech files (but you have to make them in the resources tab)
 - Export: save all and export (does nothing)

#### Main Config
Self explanatory. Nothing complicated here.
#### Items
This is where it gets a bit tricky. Each item has the following properties: Name, id, texture and components:
 - Name: Anything you want. Shown in the game
 - Id: all lowercase and underscores instead of spaces. Used in the code
 - Texture: what the item looks like
 - Components: split into two trees, Not Added and Added. To add or remove a component, double click on it. To edit a component select the dropdown and double the properties. 

(Make sure the item is selected in the dropdown in the top right)
#### Recipes
Works similar to items. Create and select the recipe, and double click anything to edit it.

 - Id (at the very top): The resulting item's id. Example: if you want to make a recipe for nightmare fuel, it would be "nightmarefuel"
 - Ingredients. Double click on "Add" to add a ingredient (again, the id). Double click on an ingredient to delete it
 - Workstation: What workstation required to craft it. Example, for a Science Machine it would be "researchlab" (More ids)
 - Tag: A limiting tag, used for character specific crafts. 
#### Resources
Has the ability to create/import textures, animations or speech files. 
 - Textures: First select the .text file, then the .xml file
 - Speech: Specific the item name and the defaults for the first entry, also the type of speech (Character is simply useless)
 - Animation: Pick a .zip that has the animation
#### Speech
Shows only the speech resources, their location and how many entries they have. 
#### Export
Does nothing (at the moment)

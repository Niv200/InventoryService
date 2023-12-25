# InventoryService
A microservice used to manage items and stocks


#### In a nutshell
In a nutshell, I become frustrated by the amount of parts and screws Im ordering for my projects,\
to combat the issue, I decided that I want to index every item and order that I make so that if Il need\
some parts in the future I can just look up and see if I have them in stock or that I need to order new ones.

###How does it work?
The microservice has three main endpoints and objects

/types
/items 
/boxes

###### Types:
A 'type' is a general category for a group of similar items.\
Types can be created/deleted/modified via HTTP request to the endpoint.

A few examples:
Type: Fasteners, which groups all items that are related to that category such as screws, bolts, nuts, spacers and shims.\
Type: Wires, which will group all wire related items, such as insulated copper wire, enamel wire and heatshrinks.

###### Items:
An item is a record that holds a single inventory entry, such as screw of X size, or wire of thickness L with quantity of Z pcs/meters.\
in order to create an item record, one must first define the relevant 'type' of the item (Look above).\
Each item holds a quantity, name, location (where the item is stored), and a few more optional fields.

###### Boxes:
Whats boxes you may ask?
A boxes is a set of items of type T, where all the items are stored within the box.

In order to create box, one shall first define the type and items and only then group all of the items into the box

example of where this would be relevant:
Box of M3 screws with lengths 10mm, 12mm, 16mm, 20mm.



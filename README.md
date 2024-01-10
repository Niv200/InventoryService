# InventoryService
A microservice used to manage items and stocks
The service is based on Java framework Spring boot, and SQL database

#### In a nutshell
In a nutshell, I become frustrated by the amount of parts and screws Im ordering for my projects,\
to combat the issue, I decided that I want to index every item and order that I make so that if Il need\
some parts in the future I can just look up and see if I have them in stock or that I need to order new ones.

### How does it work?
The microservice has three main resource and a total of 11 endpoints.

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

##### Usage

There are in total 11 endpoints

**Types /types** 
get - return list of all currently existing types
post - create a new type\
put - edit existing type\
delete - delete an existing type, path variable 'type' is the name of the type to be deleted.
where {?} is a request param
```
/types
  GET - Retrieve a list of all types
  POST - Create a new type

/types/{type}
  DELETE - Delete an existing type with the specified type parameter

/types
  PUT - Update an existing type
```

**Items /items** 
```/items
  GET - Retrieve a list of all items
  POST - Create a new item

/items/{id}
  GET - Retrieve information about a specific item by its ID
  PUT - Update information for a specific item by its ID
  DELETE - Delete an item by its ID

/items/{id}/quantity
  PATCH - Update the quantity of a specific item by its ID 
```

**Boxes /box** 
```/box
GET - Retrieve a list of all boxes
POST - Create a new box

/box/{id}
GET - Retrieve information about a specific box by its ID
DELETE - Delete a box by its ID
PUT - Update information for a specific box by its ID

/box/{id}
POST - Manage items within a specific box by its ID
```

###### Bodies for each request:
POST /types 
```
TypeRequest {
    "type": "exampleType"
}
```
PUT /types
```
TypeModifyRequest {
    "oldType": "existingType",
    "type": "updatedType"
}
```
POST /items (create item)
```
ItemRequest {
    "name": "exampleItem",
    "type": "exampleType",
    "quantity": 10,
    "properties": "exampleProperties",
    "location": "exampleLocation",
    "extraProperties": "exampleExtraProperties",
    "resourcePath": "exampleResourcePath"
}
```
PUT /items/{id} (edit items)
```
ItemRequest {
    "name": "updatedItemName",
    "type": "updatedItemType",
    "quantity": 20,
    "properties": "updatedProperties",
    "location": "updatedLocation",
    "extraProperties": "updatedExtraProperties",
    "resourcePath": "updatedResourcePath"
}
```
PATCH /items/{id}/quantity (change item quantity)
```
ItemQuantityRequest {
    "id": 123,
    "change": "ADD",
    "number": 5
}
```
POST /box (create new box)
```
BoxRequest {
    "name": "exampleBox",
    "contents": "exampleContents",
    "location": "exampleLocation",
    "extraInformation": "exampleExtraInformation",
    "resourcePath": "exampleResourcePath"
}
```
PUT /box/{id} (edit box items or info)
```
BoxRequest {
    "name": "updatedBoxName",
    "contents": "updatedContents",
    "location": "updatedLocation",
    "extraInformation": "updatedExtraInformation",
    "resourcePath": "updatedResourcePath"
}
```
POST /box/{id} (modify existing box item count)
```
BoxItemRequest {
    "itemId": 456,
    "action": "ADD" --ADD/REMOVE
}
```
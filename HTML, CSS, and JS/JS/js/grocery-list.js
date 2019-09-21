/*
    Create a function that will add the item entered into the input 
    field into the Grocery list.
*/
function addItem() {

    // Create variable to hold the value of the input field (#new-item)
    let newItemField = document.getElementById('new-item');
    let newItem = newItemField.value;

    // Create variable to hold the DOM object which represents the ul (#list-items)
    let groceryList = document.getElementById('list-items');

    // Perform some validation to ensure that the input field value is not an empty string
    if(newItem) {
        // Create a new list item
        let newListItem = document.createElement('li');

        // Set the text of the newly created list item to the value provided by user
        newListItem.innerText = newItem;

        // Append the list item to the #list-items ul.
        groceryList.appendChild(newListItem);

        // Clear the input field for future values
        newItemField.value = '';
    }

}

// Add event listeners to the DOM elements that require them
document.getElementById('add-item').addEventListener('click', addItem);

/*
    Add an event listener to our Grocery List, which listens for a click event
    on any of its list items. When this occurs, the targeted list item should
    be removed from the Grocery List and added to the Purchased List
*/
document.getElementById('list-items').addEventListener('click', (e) => {

    // Obtain the target of the click event from the event object
    let eventTarget = e.target;

    // Determine if the tag name of the event target is a list item
    if(eventTarget.tagName === 'LI') {

        // Target the Purchased List
        let purchasedList = document.getElementById('purchased-items');

        // Add the event target to the Purchased List
        purchasedList.appendChild(eventTarget);
    }
});

document.getElementById('purchased-items').addEventListener('click', e => {
    if(e.target.tagName === 'LI') {
        document.getElementById('list-items').appendChild(e.target);
    }
});
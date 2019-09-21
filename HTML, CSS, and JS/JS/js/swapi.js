/*
    Asynchronous JavaScript And XML

        AJAX is NOT a programming language, library, or framework - it is a technique
        used to access web servers from a web page using JavaScript.

        It allows for us to "asynchronously" send a request and process the response.
        While our page is awaiting the response from the server, our web page remains
        functional and does not stop just because we are awaiting information from 
        the server.
*/
window.onload = function() {

    // Add an EL to the ID_FIELD and SELECT_FIELD for validating input
    ID_FIELD.addEventListener('blur', fieldsValid);
    SELECT_FIELD.addEventListener('blur', fieldsValid);

    // Add an EL to the SUBMIT_BTN to send an HTTP request to the API
    SUBMIT_BTN.addEventListener('click', awaitInfo);

    // Disable the SUBMIT_BTN
    SUBMIT_BTN.setAttribute('disabled', true);

    // Hide the alert message (manipulating CSS to hide it)
    ALERT_MSG.style.display = 'none';
    // ALERT_MSG.setAttribute('hidden', true);

}

function fieldsValid() {
    console.log('in fieldsValid()...');

    let id = ID_FIELD.value;
    let category = SELECT_FIELD.value;

    if(id && category) {
        console.log('fields are valid!');
        ALERT_MSG.style.display = 'none';
        SUBMIT_BTN.removeAttribute('disabled');
    } else {
        console.log('fields are not valid!');
        ALERT_MSG.style.display = 'block';
        SUBMIT_BTN.setAttribute('disabled', true);
    }
}

function getInfo() {
    console.log('in getInfo()...');

    // Get the values of the input and select fields
    let id = ID_FIELD.value;
    let category = SELECT_FIELD.value;

    /*
        Determine what is being searched for (people, planets, or starships) and
        create the appropriate HTML results container.
    */
   createResultsContainer(category);

    // Make our AJAX request to retrieve the requested data

    /*
        AJAX Workflow

            Making asynchronous calls to an external web server using AJAX:

                1) Create an XMLHttpRequest (XHR) object
                2) Open the request by defining the HTTP method/verb and the target server URL
                3) Send the request (which may contain data in the request body) to the web server
                *4*) Define a callback function which will handle the response when received
    */

    // Step 1
    let xhr = new XMLHttpRequest();

    // Step 2
    xhr.open('GET', `https://swapi.co/api/${category}/${id}`, true);

    // Step 3
    xhr.send();

    // Step 4
    xhr.onreadystatechange = function () {

        /*
            The XMLHttpRequest.readyState property returns the state an XHR object/client
            is currently in. An XHR object/client exists in one of the following states:

                0 - UNSENT (the XHR object has been created, but .open() has not been called)
                1 - OPENED (.open() has been called)
                2 - HEADERS_RECEIVED (.send() has been called, and the response headers and status code are available)
                3 - LOADING (beginning to receive the response body, xhr.responseText() holds partial data)
                4 - DONE (the operation is complete, and the response is fully received)


        */
       console.log(`The current ready state is: ${xhr.readyState}`);

       if(xhr.readyState === 4 && xhr.status === 200) {
           if(category === 'people') {
               let jedi = JSON.parse(xhr.responseText);
               processPeopleResponse(jedi);
           } else if(category === 'planets') {
            //    processPlanetResponse(xhr);
           } else if (category === 'starships') {
            //    processStarshipResponse(xhr);
           }
       }
    }

}

function processPeopleResponse(jedi) {
    document.getElementById('name').innerText = `Name: ${jedi.name}`;
    document.getElementById('height').innerText = `Height (cm): ${jedi.height}`;
    document.getElementById('mass').innerText = `Mass (kg): ${jedi.mass}`;
    document.getElementById('birth-year').innerText = `Birth Year: ${jedi.birth_year}`;
    document.getElementById('gender').innerText = `Gender: ${jedi.gender}`;
}

function createResultsContainer(category) {
    console.log('in createResultsContainer()...');

    // If there is anything already in the #results-container DIV, remove it
    while(RESULTS_CONTAINER.firstChild) {
        RESULTS_CONTAINER.removeChild(RESULTS_CONTAINER.firstChild);
    }

    if(category === 'people') {

        // Create the DOM elements that will be added to the results container
        let nameContainer = document.createElement('h3');
        let heightContainer = document.createElement('h5');
        let massContainer = document.createElement('h5');
        let birthYearContainer = document.createElement('h5');
        let genderContainer = document.createElement('h5');

        // Add ID attributes to each of the newly created DOM elements
        nameContainer.setAttribute('id', 'name');
        heightContainer.setAttribute('id', 'height');
        massContainer.setAttribute('id', 'mass');
        birthYearContainer.setAttribute('id', 'birth-year');
        genderContainer.setAttribute('id', 'gender');

        // Attach the DOM elements to the #results-container DIV
        RESULTS_CONTAINER.appendChild(nameContainer);
        RESULTS_CONTAINER.appendChild(heightContainer);
        RESULTS_CONTAINER.appendChild(massContainer);
        RESULTS_CONTAINER.appendChild(birthYearContainer);
        RESULTS_CONTAINER.appendChild(genderContainer);
    }
}


/*
    Fetch API

        Fetch is a browser API for loading text, images, structured data, etc. asynchronously
        to update an HTML page. Similar to AJAX, in fact AJAX is leveraged "under the hood",
        but Fetch is built upon the JS Promise object which greatly simplifies our code.
*/

function fetchInfo() {

    console.log('in fetchInfo()...');

    // Get values of the input and select fields
    let id = ID_FIELD.value;
    let category = SELECT_FIELD.value;

    // Create the appropriate results-container children based on the category
    createResultsContainer(category);

    let responsePromise = fetch(`https://swapi.co/api/${category}/${id}`);

    console.log(responsePromise);

    responsePromise.then(response => {
        console.log(response);

        if(category === 'people') {
            response.json().then(data => {
                processPeopleResponse(data);
            });
        }
    }).catch(err => {
        console.log('Something went wrong!');
        console.log(err);
    });

    /*
        JS Promises

            The Promise object represents the eventual completion (or failure) of an
            asynchronous operation, and its resulting value. Using the .then() method
            we can process a successful* response, otherwise we can use the .catch()
            method to handle a failed request. Promises return one thing per request,
            which could be the data/resource requested or some error.
    */
}

async function awaitInfo() {
    
    console.log('in awaitInfo()...');

    let id = ID_FIELD.value;
    let category = SELECT_FIELD.value;
    createResultsContainer(category);

    let response = await fetch(`https://swapi.co/api/${category}/${id}`);
    console.log(response);

    let data = await response.json();
    processPeopleResponse(data);

}


const ID_FIELD = document.getElementById('sw-id');
const SELECT_FIELD = document.getElementById('sw-category');
const SUBMIT_BTN = document.getElementById('submit-btn');
const SW_FORM = document.getElementById('sw-form');
const RESULTS_CONTAINER = document.getElementById('results-container');
const ALERT_MSG = document.getElementById('alert-msg');
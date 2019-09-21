// Single line comments

/*
 *  Multi-line
 *  comments!
 */

/**
 * Documentation comments
 */



/*
    JavaScript Basics

        - The language of the Internet
        - Interpreted (not compiled)
        - Loosely-typed
        - Single-threaded (look into the event loop and callbacks)
        - used to make webpages more interactive and dynamic
*/

/*
    Data Types

        - string
        - number
        - boolean
        - object
            + function
            + array
        - null
        - undefined
        - NaN
*/

let variable = 'color';
console.log(typeof(variable));  // prints 'string'

variable = 5;
console.log(typeof(variable));  // prints 'number'

variable = 3 > 5;
console.log(typeof(variable));  // prints 'boolean'

// JS has no distinction between integers and floating point values
variable = 3.141519;
console.log(typeof(variable));  // prints 'number' 

variable = [2, 3, 5, 7, 11];
console.log(typeof(variable));  // prints 'object' 

variable = {
    username: 'tester',
    password: 'test-password'
}
console.log(typeof(variable));  // prints 'object' 
console.log(variable.username);     // dot notation to access object members
console.log(variable["username"]);  // bracket notation to access object members

variable = function () {
    let x = 4;
    console.log('did some stuff');
}
console.log(typeof(variable));  // prints 'function'

let oops;
variable = oops;
console.log(typeof(variable));  // prints 'undefined'

variable = 10 / 0;
console.log(variable);
console.log(typeof(variable));  // prints 'number'

variable = 10 / 'oops';
console.log(variable);
console.log(typeof(variable));  // prints 'number' (yes, NaN is a number...)

//------------------------------------------------------------------------------

// Flow control is not too much different than it is in Java (similar syntax)

let today = new Date();
let currentHour = today.getHours();
let greeting;

if(currentHour > 18) {
    greeting = 'Good evening!';
} else if(currentHour > 12) {
    greeting = 'Good afternoon!';
} else if(currentHour > 0) {
    greeting = 'Good morning';
} else {
    greeting = 'Welcome';
}

// We can use the document object to inject HTML into out web page
document.write('<h3>' + greeting + '</h3>');

/*
    Template Literals

        Template literals are strings that are enclosed within the back-tick
        characters (``) instead of single/double quotes. Template literals can
        contain placeholder values, which can be "interpolated" when rendered 
        on the screen. These placeholder values are indicated by a dollar sign ($)
        followed by a pair of curly brackets ({}), which contain a variable reference
        or expression. Additionally, whitespace that is included within template 
        literals is preserved.
*/
document.write(`<h3>${greeting}</h3>`); // variable references can be passed and interpolated
document.write(`<h3>${50 > 25}</h3>`); // expressions too!

/*
    Arrays

        Arrays work differently in JS than they do in Java. For instance, arrays 
        in JS are dynamically sized, also they have quite a few more useful methods
        that exist on them.
*/

console.log(typeof(today));
console.log(typeof(currentHour));
let myNumArray = [1, 9, 2, 8, 3, 7, 4, 6, 5];
for(let i = 0; i < myNumArray.length; i++) {
    console.log(myNumArray[i]);
}

// However in JS, arrays can store values of varying data types
let myCrazyArray = [1, 'no', 3.141519, new Date(), function() {console.log('whoa!')}, false, null, undefined, 4/'hi'];
for(let i = 0; i < myCrazyArray.length; i++) {
    console.log(myCrazyArray[i]);
}

console.log('+-----------------------------+');

// Pre-ES6 syntax for providing a callback function to a method
myCrazyArray.forEach(function (val) {
    console.log(typeof(val));
});

// Post-ES6 syntax for providing a callback function to a method (arrow notation)
myCrazyArray.forEach(val => {
    console.log(typeof(val));
});

// JS arrays are dynamically sized
myCrazyArray.push('hello!');

//-------------------------------------------------------------------------

// Function Declarations

function getArea(width, height) {
    return width * height;
}

let myArea = getArea(5, 10); // this is an implict (direct) function call
console.log(myArea);    // prints 50

myArea = getArea('5', 10);
console.log(myArea); // prints 50 (type coercion!)

myArea = getArea('five', 10);
console.log(myArea); // NaN

// A brief tangent on type coercion
console.log(7 - 7 + '7');
console.log('7' + 7 + 7);
console.log(7 + '7' + 7);

function getSize(width, height, depth) {
    let area = getArea(width, height);
    let volume = area * depth;
    return [area, volume];
}

let result = getSize(7, 5, 10);
myArea = result[0];
let myVolume = result[1];

console.log(getSize(4, 10, 45));

/*
    Anonymous Functions

        Expressions produces a value. They can be used where values are expected. If a
        function is placed where the JS engine expects to see an expression, then the
        function is simply treated as such. A function with no explicitly defined name
        is called an anonymous function, and cannot be directly (implicitly) invoked.
*/

let area = function(width, height) {
    return width * height;
}

console.log(`Function expression - area: ${area}`);
console.log(`Invoking area: ${area(5, 10)}`);

let xx = area;
console.log(xx(5, 40));

/*
    Immediately-Invoked Function Expressions (IIFEs)

        Anonymous functions that are executed as soon as the interpreter comes across
        them are known as IIFEs, or immediately-invoked function expressions. IIFEs are
        used when some code only needs to be executed once, usually within some other
        task, rather than being repeatedly called and invoked by other parts of our script.

        For example:

            - As an argument when a function is called
            - To assign the value of a property to an object
            - In event handlers and listeners, to perform a task when an event occurs
            - To prevent conflicts between scripts that might use the same variable names
*/

let a = 1; // this is visible in this scope as well as more specific (lower) scopes

let someArea = (function(width, height) {
    console.log('This should execute when the interpreter first sees this declaration.');
    console.log(a); // visible!
    return width * height;
})(5, 20);

console.log(someArea);

let otherArea = function() {
    console.log(this);
    
    console.log('This will not be automatically executed, we need to invoke it ourselves.');
    console.log(a); // visible!
    return 5 * 20;
}

console.log(otherArea);

//-------------------------------------------------------

/*
    var vs let vs const

        var

            Before ES6, the var keyword was the primary way to declare a variable in JS.
            Variables declared using var are allowed to be reassigned, and their scope
            can be either global or local (function) level - depending on where the
            variable is first declared.

        let

            Since ES6, the let keyword has been the preferred way to declare variables
            in JS. Variables declared using let are also able to be reassigned, however
            their scope is restricted to the block in which the variable is declared as
            well as lower scopes (but not upper scopes!).

        const

            Introduced in ES6, and the meaning is likely obvious. The purpose of const
            is to declare a variable that cannot be changed/reassigned after its 
            initialization. Variables declared using const are scoped using the same
            rules as let.
            
*/

// Does not work! Variables declared using const cannot be reassigned
// const MEANING_OF_LIFE = 42;
// MEANING_OF_LIFE = 43;

// Does not work! Variables declared using const must have an initial value
// const PI;
// PI = 3.14;

function test1() {

    // undefined (the declaration of x is hoisted to the top of the function scope)
    console.log(x);     

    if(true) {
        var x = 5;
        let y = 10;
        const z = 20;
    }

    console.log(x);     // 5
    // console.log(y);     // throw ReferenceError
    // console.log(z);     // throw ReferenceError
}

test1();

/*
    Hoisting

        The JS interpreter does not execute scripts sequentially (line by line), 
        but instead whenever a script enters into a new execution context, there 
        are two main phases of activity:
    
            1) Prepare
                - The new scope(s) are created
                - Variable and function declarations are created
        
            2) Execute
                - Assign value to the previous declared variables
                - Reference functions and run their code if invoked
                - Execute statements/expressions
        
            Understand these two phases help us to understand the concept 'hoisting'. 
            Because of this feature of JS, we can:

            - Call functions before they have been declared (if they were created 
                using function declarations and not function expression).

            - Assign a value to a variable on a line before its declaration 
                (depending on the declarative keyword used - only var)
*/

function hoist_test1() {
    var x;
    x = 6;
    console.log(x); // 6
}

function hoist_test2() {
    var x;
    console.log(x); // undefined
    x = 6;
}

function hoist_test3() {
    console.log(x); // undefined
    var x = 6;
}

function hoist_test4() {
    x = 6;
    console.log(x); // 6
    var x;
}

function hoist_test5() {
    console.log(x); // throw ReferenceError
    let x = 6;
}

function hoist_test6() {
    console.log(x);
    x = 6;
}

/* 
    using var, the declaration "hoist_test8" is visible (b/c it attaches to 
    the window object), but has no value
*/
// hoist_test8(); 
var hoist_test8 = function() {
    console.log(x);
    var x = 6;
}

/*
    using let, the declaration "hoist_test8" not visible
*/
// hoist_test9();
let hoist_test9 = function() {
    console.log(x);
    var x = 6;
}

function hoist_test10() {
    // console.log(x); // throw ReferenceError

    function innerFunction() {
        console.log(x); // undefined
        var x = 6;
        console.log(x); // 6
    }

    // console.log(x); // throw ReferenceError
}

hoist_test10();

//-----------------------------------------------------

/*
    Objects

        Objects group together a set of variables and functions (methods) to create
        a model of a real world concept. In object, variables become known as
        properties/fields and functions are known as methods. We have a few ways that
        we can declare objects in JS.
*/

// Object Creation: Object Literals
let hotel = {
    name: 'Marriot',
    rooms: 180,
    booked: 162,

    available() {
        return this.rooms - this.booked;
    }
}

let myHotel = {
    name: 'Ritz',
    rooms: 300,
    booked: 150,

    available: function() {
        return this.rooms - this.booked;
    }
}

document.write('<h2>' + hotel.name + '</h2>');
document.write(`<ul>
                    <li>Total Rooms: ${hotel.rooms}</li>
                    <li>Booked Rooms: ${hotel.booked}</li>
                    <li>Available Rooms: ${hotel.available()}</li>
                </ul>`);

// Object Creation: Constructor Notation
let newHotel = new Object();
newHotel.name = 'Four Seasons';
newHotel.rooms = 300;
newHotel.booked = 190;
newHotel.available = function() {
    return this.rooms - this.booked;
}

document.write('<h2>' + newHotel.name + '</h2>');
document.write(`<ul>
                    <li>Total Rooms: ${newHotel.rooms}</li>
                    <li>Booked Rooms: ${newHotel.booked}</li>
                    <li>Available Rooms: ${newHotel.available()}</li>
                </ul>`);

// Object Creation: Function Object Creation (AKA Functional Classes)
function Hotel(name, rooms, booked) {
    this.name = name;
    this.rooms = rooms;
    this.booked = booked;
    this.available = function() {
        return this.rooms - this.booked;
    }
}

let yourHotel = new Hotel('Holiday Inn', 100, 75);

document.write('<h2>' + yourHotel.name + '</h2>');
document.write(`<ul>
                    <li>Total Rooms: ${yourHotel.rooms}</li>
                    <li>Booked Rooms: ${yourHotel.booked}</li>
                    <li>Available Rooms: ${yourHotel.available()}</li>
                </ul>`);

// The Prototype property
function Fruit(type) {
    this.type = type;
    this.color = 'unknown';
}

Fruit.prototype.getInformation = function() {
    return `This ${this.type} is ${this.color}`;
}

let eggplant = new Fruit('Eggplant');
eggplant.color = 'purple';
console.log(eggplant);
console.dir(eggplant);

console.log(eggplant.getInformation());

// Object Creation: ES6 Class Syntax
class Polygon {

    constructor(height, width) {
        this.height = height;
        this.width = width;
    }

}

let myPolygon = new Polygon(2, 4);
console.dir(myPolygon);

// If arguments are not provided, then they will recieve a value of 'undefined'
myPolygon = new Polygon(4);
console.dir(myPolygon);

let yourTriangle = class {
    constructor(base, height) {
        this.base = base;
        this.height = height;
    }
}

console.log(yourTriangle);
console.log(new yourTriangle(4,10));

// Subclassing using ES6 class syntax
class Animal {
    static x = 5;
    constructor(name) {
        this.name = name;
    }

    speak() {
        console.log(this);
        console.log(this.name + ' speaks...');
    }
}

class Dog extends Animal {
    constructor(name) {
        super(name);
    }

    speak() {
        console.log(this.name + ' barks...');
    }

    // declaring methods in this way actually creates a closure (more on that later)
    wagTail = () => {
        console.log(this);
        console.log('The good boy is wagging his tail');
    }

}

let spot = new Dog('Spot');
spot.speak();
spot.wagTail();
console.log(Animal.x);

let fido = new Animal('Fido');
fido.speak();

/*
    Closures

        Closures are functions that are nested within other functions, and are an
        effective way of employing encapsulation in JS. The encapsulated variables
        will be hidden from outside callers, so that they cannot be easily accessed
        directly by other parts of the script.
*/

// Create some counter
let counter = 0;

function incrementCounter() {
    counter += 1;
}

incrementCounter();
incrementCounter();
incrementCounter();

counter = 6;
console.log(counter);

function incrementCounter_v2() {
    let myCounter = 0;
    myCounter += 1;
    return myCounter;
}

console.log(incrementCounter_v2());
console.log(incrementCounter_v2());
console.log(incrementCounter_v2());

function incrementCounter_v3() {
    let muhCounter = 0;

    return function() {
        muhCounter += 1;
        return muhCounter;
    }
}

let bigCount = incrementCounter_v3();
console.log(bigCount());
console.log(bigCount());
console.log(bigCount());

console.dir(bigCount);
console.log(bigCount());
console.dir(bigCount);


//-------------------------------------------------------

function doBurrito() {
    console.log('Burrito done.');
}

function register() {
    console.log('registering...');
}

let registerBtn = document.getElementById('register-btn');
registerBtn.addEventListener('click', register);

registerBtn.addEventListener('mouseover', function() {
    console.log('an anonymous function tied to the mouseover event on register-btn')
});

document.getElementById('login-btn').addEventListener('click', () => {
    console.log('logging in...')
});


//--------------------------------------------------------------------------

// Spread operator
function sum(x, y, z) {
    return x + y + z;
}

const numbers = [1, 2, 3, 4];

let sumResult = sum(...numbers); // spreads out the values of the array into the parameters list
console.log(sumResult);


// Destructuring operator
var j, k, rest;
[j, k] = [10, 20];

console.log(j);
console.log(k);

[j, k, ...rest] = [10, 20, 30, 40, 50];
console.log(rest);
console.log(typeof(rest));

let obj = {
    p: 42,
    q: true
}

let {p, q} = obj;
console.log(p);
console.log(q);

//----------------------------------------------------------

/*
    Type Coercion

        If you use a data type that the JS interpreter did not expect, it will
        try to make sense of the operation rather than report an error. JS can 
        convert data types behind the scenes to complete the operation.
*/

let z = 1 + 1 + '1';
console.log(z); // '21'

z = '1' + 1 + 1;
console.log(z); // '111'

let y = (1 == '1');
console.log(y); // true

y = (1 === '1');
console.log(y); // false

console.log(0 == '0');  // true
console.log(0 == '');   // but why?! (psst...because of truthy/falsy values)
console.log(0 == '      '); // true
console.log('' == ' '); // false
console.log(NaN == null); // false

/*
    Truthy and Falsy

        Every value in JS can be treated as if it were true or false; this has
        some interesting applications (guard & default operators)

        Falsy values:
            - undefined
            - null
            - false
            - NaN
            - 0
            - ''

        Truthy values:
            - everything that is not falsy
                + {}
                + []
                + '      '
*/

let yourValue;
console.log('yourValue: ' + yourValue);

if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

yourValue = '';
if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

yourValue = 10 / 'hi';
if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

yourValue = null;
if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

yourValue = {};
if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

yourValue = 10 / 0;
if(yourValue) {
    console.log('truthy');
} else {
    console.log('falsy');
}

let wat = 10 / 'hi';
console.log(wat);
console.log(wat == wat); // false
console.log(NaN == NaN); // wat? (ok...it's due to IEEE 754 standard)
console.log(NaN === NaN); // at least they're consistent...
console.log(null == null); // true
console.log(undefined == undefined); // true


//--------------------------------------------

// Arrow Notation
let elements = [
    'Hydrogen',
    'Helium',
    'Lithium',
    'Beryllium'
];

let elementalLength_1 = elements.map(function(element) {
    return element.length;
});

console.log(elementalLength_1)

let elementalLength_2 = elements.map(el => {
    return el.length;
});

console.log(elementalLength_2);

let elementalLength_3 = elements.map(el => el.length);

console.log(elementalLength_3);


function errorTest() {
    throw new Error('A message for this error.');
}

try {
    errorTest();
} catch {
    console.log('oops. error caught, doe.')
}

class CustomError extends Error {
    constructor() {
        super('custom message here');
    }
}

// probably can be tweaked and written better.
let myError = new CustomError();
console.dir(myError);
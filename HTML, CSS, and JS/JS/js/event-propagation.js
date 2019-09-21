const OUTER_DIV = document.getElementById('outer');
const MIDDLE_DIV = document.getElementById('middle');
const INNER_DIV = document.getElementById('inner');

function innerAlert(e) {
    alert('INNER!');
    console.log(e);

    /*
        e.stopImmediatePropagation()
        
            For this particulare event, no other listeners will be called. Neither
            those attached on the same element, nor those attached on elements which
            will be traversed later (capture phase or in a bubbling phase)
    */
    // e.stopImmediatePropagation();

    /*
        e.stopPropagation()

            Same as e.stopImmediatePropagation, except chained events attached to the
            same target object will still fire off.
    */
    // e.stopPropagation();
    console.log('do i print?');
    console.dir(e.target);
    console.log(e.eventPhase);
    console.dir(e.currentTarget);
}

function middleAlert(e) {
    alert('MIDDLE!');
    console.dir(e.target);
    console.log(e.eventPhase);
    console.dir(e.currentTarget);
}

function outerAlert(e) {
    alert('OUTER!');
    console.dir(e.target);
    console.log(e.eventPhase);
    console.dir(e.currentTarget);
}

function innerPrompt() {
    prompt('INNER PROMPT!');
}

// ALL BUBBLING (w/o specifying a 3rd param, it is false by default)
// Order of alerts: INNER -> MIDDLE -> OUTER
INNER_DIV.addEventListener('click', innerAlert);
// INNER_DIV.addEventListener('click', innerPrompt);
MIDDLE_DIV.addEventListener('click', middleAlert);
OUTER_DIV.addEventListener('click', outerAlert);

//----------------------------------------------------------------------

// ALL CAPTURING
// Order of alerts: OUTER -> MIDDLE -> INNER
// INNER_DIV.addEventListener('click', innerAlert, true);
// MIDDLE_DIV.addEventListener('click', middleAlert, true);
// OUTER_DIV.addEventListener('click', outerAlert, true);

//-----------------------------------------------------------------------

// INNER = bubbling, OUTER & MIDDLE = capturing
// Order of alerts: OUTER -> MIDDLE -> INNER
// INNER_DIV.addEventListener('click', innerAlert, false);
// MIDDLE_DIV.addEventListener('click', middleAlert, true);
// OUTER_DIV.addEventListener('click', outerAlert, true);

//-----------------------------------------------------------------------
// INNER & MIDDLE = bubbling, OUTER = capturing
// Order of alerts: OUTER -> INNER -> MIDDLE
// INNER_DIV.addEventListener('click', innerAlert, false);
// MIDDLE_DIV.addEventListener('click', middleAlert, false);
// OUTER_DIV.addEventListener('click', outerAlert, true);
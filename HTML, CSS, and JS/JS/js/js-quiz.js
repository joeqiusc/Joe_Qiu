window.onload = () => {

    // Dynamically create HTML elements
    let quizDiv = document.createElement('div');
    let resultsDiv = document.createElement('div');
    let buttonDiv = document.createElement('div');
    let submitBtn = document.createElement('button');

    // Set the attributes of the newly created elements
    quizDiv.setAttribute('id', 'quiz');
    resultsDiv.setAttribute('id', 'results');
    buttonDiv.setAttribute('id', 'button-container');
    buttonDiv.style.padding = '2px';
    buttonDiv.style.cssFloat = 'left';
    submitBtn.setAttribute('id', 'submit');
    submitBtn.setAttribute('class', 'btn btn-primary');

    // Add some text to display within the submit btn
    submitBtn.innerText = 'Submit Quiz';

    // Add the newly created elements to the body of the page
    buttonDiv.appendChild(submitBtn);
    document.body.prepend(resultsDiv);
    document.body.prepend(buttonDiv);
    document.body.prepend(quizDiv);

    // Build the quiz when the page loads
    buildQuiz();

    // Add an EL to the submit button for grading
    buttonDiv.addEventListener('mouseover', isQuizValid);
    submitBtn.addEventListener('click', showResults);

    retrieveQuestions();

}

async function buildQuiz() {

    let getQuestionsFunction = await getQuestions();
    let myQuestions = await getQuestionsFunction();

    // Convenience references for our quiz container
    let quizContainer = document.getElementById('quiz');

    // Create am array which can hold some HTML output
    const output = [];

    /*
        Next, we can start building the HTML for each question. We will
        have to loop through the questions array
    */
   myQuestions.forEach((currentQuestion, questionNumber) => {

        // create an array for storing the list of answer choices
        const answers = [];

        // for each available answer, do some logic
        for(letter in currentQuestion.answers) {
            answers.push(`
            <label>
                <input type="radio" name="question-${questionNumber}" value="${letter}" />
                ${letter}: ${currentQuestion.answers[letter]}
            </label>
            <br>
            `);
        }

        // add this question and its answers to the output array
        output.push(`
            <br>
            <div class="question">${currentQuestion.question}</div>
            <div class="answers">${answers.join('')}</div>
        `);

   });

   // combine our output array into one string on HTML and put it on the page
   quizContainer.innerHTML = output.join('');

}

function isQuizValid() {
    
    console.log('in isQuizValid()...');
    
    let submitBtn = document.getElementById('submit');
    let selectedAnswers = document.querySelectorAll('div.answers > label > input[name^="question-"]:checked');
    let myQuestions = document.querySelectorAll('div.question');

    if(selectedAnswers.length != myQuestions.length) {
        submitBtn.setAttribute('disabled', true);
    } else {
        submitBtn.removeAttribute('disabled');
    }
}

async function showResults() {

    let getQuestionsFunction = await getQuestions();
    let myQuestions = await getQuestionsFunction();
    console.log(myQuestions);

    // Convenience references for our HTML elements
    let resultsContainer = document.getElementById('results');

    // Gather user selected answers from the quiz into an array
    let selectedAnswers = document.querySelectorAll('div.answers > label > input[name^="question-"]:checked');

    // Create a variable to keep track of the number correct
    let numCorrect = 0;

    // Loop through the list of quiz questions
    myQuestions.forEach((currentQuestion, questionNumber) => {

        console.log(selectedAnswers);
        let userAnswerLabel = ((selectedAnswers[questionNumber] || {}).parentElement || {});
        let userAnswer = (selectedAnswers[questionNumber] || {}).value;
        
        if(userAnswer === currentQuestion.correctAnswer) {
            numCorrect++;
            (userAnswerLabel.style || {}).color = 'lightgreen';
        } else {
            (userAnswerLabel.style || {}).color = 'red';
        }

    });

    // Calculate the user's score
    let userScore = ((numCorrect / myQuestions.length) * 100).toFixed(2);

    // Display the calculated score onto the page
    resultsContainer.innerText = `${numCorrect} correct out of ${myQuestions.length} (${userScore}%)`;

}

//------------------------------------------------------

async function getQuestions() {

    let myQuestions;

    return async function() {
        if(myQuestions) {
            console.log(myQuestions);
            return myQuestions;
        } else {
            let response = await fetch('https://api.myjson.com/bins/17kvil');
            myQuestions = await response.json();
            console.log(myQuestions);
            return myQuestions;
        }
    }

}

function retrieveQuestions() {

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'https://api.myjson.com/bins/17kvil');

    xhr.send();

    xhr.onreadystatechange = function() {

        if(xhr.readyState === 4 && xhr.status === 200) {
            return JSON.parse(xhr.responseText);
        }
    }
}


// const myQuestions = [
//     {
//         question: '7 + 7 + "7" = ?',
//         answers: {
//             a: '"777"',
//             b: '"147"',
//             c: 21
//         },
//         correctAnswer: 'b'
//     },
//     {
//         question: 'What is the inherent boolean value of an empty object?',
//         answers: {
//             a: 'true',
//             b: 'false',
//             c: 'undefined'
//         },
//         correctAnswer: 'a'
//     },
//     {
//         question: 'What is the inherent boolean value of an empty array?',
//         answers: {
//             a: 'true',
//             b: 'false',
//             c: 'null'
//         },
//         correctAnswer: 'a'
//     },
//     {
//         question: 'typeof(NaN) = ?',
//         answers: {
//             a: 'NaN',
//             b: 'number',
//             c: 'undefined'
//         },
//         correctAnswer: 'b'
//     },
//     {
//         question: 'NaN == NaN',
//         answers: {
//             a: 'true',
//             b: 'false',
//             c: 'undefined'
//         },
//         correctAnswer: 'b'
//     },
//     {
//         question: '[] == ![]',
//         answers: {
//             a: 'undefined',
//             b: 'true',
//             c: 'false'
//         },
//         correctAnswer: 'b'
//     }
// ]
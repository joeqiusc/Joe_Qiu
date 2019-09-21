let studentId = 1000;

function addStudent() {

    console.log('addStudent firing off');

    // Get the input field values
    let studentNameInputField = document.getElementById('name');
    let studentName = studentNameInputField.value;
    let studentMajor = document.getElementById('major').value;

    // Validate the values (ensure they are not empty strings)
    if(studentName && studentMajor) {

        // Create a row and cells for the students table
        let row = document.createElement('tr');
        let studentIdCell = document.createElement('td');
        let studentNameCell = document.createElement('td');
        let studentMajorCell = document.createElement('td');

        // Append the cells to the row
        row.appendChild(studentIdCell);
        row.appendChild(studentNameCell);
        row.appendChild(studentMajorCell);

        // Append the row to our pre-existing table
        document.getElementById('students').appendChild(row);

        // Add student information to the newly appended cells
        studentIdCell.innerText = studentId++;
        studentNameCell.innerText = studentName;
        studentMajorCell.innerText = studentMajor;

        // Clear the input fields for future values to be provided
        studentNameInputField.value = '';
        document.getElementById('major').value ='';
    }
}

// Create a event listener on the Add Student button to fire when clicked
document.getElementById('add').addEventListener('click', addStudent);
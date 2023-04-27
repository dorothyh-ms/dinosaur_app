const radioButtonName = document.getElementById("radiobutton-script").getAttribute("data-radiobuttonOption")
const primaryURL = document.getElementById("radiobutton-script").getAttribute("data-primaryURL")
const entity = document.getElementById("radiobutton-script").getAttribute("data-entity")
const roleTag = document.querySelector("meta[name=\"role\"]")
const radioButtons = document.getElementsByName(radioButtonName);
const addButton = document.getElementById("add-" + entity);
const deleteButton = document.getElementById("delete-" + entity);
const saveButton = document.getElementById("save-" + entity)
const allInputs = document.querySelectorAll(`.field-${entity}`);



if (addButton) {
    addButton.setAttribute("href", "/" + primaryURL + "/add")
};


const tableRows = document.querySelectorAll(`#${entity}Table tbody tr`);
if (roleTag) {
    if (roleTag.content === "admin" || roleTag.content === "moderator") {
        for (let row of tableRows) {
            row.addEventListener("click", function (event) {
                let radioButton = document.getElementById(`${entity}Option_${row.id}`)
                lastClickedRadioButtonId = radioButton.id;
                radioButton.checked = true;
                saveButton.classList.add("disabled");
                hideAllInputFields();
                showAllTableData();
                if (deleteButton) {
                    deleteButton.classList.remove("disabled");
                }
                if (saveButton) {
                    saveButton.classList.remove("disabled");
                }
                let spans = row.querySelectorAll("td span")
                for (let span of spans) {
                    span.classList.add("invisible");
                }
                let inputs = row.querySelectorAll(`.field-${entity}`);
                for (let input of inputs) {
                    console.log("visible");
                    input.classList.remove("invisible");

                }
            })
        }
    }
}



function hideAllInputFields() {
    for (let input of allInputs) {
        input.classList.add("invisible");
    }
}


function showAllTableData() {
    let spans = document.querySelectorAll(`#${entity}Table tbody tr td span`)
    for (let span of spans) {
        span.classList.remove("invisible");
    }
}


function handleDeletionResponse(response) {
    if (response.status === 204) {
        let id = +response.url.substring(response.url.lastIndexOf('/') + 1)
        const tableRow = document.getElementById(`${id}`);
        tableRow.parentNode.removeChild(tableRow);
    } else if (response.status === 404) {
        alert("Delete failed");
    }
}





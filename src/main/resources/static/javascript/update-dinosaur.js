const saveDinosaurButton =  document.getElementById("save-dinosaur");
const dinosaurRadioButtons = document.getElementsByName("dinosaurOption");
const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
const headers = {
    "Accept" : "application/json",
    "Content-Type": "application/json",
    [header]: token
};

saveDinosaurButton.addEventListener("click", function (event) {
    for (let radioButton of dinosaurRadioButtons){
        if (radioButton.checked){
            const parentRow = radioButton.closest("tr")
            const newScientificName = parentRow.querySelector(`#dinosaur_${parentRow.id}_scientificName_input`).value
            const newPeriodName = parentRow.querySelector(`#dinosaur_${parentRow.id}_period_input`).value
            const newNumberOfSpecimens = parentRow.querySelector(`#dinosaur_${parentRow.id}_numberOfSpecimens_input`).value
            const newDietName = parentRow.querySelector(`#dinosaur_${parentRow.id}_diet_input`).value
            fetch(`/api/dinosaurs/${parentRow.id}`, {
                method: 'PATCH',
                headers,
                body: JSON.stringify({
                    "scientificName" : newScientificName,
                    "numberOfSpecimensFound" : newNumberOfSpecimens,
                    "periodName" : newPeriodName,
                    "dietName" : newDietName})
            })
                .then(response => {
                if (response.status === 204) {
                    alert("Species successfully updated")
                    const scientificNameSpan = document.getElementById(`dinosaur_${parentRow.id}_scientificName`)
                    const periodSpan = document.getElementById(`dinosaur_${parentRow.id}_period`)
                    const numberOfSpecimensSpan = document.getElementById(`dinosaur_${parentRow.id}_numberOfSpecimens`)
                    const dietSpan = document.getElementById(`dinosaur_${parentRow.id}_diet`)

                    scientificNameSpan.textContent=newScientificName;
                    periodSpan.textContent=newPeriodName[0].toUpperCase() + newPeriodName.substring(1);
                    numberOfSpecimensSpan.textContent=newNumberOfSpecimens;
                    dietSpan.textContent=((newDietName ==='carnivore') ? "Carnivorous" : "Herbivorous");

                } else if (response.status === 400){
                    alert("Fields contain errors")
                }
            })

        }
    }
})

if (deleteButton) {
    deleteButton.addEventListener("click", function (event) {
        for (let radioButton of radioButtons) {
            if (radioButton.checked) {
                fetch(`/api/dinosaurs/${radioButton.closest("tr").id}`, {
                    method: 'DELETE',
                    headers: {
                        Accept: "application/json",
                        [header]: token,
                    }
                })
                    .then(handleDeletionResponse)
            }
        }
    })
};


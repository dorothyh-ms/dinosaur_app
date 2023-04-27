const saveDigsiteButton =  document.getElementById("save-digsite");
const digsiteRadioButtons = document.getElementsByName("digsiteOption");
const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
const headers = {
    "Accept" : "application/json",
    "Content-Type": "application/json",
    [header]: token
};

saveDigsiteButton.addEventListener("click", function (event) {
    console.log("clicked")
    for (let radioButton of digsiteRadioButtons){
        console.log("clicked")
        if (radioButton.checked){
            const parentRow = radioButton.closest("tr")
            const newCountry = parentRow.querySelector(`#digsite_${parentRow.id}_country_input`).value
            const newLatitude = parentRow.querySelector(`#digsite_${parentRow.id}_latitude_input`).value
            const newLongitude = parentRow.querySelector(`#digsite_${parentRow.id}_longitude_input`).value
            const newFirstExcavation = parentRow.querySelector(`#digsite_${parentRow.id}_date_input`).value
            console.log(`/api/digsites/${parentRow.id}`)
            fetch(`/api/digsites/${parentRow.id}`, {
                method: 'PATCH',
                headers,
                body: JSON.stringify({
                    "country" : newCountry,
                    "latitude" : newLatitude,
                    "longitude" : newLongitude,
                    "firstExcavation" : newFirstExcavation
                })
            })
                .then(response => {
                    if (response.status === 204) {
                        alert("Successfully updated digsite")
                        const countrySpan = document.getElementById(`digsite_${parentRow.id}_country`)
                        const latitudeSpan = document.getElementById(`digsite_${parentRow.id}_latitude`)
                        const longitudeSpan = document.getElementById(`digsite_${parentRow.id}_longitude`)
                        const dateSpan = document.getElementById(`digsite_${parentRow.id}_date`)
                        countrySpan.textContent=newCountry;
                        latitudeSpan.textContent=newLatitude;
                        longitudeSpan.textContent=newLongitude;
                        dateSpan.textContent=newFirstExcavation;
                    } else if (response.status === 400){
                        console.log("Fields contain errors")
                    }
                })

        }
    }
})

if (deleteButton) {
    deleteButton.addEventListener("click", function (event) {
        for (let radioButton of radioButtons) {
            if (radioButton.checked) {
                fetch(`/api/digsites/${radioButton.closest("tr").id}`, {
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


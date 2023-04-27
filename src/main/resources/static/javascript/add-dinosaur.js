const submitButton = document.getElementById("submit_dinosaur");
submitButton.addEventListener("click", submitDinosaur);
const nameInput = document.getElementById("name");
const scientificNameInput = document.getElementById("scientificName");
const numberOfSpecimensFoundInput = document.getElementById("numberOfSpecimensFound");
const dietNameInput = document.getElementById("diet");
const periodNameInput = document.getElementById("periodName")
const imageInput = document.getElementById("image");
const digSites = Array.from(document.getElementsByName("foundInDigSites"));
const dateFields = Array.from(document.getElementsByName("dateFound"));
const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

const form = document.getElementById("addSpeciesForm")

function setMaximumDate() {
    let today = new Date();
    let dd = today.getDate();
    let mm = today.getMonth() + 1; //January is 0!
    let yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    for (let dateField of dateFields) {
        dateField.setAttribute("max", today)
    }
}

const headers = {
    "Accept": "application/json",
    "Content-Type": "application/json",
    [header]: token
};

function submitDinosaur(event) {
    form.classList.add('was-validated');
    const addedDigSitesIds = digSites
        .filter(digSite => digSite.checked)
        .map(digSite => digSite.value);
    const datesFound = digSites
        .filter(digSite => digSite.checked)
        .map(digSite => {
            const date = digSite.closest("tr").querySelectorAll('[name=dateFound]')[0];
            date.required = true;
            return date.value});
    const digsiteIdDateFoundMap = addedDigSitesIds.reduce((acc, digsiteId, index) => {
        acc[digsiteId] = datesFound[index];
        return acc;
    }, {});
    const formIsValid = form.checkValidity();
    if (formIsValid) {
        fetch('/api/dinosaurs', {
            method: "POST",
            headers,
            body: JSON.stringify({
                "name": nameInput.value,
                "scientificName": scientificNameInput.value,
                "numberOfSpecimensFound": numberOfSpecimensFoundInput.value,
                "periodName": periodNameInput.value,
                "dietName": dietNameInput.value,
                "digSiteIdsDatesFound": digsiteIdDateFoundMap,
                "image": imageInput.value
            })
        }).then(response => {
            if (response.status === 201) {
                alert("Successfully added dinosaur species")
            } else if (response.status === 400) {
                alert("Please check fields and try again")
            }
            form.reset();
            form.classList.remove('was-validated');
        })
    }
}

setMaximumDate();
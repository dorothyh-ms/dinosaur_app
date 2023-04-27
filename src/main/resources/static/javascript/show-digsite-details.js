const digSiteListItems = document.querySelectorAll("#digSiteList li");
const table = document.createElement('table');
table.id = "digSiteDetails"
table.className = 'table';


for (const listItem of digSiteListItems) {
    listItem.addEventListener("mouseenter", addDigSiteDetailsTable);
    listItem.addEventListener("mouseleave", removeDigSiteTable);
}

function addDigSiteDetailsTable(event) {
    clearTable()
    const listItemEntered = event.target
    console.log(listItemEntered.id)
    fetchDigSites(listItemEntered);
}

function removeDigSiteTable(event) {
    clearTable()
}

function clearTable() {
    table.innerHTML = "";
    if (table.parentNode){
        console.log(table.parentNode)
        const tableListItems = document.querySelectorAll(".detailsListItem")
        tableListItems.forEach(listItem => {
            listItem.remove();
        }
        )
    }

}

function fetchDigSites(listItemEntered) {
    fetch(`/api/digsites/${listItemEntered.id}`,
        {
            headers: {
                Accept: "application/json"
            }
        })
        .then(resp => {
            if (resp.status !== 200) {
                // Handle error
                console.log("problem")
            } else {
                return resp.json();
            }
        })
        .then(digsite => {
            addDigSiteDetails(digsite, listItemEntered)
        })
        .catch(reason => {
            // Handle error
            console.log("error!")
        });
}


function addDigSiteDetails(digSite, listItemEntered) {
    const headerRow = document.createElement('tr');
    const countryHeader = document.createElement("th");
    countryHeader.innerText = "Country";
    const latitudeHeader = document.createElement("th");
    latitudeHeader.innerText = "Latitude";
    const longitudeHeader = document.createElement("th");
    longitudeHeader.innerText = "Longitude";
    const firstExcavated = document.createElement("th");
    firstExcavated.innerText = "First Excavated";
    headerRow.append(countryHeader, latitudeHeader, longitudeHeader, firstExcavated)
    table.appendChild(headerRow);
    table.innerHTML += `
                <tr>
                    <td>${digSite.country}</td>
                    <td>${digSite.latitude}</td>
                    <td>${digSite.longitude}</td>
                    <td>${digSite.firstExcavation}</td>
                </tr>`;
    const tableItem = document.createElement("li");
    tableItem.classList.add('detailsListItem');
    tableItem.appendChild(table);
    tableItem.classList.add('list-group-item');
    listItemEntered.after(tableItem);
}





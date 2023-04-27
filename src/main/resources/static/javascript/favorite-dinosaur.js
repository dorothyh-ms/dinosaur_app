const favoriteButton = document.getElementById("favorite");
const unFavoriteButton = document.getElementById("unfavorite");

const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;
const dinosaurId = document.getElementById("favorite-dinosaur-script").getAttribute("data-dinosaurId")
favoriteButton.addEventListener("click", (event) =>
    {
        console.log("clicked!")
        fetch(`/api/dinosaurs/${dinosaurId}/favorites`, {
            method: "POST",
            headers: {
                Accept: "application/json",
                [header]: token,
            }
        }).then((response) => {
            if (response.status === 201) {
                favoriteButton.classList.add("invisible");
                unFavoriteButton.classList.remove("invisible");
            } else if (response.status === 400){
                alert("An error occurred. Please try again later.")
            }
        })
    }
)
unFavoriteButton.addEventListener("click", (event) =>
    {
        console.log("clicked!")
        fetch(`/api/dinosaurs/${dinosaurId}/favorites`, {
            method: "DELETE",
            headers: {
                [header]: token,
            }
        }).then((response) => {
            if (response.status === 204) {
                console.log("unfavorited!")
                favoriteButton.classList.remove("invisible");
                unFavoriteButton.classList.add("invisible");
            }
        })
    }
)
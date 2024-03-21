import { getAuthenticatedUser } from "../../../utils/auths";
import { clearPage } from "../../../utils/render";
import Navigate from "../../Router/Navigate";


const MeetContactPage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser) {
        Navigate('/login');
        window.location.reload();
    } else {
        clearPage();
        document.title = "Rencontrer un contact";
        renderMeetContactPage();
    }
};

function renderMeetContactPage() {
   const main = document.querySelector('main');
   main.innerHTML = `
    <div class="search-container d-flex justify-content-between">
   <div class="filter-container">
     <h3>Filtres</h3>
     <label>
       <input type="checkbox" name="filter" value="etudiant">
       Que les étudiants
     </label>
   </div>

   <div class="search-bar">`
};

export default MeetContactPage;
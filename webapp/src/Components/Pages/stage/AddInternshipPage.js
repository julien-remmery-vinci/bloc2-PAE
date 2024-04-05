import {clearPage} from "../../../utils/render";
import {isAuthenticated} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const InternshipPage = () => {
    if (!isAuthenticated()) {
      Navigate('/login');
    } else {
      clearPage();
      renderInternshipPage();
      document.title = "Ajouter un Stage";
    }
  }

async function renderInternshipPage() {
    const main = document.querySelector('main');
    main.className = 'd-flex flex-row justify-content-around align-items-center vh-100';
    const stageSection = document.createElement('section');
    const responsibleSection = document.createElement('section');
    stageSection.innerHTML = `
    <h2>Mon stage</h2>
    <p>Vous n'avez pas encore de stage pour cette année</p>
    `;
    responsibleSection.innerHTML = `
        <h2>Inscrire un responsable</h2>
        <form class="p-5 w-150 bg-light rounded shadow col-md-8">
            <label for="name" class="fw-bold mb-1">Nom:</label><br>
            <input type="text" id="name" name="name" class="form-control mb-3"><br>
            <label for="name" class="fw-bold mb-1">Prénom:</label><br>
            <input type="text" id="firstname" name="firstname" class="form-control mb-3"><br>
            <label for="phone" class="fw-bold mb-1">Téléphone:</label><br>
            <input type="tel" id="phone" name="phone" class="form-control mb-3"><br>
            <label for="email" class="fw-bold mb-1">Email:</label><br>
            <input type="email" id="email" name="email" class="form-control mb-3">
            <button type="submit" class="btn btn-primary mt-3">Sauver</button>
        </form>
    `;
    main.appendChild(stageSection);
    main.appendChild(responsibleSection);

}

export default InternshipPage;
import {clearPage, renderBreadcrumb} from "../../../utils/render";
import {getToken, isAuthenticated} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const InternshipPage = async () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    renderBreadcrumb({"Accueil": "/", "Stage": "/stage"})
    await renderInternshipPage();
    document.title = "Stage";
  }
}

async function renderInternshipPage() {
  const stage = await getStage();
  const main = document.querySelector('main');
  const div = document.createElement('div');
  div.className = 'd-flex flex-row justify-content-around align-items-center vh-90';
  main.appendChild(div);

  const stageSection = document.createElement('section');
  stageSection.className = 'text-center';

  if (stage === 404) {
    stageSection.innerHTML = `
    <h2>Mon stage</h2>
    <p>Vous n'avez pas encore de stage pour cette année</p>
    `;
  } else {
    stageSection.innerHTML = `
    <h2>Mon stage</h2>
     <form class="p-5 w-150 bg-light rounded shadow col-md-8" style="max-width: 80%;">
      <label for="subject" class="fw-bold mb-1">Sujet du stage:</label><br>
      <textarea id="subject" name="subject" class="form-control mb-3" rows="2">${stage.internshipProject}</textarea><br>
      <label for="date" class="fw-bold mb-1">Date de signature:</label><br>
      <input type="date" id="date" name="date" value="${stage.signatureDate}" class="form-control mb-3" readonly >
      <label for="supervisor" class="fw-bold mb-1">Responsable</label><br>
      <input type="text" id="supervisor" name="supervisor" value="${stage.internshipSupervisor.firstName} ${stage.internshipSupervisor.lastName}" class="form-control mb-3" readonly>
      <button type="submit" class="btn btn-primary mt-3">Sauver</button>
  </form>
`;
  }

  main.appendChild(stageSection);

}

async function getStage() {
  const response = await fetch('http://localhost:3000/internships', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    }
  });
  if (response.status === 404) {
    return 404;
  }
  return response.json();
}

export default InternshipPage;
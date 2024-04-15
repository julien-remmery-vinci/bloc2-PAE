import {getToken, isAuthenticated} from "../../utils/auths";
import Navigate from "../Router/Navigate";
import {clearPage, renderBreadcrumb} from "../../utils/render";

const InternshipSupervisorPage = async () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    renderBreadcrumb({"Accueil": "/", "Responsabes": "/internship-supervisor"})
    await renderInternshipSupervisorPage();
    document.title = "Stage";
  }
}

async function renderInternshipSupervisorPage() {
  const internshipSupervisor = await getAllInternshipSupervisor();
  const mainElement = document.querySelector('main');
  const divElement = document.createElement('div');
  divElement.className = 'd-flex flex-row justify-content-around align-items-center vh-90';

  // Create table
  const table = document.createElement('table');
  table.className = 'table';

  // Create table header
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  ['Nom', 'Prénom', 'Email', 'Numéro de téléphone',
    'Nom de l\'entreprise'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    headerRow.appendChild(th);
  });
  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Create table body
  const tbody = document.createElement('tbody');
  internshipSupervisor.forEach(supervisorData => {
    const row = document.createElement('tr');
     [supervisorData.lastName, supervisorData.firstName, supervisorData.email,
    supervisorData.phoneNumber, supervisorData.company.tradeName].forEach((text, index) => {
    const td = document.createElement('td');
    if (index === 2) { // If the text is an email
      const mailtoLink = document.createElement('a');
      mailtoLink.href = `mailto:${text}`;
      mailtoLink.textContent = text;
      td.appendChild(mailtoLink);
    } else {
      td.textContent = text;
    }
      row.appendChild(td);
    });
    tbody.appendChild(row);
  });
  table.appendChild(tbody);

  divElement.appendChild(table);
  mainElement.appendChild(divElement);
}

async function getAllInternshipSupervisor() {
  const response = await fetch('http://localhost:3000/internshipSupervisors', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    }
  });
  return response.json();
}

export default InternshipSupervisorPage;
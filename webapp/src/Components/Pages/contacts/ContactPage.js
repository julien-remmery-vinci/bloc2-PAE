import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser, getUserToken} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const ContactPage = () => {
  const authenticatedUser = getAuthenticatedUser();
  if (!authenticatedUser) {
    Navigate('/login');
    window.location.reload();
  } else {
    clearPage();
    document.title = "contacts";
    buildPage();
  }
}

async function buildPage() {
  const main = document.querySelector('main');
  const title = document.createElement('h3');
  const containerDiv = document.createElement('div');
  title.textContent = 'Contacts';
  title.style.textAlign = 'center';
  const table = document.createElement('table');
  table.classList.add('table', 'mx-auto');
  table.style.width = '80%';
  table.style.tableLayout = 'auto';
  const tableHead = document.createElement('thead');
  tableHead.classList.add('thead-dark');
  const tableHeadRow = document.createElement('tr');
  const headings = ['Entreprise', 'État', ''];

  // Ajouter les entêtes de colonne
  headings.forEach(headingText => {
    const th = document.createElement('th');
    th.textContent = headingText;
    tableHeadRow.appendChild(th);
  });

  tableHead.appendChild(tableHeadRow);
  table.appendChild(tableHead);

  const tableBody = document.createElement('tbody');
  const contacts = await getContacts();

  contacts.forEach(contact => {
    const row = document.createElement('tr');

    // Colonne entreprise
    const companyCell = document.createElement('td');
    const companyLink = document.createElement('a');
    companyLink.textContent = contact.company.tradeName;
    if (contact.company.designation !== null) {
      companyLink.textContent += `,  ${contact.company.designation}`;
    }
    companyLink.href = '#';
    companyCell.appendChild(companyLink);

    // Colonne état
    const stateCell = document.createElement('td');
    stateCell.textContent = contact.state;

    // Colonne Ne plus suivre
    const notFollowCell = document.createElement('td');
    const notFollowButton = document.createElement('button');
    notFollowButton.classList.add('btn', 'btn-primary');
    notFollowButton.textContent = 'Ne plus suivre';
    notFollowCell.appendChild(notFollowButton);

    // Ajouter les cellules à la ligne
    row.appendChild(companyCell);
    row.appendChild(stateCell);
    row.appendChild(notFollowCell);

    // Ajouter la ligne au corps du tableau
    tableBody.appendChild(row);
  });
  table.appendChild(tableBody);
  main.appendChild(title);
  containerDiv.appendChild(table);
  main.appendChild(containerDiv);
}

async function getContacts() {
  const response = await fetch('http://localhost:3000/contact', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getUserToken()
    }
  });
  return response.json();
}

export default ContactPage;
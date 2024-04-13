import {clearPage} from "../../../utils/render";
import {
  getToken,
  isAuthenticated
} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const ContactPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    document.title = "Contacts";
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
  const contacts = await getContacts();
    const hasPrisOrAccepted = contacts.some(contact => contact.state === 'pris' || contact.state === 'accepté');
    const hasRefused = contacts.some(contact => contact.state === 'refusé');

  if (hasPrisOrAccepted) {
    headings.splice(2, 0, 'Lieu de rencontre');
  }

  if (hasRefused) {
    headings.splice(3, 0, 'Raison du refus');
  }

  headings.forEach(headingText => {
    const th = document.createElement('th');
    th.textContent = headingText;
    tableHeadRow.appendChild(th);
  });

  tableHead.appendChild(tableHeadRow);
  table.appendChild(tableHead);

  const tableBody = document.createElement('tbody');

  contacts.forEach(contact => {
    const row = document.createElement('tr');

    // row company
    const companyCell = document.createElement('td');
    const companyLink = document.createElement('a');
    companyLink.textContent = contact.company.tradeName;
    companyLink.href = '#';
    if (contact.company.designation !== null) {
      companyLink.textContent += `,  ${contact.company.designation}`;
    }
    companyLink.addEventListener('click', (event) => {
      event.preventDefault();
      let url = window.location.href;
      url += contact.state === 'initié' ? '/meet' : '/refusal';
      url += `?id=${contact.idContact}&tradename=${contact.company.tradeName}&designation=${contact.company.designation}&meetplace=${contact.meetPlace}`;
      window.location.href = url;
    });
    companyCell.appendChild(companyLink);
    row.appendChild(companyCell);

    // row state
    const stateCell = document.createElement('td');
    stateCell.textContent = contact.state;
    row.appendChild(stateCell);

    // row meetPlace
    const meetPlaceCell = document.createElement('td');
    if (contact.state === 'pris' || contact.state === 'accepté') {
      meetPlaceCell.textContent = contact.meetPlace;
    }else{
      meetPlaceCell.textContent = '';
    }
    row.appendChild(meetPlaceCell);

    // row unfollow
    const notFollowCell = document.createElement('td');
    const notFollowButton = document.createElement('button');
    notFollowButton.classList.add('btn', 'btn-primary');
    if (contact.state === 'non suivi') {
      notFollowButton.textContent = 'Suivre';
    } else {
      notFollowButton.textContent = 'Ne plus suivre';
    }
    notFollowCell.appendChild(notFollowButton);
    row.appendChild(notFollowCell);

    notFollowButton.addEventListener('click', async () => {
      console.log("enter_fetch", contact.idContact);
      const response = await fetch(
          `http://localhost:3000/contacts/${contact.idContact}/unfollow`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': getToken()
            }
          });
      if (response.status === 200) {
        const data = await response.json();
        if (notFollowButton.textContent === 'Ne plus suivre' && contact.state
            !== 'non suivi') {
          notFollowButton.textContent = 'Suivre';
        }
        stateCell.textContent = data.state;
      }
    });
    tableBody.appendChild(row);
  });
  table.appendChild(tableBody);
  main.appendChild(title);
  containerDiv.appendChild(table);
  main.appendChild(containerDiv);

  if (contacts.length === 0) {
    noContacts();
  }
}

function noContacts() {
  const main = document.querySelector('main');
  const title = document.createElement('h3');
  title.textContent = 'Aucun contact';
  title.style.textAlign = 'center';
  title.style.fontSize = '3rem';
  title.style.marginTop = '100px';
  main.appendChild(title);
}

async function getContacts() {
  const response = await fetch('http://localhost:3000/contacts', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    }
  });
  return response.json();
}

export default ContactPage;
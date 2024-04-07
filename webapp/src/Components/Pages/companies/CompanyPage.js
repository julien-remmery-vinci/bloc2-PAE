import {getToken, isAuthenticated} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";
import {clearPage} from "../../../utils/render";

const CompanyPage = (data) => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else if (!data) {
    Navigate('/dashboard');
  } else {
    clearPage();
    document.title = `${data.tradeName}`;
    document.title += data.designation ? ` - ${data.designation}` : '';
    buildPage(data);
  }
}

function buildPage(data) {
  const main = document.querySelector('main');
  const mainDiv = document.createElement('div');
  mainDiv.style.width = '100%';
  const infosDiv = document.createElement('div');
  const contactsDiv = document.createElement('div');
  infosDiv.id = 'companyInfos';
  contactsDiv.id = 'contacts';
  mainDiv.appendChild(infosDiv);
  mainDiv.appendChild(contactsDiv);
  mainDiv.style.height = '92vh';
  main.appendChild(mainDiv);
  displayCompanyInfos(data);
  displayContacts(data.contacts);
}

function displayCompanyInfos(company) {
  document.getElementById('companyInfos').innerHTML = '';
  const table = document.createElement('table');
  table.className = 'table';
  table.style.alignContent = 'center';
  const thead = document.createElement('thead');
  const tbody = document.createElement('tbody');
  const tr = document.createElement('tr');
  thead.appendChild(tr);
  const th1 = document.createElement('th');
  th1.textContent = 'Nom';
  tr.appendChild(th1);
  if(company.designation) {
    const th2 = document.createElement('th');
    th2.textContent = 'Appellation';
    tr.appendChild(th2);
  }
  const th3 = document.createElement('th');
  th3.textContent = 'Téléphone';
  tr.appendChild(th3);
  const th4 = document.createElement('th');
  th4.textContent = 'email';
  tr.appendChild(th4);
  const th5 = document.createElement('th');
  th5.textContent = 'Adresse';
  tr.appendChild(th5);
  const th6 = document.createElement('th');
  th6.textContent = 'Ville';
  tr.appendChild(th6);
  table.appendChild(thead);
  table.appendChild(tbody);
  const trow = document.createElement('tr');
  const td1 = document.createElement('td');
  td1.textContent = company.tradeName;
  trow.appendChild(td1);
  if(company.designation) {
    const td2 = document.createElement('td');
    td2.textContent = company.designation;
    trow.appendChild(td2);
  }
  const td3 = document.createElement('td');
  td3.textContent = company.phoneNumber;
  const td4 = document.createElement('td');
  td4.textContent = company.email;
  const td5 = document.createElement('td');
  td5.textContent = company.address;
  const td6 = document.createElement('td');
  td6.textContent = company.city;
  trow.appendChild(td3);
  trow.appendChild(td4);
  trow.appendChild(td5);
  trow.appendChild(td6);
  tbody.appendChild(trow);
  table.style.width = '80%';
  table.style.margin = 'auto';
  table.style.marginTop = '20px';
  document.getElementById('companyInfos').appendChild(table);
  if(company.blacklisted) {
    const table2 = document.createElement('table');
    table2.className = 'table';
    table2.style.width = '80%';
    table2.style.alignContent = 'center';
    const thead2 = document.createElement('thead');
    const tbody2 = document.createElement('tbody');
    const tr2 = document.createElement('tr');
    const th7 = document.createElement('th');
    th7.textContent = 'Black-listée';
    const th8 = document.createElement('th');
    th8.textContent = 'Raison du black-list';
    thead2.appendChild(tr2);
    tr2.appendChild(th7);
    tr2.appendChild(th8);
    table2.appendChild(thead2);
    const trow2 = document.createElement('tr');
    const td7 = document.createElement('td');
    td7.textContent = "oui";
    const td8 = document.createElement('td');
    td8.textContent = company.blacklistMotivation;
    trow2.appendChild(td7);
    trow2.appendChild(td8);
    tbody2.appendChild(trow2);
    table2.appendChild(tbody2);
    document.getElementById('companyInfos').appendChild(table2);
  } else {
    const button = document.createElement('button');
    button.className = 'btn btn-primary';
    button.id = 'blacklist';
    button.textContent = 'Black-lister';
    button.style.margin = 'auto';
    button.style.display = 'block';
    button.style.marginTop = '20px';
    document.getElementById('companyInfos').appendChild(button);
    const motivation = document.createElement('textarea');
    motivation.className = 'form-control';
    motivation.id = 'motivation';
    motivation.placeholder = 'Motivation';
    motivation.style.margin = 'auto';
    motivation.style.display = 'block';
    motivation.style.marginTop = '20px';
    motivation.style.width = '30%';
    motivation.style.height = '100px';
    motivation.hidden = true;
    document.getElementById('companyInfos').appendChild(motivation);
    const saveButton = document.createElement('button');
    saveButton.className = 'btn btn-primary';
    saveButton.id = 'saveButton';
    saveButton.textContent = 'Sauvegarder';
    saveButton.style.margin = 'auto';
    saveButton.style.display = 'block';
    saveButton.style.marginTop = '20px';
    saveButton.hidden = true;
    document.getElementById('companyInfos').appendChild(saveButton);
    button.addEventListener('click', () => {
      motivation.hidden = !motivation.hidden;
      button.hidden = !button.hidden;
      saveButton.hidden = !saveButton.hidden;
    })
    saveButton.addEventListener('click', () => {
      blacklistCompany(company.idCompany);
    });
    const error = document.createElement('p');
    error.textContent = 'Erreur lors du blacklist de l\'entreprise';
    error.hidden = true;
    error.className = 'alert alert-danger';
    error.id = 'error';
    document.getElementById('companyInfos').appendChild(error);
  }
}

function displayContacts(contacts) {
  document.getElementById('contacts').innerHTML = '';
  const table = document.createElement('table');
  table.className = 'table';
  table.style.width = '80%';
  table.style.margin = 'auto';
  const thead = document.createElement('thead');
  const tbody = document.createElement('tbody');
  const tr = document.createElement('tr');
  const th1 = document.createElement('th');
  th1.textContent = 'Etudiant';
  const th2 = document.createElement('th');
  th2.textContent = 'Statut';
  const th3 = document.createElement('th');
  th3.textContent = 'Lieu de rencontre';
  const th4 = document.createElement('th');
  th4.textContent = 'Raison de refus';
  thead.appendChild(tr);
  tr.appendChild(th1);
  tr.appendChild(th2);
  tr.appendChild(th3);
  tr.appendChild(th4);
  table.appendChild(thead);
  table.appendChild(tbody);
  document.getElementById('contacts').appendChild(table);
  contacts.forEach(contact => {
    const trow = document.createElement('tr');
    const td1 = document.createElement('td');
    td1.textContent = `${contact.student.firstname} ${contact.student.lastname}`;
    const td2 = document.createElement('td');
    td2.textContent = contact.state;
    const td3 = document.createElement('td');
    td3.textContent = contact.meetPlace;
    const td4 = document.createElement('td');
    td4.textContent = contact.refusalReason;
    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    tbody.appendChild(trow);
  });
}

async function blacklistCompany(idCompany) {
  const response = await fetch(`http://localhost:3000/companies/${idCompany}/blacklist`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getToken()
    },
    body: JSON.stringify({
      reason: document.getElementById('motivation').value
    })
  });

  if(response.status !== 200) {
    document.getElementById('error').hidden = false;
    document.getElementById('motivation').hidden = true;
    document.getElementById('blacklist').addEventListener('click', () => {
      const motivation = document.getElementById('motivation');
      motivation.hidden = !motivation.hidden;
    });
  } else {
    document.getElementById('error').hidden = true;
    document.getElementById('motivation').hidden = true;
    document.getElementById('blacklist').hidden = true;
    document.getElementById('saveButton').hidden = true;
    const data = await response.json();
    displayCompanyInfos(data.company)
    displayContacts(data.contacts)
  }
}

export default CompanyPage;
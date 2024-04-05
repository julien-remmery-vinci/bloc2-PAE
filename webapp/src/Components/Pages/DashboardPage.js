import {isAuthenticated} from "../../utils/auths";
import Navigate from "../Router/Navigate";
import {clearPage} from "../../utils/render";

let companies;

const DashboardPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    document.title = "Statistiques";
    buildPage();
  }
}

async function buildPage() {
  const main = document.querySelector('main');
  const statsDiv = document.createElement('div');
  const companiesDiv = document.createElement('div');
  main.appendChild(statsDiv);
  main.appendChild(companiesDiv);
  main.style.display = 'flex';
  // statsDiv.style.border = '1px solid red';
  // companiesDiv.style.border = '1px solid blue';
  statsDiv.style.width = '40%';
  companiesDiv.style.width = '60%';
  statsDiv.style.height = '92vh';
  companiesDiv.style.height = '92vh';
  statsDiv.id = 'stats';
  companiesDiv.id = 'companies';
  companies = await getCompanies();
  renderStats();
  renderCompanies(getCurrentAcademicYear());
}

function renderStats() {
  const div = document.getElementById('stats');
  const academicYears = getAcademicYears();
  const select = document.createElement('select');
  select.id = 'academicYear';
  select.className = 'form-select';
  select.style.width = '40%';
  select.style.margin = '10px auto';
  select.style.display = 'block';
  select.style.fontSize = '1.2em';
  select.style.borderRadius = '5px';
  select.style.padding = '5px';
  select.style.cursor = 'pointer';
  select.style.textAlign = 'center';
  academicYears.forEach(academicYear => {
    const option = document.createElement('option');
    option.value = academicYear;
    option.textContent = academicYear;
    select.appendChild(option);
  });
  const option = document.createElement('option');
  option.value = 'all';
  option.textContent = 'Toutes les années';
  select.appendChild(option);
  select.addEventListener('change', (e) => {
    renderCompanies(e.target.value);
  });
  div.appendChild(select);
}

function renderCompanies(academicYear) {
  const div = document.getElementById('companies');
  div.innerHTML = '';
  const title = document.createElement('h3');
  title.textContent = 'Liste des entreprises';
  title.style.textAlign = 'center';
  div.appendChild(title);
  const error = document.createElement('p');
  error.textContent = 'Erreur lors de la récupération des entreprises';
  error.hidden = true;
  error.className = 'alert alert-danger';
  error.id = 'companies-error';

  const table = document.createElement('table');
  table.className = 'table';
  table.style.width = '80%';
  const thead = document.createElement('thead');
  const tbody = document.createElement('tbody');
  const tr = document.createElement('tr');
  const th1 = document.createElement('th');
  const th2 = document.createElement('th');
  const th3 = document.createElement('th');
  const th4 = document.createElement('th');
  const th5 = document.createElement('th');
  th1.textContent = 'Nom';
  th2.textContent = 'Appellation';
  th3.textContent = 'Téléphone';
  th4.textContent = 'Nombre d\'étudiants';
  th5.textContent = 'Black-listée';
  thead.appendChild(tr);
  tr.appendChild(th1);
  tr.appendChild(th2);
  tr.appendChild(th3);
  tr.appendChild(th4);
  tr.appendChild(th5);
  table.appendChild(thead);
  table.appendChild(tbody);
  div.appendChild(table);
  companies.forEach(company => {
    const trow = document.createElement('tr');
    const td1 = document.createElement('td');
    const td2 = document.createElement('td');
    const td3 = document.createElement('td');
    const td4 = document.createElement('td');
    const td5 = document.createElement('td');
    td1.textContent = company.tradeName;
    td2.textContent = company.designation;
    td3.textContent = company.phoneNumber;
    td4.textContent = getNbStudents(company.idCompany, academicYear)
    td5.textContent = company.blacklisted ? "oui" : "non";
    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    trow.appendChild(td5);
    tbody.appendChild(trow);
    trow.style.cursor = 'pointer';
    trow.addEventListener('click', () => {
      Navigate(`/company`, company);
    });
  });
}

async function getCompanies() {
  // fetch companies
  const response = await fetch('http://localhost:3000/companies/contacts');
  if(response.status !== 200) {
    document.getElementById('companies-error').querySelector('p').hidden = false;
  }
  return response.json();
}

function getNbStudents (companyId, academicYear) {
  let nbStudents = 0;
  const index = companies.findIndex(company => company.idCompany === companyId);

  if (academicYear === undefined || academicYear === 'all') {
    companies[index].contacts.forEach(contact => {
      if(contact.state === 'accepté') {
        nbStudents+=1;
      }
    });
    return nbStudents;
  }
  companies[index].contacts.forEach(contact => {
    if(contact.state === 'accepté' && contact.academicYear === academicYear) {
      nbStudents+=1;
    }
  });
  return nbStudents;
}

function getAcademicYears() {
  const academicYears = [];
  companies.forEach(company => {
    company.contacts.forEach(contact => {
      if(!academicYears.includes(contact.academicYear))
        academicYears.push(contact.academicYear);
    });
  });
  academicYears.sort((a, b) => b.split('-')[0] - a.split('-')[0]);
  return academicYears;
}

function getCurrentAcademicYear() {
  const date = new Date();
  const month = date.getMonth();
  const year = date.getFullYear();
  if(month >= 9) {
    return `${year}-${year + 1}`;
  }
  return `${year - 1}-${year}`;
}

export default DashboardPage;
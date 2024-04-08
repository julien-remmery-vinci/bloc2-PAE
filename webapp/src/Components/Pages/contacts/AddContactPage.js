import {clearPage} from "../../../utils/render";
import {
  getToken, isAuthenticated
} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const AddContactPage = () => {
  if (!isAuthenticated()) {
    Navigate('/login');
  } else {
    clearPage();
    document.title = "Ajouter un contact";
    buildPage();
  }
}

async function buildPage() {
  const companyList = await getCompanies();
  const main = document.querySelector('main');
  const containerDiv = document.createElement('div');
  containerDiv.className = 'container';
  const rowDiv = document.createElement('div');
  rowDiv.className = 'row';
  const colDiv1 = document.createElement('div');
  colDiv1.className = 'col-6';
  const colDiv2 = document.createElement('div');
  colDiv2.className = 'col-6';

  const title = document.createElement('h3');
  title.textContent = 'Ajouter un nouveau contact';
  title.style.textAlign = 'center';
  title.style.marginBottom = '5%';
  main.appendChild(title);

  const company = document.createElement('label');
  company.textContent = 'Entreprise';
  company.style.marginLeft = '15%';
  colDiv1.appendChild(company);
  const companies = document.createElement('select');

  companies.className = 'form-control';
  companies.style.width = '25%';
  companies.style.marginLeft = '15%';

  const defaultOption = document.createElement('option');
  defaultOption.text = 'Choisissez votre entreprise';
  defaultOption.value = 'default';
  companies.appendChild(defaultOption);

  const companyNames = [...new Set(companyList.map(e => e.tradeName))]; // Suppression des doublons
  companyNames.forEach(name => {
    const option = document.createElement('option');
    option.value = name;
    option.text = name;
    companies.appendChild(option);
  });

  colDiv1.appendChild(companies);

  const designation = document.createElement('label');
  designation.textContent = 'Appellation';
  designation.style.marginLeft = '15%';
  colDiv1.appendChild(designation);

  const designations = document.createElement('select');
  designations.id = 'designation';
  designations.className = 'form-control';
  designations.style.width = '25%';
  designations.style.marginLeft = '15%';

  colDiv1.appendChild(designations);

  companies.addEventListener('change', (e) => {
    const alert = document.querySelector('#alert');
    if (alert.hidden === false) {
      alert.hidden = true;
    }
    while (designations.firstChild) {
      designations.removeChild(designations.firstChild);
    }

    const selectedCompany = companyList.find(
        (c) => c.tradeName === e.target.value);

    if (e.target.value === 'default') {
      const defaultOptionDesignation = document.createElement('option');
      defaultOptionDesignation.text = "Choisissez d'abord votre entreprise";
      defaultOptionDesignation.value = 'default';
      designations.appendChild(defaultOptionDesignation);
    } else if (selectedCompany && selectedCompany.designation !== null) {
      const designationList = companyList.filter(
          (c) => c.tradeName === e.target.value);
      designationList.forEach(name => {
        const option = document.createElement('option');
        option.value = name.designation;
        option.text = name.designation;
        designations.appendChild(option);
      });
    } else if (selectedCompany.designation === null) {
      const noDesignation = document.createElement('option');
      noDesignation.textContent = 'Aucune appellation';
      designations.appendChild(noDesignation);
    }

  });

  const alert = document.createElement('p');
  alert.id = 'alert';
  alert.className = 'alert alert-danger';
  alert.style.marginLeft = '15%';
  alert.style.width = '40%';
  alert.hidden = true;
  colDiv1.appendChild(alert);

  const submit = document.createElement('input');
  submit.value = 'Enregistrer';
  submit.type = 'submit';
  submit.className = 'btn btn-primary';
  submit.style.marginBottom = '25%';
  submit.style.marginLeft = '15%';
  submit.style.width = '50%';
  colDiv1.appendChild(submit);
  rowDiv.appendChild(colDiv1);
  containerDiv.appendChild(rowDiv);
  main.appendChild(containerDiv);

  submit.addEventListener('click', onSubmit);

  const createCompanyButton = document.createElement('button');
  createCompanyButton.textContent = 'Ajouter une entreprise non répertoriée';
  createCompanyButton.className = 'btn btn-secondary';
  colDiv2.style.marginBottom = '50%';
  colDiv2.appendChild(createCompanyButton);
  rowDiv.appendChild(colDiv2);

  createCompanyButton.addEventListener('click', () => {
      if (document.getElementById('companyForm')) {
      return;
      }
      const form = document.createElement('form');
      form.id = 'companyForm';

      const nameLabel = document.createElement('label');
      nameLabel.textContent = 'Nom';
      form.appendChild(nameLabel);
      const nameInput = document.createElement('input');
      nameInput.type = 'text';
      nameInput.className = 'form-control';
      nameInput.required = true;
      form.appendChild(nameInput);

      const addressLabel = document.createElement('label');
      addressLabel.textContent = 'Adresse';
      form.appendChild(addressLabel);
      const addressInput = document.createElement('input');
      addressInput.type = 'text';
      addressInput.className = 'form-control';
      addressInput.required = true;
      form.appendChild(addressInput);

      const cityLabel = document.createElement('label');
      cityLabel.textContent = 'Ville';
      form.appendChild(cityLabel);
      const cityInput = document.createElement('input');
      cityInput.type = 'text';
      cityInput.className = 'form-control';
      cityInput.required = false;
      form.appendChild(cityInput);

      const phoneLabel = document.createElement('label');
      phoneLabel.textContent = 'Numero de téléphone';
      form.appendChild(phoneLabel);
      const phoneInput = document.createElement('input');
      phoneInput.type = 'text';
      phoneInput.className = 'form-control';
      phoneInput.required = false;
      form.appendChild(phoneInput);

      const appellationLabel = document.createElement('label');
      appellationLabel.textContent = 'Appellation';
      form.appendChild(appellationLabel);
      const appellationInput = document.createElement('input');
      appellationInput.type = 'text';
      appellationInput.className = 'form-control';
      appellationInput.required = false;
      form.appendChild(appellationInput);

      const submitButton = document.createElement('input');
      submitButton.type = 'submit';
      submitButton.value = "Ajouter l'entreprise";
      submitButton.className = 'btn btn-primary';
      form.appendChild(submitButton);

      colDiv2.appendChild(form);
      containerDiv.appendChild(rowDiv);
      main.appendChild(containerDiv);

      submitButton.addEventListener('click', createSubmit);
  });
}
// fetch function to get all entreprises
async function getCompanies() {
  const response = await fetch('http://localhost:3000/companies', {
    method: 'GET', headers: {
      'Content-Type': 'application/json', 'Authorization': getToken(),
    },
  });
  if (response.status === 200) {
    return response.json();
  }
  return undefined;
}
async function createSubmit(e) {
  e.preventDefault();
  const tradeName = document.querySelector('input[type="text"]').value;
  const address = document.querySelectorAll('input[type="text"]')[1].value;
  const city = document.querySelectorAll('input[type="text"]')[2].value;
  const phoneNumber = document.querySelectorAll('input[type="text"]')[3].value;
  const designation = document.querySelectorAll('input[type="text"]')[4].value;
  const alert = document.querySelector('#alert');
  if (tradeName === '' || address === '') {
    alert.hidden = false;
    alert.textContent = 'Veuillez remplir les champs obligatoires';
    return;
  }
  const options = {
    method: 'POST', body: JSON.stringify({
          tradeName,
          designation,
          address,
          city,
          phoneNumber
    }), headers: {
      'Content-Type': 'application/json', 'Authorization': getToken(),
    },
  };
  const response = await fetch('http://localhost:3000/companies/', options);
  if (response.status === 200) {
      Navigate('/contact');
  } else {
    alert.hidden = false;
    alert.textContent = await response.text();
  }
}
// function to submit the form
async function onSubmit(e) {
  e.preventDefault();
  const company = document.querySelector('select').value;
  const designation = document.querySelectorAll('select')[1].value;
  const alert = document.querySelector('#alert');
  if (company === 'default' || designation === 'default') {
    alert.hidden = false;
    alert.textContent = 'Veuillez sélectionner une entreprise et une appellation';
    return;
  }
  // trying to get the company id
  const companyList = await getCompanies();
  let companyFound = 0;
  if (designation === 'Aucune appellation') {
    companyFound = companyList.find(
        (c) => c.tradeName === company && c.designation === null);
  } else {
    companyFound = companyList.find(
        (c) => c.tradeName === company && c.designation === designation);
  }

  const options = {
    method: 'POST', body: JSON.stringify({
      idCompany: companyFound.idCompany
    }), headers: {
      'Content-Type': 'application/json', 'Authorization': getToken(),
    },
  };

  const response = await fetch('http://localhost:3000/contacts/', options);
  if (response.status === 200) {
    Navigate('/contact');

  } else {
    alert.hidden = false;
    alert.textContent = await response.text();
  }
}

export default AddContactPage;


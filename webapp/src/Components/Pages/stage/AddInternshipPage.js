import {clearPage} from "../../../utils/render";
import {isAuthenticated, getToken} from "../../../utils/auths";
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
    const title = document.createElement('h3');
    title.textContent = 'Ajouter un stage';
    title.style.textAlign = 'center';
    main.appendChild(title);
    const mainDiv = document.createElement('div');
    mainDiv.style.display = 'flex';
    const leftDiv = document.createElement('div');
    leftDiv.style.width = '50%';
    leftDiv.appendChild(getInternshipInfos());
    const rightDiv = document.createElement('div');
    rightDiv.style.width = '50%';
    rightDiv.appendChild(getSupervisorInfos());
    rightDiv.appendChild(addNewSupervisor());
    mainDiv.appendChild(leftDiv);
    mainDiv.appendChild(rightDiv);
    main.appendChild(mainDiv);
}

function getInternshipInfos() {
    const queryParams = new URLSearchParams(window.location.search);
    const contactInfosDiv = document.createElement('div');
    contactInfosDiv.className = 'p-5';
    const entrepriseName = document.createElement('label');
    entrepriseName.textContent = 'Entreprise';
    contactInfosDiv.appendChild(entrepriseName);
    const entrepriseNameValue = document.createElement('input');
    entrepriseNameValue.type = 'text';
    entrepriseNameValue.value = queryParams.get('tradename');
    entrepriseNameValue.readOnly = true;
    entrepriseNameValue.className = 'bg-info form-control';
    contactInfosDiv.appendChild(entrepriseNameValue);
    if(queryParams.get('designation') !== 'null'){
        const entrepriseDesignation = document.createElement('label');
        entrepriseDesignation.textContent = 'Appellation';
        const entrepriseDesignationValue = document.createElement('input');
        entrepriseDesignationValue.type = 'text';
        entrepriseDesignationValue.value = queryParams.get('designation');
        entrepriseDesignationValue.readOnly = true;
        entrepriseDesignationValue.className = 'bg-info form-control';
        contactInfosDiv.appendChild(entrepriseDesignation);
        contactInfosDiv.appendChild(entrepriseDesignationValue);
    }
    return contactInfosDiv;
}

function getSupervisorInfos() {
    const supervisorInfosDiv = document.createElement('div');
    const supervisorLabel = document.createElement('label');
    supervisorLabel.textContent = 'Veuillez selectionner un responsable de stage';
    supervisorLabel.style.marginTop = '5%';
    supervisorLabel.style.marginBottom = '1%';
    supervisorInfosDiv.appendChild(supervisorLabel);
    const supervisorSelect = document.createElement('select');
    supervisorSelect.className = 'form-control';
    supervisorSelect.style.width = '50%';
    if (supervisorSelect.length === 0) {
        const option = document.createElement('option');
        option.textContent = 'Aucun responsable de stage trouvé';
        supervisorSelect.appendChild(option);
    } else {
    getSupervisors().then(supervisors => {
        supervisors.forEach(supervisor => {
            const option = document.createElement('option');
            option.value = supervisor.idSupervisor;
            option.textContent = `${supervisor.firstName  } ${  supervisor.lastName}`;
            supervisorSelect.appendChild(option);
        });
    });
    }
    supervisorInfosDiv.appendChild(supervisorSelect);
    return supervisorInfosDiv;
}

async function getSupervisors(){
  const queryParams = new URLSearchParams(window.location.search);
  const supervisors = await fetch(`http://localhost:3000/internshipSupervisors/company/${queryParams.get('idCompany')}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json',
          'Authorization': getToken(),
      },
  });
  if (supervisors.status === 200) {
      return supervisors.json();
  }
  return "error";
}

function addNewSupervisor() {
    const addSupervisorDiv = document.createElement('div');
    addSupervisorDiv.style.marginTop = '5%';
    const addSupervisorLabel = document.createElement('label');
    addSupervisorLabel.textContent = 'Vous ne trouvez pas le responsable de stage que vous cherchez ?';
    addSupervisorLabel.style.marginBottom = '1%';
    addSupervisorDiv.appendChild(addSupervisorLabel);
    const addSupervisorButton = document.createElement('button');
    addSupervisorButton.textContent = 'Ajouter un nouveau responsable de stage';
    addSupervisorButton.className = 'btn btn-primary';
    addSupervisorButton.style.width = '50%';
    addSupervisorButton.addEventListener('click', () => {
        addNewSupervisorForm();
    });
    addSupervisorDiv.appendChild(addSupervisorButton);
    return addSupervisorDiv;
}

function addNewSupervisorForm() {
    const main = document.querySelector('main');
    const form = document.createElement('form');
    form.className = 'p-5';
    const firstNameLabel = document.createElement('label');
    firstNameLabel.textContent = 'Prénom';
    form.appendChild(firstNameLabel);
    const firstNameInput = document.createElement('input');
    firstNameInput.type = 'text';
    firstNameInput.className = 'form-control';
    form.appendChild(firstNameInput);
    const lastNameLabel = document.createElement('label');
    lastNameLabel.textContent = 'Nom';
    form.appendChild(lastNameLabel);
    const lastNameInput = document.createElement('input');
    lastNameInput.type = 'text';
    lastNameInput.className = 'form-control';
    form.appendChild(lastNameInput);
    const emailLabel = document.createElement('label');
    emailLabel.textContent = 'Email (facultatif)';
    form.appendChild(emailLabel);
    const emailInput = document.createElement('input');
    emailInput.type = 'email';
    emailInput.className = 'form-control';
    form.appendChild(emailInput);
    const phoneLabel = document.createElement('label');
    phoneLabel.textContent = 'Téléphone';
    form.appendChild(phoneLabel);
    const phoneInput = document.createElement('input');
    phoneInput.type = 'text';
    phoneInput.className = 'form-control';
    form.appendChild(phoneInput);
    const submitButton = document.createElement('button');
    submitButton.textContent = 'Enregistrer';
    submitButton.className = 'btn btn-primary';
    submitButton.style.width = '50%';
    submitButton.style.marginTop = '5%';
    submitButton.addEventListener('click', (e) => {
        e.preventDefault();
        const supervisor = {
            firstName: firstNameInput.value,
            lastName: lastNameInput.value,
            email: emailInput.value,
            phone: phoneInput.value,
        };
        addSupervisor(supervisor);
    });
    form.appendChild(submitButton);
    main.appendChild(form);
}

async function addSupervisor(supervisor) {
    const response = await fetch('http://localhost:3000/internshipSupervisors', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getToken(),
        },
        body: JSON.stringify(supervisor),
    });
    if (response.status === 201) {
        alert('Responsable de stage ajouté avec succès');
    }
}


export default InternshipPage;
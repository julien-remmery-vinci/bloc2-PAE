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
    getSupervisors().then(supervisors => {
        supervisors.forEach(supervisor => {
            const option = document.createElement('option');
            option.value = supervisor.idSupervisor;
            option.textContent = `${supervisor.firstName  } ${  supervisor.lastName}`;
            supervisorSelect.appendChild(option);
        });
    });
    supervisorInfosDiv.appendChild(supervisorSelect);
    return supervisorInfosDiv;
}

async function getSupervisors(){
  const supervisors = await fetch('http://localhost:3000/internshipSupervisors', {
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


export default InternshipPage;
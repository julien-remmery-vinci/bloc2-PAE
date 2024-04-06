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
    mainDiv.appendChild(leftDiv);
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
    console.log(queryParams);
    console.log(queryParams.get('tradename'));
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


export default InternshipPage;
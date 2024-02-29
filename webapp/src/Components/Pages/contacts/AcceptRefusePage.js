import {clearPage} from "../../../utils/render";
import {getAuthenticatedUser} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const AcceptRefusePage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser) {
        Navigate('/login');
        window.location.reload();
    } else {
        clearPage();
        buildPage();
    }
}

function buildPage() {
    const main = document.querySelector('main');
    const title = document.createElement('h1');
    title.textContent = 'Indiquer que le contact a été accpeté ou refusé';
    const mainDiv = document.createElement('div');
    mainDiv.style.display = 'flex';
    const leftDiv = document.createElement('div');
    leftDiv.style.width = '50%';
    const contactInformations = getContactInfos();
    leftDiv.appendChild(contactInformations);
    const rightDiv = document.createElement('div');
    rightDiv.style.width = '50%';
    const form = getForm();
    rightDiv.appendChild(form);
    mainDiv.appendChild(leftDiv);
    mainDiv.appendChild(rightDiv);
    main.appendChild(mainDiv);
}

function getContactInfos() {
    const entrepriseName = document.createElement('label');
    entrepriseName.textContent = 'Entreprise';
    const entrepriseNameValue = document.createElement('input');
    entrepriseNameValue.type = 'text';
    entrepriseNameValue.value = 'entreprise.nom';
    entrepriseNameValue.readOnly = true;
    const entrepriseDesignation = document.createElement('label');
    entrepriseDesignation.textContent = 'Appellation';
    const entrepriseDesignationValue = document.createElement('input');
    entrepriseDesignationValue.type = 'text';
    entrepriseDesignationValue.value = 'entreprise.appellation';
    entrepriseDesignationValue.readOnly = true;
    const contactMeetPlace = document.createElement('label');
    contactMeetPlace.textContent = 'Lieu de rencontre';
    const entrepriseMeetPlaceValue = document.createElement('input');
    entrepriseMeetPlaceValue.type = 'text';
    entrepriseMeetPlaceValue.value = 'entreprise.lieu_rencontre';
    entrepriseMeetPlaceValue.readOnly = true;
    const contactInfosDiv = document.createElement('div');
    contactInfosDiv.appendChild(entrepriseName);
    contactInfosDiv.appendChild(entrepriseNameValue);
    contactInfosDiv.appendChild(entrepriseDesignation);
    contactInfosDiv.appendChild(entrepriseDesignationValue);
    contactInfosDiv.appendChild(contactMeetPlace);
    contactInfosDiv.appendChild(entrepriseMeetPlaceValue);
    return contactInfosDiv;
}

function getForm() {
    const contactState = document.createElement('label');
    contactState.textContent = 'Etat';
    const contactStateValue = document.createElement('select');
    contactStateValue.addEventListener('change', (event) => {
        console.log(event.target.value);
    });
    const optionAccepted = document.createElement('option');
    optionAccepted.value = 'true';
    optionAccepted.textContent = 'Accepté';
    const optionRefused = document.createElement('option');
    optionRefused.value = 'false';
    optionRefused.textContent = 'Refusé';
    contactStateValue.appendChild(optionAccepted);
    contactStateValue.appendChild(optionRefused);
    const refusalReason = document.createElement('label');
    refusalReason.hidden = true;
    refusalReason.textContent = 'Raison du refus';
    const refusalReasonValue = document.createElement('input');
    refusalReasonValue.type = 'text';
    refusalReasonValue.placeholder = 'Entrer la raison du refus';
    refusalReasonValue.hidden = true;
    contactStateValue.addEventListener('change', (event) => {
        refusalReason.hidden = event.target.value !== 'false';
        refusalReasonValue.hidden = event.target.value !== 'false';
    });
    const submit = document.createElement('input');
    submit.type = 'submit';
    submit.value = 'Enregistrer';
    const form = document.createElement('form');
    form.addEventListener('submit', onSubmit);
    form.appendChild(contactState);
    form.appendChild(contactStateValue);
    form.appendChild(refusalReason);
    form.appendChild(refusalReasonValue);
    form.appendChild(submit);
    return form;
}

function onSubmit() {
    // TODO: Implement onSubmit function
}

export default AcceptRefusePage;
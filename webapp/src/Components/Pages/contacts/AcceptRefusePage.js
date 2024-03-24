import {clearPage} from "../../../utils/render";
import {
    getToken,
    isAuthenticated
} from "../../../utils/auths";
import Navigate from "../../Router/Navigate";

const AcceptRefusePage = () => {
    if (!isAuthenticated()) {
        Navigate('/login');
    } else {
        clearPage();
        document.title = "Accepter ou refuser un contact";
        buildPage();
    }
}

function buildPage() {
    const main = document.querySelector('main');
    const title = document.createElement('h3');
    title.textContent = 'Indiquer que le contact a été accepté ou refusé';
    title.style.textAlign = 'center';
    main.appendChild(title);
    const mainDiv = document.createElement('div');
    mainDiv.style.display = 'flex';
    const leftDiv = document.createElement('div');
    leftDiv.style.width = '50%';
    leftDiv.appendChild(getContactInfos());
    const rightDiv = document.createElement('div');
    rightDiv.style.width = '50%';
    rightDiv.appendChild(getForm());
    const errorDiv = document.createElement('div');
    errorDiv.className = 'alert alert-danger';
    errorDiv.hidden = true;
    errorDiv.textContent = 'Erreur lors de la sauvegarde';
    rightDiv.appendChild(errorDiv);
    mainDiv.appendChild(leftDiv);
    mainDiv.appendChild(rightDiv);
    main.appendChild(mainDiv);
}

// Display contact informations
function getContactInfos() {
    const entrepriseName = document.createElement('label');
    entrepriseName.textContent = 'Entreprise';
    const entrepriseNameValue = document.createElement('input');
    entrepriseNameValue.type = 'text';
    entrepriseNameValue.value = 'entreprise.nom';
    entrepriseNameValue.readOnly = true;
    entrepriseNameValue.className = 'bg-info form-control';
    const entrepriseDesignation = document.createElement('label');
    entrepriseDesignation.textContent = 'Appellation';
    const entrepriseDesignationValue = document.createElement('input');
    entrepriseDesignationValue.type = 'text';
    entrepriseDesignationValue.value = 'entreprise.appellation';
    entrepriseDesignationValue.readOnly = true;
    entrepriseDesignationValue.className = 'bg-info form-control';
    const contactMeetPlace = document.createElement('label');
    contactMeetPlace.textContent = 'Lieu de rencontre';
    const entrepriseMeetPlaceValue = document.createElement('input');
    entrepriseMeetPlaceValue.type = 'text';
    entrepriseMeetPlaceValue.value = 'contact.lieu_rencontre';
    entrepriseMeetPlaceValue.readOnly = true;
    entrepriseMeetPlaceValue.className = 'bg-info form-control';
    const contactInfosDiv = document.createElement('div');
    contactInfosDiv.className = 'p-5';
    contactInfosDiv.appendChild(entrepriseName);
    contactInfosDiv.appendChild(entrepriseNameValue);
    contactInfosDiv.appendChild(entrepriseDesignation);
    contactInfosDiv.appendChild(entrepriseDesignationValue);
    contactInfosDiv.appendChild(contactMeetPlace);
    contactInfosDiv.appendChild(entrepriseMeetPlaceValue);
    return contactInfosDiv;
}

// Display form to accept or refuse contact
function getForm() {
    const contactState = document.createElement('label');
    contactState.textContent = 'Etat';
    const contactStateValue = document.createElement('select');
    contactStateValue.className = 'form-select';
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
    const refusalReasonValue = document.createElement('textarea');
    refusalReasonValue.placeholder = 'Entrer la raison du refus';
    refusalReasonValue.hidden = true;
    refusalReasonValue.required = !refusalReasonValue.hidden;
    refusalReasonValue.className = 'form-control';
    refusalReasonValue.rows = 5;
    contactStateValue.addEventListener('change', (event) => {
        refusalReason.hidden = event.target.value !== 'false';
        refusalReasonValue.hidden = event.target.value !== 'false';
        refusalReasonValue.required = !refusalReasonValue.hidden;
    });
    const submit = document.createElement('input');
    submit.type = 'submit';
    submit.value = 'Enregistrer';
    submit.className = 'btn btn-primary';
    const form = document.createElement('form');
    form.className = 'p-5';
    form.addEventListener('submit', onSubmit);
    form.appendChild(contactState);
    form.appendChild(contactStateValue);
    form.appendChild(refusalReason);
    form.appendChild(refusalReasonValue);
    form.appendChild(submit);
    return form;
}

async function onSubmit(event) {
    event.preventDefault();
    const id = window.location.href.split("?")[1].split("=")[1];
    const contactState = document.querySelector('select').value;
    const refusalReason = document.querySelector('textarea').value;
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getToken(),
        },
        body: JSON.stringify({
            refusalReason,
        }),
    };
    if (contactState === 'true') {
        // TODO add accept contact
    } else {
        fetch(`http://localhost:3000/contact/${id}/refuse`, options)
        .then(request => {
            if(request.status === 401) {
                document.querySelector('.alert-danger').hidden = false;
            }
        });
    }
}

export default AcceptRefusePage;
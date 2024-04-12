import {clearPage, renderBreadcrumb} from "../../utils/render";
import {getAuthenticatedUser, isAuthenticated} from "../../utils/auths";
import Navigate from "../Router/Navigate";

const HomePage = () => {
    if (!isAuthenticated()) {
        Navigate('/login');
    } else {
        clearPage();
        renderBreadcrumb({"Accueil": "/"})
        renderHomePage(getAuthenticatedUser());
        document.title = "Home";
    }
};

function renderHomePage(authenticatedUser) {
    const main = document.querySelector('main');
    const h1 = document.createElement('h1');
    h1.textContent = `Bienvenue sur VinciOBS, ${authenticatedUser.firstname} !`;
    main.appendChild(h1);
};

export default HomePage;
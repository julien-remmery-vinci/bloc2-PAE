import { clearPage } from "../../utils/render";
import { getAuthenticatedUser } from "../../utils/auths";
import Navigate from "../Router/Navigate";

const HomePage = () => {
    const authenticatedUser = getAuthenticatedUser();
    if (!authenticatedUser) {
        Navigate('/login');
        window.location.reload();
    } else {
        clearPage();
        renderHomePage(authenticatedUser);
    }
};

function renderHomePage(authenticatedUser) {
    const main = document.querySelector('main');
    const h1 = document.createElement('h1');
    h1.textContent = `Bienvenue sur VinciOBS, ${authenticatedUser.firstname} !`;
    main.appendChild(h1);
};

export default HomePage;
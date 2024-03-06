import { clearPage } from "../../utils/render";
import { getAuthenticatedUser } from "../../utils/auths";

const HomePage = () => {
    const authenticatedUser = getAuthenticatedUser();
    clearPage();
    renderHomePage(authenticatedUser);
};

function renderHomePage(authenticatedUser) {
    const main = document.querySelector('main');
    const h1 = document.createElement('h1');
    h1.textContent = `Bienvenue sur VinciOBS, ${authenticatedUser.firstname} !`;
    main.appendChild(h1);
};

export default HomePage;
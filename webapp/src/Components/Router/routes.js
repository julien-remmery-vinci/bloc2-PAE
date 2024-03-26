import HomePage from "../Pages/HomePage";
import LoginPage from "../Pages/LoginPage";
import RegisterPage from "../Pages/RegisterPage";
import AcceptRefusePage from "../Pages/contacts/AcceptRefusePage";
import AddContactPage from "../Pages/contacts/AddContactPage";
import SearchPage from "../Pages/SearchPage";
import MeetContactPage from "../Pages/contacts/MeetContactPage";
import ContactPage from "../Pages/contacts/ContactPage";
import ProfilePage from "../Pages/ProfilePage";
import Logout from "../Logout/Logout";
import StagePage from "../Pages/stage/StagePage";

// Define routes
const routes = {
    '/':HomePage,
    '/login':LoginPage,
    '/register':RegisterPage,
    '/logout': Logout,
    '/contact/refusal': AcceptRefusePage,
    '/contact/add': AddContactPage,
    '/search': SearchPage,
    '/contact/meet': MeetContactPage,
    '/contact': ContactPage,
    '/profile': ProfilePage,
    '/stage': StagePage
};

export default routes;
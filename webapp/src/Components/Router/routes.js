import HomePage from "../Pages/HomePage";
import LoginPage from "../Pages/LoginPage";
import RegisterPage from "../Pages/RegisterPage";
import AcceptRefusePage from "../Pages/contacts/AcceptRefusePage";
import AddContactPage from "../Pages/contacts/AddContactPage";
import SearchPage from "../Pages/SearchPage";
import MeetContactPage from "../Pages/contacts/MeetContactPage";

// Define routes
const routes = {
    '/':HomePage,
    '/login':LoginPage,
    '/contact/refusal': AcceptRefusePage,
    '/register':RegisterPage,
    '/contact/add': AddContactPage,
    '/search': SearchPage,
    '/contact/meet': MeetContactPage,
};

export default routes;
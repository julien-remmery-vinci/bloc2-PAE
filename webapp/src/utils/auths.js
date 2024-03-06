import Navigate from "../Components/Router/Navigate";

const STORE_NAME = 'user_token';
const REMEMBER_ME = 'remembered';

let currentUser;

const getAuthenticatedUser = () => {
  if (currentUser !== undefined) return currentUser;

  return undefined;
};

const getUserToken = () => {
  const remembered = getRememberMe();
  const serializedToken = remembered
    ? localStorage.getItem(STORE_NAME)
    : sessionStorage.getItem(STORE_NAME);

  if (!serializedToken) return undefined;

  return JSON.parse(serializedToken);
}

const setAuthenticatedUser = (authenticatedUser) => {
  const serializedToken = JSON.stringify(authenticatedUser.token);
  const remembered = getRememberMe();
  if (remembered) localStorage.setItem(STORE_NAME, serializedToken);
  else sessionStorage.setItem(STORE_NAME, serializedToken);

  currentUser = authenticatedUser.user;
};

const isAuthenticated = () => currentUser !== undefined;

const clearAuthenticatedUser = () => {
  localStorage.clear();
  sessionStorage.clear();
  currentUser = undefined;
};

const verifyToken = async (token) => {
    const response= await fetch('http://localhost:3000/auths/user', {
      method:'GET',
      headers: {
        'Authorization': token,
      },
    });
    if(response.status === 401) {
      Navigate('/login');
      clearAuthenticatedUser();
      return false;
    }
    currentUser = await response.json();
    return true;
};

function getRememberMe() {
  const rememberedSerialized = localStorage.getItem(REMEMBER_ME);
  const remembered = JSON.parse(rememberedSerialized);
  return remembered;
}

function setRememberMe(remembered) {
  const rememberedSerialized = JSON.stringify(remembered);
  localStorage.setItem(REMEMBER_ME, rememberedSerialized);
}

export {
  getAuthenticatedUser,
  setAuthenticatedUser,
  isAuthenticated,
  clearAuthenticatedUser,
  getRememberMe,
  setRememberMe,
  verifyToken,
  getUserToken,
};

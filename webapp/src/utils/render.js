import Toast from 'bootstrap/js/dist/toast';
import Navigate from "../Components/Router/Navigate";

const clearPage = () => {
    const main = document.querySelector('main');
    main.innerHTML = '';
  };
  

  // Function to render the page title 
  const renderPageTitle = (title) => {
    if (!title) return;
    const main = document.querySelector('main');
    const pageTitle = document.createElement('h1');
    pageTitle.className = 'page-title';
    pageTitle.innerText = title;
    main.appendChild(pageTitle);
  };

const renderBreadcrumb = (path) => {
  const main = document.querySelector('main');
  const breadcrumb = document.createElement('div');
  const list = document.createElement('ol');
  list.className = 'breadcrumb';
  breadcrumb.style.overflow = 'auto';
  breadcrumb.style.margin = '10px';
  Object.entries(path).forEach(([name, url]) => {
    const item = document.createElement('li');
    item.style.color = 'blue'
    item.className = 'breadcrumb-item';
    item.textContent = name;
    item.style.cursor = 'pointer';
    item.addEventListener('click', () => {
      Navigate(url);
    });
    list.appendChild(item);
  });
  list.lastChild.className = 'breadcrumb-item active';
  list.lastChild.style.color = 'gray'
  breadcrumb.appendChild(list);
  main.prepend(breadcrumb);
}

function displayToast(message, type = 'danger') {
  const toast = document.createElement('div');
  toast.className = `toast align-items-center text-white bg-${type} border-0`;
  toast.role = 'alert';
  toast.ariaLive = 'assertive';
  toast.ariaAtomic = 'true';

  toast.style.position = 'fixed';
  toast.style.bottom = '20px';
  toast.style.right = '20px';

  const toastBody = document.createElement('div');
  toastBody.className = 'toast-body';
  toastBody.innerText = message;

  toast.appendChild(toastBody);
  document.body.appendChild(toast);

  const toastInstance = new Toast(toast);

  toastInstance.show();

  toast.addEventListener('hidden.bs.toast', () => {
    document.body.removeChild(toast);
  });
}
  
export {
  clearPage,
  renderPageTitle,
  renderBreadcrumb,
  displayToast
};
  
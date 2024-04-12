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
  breadcrumb.style.maxHeight = '5vh'
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
  
export {
  clearPage,
  renderPageTitle,
  renderBreadcrumb
};
  
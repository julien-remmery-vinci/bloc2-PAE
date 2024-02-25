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
  
  export { clearPage, renderPageTitle };
  
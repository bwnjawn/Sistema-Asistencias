const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registrarLink = document.querySelector('.registrar-link');

registrarLink.addEventListener('click', ()=> {wrapper.classList.add('active');});
loginLink.addEventListener('click', ()=> {wrapper.classList.remove('active');});
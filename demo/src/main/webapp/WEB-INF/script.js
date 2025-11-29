const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registrarLink = document.querySelector('.registrar-link');
const btnPopup = document.querySelector('.btnLogin-popup');
const IconClose = document.querySelector('.icon-close');

registrarLink.addEventListener('click', ()=> {wrapper.classList.add('active');});
loginLink.addEventListener('click', ()=> {wrapper.classList.remove('active');});
btnPopup.addEventListener('click', ()=> {wrapper.classList.add('active-popup')});
IconClose.addEventListener('click', ()=> {wrapper.classList.remove('active-popup');});

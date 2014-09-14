$(function(){
    document.documentElement.innerHTML = document.documentElement.innerHTML.replace(/(\${[A-z][A-z0-9.]*[A-z]})/g,function(identify){return eval("ViewData."+identify.match(/[A-z][A-z0-9.]*[A-z]/)[0])});
})

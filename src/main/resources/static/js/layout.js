"use strict";
(function () {
    var menuArray = document.getElementsByClassName("aside-wrapper-menu-item");
    function menuClickHandler(evt) {
        if (this.parentNode.className.indexOf("active") == -1) {
            for(var i=0;i<menuArray.length;i++){
                menuArray[i].parentNode.classList.remove("active");
                menuArray[i].nextElementSibling.style.height=0+"px";
            }
            this.parentNode.classList.add("active");
            this.nextElementSibling.style.height=this.nextElementSibling.childElementCount*35+"px";
        } else {
            this.parentNode.classList.remove("active");
            this.nextElementSibling.style.height=0+"px";
        }
        console.log(evt);
    }
    for(var i=0;i<menuArray.length;i++){
        if(menuArray[i].parentNode.className.indexOf("active") >-1) {
            menuArray[i].nextElementSibling.style.height=menuArray[i].nextElementSibling.childElementCount*35+"px";
        }
        menuArray[i].addEventListener("click",menuClickHandler);
    }
})();
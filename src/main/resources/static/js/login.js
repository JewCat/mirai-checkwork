'use strict';
(function() {
    let checkBox = document.querySelector("input[type=checkbox]");
    let form = document.querySelector("form[name=f]");
    let storage = window.localStorage;

    if (storage.getItem('username') != null) {
        checkBox.checked = true;
        document.querySelector('input[name=username]').value = storage.getItem('username');
        document.querySelector('input[name=password]').value = storage.getItem('password');
    }

    form.addEventListener('submit', function (evt) {
        evt.preventDefault();
        let username = document.querySelector('input[name=username]').value;
        let password = document.querySelector('input[name=password]').value;

        if (checkBox.checked) {
            storage.setItem('username', username);
            storage.setItem('password', password);
        }
        else {
            storage.removeItem('username');
            storage.removeItem('password');
        }
        form.submit();
    })

})();
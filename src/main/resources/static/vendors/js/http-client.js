class HttpClient {
    static get(url, successCallback, failCallback) {
        ajaxCall(url, 'GET', '', successCallback, failCallback);
    }

    static post(url, requestBody, successCallback, failCallback) {
        ajaxCall(url, 'POST', requestBody, successCallback, failCallback);
    }
}

const ajaxCall = (url, method, requestBody, successCallback, failCallback) => {
    $.ajax({
        type: method,
        url: url,
        data: JSON.stringify(requestBody),
        dataType: 'json',
        contentType: 'application/json'
    }).done(function(res) {
        successCallback(res);
    }).fail(function(err) {
        failCallback(err);
    });
};


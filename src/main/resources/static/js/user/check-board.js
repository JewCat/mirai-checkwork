// Geolocation
let longitude;
let latitude;

if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
        longitude = position.coords.longitude;
        latitude = position.coords.latitude;
    });
} else {
    alert("Geolocation is not supported by this browser.");
}

navigator.geolocation.watchPosition(function(position) {},
    function(error) {
        if (error.code === error.PERMISSION_DENIED) {
            document.querySelector('.btn-check').setAttribute('disabled', 'true');
            alert("Please allow geolocation permission on this domain");
        }
    });

// Date time
const displayDateTime = function() {
    let dateTime = new Date();
    let date = ('0' + dateTime.getDate()).slice(-2) + '/' +
        ('0' + (dateTime.getMonth() + 1)).slice(-2) + '/' +
        dateTime.getFullYear();
    let time = ('0' + dateTime.getHours()).slice(-2) + ':' +
        ('0' + dateTime.getMinutes()).slice(-2) + ':' +
        ('0' + dateTime.getSeconds()).slice(-2);
    document.querySelector('.date-display').innerHTML = date;
    document.querySelector('.time-display').innerHTML = time;
    setTimeout(displayDateTime, 1000);
};
displayDateTime();

// Form handler
let form = document.querySelector('form[name=check-form]');
form.addEventListener('submit', function(evt) {
    evt.preventDefault();
    let data = {
        'longitude': longitude,
        'latitude': latitude
    };

    $.ajax({
        type: "POST",
        url: '/check',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json'
    }).done(function(data) {
        Swal.fire({
            icon: 'success',
            title: 'OK',
            text: data.data.checkNum == 1 ? 'You have checked-in successfully !' : 'You have checked-out successfully !',
        }).then(rs => {
            window.location.reload();
        });
    }).fail(function(err) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: err.responseJSON.message,
        });
    });
});
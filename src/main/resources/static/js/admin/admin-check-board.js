let isDatePickerDisplay = false;
let myCalendar = jsCalendar.new(document.querySelector('.my-jsCalendar'));
let count = 0;

myCalendar.onDateClick(function(evt, date) {
    closeDatePicker();
    document.querySelector('.date-display').innerHTML = ('0' + date.getDate()).slice(-2) + '/' +
        ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
        date.getFullYear();

    HttpClient.get('/admin/check-board/' + date.getTime(),
        function (res) {
            document.querySelector('.check-list-tbody').innerHTML = '';
            res.data.forEach(i => {
                let checkInTime = i.checkInTime != null
                    ?   i.checkInTime
                    :   '';
                let checkOutTime = i.checkOutTime != null
                    ?   i.checkOutTime
                    :   '';
                document.querySelector('.check-list-tbody').innerHTML +=
                    `<tr>
                            <td>${i.userId}</td>
                            <td>${i.userName}</td>
                            <td>${checkInTime}</td>
                            <td>${checkOutTime}</td>
                        </tr>`
            })
        },
        function(err) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: err.responseJSON.message,
            });
        });
});

document.querySelector('.btn-date-picker').addEventListener('click', function() {
    if (isDatePickerDisplay) {
        closeDatePicker();
    }
    else {
        openDatePicker();
    }
});

closeDatePicker = function() {
    isDatePickerDisplay = false;
    document.querySelector('.my-jsCalendar').classList.remove('my-jsCalendar-show');
    document.querySelector('.my-jsCalendar').classList.add('my-jsCalendar-hide');
    document.querySelector('.shadow-layer').classList.remove('d-block');
    document.querySelector('.shadow-layer').classList.add('d-none');
};

openDatePicker = function() {
    isDatePickerDisplay = true;
    document.querySelector('.my-jsCalendar').classList.remove('my-jsCalendar-hide');
    document.querySelector('.my-jsCalendar').classList.add('my-jsCalendar-show');
    document.querySelector('.shadow-layer').classList.remove('d-none');
    document.querySelector('.shadow-layer').classList.add('d-block');
};
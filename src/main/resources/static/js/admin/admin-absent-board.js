let isDatePickerDisplay = false;
let myCalendar = jsCalendar.new(document.querySelector('.my-jsCalendar'));
let count = 0;

myCalendar.onDateClick(function(evt, date) {
    closeDatePicker();
    document.querySelector('.date-display').innerHTML = ('0' + date.getDate()).slice(-2) + '/' +
        ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
        date.getFullYear();

    HttpClient.get('/admin/absent-board/' + date.getTime(),
        function (res) {
            console.log(res)
            document.querySelector('.absent-list-tbody').innerHTML = '';
            res.data.forEach(i => {
                let html = ``;
                switch (i.absentShift) {
                    case 1:
                        html =
                            `<tr class="bg-gradient-warning">
                                <td>${i.userId}</td>
                                <td>${i.userName}</td>
                                <td>1st</td>
                            </tr>`
                        break;
                    case 2:
                        html =
                            `<tr class="bg-gradient-primary">
                                <td>${i.userId}</td>
                                <td>${i.userName}</td>
                                <td>2nd</td>
                            </tr>`
                        break;
                    case 3: 
                    html =
                            `<tr class="bg-gradient-danger">
                                <td>${i.userId}</td>
                                <td>${i.userName}</td>
                                <td>All day</td>
                            </tr>`
                        break;
                }                    
                document.querySelector('.absent-list-tbody').innerHTML += html;
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
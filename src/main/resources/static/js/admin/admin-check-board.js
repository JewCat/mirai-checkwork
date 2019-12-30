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
                        <td>
                            ${i.checkId != 0 
                                ?   `<button class="btn btn-sm btn-gradient-success" onclick="showModal(this)" data-id=${i.checkId}>
                                        <i class="mdi mdi-table-edit"></i>
                                    </button>`
                                :   ``
                            }
                        </td>
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


// Edit
const pureCheckInInput = $('#edit-checkin-time').clone();
const pureCheckOutInput = $('#edit-checkout-time').clone();
let showModal = function(target) {
    let id = parseInt(document.querySelector('.btn-edit').dataset.id = target.dataset.id);
    HttpClient.get('/admin/check-board/id/' + id,
        function (res) {
            console.log(res)
            let defTime = new Date();
            let wickedpickerOption1 = {
                twentyFour: true,
                showSeconds: true,
                now: res.data != null && res.data.checkInTime != null
                    ? res.data.checkInTime
                    : defTime.getHours() + ':' + defTime.getMinutes() + ':' + defTime.getSeconds()
            };
            let wickedpickerOption2 = {
                twentyFour: true,
                showSeconds: true,
                now: res.data != null && res.data.checkOutTime != null
                    ? res.data.checkOutTime
                    : defTime.getHours() + ':' + defTime.getMinutes() + ':' + defTime.getSeconds()
            };
            $('#edit-checkin-time').replaceWith(pureCheckInInput.clone());
            $('#edit-checkin-time').wickedpicker(wickedpickerOption1);
            $('#edit-checkout-time').replaceWith(pureCheckOutInput.clone());
            $('#edit-checkout-time').wickedpicker(wickedpickerOption2);

            $('#edit-modal').modal('show');
        },
        function (err) {
            console.log(err)
        })

};

document.querySelector('.btn-edit').addEventListener('click', function (evt) {
    let checkInTime = new Date();
    checkInTime.setHours($('#edit-checkin-time').val().split(':')[0]);
    checkInTime.setMinutes($('#edit-checkin-time').val().split(':')[1]);
    checkInTime.setSeconds($('#edit-checkin-time').val().split(':')[2]);

    let checkOutTime = new Date();
    checkOutTime.setHours($('#edit-checkout-time').val().split(':')[0]);
    checkOutTime.setMinutes($('#edit-checkout-time').val().split(':')[1]);
    checkOutTime.setSeconds($('#edit-checkout-time').val().split(':')[2]);

    let data = {
        checkId: evt.target.dataset.id,
        checkInTime: checkInTime.getTime(),
        checkOutTime: checkOutTime.getTime()
    };

    console.log(data)

    HttpClient.post('/admin/check-board/edit', data,
        function (res) {
            Swal.fire({
                icon: 'success',
                title: 'OK',
                text: '',
            }).then(rs => {
                window.location.reload();
            });
        },
        function (err) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: err.responseJSON.message,
            });
        });

    $('#edit-modal').modal('hide');
});
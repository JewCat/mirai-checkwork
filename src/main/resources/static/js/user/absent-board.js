const myCalendar = jsCalendar.new(document.querySelector('.my-jsCalendar'));
let currentMonth;
let datePicked = {
    'absentFirstShift': [],
    'absentSecondShift': [],
    'absentAllDay': []
};
let targetPicked = {
    'absentFirstShift': [],
    'absentSecondShift': [],
    'absentAllDay': []
};

myCalendar.onDateRender(function(date, element, info) {
    if (!info.isCurrent && (date.getDay() == 0 || date.getDay() == 6)) {
        element.style.fontWeight = 'bold';
        element.style.color = (info.isCurrentMonth) ? '#07cdae' : '#84d9d2';
    }
});

myCalendar.onDateClick(function(event, date){
    if (date.getMonth() + 1 !== currentMonth) {

    }
    else if (event.target.classList.contains('bg-gradient-warning')) {
        datePicked.absentFirstShift.splice(datePicked.absentFirstShift.indexOf(date.getTime()), 1);
        datePicked.absentSecondShift.push(date.getTime());
        targetPicked.absentFirstShift
            .splice(targetPicked.absentFirstShift.indexOf({
                'month': date.getMonth() + 1,
                'target': event.target
            }), 1);
        targetPicked.absentSecondShift
            .push({
                'month': date.getMonth() + 1,
                'target': event.target
            });
        event.target.classList.remove('bg-gradient-warning', 'text-dark');
        event.target.classList.add('bg-gradient-primary', 'text-white');
    }
    else if (event.target.classList.contains('bg-gradient-primary')) {
        datePicked.absentSecondShift.splice(datePicked.absentSecondShift.indexOf(date.getTime()), 1);
        datePicked.absentAllDay.push(date.getTime());
        targetPicked.absentSecondShift
            .splice(targetPicked.absentSecondShift.indexOf({
                'month': date.getMonth() + 1,
                'target': event.target
            }), 1);
        targetPicked.absentAllDay
            .push({
                'month': date.getMonth() + 1,
                'target': event.target
            });
        event.target.classList.remove('bg-gradient-primary', 'text-white');
        event.target.classList.add('bg-gradient-danger', 'text-white');
    }
    else if (event.target.classList.contains('bg-gradient-danger')) {
        datePicked.absentAllDay.splice(datePicked.absentAllDay.indexOf(date.getTime()), 1);
        targetPicked.absentAllDay
            .splice(targetPicked.absentAllDay.indexOf({
                'month': date.getMonth() + 1,
                'target': event.target
            }), 1);
        event.target.classList.remove('bg-gradient-danger', 'text-white');
    }
    else {
        datePicked.absentFirstShift.push(date.getTime());
        targetPicked.absentFirstShift
            .push({
                'month': date.getMonth() + 1,
                'target': event.target
            });
        event.target.classList.add('bg-gradient-warning', 'text-dark');
    }

    console.log(datePicked);
    console.log(targetPicked);

});

myCalendar.onMonthRender(function(index, element, info) {
    currentMonth = info.start.getMonth() + 1;

    targetPicked.absentFirstShift.forEach(function(i) {
        if (i.month === currentMonth) {
            i.target.classList.add('bg-gradient-warning', 'text-dark');
        }
    });
    targetPicked.absentSecondShift.forEach(function(i) {
        if (i.month === currentMonth) {
            i.target.classList.add('bg-gradient-primary', 'text-white');
        }
    });
    targetPicked.absentAllDay.forEach(function(i) {
        if (i.month === currentMonth) {
            i.target.classList.add('bg-gradient-danger', 'text-white');
        }
    });
});

myCalendar.refresh();

document.querySelector('form[name=frm-absent]').addEventListener('submit', function(evt) {
    evt.preventDefault();

    $.ajax({
        type: "POST",
        url: '/absent',
        data: JSON.stringify(datePicked),
        dataType: 'json',
        contentType: 'application/json'
    }).done(function(data) {
        console.log(data);
        Swal.fire({
            icon: 'success',
            title: 'OK',
            text: '',
        });
        Swal.getConfirmButton().addEventListener('click', function() {
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

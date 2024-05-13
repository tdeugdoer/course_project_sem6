let beginDates = [];
const beginDateElements = document.querySelectorAll('h3[id^="beginDate_"]');
for (var i = 0; i < beginDateElements.length; i++) {
    beginDates.push(beginDateElements[i].textContent);
}

let endDates = [];
const endDateElements = document.querySelectorAll('h3[id^="endDate_"]');
for (var i = 0; i < endDateElements.length; i++) {
    endDates.push(endDateElements[i].textContent);
}

let disabledDates = [];

function getDatesBetween(startDate, endDate) {
    const currentDate = new Date(startDate);
    endDate = new Date(endDate);

    while (currentDate <= endDate) {
        disabledDates.push(new Date(currentDate.toISOString().slice(0, 10)));
        currentDate.setDate(currentDate.getDate() + 1);
    }
}

for (var i = 0; i < beginDates.length; i++) {
    getDatesBetween(beginDates[i], endDates[i]);
}


const reservationForm = document.getElementById('reservationForm');
const datePicker = document.getElementById('datePicker');

reservationForm.addEventListener('submit', function (event) {
    if (datePicker.value.trim() === '') {
        event.preventDefault();
        alert('Пожалуйста, выберите дату');
    }
});


const currentDate = new Date();
const maxDate = new Date().setDate(currentDate.getDate() + 30);

let selectedStartDate = null;
let selectedEndDate = null;

new AirDatepicker('#datePicker', {
    inline: false,
    range: true,
    multipleDatesSeparator: ' - ',
    minDate: currentDate,
    maxDate: maxDate,
    onSelect: ({date, datepicker}) => {
        if (!selectedStartDate) {
            selectedStartDate = date;
        } else if (!selectedEndDate) {
            selectedEndDate = date;
        } else {
            selectedStartDate = date;
            selectedEndDate = null;
        }
    },

    onBeforeSelect: ({date, datepicker}) => {
        if (selectedStartDate && !selectedEndDate) {
            const sortedDates = [selectedStartDate, date].sort((a, b) => a - b);
            const start = sortedDates[0];
            const end = sortedDates[1];

            const rangeContainsDisabledDate = isRangeContainsDisabledDate(start, end);


            if (rangeContainsDisabledDate) {
                return false;
            }
        }

        return true;
    },


    onRenderCell: ({date}) => {
        if (disabledDates.some(disabledDate => date.toLocaleDateString() === disabledDate.toLocaleDateString())) {
            return {
                disabled: true
            };
        }
    },
});

function isRangeContainsDisabledDate(start, end) {
    const currentDate = new Date(start);

    while (currentDate <= end) {
        if (disabledDates.some(disabledDate => currentDate.toLocaleDateString() === disabledDate.toLocaleDateString())) {
            return true;
        }
        currentDate.setDate(currentDate.getDate() + 1);
    }

    return false;
}
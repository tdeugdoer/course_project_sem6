let beginDates = [];
const beginDateElements = document.querySelectorAll('span[id^="beginDate_"]');
for (var i = 0; i < beginDateElements.length; i++) {
    beginDates.push(new Date(beginDateElements[i].textContent));
}

let endDates = [];
const endDateElements = document.querySelectorAll('span[id^="endDate_"]');
for (var i = 0; i < endDateElements.length; i++) {
    endDates.push(new Date(endDateElements[i].textContent));
}

const priceElements = document.querySelectorAll('td[id^="price_"]');
priceElements.forEach((element, index) => {
    const diffInMilliseconds = Math.abs(endDates[index] - beginDates[index]);
    const diffInDays = Math.ceil(diffInMilliseconds / (1000 * 60 * 60 * 24)) + 1;
    element.textContent = parseFloat(element.textContent) * diffInDays;
});
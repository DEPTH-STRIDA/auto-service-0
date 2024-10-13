// Проверяет достаточно ли данных для оформления заявки.
function CheckRequest() {
    var client = $jsapi.context().client;
    
    var counter = 0;
    
    if (CheckFullName()) {
        counter++;
    }
    if (client.phone) {
        counter++;
    }
    if (client.car) {
        counter++;
    }
    
    return counter >= 2;
}

// Проверяет полноту данных об имени пользователя.
function CheckFullName() {
    var client = $jsapi.context().client;
    return !!(client.firstName && client.lastName && client.patronymic);
}

// Получить сообщения в случае недостатка информации
function GetServiceMessage() {
    var client = $jsapi.context().client;
    
    var msg = 'Для оформления заявки на ТО, пожалуйста, укажите:\n';
    
    if (!client.firstName || !client.lastName || !client.patronymic) {
        var nameFields = [];
        if (!client.firstName) nameFields.push("имя");
        if (!client.lastName) nameFields.push("фамилию");
        if (!client.patronymic) nameFields.push("отчество");
        msg += '- ' + nameFields.join(", ") + '\n';
    }
    
    if (!client.phone) {
        msg += '- телефон\n';
    }
    if (!client.car) {
        msg += '- марку автомобиля\n';
    }
    
    return msg;
}

// Возвращает сообщение, которое уведомляет пользователя о достаточности данных для заявки.
function GetReadyMessage() {
    var client = $jsapi.context().client;
    
    var msg = 'Оформляю заявку на техобслуживание на следующие данные:\n';
    
    if (CheckFullName()) {
        msg += client.lastName + ' ' + client.firstName + ' ' + client.patronymic + '\n';
    }
    if (client.phone) {
        msg += client.phone + '\n';
    }
    if (client.car) {
        msg += client.car + '\n';
    }
    
    msg += 'Данные верны?';
    
    return msg;
}
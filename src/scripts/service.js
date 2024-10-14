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
    return !!(client.firstName && client.lastName); // Отчество не учитывается в проверке
}

// Получить сообщения в случае недостатка информации
function GetServiceMessage() {
    var client = $jsapi.context().client;
    
    var msg = 'Для оформления заявки на ТО, пожалуйста, введите:\n';
    
    if (!client.firstName || !client.lastName) {
        var nameFields = [];
        if (!client.firstName) nameFields.push("имя");
        if (!client.lastName) nameFields.push("фамилию");
        msg += '- ' + nameFields.join(", ") + '\n';
    }
    
    if (!client.phone) {
        msg += '- телефон\n';
    }
    if (!client.car) {
        msg += '- марку автомобиля\n';
    }
    
    msg += '\nВы можете указать все данные сразу или по одному.\n';
    
    return msg;
}

// Возвращает сообщение, которое уведомляет пользователя о достаточности данных для заявки.
function GetReadyMessage() {
    var client = $jsapi.context().client;
    
    var msg = 'Оформляю заявку на техобслуживание на следующие данные:\n';
    
    if (CheckFullName()) {
        msg += client.lastName + ' ' + client.firstName;
        if (client.patronymic) {
            msg += ' ' + client.patronymic;
        }
        msg += '\n';
    }
    if (client.phone) {
        msg += 'номер телефона ' + client.phone + '\n';
    }
    if (client.car) {
        msg += 'автомобиль ' + client.car + '\n';
    }
    
    msg += '\nДля изменения данных просто введите новую информацию.';
    
    return msg;
}
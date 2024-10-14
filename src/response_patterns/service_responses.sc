require: slotfilling/slotFilling.sc
    module = sys.zb-common

theme: /
    
    state: ResponseService
        q: * || fromState = "/Analyzer"
        script:
            if ($entities && $entities.length > 0) {
                var targetEntities = ["pymorphy.name", "mystem.famn", "pymorphy.patr", "duckling.phone-number", "Марка_автомобиля"];
                var foundTypes = {};
                $entities.forEach(function(entity) {
                    if (targetEntities.indexOf(entity.pattern) !== -1 && !foundTypes[entity.pattern]) {
                        foundTypes[entity.pattern] = true;
                        switch(entity.pattern) {
                            case "pymorphy.name":
                                $client.firstName = capitalize(entity.value);
                                break;
                            case "mystem.famn":
                                $client.lastName = capitalize(entity.value);
                                break;
                            case "pymorphy.patr":
                                $client.patronymic = capitalize(entity.value);
                                break;
                            case "duckling.phone-number":
                                // Используем функцию нормализации для номера телефона
                                $client.phone = normalizePhoneNumber(entity.value);
                                break;
                            case "Марка_автомобиля":
                                $client.car = entity.value;
                                break;
                        }
                    }
                });
            }
        if: CheckRequest()
            a: {{GetReadyMessage()}}
            buttons:
                "Отправить" -> /ResponseSendRequest
        else: 
            a: {{GetServiceMessage()}}
        buttons:
            "Отмена" -> /ResponseMenu


    
    state: ResponseSendRequest
        HttpRequest: 
            url = https://auto-service/request
            method = PUT
            dataType = 
            body = {
                "name":"{{$client.name}}",
                "phone":"{{$client.phone}}",
                "car":"{{$client.car}}"
                }
            timeout = 0
            headers = [{"name":"","value":""}]
            vars = []
            okState = /ResponseSuccessRequest
            errorState = /ResponseErrorRequest
    
    state: ResponseReadyMessage
        a: {{GetReadyMessage()}}
        buttons:
            "Отправить" -> /ResponseSendRequest
            "Отмена" -> /ResponseMenu

    state: ResponseServiceMessage
        a: {{GetServiceMessage()}}
        buttons:
            "Отмена" -> /ResponseMenu

    state: ResponseSuccessRequest
        a: Заявка успешно отправлена.
        go!: /ResponseMenu

    state: ResponseErrorRequest
        a: К сожалению, я никак не могу связаться с сервером. Попробуйте позже, пожалуйста.
        go!: /ResponseMenu

    state: ResponseDeletePatronymic
        script:
            $client.patronymic = "";
        a: Отчество удалено

    state: ResponseDeleteLastName
        script:
            $client.lastName = "";
        a: Фамилия удалена

    state: ResponseDeleteFirstName
        script:
            $client.firstName = "";
        a: Имя удалено

    state: ResponseDeletePhone
        script:
            $client.phone = "";
        a: Номер телефона удален

    state: ResponseDeleteCar
        script:
            $client.car = "";
        a: Марка автомобиля удалена
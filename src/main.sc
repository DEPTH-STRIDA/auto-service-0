require: slotfilling/slotFilling.sc
    module = sys.zb-common
# SC
require: general_patterns.sc
require: service_patterns.sc
require: communication_patterns.sc
# JS
require: scripts/service.js
require: scripts/utils.js

init:
    bind("preProcess", function($context) {
        if (!$context.request.query){
            return
        }
        
        if ($context.request.query.length > 200) {
            $context.temp.targetState  = "/NoMatch";
        } else {
            $context.request.query = $context.request.query.replace(/[^a-zа-яё0-9\s]/gi, ' ');
        }
    });

    bind("postProcess", function($context) {
        $context.session.lastState = $context.currentState;
    });
 
theme: /

    state: Start || sessionResult = "Сценарий начинается отсюда", sessionResultColor = "#143AD1"
        q!: $regex</start>
        script:
            $client.firstName = ""
            $client.lastName = ""
            $client.patronymic=""
            $client.phone = ""
            
        a: Привет! 👋 Я виртуальный помощник автосервиса.
        go!: /Menu

    state: NoMatch
        event!: noMatch
        a: Я не понял. Пожалуйста, перефразируйте вопрос.
        go!: /Menu

    state: Menu
        a: Чем я могу вам помочь?
        buttons:
            "Записаться на ТО" -> /Service

    state: Service
        go!: /Analyzer

    state: Analyzer
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
                "Отправить" -> /SendRequest
        else: 
            a: {{GetServiceMessage()}}
        buttons:
            "Отмена" -> /Menu

    state: SendRequest
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
            okState = /SuccesRequest
            errorState = /ErrorRequest

    state: ErrorRequest
        a: К сожалению, я никак не могу связаться с сервером. Попробуйте позже, пожалуйста.
        go!: /Menu

    state: SuccessRequest
        a: Заявка успешно отправлена.
        go!: /Menu
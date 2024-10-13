require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: scripts/service.js
    
theme: /
    state: Start || sessionResult = "Сценарий начинается отсюда", sessionResultColor = "#143AD1"
        q!: $regex</start>
        a: Привет! 👋 Я виртуальный помощник автосервиса.
        go!: /Menu

    state: NoMatch
        event!: noMatch
        a: Я не понял. Пожалуйста, перефразируйте вопрос.
        go!: /Menu
    state: Hello || noContext = true, sessionResultColor = "#FFFF00"
        intent!: /sys/aimylogic/ru/hello
        a: Здравствуйте!
    state: GoodBye || noContext = true, sessionResultColor = "#FFFF00"
        q!: $regex</start>
        a: До свидания!
        intent!: /sys/aimylogic/ru/parting
    state: HowAreYou
        intent!: /Как дела
        random: 
            a: Отлично! А у вас?
            a: Отлично! А вы?
        intent: /sys/aimylogic/ru/approval || toState = "/GoodMood"
        intent: /sys/aimylogic/ru/negative || toState = "/BadMood"
        intent: /sys/aimylogic/ru/insults || toState = "/BadMood"
        event: noMatch || toState = "/UnknownReaction"
    state: UnknownReaction
        a: Понятно. Задайте
                мне любой вопрос про 
                наш автосервис.
        go!: /ReturnTo
    state: GoodMood
        a: Я рад. Теперь давайте
            поговорим про 
            автомобили.
        go!: /ReturnTo

    state: BadMood
        a: Жаль. Возможно, я
                смогу помочь. Задайте 
                мне любой вопрос про 
                наш автосервис.
        go!: /ReturnTo
    state: YourName
        intent!: /Кто ты
        a: Меня зовут Вася.
        intent: /sys/aimylogic/ru/name || toState = "/YourNameReaction"
    state: YourNameReaction
        a: Приятно
            познакомиться!
        go!: /ReturnTo
    state: Service
        q!: то
        q!: тех
        q!: *тех*
        q!: *обс*
        q!: *смотр*
        script:
            $session.lastState = "/Service"
        if: CheckRequest()
            a: {{GetReadyMessage()}}
            go!: /Confirmation
        else: 
            a: a: {{GetServiceMessage()}}
            go: /Analyzer
        event: noMatch || toState = "/Service"
    state: ReturnTo
        if: $session.lastState && $session.lastState === "/Service"
            a: Давайте вернемся к заполнению заявки на ТО.
            go!: /Service
        else: 
            go!: /Menu
    state: Confirmation
        intent: /sys/aimylogic/ru/agreement || toState = "/SendRequest"
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
        a: К сожалению, я никак не могу связаться с сервером. Попробуйте позже, пожалуйста. || htmlEnabled = false, html = "К сожалению, я никак не могу связаться с сервером. Попробуйте позже, пожалуйста."
        go!: /Service
    state: SuccesRequest
        a: Заявка успешно отравлена. || htmlEnabled = false, html = "Заявка успешно отравлена."
        go!: /Service

    state: Menu
        intent!: /Меню
        a: Чем я могу вам помочь?
        script:
            $session.lastState = "/Menu"
        buttons:
            "Записаться на ТО" -> /Service
    state: Analyzer
        q: * || fromState = "/Analyzer"
        script:
            // Список нужных системных сущностей
            var targetEntities = ["pymorphy.name", "mystem.famn", "pymorphy.patr", "duckling.phone-number", "Марка_автомобиля"];
            
            // Объект для отслеживания, какие сущности уже были найдены
            var foundTypes = {};
            
            // Фильтрация сущностей из $entities
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
                            $client.phone = entity.value;
                            break;
                        case "Марка_автомобиля":
                            $client.car = entity.value;
                            break;
                    }
                }
            });
    
        if: CheckRequest()
            a: {{GetReadyMessage()}}
            go!: /Confirmation
        else: 
            a: {{GetServiceMessage()}}
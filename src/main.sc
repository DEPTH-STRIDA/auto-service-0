require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: generalCommunication.sc

theme: /
    state: Start || sessionResult = "Сценарий начинается отсюда", sessionResultColor = "#143AD1"
        q!: $regex</start>
        a: Привет! 👋 Я виртуальный помощник автосервиса.
            
            Расскажите, что привело вас к нам сегодня? Может, пришло время для техобслуживания? Или у вас есть вопросы о вашем автомобиле?
            
            Не стесняйтесь, спрашивайте о чём угодно - от записи на ТО до стоимости услуг. Я здесь, чтобы помочь! 😊
            
            P.S. Если вам удобнее использовать команды:
            🔧 /service - записаться на ТО
            📋 /menu - посмотреть все возможности || htmlEnabled = true, html = "Привет! 👋 Я виртуальный помощник автосервиса.&nbsp;<br><br>            Расскажите, что привело вас к нам сегодня? Может, пришло время для техобслуживания? Или у вас есть вопросы о вашем автомобиле?<br><br>            Не стесняйтесь, спрашивайте о чём угодно - от записи на ТО до стоимости услуг. Я здесь, чтобы помочь! 😊<br><br>            P.S. Если вам удобнее использовать команды:<br>            🔧 /service - записаться на ТО<br>            📋 /menu - посмотреть все возможности"

    state: NoMatch
        event!: noMatch
        a: Я не понял. Пожалуйста, перефразируйте вопрос.
        
    state: Hello || noContext = true
        intent: /sys/aimylogic/ru/hello
        a: Здравствуйте!
        
    state: GoodBey
        a: До свидания!
        intent: /sys/aimylogic/ru/parting || noContext = true

    state: HowAreYou
        random: 
            a: Отлично! А у вас?
            a: Отлично! А вы?
        go!: /HowAreYouReaction

    state: HowAreYouReaction
        state: GoodMood
            q: * (хорошо|отлично|замечательно|прекрасно|супер|нормально|неплохо) *
            a: Я рад. Теперь давайте
                поговорим про 
                автомобили.
            intent: /sys/aimylogic/ru/approval || toState = "./"

        state: BadMood
            q: * (плохо|ужасно|отвратительно|паршиво|не очень|так себе) *
            a: Жаль. Возможно, я
                смогу помочь. Задайте 
                мне любой вопрос про 
                наш автосервис.
            intent: /sys/aimylogic/ru/insults || toState = "./"
            intent: /sys/aimylogic/ru/negative || toState = "./"

        state: Neutral
            intent: /sys/aimylogic/ru/normal || toState = "./"
            a: Понятно. Задайте
                мне любой вопрос про 
                наш автосервис.

    state: Service
        q!: /service
        a: Отлично, вы хотите записаться на техобслуживание. Давайте начнем с марки вашего автомобиля. Какой у вас автомобиль?


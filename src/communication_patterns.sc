require: slotfilling/slotFilling.sc
  module = sys.zb-common

theme: /
    
    
    state: Hello || noContext = true
        q!: * (прив*/здравствуй*/добрый день/добрый вечер/доброе утро) *
        a: Здравствуйте!
        
    state: GoodBye || noContext = true
        q!: * (пока/до свидания/прощай) *
        a: До свидания!



    state: HowAreYou  || noContext = true
        q!: * как* (дела/ты/вы/поживае*) *
        a: Отлично! А у вас?
        go: ./WaitMoodResponse

        state: WaitMoodResponse || noContext = true
            q: * (хорошо/отлично/нормально/супер/здорово) *
            a: Я рад. Теперь давайте поговорим про автомобили.
            go!: /Menu

            q: * (плохо/ужасно/не очень/так себе) *
            a: Жаль. Возможно, я смогу помочь. Задайте мне любой вопрос про наш автосервис.
            go!: /Menu

            q: *
            a: Понятно. Чем я могу вам помочь?
            go!: /Menu



    state: YourName || noContext = true
        q!: * (как тебя зовут/кто ты/твое имя) *
        a: Меня зовут Вася.
        go: ./WaitNameResponse

        state: WaitNameResponse || noContext = true
            intent: /sys/aimylogic/ru/name || toState = "/YourName/OnNameResponse"
        
    
        state: OnNameResponse || noContext = true
            a: Приятно познакомиться!
            go!: /Menu
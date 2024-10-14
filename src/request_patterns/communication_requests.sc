theme: /
    state: RequestHello 
        q!: * $Greeting *
        go!: /ResponseHello

    state: RequestHowAreYou
        q!: * $HowAreYou *
        go!: /ResponseHowAreYou

    state: RequestYourName 
        q!: * $AskName *
        go!: /ResponseYourName
        
    state: RequestUserName
        intent: /sys/aimylogic/ru/name || toState = "/ResponseUserName"

    state: RequestGoodBye 
        q!: * $Goodbye *
        go!: /ResponseUserName
        
        
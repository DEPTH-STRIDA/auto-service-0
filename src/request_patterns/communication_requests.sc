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

    state: RequestGoodBye 
        q!: * $Goodbye *
        go!: /ResponseGoodBye
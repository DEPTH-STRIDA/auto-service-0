theme: /
    state: ResponseStart || sessionResult = "Сценарий начинается отсюда", sessionResultColor = "#143AD1"
        q!: $regex</start>
        script:
            $client.firstName = ""
            $client.lastName = ""
            $client.patronymic=""
            $client.phone = ""
            
        go!: /ResponseMenu
        
    
    state: RequestServiceCost
        q!: * {(стоимость|цена|сколько стоит) * $TO}
        q!: * {дорогое * $TO}
        go!: /ResponseServiceCost

    state: RequestServiceSchedule
        q!: * {когда (делать|проходить) * $TO}
        q!: * {нужно ли $TO *}
        q!: * {график * $TO}
        go!: /ResponseServiceSchedule

    state: RequestServiceNeed
        q!: * {зачем нужно * $TO}
        q!: * {для чего * $TO}
        q!: * {почему важно * $TO}
        go!: /ResponseServiceNeed

    state: RequestServiceDiscounts
        q!: * {(скидки|акции) * $TO}
        go!: /ResponseServiceDiscounts

    state: RequestServiceDuration
        q!: * {сколько (времени|часов) * $TO}
        q!: * {как долго (делают|проводят) * $TO}
        go!: /ResponseServiceDuration

    state: RequestServiceIncluded
        q!: * {что входит * $TO}
        q!: * {план * (работ|$TO)}
        go!: /ResponseServiceIncluded
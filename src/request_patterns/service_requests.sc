theme: /

    state: RequestService
        q!: $TO
        q!: * {$TO * $TOAction}
        q!: * {$TONeed * $TO}
        q!: * {$Vehicle * $TO}
        go!: /ResponseService

    state: RequestDeletePatronymic
        q: * {$Delete * отчеств*}
        go!: /ResponseDeletePatronymic

    state: RequestDeleteLastName
        q: * {$Delete * фам*}
        go!: /ResponseDeleteLastName

    state: RequestDeleteFirstName
        q: * {$Delete * имя*}
        go!: /ResponseDeleteFirstName

    state: RequestDeletePhone
        q: * {$Delete * (ном*|тел*)*}
        go!: /ResponseDeletePhone

    state: RequestDeleteCar
        q: * {$Delete * (маш*|марк*|авто|авто*)*}
        go!: /ResponseDeleteCar
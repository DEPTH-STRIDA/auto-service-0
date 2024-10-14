patterns:
    $Name = (Марка_автомобиля)
    $Phone = $regexp<((\+7|7|8)?(\s*)?(\(?9\d{2}\)?)?(\s*)?(\d{3})(\s*)?(\d{2})(\s*)?(\d{2}))>
    $Delete = (удал*|очист*|стер*|убер*)

theme: /

    state: DeletePatronymic || noContext = true
        q: * {$Delete * отчеств*}
        script:
            $client.patronymic = "";
        a: Отчество удалено

    state: DeleteLastName || noContext = true
        q: * {$Delete * фам*}
        script:
            $client.lastName = "";
        a: Фамилия удалена

    state: DeleteFirstName || noContext = true
        q: * {$Delete * имя*}
        script:
            $client.firstName = "";
        a: Имя удалено

    state: DeletePhone || noContext = true
        q: * {$Delete * (ном*|тел*)*}
        script:
            $client.phone = "";
        a: Номер телефона удален

    state: DeleteCar || noContext = true
        q: * {$Delete * (маш*|марк*|авто|авто*)*}
        script:
            $client.car = "";
        a: Марка автомобиля удалена
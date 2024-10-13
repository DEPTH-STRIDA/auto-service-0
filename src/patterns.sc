theme: /
patterns:
    $Name = $regexp<([А-Я][а-я]+(\s[А-Я][а-я]+){1,2})>
    $Phone = $regexp<(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}>
    $Car = $regexp<(([А-Я][а-я]+)|([A-Z][a-z]+))>
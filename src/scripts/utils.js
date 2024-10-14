function normalizePhoneNumber(phone) {
    phone = phone.replace(/\D/g, '');
    
    if (phone.startsWith('8')) {
        phone = '7' + phone.slice(1);
    }
    
    if (!phone.startsWith('7')) {
        phone = '7' + phone;
    }
    
    // Форматируем номер в виде +7 (XXX) XXX-XX-XX
    return '+7 (' + phone.slice(1, 4) + ') ' + phone.slice(4, 7) + '-' + phone.slice(7, 9) + '-' + phone.slice(9, 11);
}

function normalizeCarBrand(brand) {
    // Простой пример нормализации марки автомобиля
    var brands = {
        "бмв": "BMW",
        "мерс": "Mercedes",
        "мерседес": "Mercedes",
        "ауди": "Audi",
        "фольксваген": "Volkswagen",
        "фольц": "Volkswagen",
        "тойота": "Toyota"
    };
    return brands[brand.toLowerCase()] || capitalize(brand);
}


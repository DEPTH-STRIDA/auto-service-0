function normalizePhoneNumber(phone) {
    // Удаляем все нецифровые символы
    phone = phone.replace(/\D/g, '');
    
    // Если номер начинается с 8, заменяем на +7
    if (phone.startsWith('8')) {
        phone = '7' + phone.slice(1);
    }
    
    // Если номер не начинается с +7 или 7, добавляем 7 в начало
    if (!phone.startsWith('7')) {
        phone = '7' + phone;
    }
    
    // Форматируем номер в виде +7 (XXX) XXX-XX-XX
    return '+7 (' + phone.slice(1, 4) + ') ' + phone.slice(4, 7) + '-' + phone.slice(7, 9) + '-' + phone.slice(9, 11);
}
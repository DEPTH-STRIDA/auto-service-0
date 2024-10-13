function CheckRequest(){
    var client = $jsapi.context().client;
    
    var counter = 0;
    
    if (client.name){
        counter++;
    }
    if (client.phone){
        counter++;
    }
    if (client.car){
        counter++;
    }
    
    if (counter>=2){
        return true
    }
    
    return false;
}

function GetReadyMessage(){
    var client = $jsapi.context().client;
    
    var msg = 'Оформляю заявку на техобслуживание на следующие данные:\n'
    
     if (!client.name){
        msg+=client.name+'\n'
    }
    if (!client.phone){
        msg+=client.phone+'\n'
    }
    if (!client.car){
        msg+=client.car+'\n'
    }
    
    msg+='Данные верны?'
    
    return msg
}


function GetServiceMessage(){
    var client = $jsapi.context().client;
    
    var msg='Для оформления заявки на ТО, пожалуйста, укажите:\n'
    
    if (!client.name){
        msg+='- ФИО\n'
    }
    if (!client.phone){
        msg+='- телефон\n'
    }
    if (!client.car){
        msg+='- марку автомобиля'
    }
    
    return msg
}
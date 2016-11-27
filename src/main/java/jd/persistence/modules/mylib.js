/**
 * Created by eduardom on 9/22/16.
 */
var fun1= function (name) {
    print('Hola desde javascript, '+name);
    return "Greetings from javascript  "+name;
}

var fun2 = function (object) {
    print("definicion de clase JS: "+object.prototype.toString().call(object));
}

var fun3 = function (data) {
    window.alert("hola");
}

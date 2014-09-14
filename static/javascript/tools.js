function apply(temp, obj){
       temp = temp.replace(/(\${[A-z][A-z0-9.]*[A-z]})/g,function(identify){return eval("obj."+identify.match(/[A-z][A-z0-9.]*[A-z]/)[0])});
       //temp.replace(/<\%/)
       return temp;
    }
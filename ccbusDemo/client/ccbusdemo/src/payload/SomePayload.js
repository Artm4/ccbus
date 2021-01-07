export class SomePayload
{
    
    /**
    * @returns {}
    */ 
    constructor_0()
    {
    }
    
    /**
    * @param {number} a
    * @param {String} name
    * @returns {}
    */ 
    constructor_1(a,name)
    {
        this.a=a;
        this.name=name;
    }
    
    /**
    * @returns {}
    */ 
    constructor()
    {
        if(arguments.length==0){this.constructor_0.apply(this,arguments)}
        if(arguments.length==2){this.constructor_1.apply(this,arguments)}
    }
    
    /**
    * @type {number}
    */ 
    a;
    
    /**
    * @type {String}
    */ 
    name;
}